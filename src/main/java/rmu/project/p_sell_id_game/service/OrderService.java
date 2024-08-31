package rmu.project.p_sell_id_game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.OrdersEntity;
import rmu.project.p_sell_id_game.entity.OrderImgEntity;
import rmu.project.p_sell_id_game.entity.OrderItemEntity;
import rmu.project.p_sell_id_game.entity.UserDetailEntity;
import rmu.project.p_sell_id_game.model.OrderImgResponseModel;
import rmu.project.p_sell_id_game.model.OrderRequestModel;
import rmu.project.p_sell_id_game.model.OrderResponseModel;
import rmu.project.p_sell_id_game.repository.OrderImgRepository;
import rmu.project.p_sell_id_game.repository.OrderItemRepository;
import rmu.project.p_sell_id_game.repository.OrderRepository;
import rmu.project.p_sell_id_game.repository.UserDetailRepository;
import rmu.project.p_sell_id_game.utils.Constants;
import rmu.project.p_sell_id_game.utils.ImgUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private OrderImgRepository orderImgRepository;

    @Transactional
    public Integer addOrder(OrderRequestModel request) {
        OrdersEntity orderEntity = new OrdersEntity();
        orderEntity.setUserDetailId(request.getUserDetailId());
        orderEntity.setPaymentId(request.getPaymentId());
        orderEntity.setTotalAmount(request.getTotalAmount());
        orderEntity.setOrderDate(new Date());
        orderEntity.setStatus(request.getStatus());
        orderEntity.setDescOrder(request.getDescOrder());

        orderEntity = orderRepository.save(orderEntity);
        Integer orderId = orderEntity.getOrderId();

        if (orderEntity != null) {
            for (OrderRequestModel.Item item : request.getItems()) {
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                orderItemEntity.setOrderId(orderId);
                orderItemEntity.setProductId(item.getProductId());
                orderItemEntity.setQuantity(item.getQuantity());
                orderItemEntity.setPrice(item.getPrice());
                orderItemEntity.setStatus("1");
                orderItemRepository.save(orderItemEntity);
            }
        }

        return orderId;
    }

    public OrderResponseModel getOrderDetails(Integer orderId) {
        OrdersEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity == null) {
            return null;
        }

        OrderResponseModel responseModel = new OrderResponseModel();
        responseModel.setOrderId(orderEntity.getOrderId());
        responseModel.setUserDetailId(orderEntity.getUserDetailId());
        responseModel.setPaymentId(orderEntity.getPaymentId());
        responseModel.setTotalAmount(orderEntity.getTotalAmount());
        responseModel.setOrderDate(orderEntity.getOrderDate());
        responseModel.setStatus(orderEntity.getStatus());
        responseModel.setDescOrder(orderEntity.getDescOrder());

        UserDetailEntity userDetailEntity = userDetailRepository.findById(orderEntity.getUserDetailId()).orElse(null);
        if (userDetailEntity != null) {
            OrderResponseModel.UserDetail userDetail = new OrderResponseModel.UserDetail();
            userDetail.setUserDetailId(userDetailEntity.getUserDetailId());
            userDetail.setUserId(userDetailEntity.getUserId());
            userDetail.setFirstName(userDetailEntity.getFirstName());
            userDetail.setLastName(userDetailEntity.getLastName());
            userDetail.setAge(userDetailEntity.getAge());
            userDetail.setPhone(userDetailEntity.getPhone());
            userDetail.setEmail(userDetailEntity.getEmail());
            userDetail.setLine(userDetailEntity.getLine());
            responseModel.setUserDetail(userDetail);
        }

        List<OrderItemEntity> orderItemEntities = orderItemRepository.findByOrderId(orderId);
        List<OrderResponseModel.OrderItem> orderItems = orderItemEntities.stream().map(orderItemEntity -> {
            OrderResponseModel.OrderItem item = new OrderResponseModel.OrderItem();
            item.setOrderItemId(orderItemEntity.getOrderItemId());
            item.setProductId(orderItemEntity.getProductId());
            item.setOrderId(orderItemEntity.getOrderId());
            item.setQuantity(orderItemEntity.getQuantity());
            item.setPrice(orderItemEntity.getPrice());
            item.setStatus(orderItemEntity.getStatus());
            return item;
        }).collect(Collectors.toList());
        responseModel.setItems(orderItems);

        return responseModel;
    }

    public List<OrderResponseModel> getAllOrders() {
        List<OrdersEntity> orderEntities = orderRepository.findAll();
        return orderEntities.stream().map(orderEntity -> {
            OrderResponseModel responseModel = new OrderResponseModel();
            responseModel.setOrderId(orderEntity.getOrderId());
            responseModel.setUserDetailId(orderEntity.getUserDetailId());
            responseModel.setPaymentId(orderEntity.getPaymentId());
            responseModel.setTotalAmount(orderEntity.getTotalAmount());
            responseModel.setOrderDate(orderEntity.getOrderDate());
            responseModel.setStatus(orderEntity.getStatus());
            responseModel.setDescOrder(orderEntity.getDescOrder());

            return responseModel;
        }).collect(Collectors.toList());
    }

    public List<OrderResponseModel> getOrdersByUserId(Integer userDetailId) {
        List<OrdersEntity> orderEntities = orderRepository.findByUserDetailId(userDetailId);
        return orderEntities.stream().map(orderEntity -> {
            OrderResponseModel responseModel = new OrderResponseModel();
            responseModel.setOrderId(orderEntity.getOrderId());
            responseModel.setUserDetailId(orderEntity.getUserDetailId());
            responseModel.setPaymentId(orderEntity.getPaymentId());
            responseModel.setTotalAmount(orderEntity.getTotalAmount());
            responseModel.setOrderDate(orderEntity.getOrderDate());
            responseModel.setStatus(orderEntity.getStatus());
            responseModel.setDescOrder(orderEntity.getDescOrder());

            UserDetailEntity userDetailEntity = userDetailRepository.findById(orderEntity.getUserDetailId())
                    .orElse(null);
            if (userDetailEntity != null) {
                OrderResponseModel.UserDetail userDetail = new OrderResponseModel.UserDetail();
                userDetail.setUserDetailId(userDetailEntity.getUserDetailId());
                userDetail.setUserId(userDetailEntity.getUserId());
                userDetail.setFirstName(userDetailEntity.getFirstName());
                userDetail.setLastName(userDetailEntity.getLastName());
                userDetail.setAge(userDetailEntity.getAge());
                userDetail.setPhone(userDetailEntity.getPhone());
                userDetail.setEmail(userDetailEntity.getEmail());
                userDetail.setLine(userDetailEntity.getLine());
                responseModel.setUserDetail(userDetail);
            }

            List<OrderItemEntity> orderItemEntities = orderItemRepository.findByOrderId(orderEntity.getOrderId());
            List<OrderResponseModel.OrderItem> orderItems = orderItemEntities.stream().map(orderItemEntity -> {
                OrderResponseModel.OrderItem item = new OrderResponseModel.OrderItem();
                item.setOrderItemId(orderItemEntity.getOrderItemId());
                item.setProductId(orderItemEntity.getProductId());
                item.setOrderId(orderItemEntity.getOrderId());
                item.setQuantity(orderItemEntity.getQuantity());
                item.setPrice(orderItemEntity.getPrice());
                item.setStatus(orderItemEntity.getStatus());
                return item;
            }).collect(Collectors.toList());
            responseModel.setItems(orderItems);

            return responseModel;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrder(Integer orderId) throws IOException {
        // Delete associated images
        removeImgAndDeleteFileImgByOrderId(orderId);

        // Delete order items
        List<OrderItemEntity> orderItems = orderItemRepository.findByOrderId(orderId);
        if (!orderItems.isEmpty()) {
            orderItemRepository.deleteAll(orderItems);
        }

        // Delete the order
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public Integer saveOrderImage(MultipartFile file, Integer orderId) throws IOException {
        Integer response = null;
        if (null != file && null != orderId) {
            OrderImgEntity orderImg = new OrderImgEntity();
            String preFixNameFile = ImgUtils.genaratePrefixFile();
            String genarateFileName = ImgUtils.genarateFileName() + ImgUtils.subStrFileName(file.getOriginalFilename());
            String fileName = ImgUtils.concatStr(preFixNameFile, genarateFileName);
            orderImg.setOrderImageName(fileName);
            orderImg.setOrderImagePath(ImgUtils.getPathInput());
            orderImg.setOrderId(orderId);
            orderImg.setOrderImageData(ImgUtils.compressImage(file.getBytes()));
            orderImg.setCreateAt(new Date());
            orderImg.setUpdateAt(new Date());

            orderImg = orderImgRepository.save(orderImg);

            response = orderImg.getOrderImageId();
        }
        return response;
    }

    @Transactional
    public void removeImgAndDeleteFileImgByOrderId(Integer orderId) throws IOException {
        List<OrderImgEntity> orderImgList = orderImgRepository.findByOrderId(orderId);
        orderImgRepository.deleteAll(orderImgList);
    }

    @Transactional
    public void deleteImgByFileName(String fileName) throws IOException {
        OrderImgEntity orderImgList = orderImgRepository.findByOrderImgName(fileName);
        orderImgRepository.delete(orderImgList);
    }

    public byte[] getImageByte(String fileName) throws IOException, DataFormatException {
        OrderImgEntity orderImg = orderImgRepository.findByOrderImgName(fileName);

        if (null != orderImg) {
            return ImgUtils.decompressImage(orderImg.getOrderImageData());
        }

        return null;
    }

    public List<OrderImgResponseModel> getOrderImgByOrderId(Integer orderId) {
        List<OrderImgResponseModel> response = null;

        List<OrderImgEntity> orderImgList = orderImgRepository.findByOrderId(orderId);

        if (null != orderImgList) {
            response = new ArrayList<>();
            for (OrderImgEntity orderImg : orderImgList) {
                OrderImgResponseModel objectResponse = new OrderImgResponseModel();
                objectResponse.setOrderImgId(orderImg.getOrderImageId());
                objectResponse.setOrderId(orderImg.getOrderId());
                objectResponse.setOrderImageName(orderImg.getOrderImageName());
                objectResponse.setOrderImagePath(orderImg.getOrderImagePath());
                objectResponse.setOrderImageData(orderImg.getOrderImageData());
                response.add(objectResponse);
            }
        }

        return response;
    }

    @Transactional
    public void deleteImgSever(String fileName) throws IOException {
        if (null != fileName) {
            ImgUtils.deleteFile(fileName, Constants.PATH_TYPE_OUTPUT);
        }

    }

    @Transactional
    public void updateOrder(OrderRequestModel request, Integer orderId) {
        OrdersEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity != null) {
            orderEntity.setUserDetailId(request.getUserDetailId());
            orderEntity.setPaymentId(request.getPaymentId());
            orderEntity.setTotalAmount(request.getTotalAmount());
            orderEntity.setOrderDate(new Date());
            orderEntity.setStatus(request.getStatus());
            orderEntity.setDescOrder(request.getDescOrder());

            orderRepository.save(orderEntity);

            List<OrderItemEntity> existingItems = orderItemRepository.findByOrderId(orderId);
            orderItemRepository.deleteAll(existingItems);

            for (OrderRequestModel.Item item : request.getItems()) {
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                orderItemEntity.setOrderId(orderId);
                orderItemEntity.setProductId(item.getProductId());
                orderItemEntity.setQuantity(item.getQuantity());
                orderItemEntity.setPrice(item.getPrice());
                orderItemEntity.setStatus("1");
                orderItemRepository.save(orderItemEntity);
            }
        }
    }
}
