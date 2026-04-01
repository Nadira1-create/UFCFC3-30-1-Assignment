package Assignment;

/**
 * OilFilter - A subclass of {@link StockItem} representing an oil filter
 * stocked and sold by the accessories shop.
 *
 * <p>This class extends {@link StockItem} with two additional private instance
 * variables specific to oil filters: {@code filterType} (e.g. spin-on or
 * cartridge) and {@code compatibleModel} (the car model the filter is designed
 * for). It overrides the stock name, description, and string representation
 * methods inherited from {@link StockItem} to provide oil filter-specific
 * information.</p>
 *
 * <p>Both {@code filterType} and {@code compatibleModel} are defensively
 * validated on assignment: {@code null} values are replaced with an empty
 * string, and leading or trailing whitespace is removed via
 * {@link String#trim()}, ensuring clean, consistent data at all times.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *     OilFilter filter = new OilFilter("OF001", 20, 12.99,
 *                                      "Spin-on", "Ford Fiesta 1.0 EcoBoost");
 *     filter.addStock(5);
 *     filter.sellStock(3);
 *     filter.setPrice(13.99);
 *     System.out.println(filter);
 * </pre>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     StockItem
 */
public class OilFilter extends StockItem {


    // -----------------------------------------------------------------------
    // Instance Variables
    // -----------------------------------------------------------------------

    /**
     * The type of oil filter mechanism (e.g. {@code "Spin-on"} or
     * {@code "Cartridge"}). Never {@code null}; defaults to an empty string
     * if a {@code null} value is supplied.
     */
    private String filterType;

    /**
     * The specific car model this filter is compatible with
     * (e.g. {@code "Ford Fiesta 1.0 EcoBoost"}). Never {@code null};
     * defaults to an empty string if a {@code null} value is supplied.
     */
    private String compatibleModel;


    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructs a new {@code OilFilter} stock item with the specified
     * attributes.
     *
     * <p>Delegates initialisation of the stock code, quantity, and price fields
     * to the {@link StockItem} superclass constructor via {@code super()}.
     * The oil filter-specific fields {@code filterType} and
     * {@code compatibleModel} are then initialised with defensive null-checks
     * and whitespace trimming to guarantee clean data.</p>
     *
     * @param stockCode       the fixed, unique stock code for this item
     *                        (e.g. {@code "OF001"})
     * @param quantity        the initial number of units held in stock;
     *                        must be &ge; 1
     * @param price           the price per unit excluding VAT (e.g. {@code 12.99})
     * @param filterType      the type of filter mechanism (e.g. {@code "Spin-on"});
     *                        {@code null} is treated as an empty string
     * @param compatibleModel the car model this filter fits
     *                        (e.g. {@code "Ford Fiesta 1.0 EcoBoost"});
     *                        {@code null} is treated as an empty string
     */
    public OilFilter(String stockCode, int quantity, double price,
                     String filterType, String compatibleModel) {
        super(stockCode, quantity, price);
        this.filterType      = (filterType      == null) ? "" : filterType.trim();
        this.compatibleModel = (compatibleModel == null) ? "" : compatibleModel.trim();
    }


    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the filter mechanism type for this oil filter.
     *
     * @return the filter type string (e.g. {@code "Spin-on"} or
     *         {@code "Cartridge"}); never {@code null}
     */
    public String getFilterType() {
        return filterType;
    }

    /**
     * Sets a new filter mechanism type for this oil filter.
     *
     * <p>A {@code null} argument is silently replaced with an empty string,
     * and any surrounding whitespace is removed via {@link String#trim()}.</p>
     *
     * @param filterType the new filter type to assign (e.g. {@code "Cartridge"});
     *                   {@code null} is treated as an empty string
     */
    public void setFilterType(String filterType) {
        this.filterType = (filterType == null) ? "" : filterType.trim();
    }

    /**
     * Returns the car model that this oil filter is compatible with.
     *
     * @return the compatible car model string
     *         (e.g. {@code "Ford Fiesta 1.0 EcoBoost"}); never {@code null}
     */
    public String getCompatibleModel() {
        return compatibleModel;
    }

    /**
     * Sets a new compatible car model for this oil filter.
     *
     * <p>A {@code null} argument is silently replaced with an empty string,
     * and any surrounding whitespace is removed via {@link String#trim()}.</p>
     *
     * @param compatibleModel the new compatible car model to assign
     *                        (e.g. {@code "Ford Fiesta 1.0 EcoBoost"});
     *                        {@code null} is treated as an empty string
     */
    public void setCompatibleModel(String compatibleModel) {
        this.compatibleModel = (compatibleModel == null) ? "" : compatibleModel.trim();
    }


    // -----------------------------------------------------------------------
    // Overridden Methods from StockItem
    // -----------------------------------------------------------------------

    /**
     * Returns the display name for this category of stock item.
     *
     * <p>Overrides {@link StockItem#getStockName()} to return a descriptive
     * name that correctly identifies this item as an oil filter, rather than
     * the generic {@code "Unknown Stock Name"} provided by the superclass.</p>
     *
     * @return the string {@code "Oil Filter"}
     */
    @Override
    public String getStockName() {
        return "Oil Filter";
    }

    /**
     * Returns a human-readable description of this specific oil filter,
     * incorporating its filter mechanism type and compatible car model.
     *
     * <p>Overrides {@link StockItem#getStockDescription()} to replace the
     * generic {@code "Unknown Stock Description"} with meaningful,
     * product-specific details drawn from the instance variables.</p>
     *
     * @return a formatted description string,
     *         e.g. {@code "Spin-on Filter for Ford Fiesta 1.0 EcoBoost"}
     */
    @Override
    public String getStockDescription() {
        return filterType + " filter for " + compatibleModel;
    }

    /**
     * Returns a full, formatted string representation of this {@code OilFilter}
     * item, including all fields from the superclass and the filter-specific
     * type and compatible model details.
     *
     * <p>Calls {@link StockItem#toString()} via {@code super.toString()} to
     * retrieve the standard stock information (stock code, name, description,
     * quantity, price before and after VAT), then appends the oil
     * filter-specific {@code filterType} and {@code compatibleModel} on new
     * lines. This demonstrates the use of {@code super} in method overriding
     * to extend rather than replace superclass behaviour.</p>
     *
     * <p>Example output:</p>
     * <pre>
     *     Stock Type: Oil Filter
     *     Description: Spin-on filter for Ford Fiesta 1.0 EcoBoost
     *     Stock Code: OF001
     *     Price Without VAT: 12.99
     *     Price With VAT: 15.26
     *     Total Units in Stock: 20
     *     Filter Type: Spin-on
     *     Compatible Model: Ford Fiesta 1.0 EcoBoost
     * </pre>
     *
     * @return a complete, formatted string representation of this item
     */
    @Override
    public String toString() {
        return super.toString()
             + "\nFilter Type: "      + filterType
             + "\nCompatible Model: " + compatibleModel;
    }
}
