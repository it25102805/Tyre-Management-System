package lk.asityre.tyrerebuild.webapp.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "stockMovement")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Integer movementId;

    @Column(name = "material_type_id", nullable = false)
    private Integer materialTypeId;

    @Column(name = "movement_type" , nullable = false)
    private String movementType;

    @Column(name = "quantity" , nullable = false)
    private Integer quantity;

    @Column(name = "movement_date")
    private LocalDate movementDate;

    @Column(name= "handled_by" , nullable = false)
    private Integer handledBy;

    public Integer getMovementId() {
        return movementId;
    }

    public void setMovementId(Integer movementId) {
        this.movementId = movementId;
    }

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDate movementDate) {
        this.movementDate = movementDate;
    }

    public Integer getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(Integer handledBy) {
        this.handledBy = handledBy;
    }
}