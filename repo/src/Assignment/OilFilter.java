package Assignment;

/**
 * Step 3 invented subclass: OilFilter.
 * Represents an oil filter sold by the accessories shop.
 * Adds private instance variables: filterType and compatibleModel.
 */
public class OilFilter extends StockItem {

    private String filterType;      // e.g. Spin-on / Cartridge
    private String compatibleModel; // e.g. Ford Fiesta 1.0 EcoBoost

    /**
     * Constructs an OilFilter stock item.
     *
     * @param stockCode       fixed stock code
     * @param quantity        initial quantity in stock
     * @param price           price per unit without VAT
     * @param filterType      type of filter (e.g. Spin-on)
     * @param compatibleModel compatible car model (e.g. Ford Fiesta 1.0 EcoBoost)
     */
    public OilFilter(String stockCode, int quantity, double price,
                     String filterType, String compatibleModel) {
        super(stockCode, quantity, price);
        this.filterType      = (filterType      == null) ? "" : filterType.trim();
        this.compatibleModel = (compatibleModel == null) ? "" : compatibleModel.trim();
    }

    /** @return filter type */
    public String getFilterType() {
        return filterType;
    }

    /** @param filterType filter type to set */
    public void setFilterType(String filterType) {
        this.filterType = (filterType == null) ? "" : filterType.trim();
    }

    /** @return compatible car model */
    public String getCompatibleModel() {
        return compatibleModel;
    }

    /** @param compatibleModel compatible car model to set */
    public void setCompatibleModel(String compatibleModel) {
        this.compatibleModel = (compatibleModel == null) ? "" : compatibleModel.trim();
    }

    /**
     * Returns the stock name for this type.
     *
     * @return "Oil filter"
     */
    @Override
    public String getStockName() {
        return "Oil filter";
    }

    /**
     * Returns a description including filter type and compatible model.
     *
     * @return description string
     */
    @Override
    public String getStockDescription() {
        return filterType + " filter for " + compatibleModel;
    }

    /**
     * Returns formatted stock info including oil filter-specific fields.
     * Calls super.toString() then appends filter type and compatible model.
     *
     * @return formatted string representation of this OilFilter item
     */
    @Override
    public String toString() {
        return super.toString()
             + "\nFilter Type: "      + filterType
             + "\nCompatible Model: " + compatibleModel;
    }
}
