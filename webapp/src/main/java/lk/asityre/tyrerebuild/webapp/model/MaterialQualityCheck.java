package lk.asityre.tyrerebuild.webapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "materialQualityCheck")
public class MaterialQualityCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_check_id")
    private Integer materialCheckId;

    @Column(name = "purchase_id", nullable = false)
    private Integer purchaseId;

    @Column(name = "checked_by", nullable = false)
    private Integer checkedBy;

    @Column(name = "check_date")
    private LocalDate checkDate;

    @Column(name = "quality_grade")
    private String qualityGrade;

    @Column(name = "result")
    private String result; // 'Pending', 'Passed', 'Failed'

    @Column(name = "remarks")
    private String remarks;

    // Getters and Setters
    public Integer getMaterialCheckId() { return materialCheckId; }
    public void setMaterialCheckId(Integer materialCheckId) { this.materialCheckId = materialCheckId; }
    public Integer getPurchaseId() { return purchaseId; }
    public void setPurchaseId(Integer purchaseId) { this.purchaseId = purchaseId; }
    public Integer getCheckedBy() { return checkedBy; }
    public void setCheckedBy(Integer checkedBy) { this.checkedBy = checkedBy; }
    public LocalDate getCheckDate() { return checkDate; }
    public void setCheckDate(LocalDate checkDate) { this.checkDate = checkDate; }
    public String getQualityGrade() { return qualityGrade; }
    public void setQualityGrade(String qualityGrade) { this.qualityGrade = qualityGrade; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}