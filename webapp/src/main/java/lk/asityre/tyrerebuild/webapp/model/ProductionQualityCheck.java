package lk.asityre.tyrerebuild.webapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "productionQualityCheck")
public class ProductionQualityCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_id")
    private Integer checkId;

    @Column(name = "tyre_id")
    private Integer tyreId;

    @Column(name = "checked_by")
    private Integer checkedBy;

    @Column(name = "check_date")
    private LocalDate checkDate;

    @Column(name = "quality_grade")
    private String qualityGrade;

    @Enumerated(EnumType.STRING)
    private Result result;

    private String remarks;


    public enum Result {
        Pending,
        Passed,
        Failed
    }


    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public Integer getTyreId() {
        return tyreId;
    }

    public void setTyreId(Integer tyreId) {
        this.tyreId = tyreId;
    }

    public Integer getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(Integer checkedBy) {
        this.checkedBy = checkedBy;
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    public String getQualityGrade() {
        return qualityGrade;
    }

    public void setQualityGrade(String qualityGrade) {
        this.qualityGrade = qualityGrade;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}