package rmu.project.p_sell_id_game.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_image")
public class OrderImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_image_id")
    private Integer orderImageId;

    @Column(name = "orders_id")
    private Integer orderId;

    @Column(name = "order_image_path")
    private String orderImagePath;

    @Column(name = "order_image_name")
    private String orderImageName;

    @Lob
    @Column(name = "order_image_data")
    private byte[] orderImageData;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    public Integer getOrderImageId() {
        return orderImageId;
    }

    public void setOrderImageId(Integer orderImageId) {
        this.orderImageId = orderImageId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderImagePath() {
        return orderImagePath;
    }

    public void setOrderImagePath(String orderImagePath) {
        this.orderImagePath = orderImagePath;
    }

    public String getOrderImageName() {
        return orderImageName;
    }

    public void setOrderImageName(String orderImageName) {
        this.orderImageName = orderImageName;
    }

    public byte[] getOrderImageData() {
        return orderImageData;
    }

    public void setOrderImageData(byte[] orderImageData) {
        this.orderImageData = orderImageData;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
