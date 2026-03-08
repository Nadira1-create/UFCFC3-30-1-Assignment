package Assignment;

/**
 * Step 2: NavSys subclass of StockItem.
 * Represents a GeoVision Sat Nav navigation system.
 *
 * Overrides:
 * - getStockName()        returns "Navigation system"
 * - getStockDescription() returns " GeoVision Sat Nav"
 * - toString()            using super
 */
public class NavSys extends StockItem {

    /**
     * Constructor for NavSys.
     * Calls the parent StockItem constructor using super to initialise instance variables.
     *
     * @param stockCode fixed stock code
     * @param quantity  initial quantity in stock
     * @param price     price per unit without VAT
     */
    public NavSys(String stockCode, int quantity, double price) {
        super(stockCode, quantity, price);
    }

    /**
     * Returns the stock name for this item.
     * Overrides the parent class method.
     *
     * @return "Navigation system"
     */
    @Override
    public String getStockName() {
        return "Navigation system";
    }

    /**
     * Returns the description of this item.
     * Overrides the parent class method.
     *
     * @return " GeoVision Sat Nav"
     */
    @Override
    public String getStockDescription() {
        return " GeoVision Sat Nav";
    }

    /**
     * Returns formatted stock item information
     * using the parent class toString() method via super.
     *
     * @return formatted string representation of this NavSys item
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
