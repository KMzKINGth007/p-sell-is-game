package rmu.project.p_sell_id_game.model;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequestModel {
    private Integer userDetailId;
    private Integer paymentId;
    private BigDecimal totalAmount;
    private String status;
    private String descOrder;
    private List<Item> items;

    public Integer getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getDescOrder() {
        return descOrder;
    }

    public void setDescOrder(String descOrder) {
        this.descOrder = descOrder;
    }

    public static class Item {
        private Integer productId;
        private Integer quantity;
        private BigDecimal price;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }

}
