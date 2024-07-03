package rmu.project.p_sell_id_game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

import jakarta.transaction.Transactional;
import rmu.project.p_sell_id_game.entity.OrdersEntity;
import rmu.project.p_sell_id_game.entity.OrderItemEntity;
import rmu.project.p_sell_id_game.model.OrderRequestModel;
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

        orderEntity = orderRepository.save(orderEntity);
        Integer orderId = orderEntity.getOrderId();

        if(orderEntity != null) {
            for(OrderRequestModel.Item item : request.getItems()) {
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
}
