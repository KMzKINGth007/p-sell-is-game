package rmu.project.p_sell_id_game.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rmu.project.p_sell_id_game.entity.OrderImgEntity;

@Repository
public interface OrderImgRepository extends JpaRepository<OrderImgEntity, Integer> {
    
    @Query("select t from OrderImgEntity t where t.orderId = ?1")
    List<OrderImgEntity> findByOrderId(Integer orderId);

    @Modifying(clearAutomatically = true)
    @Query("delete from OrderImgEntity t where t.orderId = ?1")
    public void deleteByOrderId(Integer orderId);

    @Query("select t from OrderImgEntity t where t.orderImageName = ?1")
    public OrderImgEntity findByOrderImgName(String orderImageName);
}
