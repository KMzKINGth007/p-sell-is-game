package rmu.project.p_sell_id_game.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_detail_id", insertable = false, updatable = false)
    private UserDetailEntity userDetail;

    @Column(name = "user_detail_id")
    private Integer userDetailId;

    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "status")
    private String status;

    // Getters and setters for all fields including userDetail
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
    }

    public UserDetailEntity getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailEntity userDetail) {
        this.userDetail = userDetail;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
