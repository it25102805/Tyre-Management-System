package lk.asityre.tyrerebuild.webapp.model;

public class StockLevel {

    private Integer materialTypeId;
    private String materialName;
    private String unit;
    private int currentStock;
    private int reorderLevel;
    private boolean lowStock;

    public StockLevel(Integer materialTypeId, String materialName, String unit,
                      int currentStock, int reorderLevel, boolean lowStock) {
        this.materialTypeId = materialTypeId;
        this.materialName = materialName;
        this.unit = unit;
        this.currentStock = currentStock;
        this.reorderLevel = reorderLevel;
        this.lowStock = lowStock;
    }

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getUnit() {
        return unit;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public boolean isLowStock() {
        return lowStock;
    }
}
