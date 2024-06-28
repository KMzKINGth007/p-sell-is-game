package rmu.project.p_sell_id_game.model;

import java.math.BigDecimal;

public class ProductRequestModel {

    private Integer productTypeId;
    private String productName;
    private String productDesc;
    private BigDecimal productPrice;
    private Integer productStock;

    
    public Integer getProductTypeId() {
        return productTypeId;
    }
    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    public Integer getProductStock() {
        return productStock;
    }
    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    
}
