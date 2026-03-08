package Assignment;

/**
 * Step 1: StockItem
 * Represents one stock item sold by the accessories shop.
 *
 * Specification requirements:
 * - fixed stock code (String)
 * - variable quantity in stock (int)
 * - variable price without VAT (double)
 * - getStockName():        returns "Unknown Stock Name"
 * - getStockDescription(): returns "Unknown Stock Description"
 * - addStock():  if amount < 1 OR stock exceeds 100 -> print suitable error message
 * - sellStock(): if amount < 1 -> print suitable error message; return true/false as specified
 * - getVAT():    returns standard VAT percentage (17.5)
 * - getters for price with and without VAT; setter for price without VAT
 * - toString():  returns stock code, name, description, quantity, price before VAT, price after VAT
 *                and MUST use the appropriate methods above to obtain them.
 */
public class StockItem {

    /** Maximum stock allowed (brief: stock cannot exceed 100). */
    public static final int MAX_STOCK = 100;

    /** Standard VAT percentage (brief example: 17.5). */
    public static final double STANDARD_VAT_PERCENT = 17.5;

    private final String stockCode;   // fixed
    private int quantityInStock;      // variable
    private double priceWithoutVAT;   // variable

    /**
     * Constructs a StockItem.
     *
     * @param stockCode  fixed stock code
     * @param quantity   initial quantity in stock
     * @param price      price per unit without VAT
     * @throws IllegalArgumentException if stockCode is null/blank, quantity is out of range,
     *                                  or price is negative
     */
    public StockItem(String stockCode, int quantity, double price) {
        if (stockCode == null || stockCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock code must not be null/blank.");
        }
        if (quantity < 0 || quantity > MAX_STOCK) {
            throw new IllegalArgumentException(
                    "Quantity must be between 0 and " + MAX_STOCK + ".");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be greater than or equal to 0.");
        }

        this.stockCode       = stockCode.trim();
        this.quantityInStock = quantity;
        this.priceWithoutVAT = price;
    }

    /** @return fixed stock code */
    public String getStockCode() {
        return stockCode;
    }

    /** @return current quantity in stock */
    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Sets quantity directly (allowed as an "appropriate setter").
     * Keeps it safe within 0..MAX_STOCK.
     */
    public void setQuantityInStock(int quantityInStock) {
        if (quantityInStock < 0 || quantityInStock > MAX_STOCK) {
            System.out.println("The error was: Stock cannot exceed " + MAX_STOCK);
            return;
        }
        this.quantityInStock = quantityInStock;
    }

    /** @return price per unit without VAT */
    public double getPriceWithoutVAT() {
        return priceWithoutVAT;
    }

    /**
     * Sets the price per unit without VAT.
     * If negative, prints a suitable error message and does not change the price.
     *
     * @param priceWithoutVAT new price without VAT
     */
    public void setPriceWithoutVAT(double priceWithoutVAT) {
        if (priceWithoutVAT < 0) {
            System.out.println("The error was: Price must be greater than or equal to 0");
            return;
        }
        this.priceWithoutVAT = priceWithoutVAT;
    }

    /**
     * Returns the default stock name.
     * Overridden by subclasses to provide a specific name.
     *
     * @return "Unknown Stock Name"
     */
    public String getStockName() {
        return "Unknown Stock Name";
    }

    /**
     * Returns the default stock description.
     * Overridden by subclasses to provide a specific description.
     *
     * @return "Unknown Stock Description"
     */
    public String getStockDescription() {
        return "Unknown Stock Description";
    }

    /**
     * Increases stock by the given amount.
     * If amount < 1 OR stock would exceed MAX_STOCK, prints a suitable error message.
     *
     * @param amount units to add
     * @return true if stock was added successfully, false otherwise
     */
    public boolean addStock(int amount) {
        if (amount < 1) {
            System.out.println("The error was: Increased item must be greater than or equal to one");
            return false;
        }
        if (quantityInStock + amount > MAX_STOCK) {
            System.out.println("The error was: Stock cannot exceed " + MAX_STOCK);
            return false;
        }
        quantityInStock += amount;
        return true;
    }

    /**
     * Attempts to sell stock.
     * - If amount < 1  => prints error and returns false
     * - If amount <= stock => reduces stock and returns true
     * - Else => no change and returns false
     *
     * @param amount units to sell
     * @return true if successful, false otherwise
     */
    public boolean sellStock(int amount) {
        if (amount < 1) {
            System.out.println("The error was: Sell amount must be greater than or equal to one");
            return false;
        }
        if (amount <= quantityInStock) {
            quantityInStock -= amount;
            return true;
        }
        return false;
    }

    /**
     * Returns the standard VAT percentage rate.
     *
     * @return STANDARD_VAT_PERCENT (17.5)
     */
    public double getVAT() {
        return STANDARD_VAT_PERCENT;
    }

    /**
     * Returns the price including VAT.
     *
     * @return price with VAT applied
     */
    public double getPriceWithVAT() {
        return priceWithoutVAT * (1 + STANDARD_VAT_PERCENT / 100.0);
    }

    /**
     * Returns a formatted string with full stock item details.
     * Uses getStockName(), getStockDescription(), getQuantityInStock(),
     * getPriceWithoutVAT(), and getPriceWithVAT() as required by the brief.
     *
     * @return formatted string representation of the stock item
     */
    @Override
    public String toString() {
        return "Stock Type: "          + getStockName()        + "\n"
             + "Description: "         + getStockDescription() + "\n"
             + "Stock Code: "          + stockCode             + "\n"
             + "Price Without VAT: "   + getPriceWithoutVAT()  + "\n"
             + "Price With VAT: "      + getPriceWithVAT()     + "\n"
             + "Total unit in stock: " + getQuantityInStock();
    }
}
