package rmu.project.p_sell_id_game.model;

public class OrderImgResponseModel {
    private Integer orderImgId;
    private Integer orderId;
    private String orderImagePath;
    private String orderImageName;
    private byte[] orderImageData;

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

    public Integer getOrderImgId() {
        return orderImgId;
    }

    public void setOrderImgId(Integer orderImgId) {
        this.orderImgId = orderImgId;
    }
}
