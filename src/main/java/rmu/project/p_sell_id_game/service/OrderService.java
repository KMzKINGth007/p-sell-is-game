package rmu.project.p_sell_id_game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.OrdersEntity;
import rmu.project.p_sell_id_game.entity.OrderItemEntity;
import rmu.project.p_sell_id_game.entity.UserDetailEntity;
import rmu.project.p_sell_id_game.model.OrderRequestModel;
import rmu.project.p_sell_id_game.model.OrderResponseModel;
import rmu.project.p_sell_id_game.repository.OrderItemRepository;
import rmu.project.p_sell_id_game.repository.OrderRepository;
import rmu.project.p_sell_id_game.repository.UserDetailRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Transactional
    public Integer addOrder(OrderRequestModel request) {
        OrdersEntity orderEntity = new OrdersEntity();
        orderEntity.setUserDetailId(request.getUserDetailId());
        orderEntity.setPaymentId(request.getPaymentId());
        orderEntity.setTotalAmount(request.getTotalAmount());
        orderEntity.setOrderDate(new Date());
        orderEntity.setStatus(request.getStatus());

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
    public void deleteOrder(Integer orderId) {

        List<OrderItemEntity> orderItems = orderItemRepository.findByOrderId(orderId);
        if (!orderItems.isEmpty()) {
            orderItemRepository.deleteAll(orderItems);
        }
        
        orderRepository.deleteById(orderId);
    }
}
