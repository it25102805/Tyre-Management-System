package lk.asityre.tyrerebuild.webapp.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "materialPurchase")
public class MaterialPurchase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "material_type_id", nullable = false)
    private Integer materialTypeId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "status")
    private String status;

    @Column(name = "handled_by" , nullable = false)
    private Integer handledBy;

    public Integer getPurchaseId(){
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseID) {
        this.purchaseId = purchaseID;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMaterialTypeId(){
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId){
        this.materialTypeId = materialTypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(Integer handledBy) {
        this.handledBy = handledBy;
    }
}