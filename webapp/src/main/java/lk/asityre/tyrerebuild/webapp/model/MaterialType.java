package lk.asityre.tyrerebuild.webapp.model;
import jakarta.persistence.*;

@Entity
@Table(name = "materialType")
public class MaterialType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_type_id")
    private Integer materialTypeId;

    @Column(name = "material_name" , nullable = false, unique = true)
    private String materialName;

    @Column(name = "unit" , nullable = false)
    private String unit;

    @Column(name ="reorder_level")
    private Integer reorderLevel;

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId){
        this.materialTypeId = materialTypeId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName){
        this.materialName = materialName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel){
        this.reorderLevel = reorderLevel;
    }
}


