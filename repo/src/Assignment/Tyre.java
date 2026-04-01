package Assignment;

/**
 * Tyre - A subclass of {@link StockItem} representing a tyre stocked and
 * sold by the accessories shop (Step 3 invented subclass).
 *
 * <p>This class extends {@link StockItem} with two additional private instance
 * variables specific to tyres: {@code size} (the tyre's dimensional specification,
 * e.g. {@code "205/55R16"}) and {@code season} (the intended season of use,
 * e.g. {@code "Summer"}, {@code "Winter"}, or {@code "All-season"}). It overrides
 * the stock name, description, and string representation methods inherited from
 * {@link StockItem} to provide tyre-specific information.</p>
 *
 * <p>Both {@code size} and {@code season} are defensively validated on
 * assignment: {@code null} values are replaced with an empty string, and
 * leading or trailing whitespace is removed via {@link String#trim()},
 * ensuring clean, consistent data at all times.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *     Tyre tyre = new Tyre("T101", 15, 59.99, "205/55R16", "All-season");
 *     tyre.addStock(5);
 *     tyre.sellStock(3);
 *     tyre.setPrice(64.99);
 *     System.out.println(tyre);
 * </pre>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     StockItem
 */
public class Tyre extends StockItem {


    // -----------------------------------------------------------------------
    // Instance Variables
    // -----------------------------------------------------------------------

    /**
     * The dimensional size specification of this tyre
     * (e.g. {@code "205/55R16"}, where {@code 205} is the width in mm,
     * {@code 55} is the aspect ratio, and {@code R16} is the rim diameter).
     * Never {@code null}; defaults to an empty string if a {@code null}
     * value is supplied.
     */
    private String size;

    /**
     * The season for which this tyre is designed
     * (e.g. {@code "Summer"}, {@code "Winter"}, or {@code "All-season"}).
     * Never {@code null}; defaults to an empty string if a {@code null}
     * value is supplied.
     */
    private String season;


    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructs a new {@code Tyre} stock item with the specified attributes.
     *
     * <p>Delegates initialisation of the stock code, quantity, and price fields
     * to the {@link StockItem} superclass constructor via {@code super()}.
     * The tyre-specific fields {@code size} and {@code season} are then
     * initialised with defensive null-checks and whitespace trimming to
     * guarantee clean data throughout the object's lifetime.</p>
     *
     * @param stockCode the fixed, unique stock code for this item
     *                  (e.g. {@code "T101"}); must not be {@code null} or blank
     * @param quantity  the initial number of units held in stock;
     *                  must be in the range {@code 0} to
     *                  {@link StockItem#MAX_STOCK} inclusive
     * @param price     the price per unit excluding VAT (e.g. {@code 59.99});
     *                  must be &ge; 0
     * @param size      the tyre size specification
     *                  (e.g. {@code "205/55R16"});
     *                  {@code null} is treated as an empty string
     * @param season    the intended season of use
     *                  (e.g. {@code "All-season"});
     *                  {@code null} is treated as an empty string
     */
    public Tyre(String stockCode, int quantity, double price,
                String size, String season) {
        super(stockCode, quantity, price);
        this.size   = (size   == null) ? "" : size.trim();
        this.season = (season == null) ? "" : season.trim();
    }


    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the dimensional size specification of this tyre.
     *
     * @return the tyre size string (e.g. {@code "205/55R16"});
     *         never {@code null}
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets a new dimensional size specification for this tyre.
     *
     * <p>A {@code null} argument is silently replaced with an empty string,
     * and any surrounding whitespace is removed via {@link String#trim()}.</p>
     *
     * @param size the new tyre size to assign (e.g. {@code "225/45R17"});
     *             {@code null} is treated as an empty string
     */
    public void setSize(String size) {
        this.size = (size == null) ? "" : size.trim();
    }

    /**
     * Returns the season for which this tyre is designed.
     *
     * @return the season type string
     *         (e.g. {@code "Summer"}, {@code "Winter"}, or {@code "All-season"});
     *         never {@code null}
     */
    public String getSeason() {
        return season;
    }

    /**
     * Sets a new season type for this tyre.
     *
     * <p>A {@code null} argument is silently replaced with an empty string,
     * and any surrounding whitespace is removed via {@link String#trim()}.</p>
     *
     * @param season the new season type to assign
     *               (e.g. {@code "Winter"});
     *               {@code null} is treated as an empty string
     */
    public void setSeason(String season) {
        this.season = (season == null) ? "" : season.trim();
    }


    // -----------------------------------------------------------------------
    // Overridden Methods from StockItem
    // -----------------------------------------------------------------------

    /**
     * Returns the display name for this category of stock item.
     *
     * <p>Overrides {@link StockItem#getStockName()} to return a descriptive
     * name that correctly identifies this item as a tyre, rather than the
     * generic {@code "Unknown Stock Name"} provided by the superclass.</p>
     *
     * @return the string {@code "Tyre"}
     */
    @Override
    public String getStockName() {
        return "Tyre";
    }

    /**
     * Returns a human-readable description of this specific tyre,
     * incorporating its size specification and intended season of use.
     *
     * <p>Overrides {@link StockItem#getStockDescription()} to replace the
     * generic {@code "Unknown Stock Description"} with meaningful,
     * product-specific details drawn from the {@code size} and {@code season}
     * instance variables.</p>
     *
     * @return a formatted description string,
     *         e.g. {@code "Tyre 205/55R16 (All-season)"}
     */
    @Override
    public String getStockDescription() {
        return "Tyre " + size + " (" + season + ")";
    }

    /**
     * Returns a full, formatted string representation of this {@code Tyre}
     * item, including all fields from the superclass and the tyre-specific
     * size and season details.
     *
     * <p>Calls {@link StockItem#toString()} via {@code super.toString()} to
     * retrieve the standard stock information (stock code, name, description,
     * quantity, price before and after VAT), then appends the tyre-specific
     * {@code size} and {@code season} on new lines. This demonstrates the use
     * of {@code super} in method overriding to extend rather than replace
     * superclass behaviour.</p>
     *
     * <p>Example output:</p>
     * <pre>
     *     Stock Type: Tyre
     *     Description: Tyre 205/55R16 (All-season)
     *     Stock Code: T101
     *     Price Without VAT: 59.99
     *     Price With VAT: 70.4
     *     Total Units in Stock: 15
     *     Tyre Size: 205/55R16
     *     Season: All-season
     * </pre>
     *
     * @return a complete, formatted string representation of this item
     */
    @Override
    public String toString() {
        return super.toString()
             + "\nTyre Size: " + size
             + "\nSeason: "    + season;
    }
}
