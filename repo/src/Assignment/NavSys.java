package Assignment;

/**
 * NavSys - A subclass of {@link StockItem} representing a GeoVision Sat Nav
 * navigation system stocked and sold by the accessories shop.
 *
 * <p>This class extends {@link StockItem} and overrides the stock name,
 * description, and string representation methods to provide navigation
 * system-specific information. As the navigation system shares all core
 * stock attributes with {@link StockItem} (stock code, quantity, and price),
 * no additional instance variables are required.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *     NavSys nav = new NavSys("NS101", 10, 99.99);
 *     nav.addStock(10);
 *     nav.sellStock(2);
 *     nav.setPrice(100.99);
 *     System.out.println(nav);
 * </pre>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     StockItem
 */
public class NavSys extends StockItem {


    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructs a new {@code NavSys} stock item with the specified attributes.
     *
     * <p>Delegates initialisation of the stock code, quantity, and price fields
     * to the {@link StockItem} superclass constructor via {@code super()}.
     * No additional fields require initialisation in this subclass.</p>
     *
     * @param stockCode the fixed, unique stock code for this item (e.g. "NS101")
     * @param quantity  the initial number of units held in stock; must be &ge; 1
     * @param price     the price per unit excluding VAT (e.g. 99.99)
     */
    public NavSys(String stockCode, int quantity, double price) {
        super(stockCode, quantity, price);
    }


    // -----------------------------------------------------------------------
    // Overridden Methods from StockItem
    // -----------------------------------------------------------------------

    /**
     * Returns the display name for this category of stock item.
     *
     * <p>Overrides {@link StockItem#getStockName()} to return a descriptive
     * name that correctly identifies this item as a navigation system, rather
     * than the generic {@code "Unknown Stock Name"} provided by the
     * superclass.</p>
     *
     * @return the string {@code "Navigation system"}
     */
    @Override
    public String getStockName() {
        return "Navigation system";
    }

    /**
     * Returns a human-readable description identifying the specific make and
     * model of this navigation system.
     *
     * <p>Overrides {@link StockItem#getStockDescription()} to replace the
     * generic {@code "Unknown Stock Description"} with the product-specific
     * brand name used by the shop.</p>
     *
     * @return the string {@code "GeoVision Sat Nav"}
     */
    @Override
    public String getStockDescription() {
        return " GeoVision Sat Nav";
    }

    /**
     * Returns a full, formatted string representation of this {@code NavSys}
     * item, including all fields provided by the superclass.
     *
     * <p>Delegates entirely to {@link StockItem#toString()} via
     * {@code super.toString()}, which uses the overridden
     * {@link #getStockName()} and {@link #getStockDescription()} methods
     * defined in this class to produce navigation system-specific output.
     * This demonstrates dynamic method binding and polymorphism.</p>
     *
     * <p>Example output:</p>
     * <pre>
     *     Stock Type: Navigation system
     *     Description: GeoVision Sat Nav
     *     Stock Code: NS101
     *     Price Without VAT: 99.99
     *     Price With VAT: 117.49
     *     Total Units in Stock: 10
     * </pre>
     *
     * @return a complete, formatted string representation of this item
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
