package rmu.project.p_sell_id_game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.OrdersEntity;
import rmu.project.p_sell_id_game.entity.OrderItemEntity;
import rmu.project.p_sell_id_game.model.OrderRequestModel;
import rmu.project.p_sell_id_game.model.OrderResponseModel;
import rmu.project.p_sell_id_game.repository.OrderItemRepository;
import rmu.project.p_sell_id_game.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

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

        List<OrderItemEntity> orderItemEntities = orderItemRepository.findByOrderId(orderId);

        OrderResponseModel response = new OrderResponseModel();
        response.setOrderId(orderEntity.getOrderId());
        response.setUserDetailId(orderEntity.getUserDetailId());
        response.setPaymentId(orderEntity.getPaymentId());
        response.setTotalAmount(orderEntity.getTotalAmount());
        response.setOrderDate(orderEntity.getOrderDate());
        response.setStatus(orderEntity.getStatus());

        List<OrderResponseModel.OrderItem> items = orderItemEntities.stream().map(item -> {
            OrderResponseModel.OrderItem orderItem = new OrderResponseModel.OrderItem();
            orderItem.setOrderItemId(item.getOrderItemId());
            orderItem.setProductId(item.getProductId());
            orderItem.setOrderId(item.getOrderId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setStatus(item.getStatus());
            return orderItem;
        }).collect(Collectors.toList());

        response.setItems(items);

        return response;
    }

    public List<OrderResponseModel> getAllOrders() {
        List<OrdersEntity> orderEntities = orderRepository.findAll();
        return orderEntities.stream().map(orderEntity -> {
            List<OrderItemEntity> orderItemEntities = orderItemRepository.findByOrderId(orderEntity.getOrderId());

            OrderResponseModel response = new OrderResponseModel();
            response.setOrderId(orderEntity.getOrderId());
            response.setUserDetailId(orderEntity.getUserDetailId());
            response.setPaymentId(orderEntity.getPaymentId());
            response.setTotalAmount(orderEntity.getTotalAmount());
            response.setOrderDate(orderEntity.getOrderDate());
            response.setStatus(orderEntity.getStatus());

            List<OrderResponseModel.OrderItem> items = orderItemEntities.stream().map(item -> {
                OrderResponseModel.OrderItem orderItem = new OrderResponseModel.OrderItem();
                orderItem.setOrderItemId(item.getOrderItemId());
                orderItem.setProductId(item.getProductId());
                orderItem.setOrderId(item.getOrderId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getPrice());
                orderItem.setStatus(item.getStatus());
                return orderItem;
            }).collect(Collectors.toList());

            response.setItems(items);

            return response;
        }).collect(Collectors.toList());
    }
}
