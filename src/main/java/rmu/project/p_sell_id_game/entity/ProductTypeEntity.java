package rmu.project.p_sell_id_game.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_type")
public class ProductTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "product_type_id")
    private Integer productTypeId;

    @Column(name = "product_type_name")
    private String productTypeName;

    @Column(name = "product_type_desc")
    private String productTypeDesc;

    @Column(name = "product_type_status")
    private String productTypeStatus;

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeDesc() {
        return productTypeDesc;
    }

    public void setProductTypeDesc(String ProductTypeDesc) {
        this.productTypeDesc = ProductTypeDesc;
    }
    
    public String getProductTypeStatus() {
        return productTypeStatus;
    }

    public void setProductTypeStatus(String productTypeStatus) {
        this.productTypeStatus = productTypeStatus;
    }

}
