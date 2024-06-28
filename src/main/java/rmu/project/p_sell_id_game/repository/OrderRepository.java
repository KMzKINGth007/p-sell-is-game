package rmu.project.p_sell_id_game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rmu.project.p_sell_id_game.entity.OrdersEntity;


@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Integer> {

    
}


