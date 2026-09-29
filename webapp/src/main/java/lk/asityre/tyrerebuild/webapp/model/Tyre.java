package lk.asityre.tyrerebuild.webapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tyre")
public class Tyre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tyre_id")
    private Integer tyreId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "tyre_number", nullable = false, unique = true)
    private String tyreNumber;

    private String brand;

    @Column(name = "tyre_size")
    private String tyreSize;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "expected_date")
    private LocalDate expectedDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "rebuild_stage")
    private RebuildStage rebuildStage;

    @Column(name = "assigned_to")
    private Integer assignedTo;


    public enum RebuildStage {
        RECEIVED,
        PREPARATION,
        BUILDING,
        CURING,
        COMPLETED
    }


    public Integer getTyreId() {
        return tyreId;
    }

    public void setTyreId(Integer tyreId) {
        this.tyreId = tyreId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTyreNumber() {
        return tyreNumber;
    }

    public void setTyreNumber(String tyreNumber) {
        this.tyreNumber = tyreNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTyreSize() {
        return tyreSize;
    }

    public void setTyreSize(String tyreSize) {
        this.tyreSize = tyreSize;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public RebuildStage getRebuildStage() {
        return rebuildStage;
    }

    public void setRebuildStage(RebuildStage rebuildStage) {
        this.rebuildStage = rebuildStage;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }
}