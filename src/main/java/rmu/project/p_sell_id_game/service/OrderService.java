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

import java.time.LocalDate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public Integer addOrder(OrderRequestModel request) {
        Integer response = null;

        OrdersEntity orderEntity = new OrdersEntity();
        orderEntity.setUserDetailId(request.getUserDetailId());
        orderEntity.setTotalAmount(request.getTotalAmount());
        orderEntity.setOrderDate(new Date());

        orderEntity = orderRepository.save(orderEntity);
        
        response = orderEntity.getOrderId();

        if(null != orderEntity) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderId(orderEntity.getOrderId());
            orderItemEntity.setProductId(request.getProductId());
            orderItemEntity.setQuantity(request.getQuantity());
            orderItemEntity.setPrice(request.getPrice());
            orderItemEntity.setStatus("1");

            orderItemRepository.save(orderItemEntity);
        }

        return response;
    }
}
