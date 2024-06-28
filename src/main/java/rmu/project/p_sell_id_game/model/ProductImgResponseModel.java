package rmu.project.p_sell_id_game.model;

public class ProductImgResponseModel {
    private Integer productImgId;
    private Integer productId;
    private String productImagePath;
    private String productImageName;
    private byte[] productImageData;

    

    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getProductImagePath() {
        return productImagePath;
    }
    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }
    public String getProductImageName() {
        return productImageName;
    }
    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }
    public byte[] getProductImageData() {
        return productImageData;
    }
    public void setProductImageData(byte[] productImageData) {
        this.productImageData = productImageData;
    }
    public Integer getProductImgId() {
        return productImgId;
    }
    public void setProductImgId(Integer productImgId) {
        this.productImgId = productImgId;
    }


    
}
