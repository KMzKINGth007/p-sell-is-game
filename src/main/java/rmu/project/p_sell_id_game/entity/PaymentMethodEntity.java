package rmu.project.p_sell_id_game.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_method")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Integer paymentMethodId;

    @Column(name = "name_payment_method")
    private String namePaymentMethod;

    @Column(name = "desc_payment_method")
    private String descPaymentMethod;

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {    
        this.paymentMethodId = paymentMethodId;
    }

    public String getNamePaymentMethod() {
        return namePaymentMethod;
    }  

    public void setNamePaymentMethod(String namePaymentMethod) {
        this.namePaymentMethod = namePaymentMethod;
    }

    public String getDescPaymentMethod() {
        return descPaymentMethod;
    }

    public void setDescPaymentMethod(String descPaymentMethod) {
        this.descPaymentMethod = descPaymentMethod;
    }
}
