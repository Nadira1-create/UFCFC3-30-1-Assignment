package Assignment;

/**
 * Step 3 invented subclass: Tyre.
 * Represents a tyre sold by the accessories shop.
 * Adds private instance variables: size and season.
 */
public class Tyre extends StockItem {

    private String size;    // e.g. 205/55R16
    private String season;  // e.g. Summer / Winter / All-season

    /**
     * Constructs a Tyre stock item.
     *
     * @param stockCode fixed stock code
     * @param quantity  initial quantity in stock
     * @param price     price per unit without VAT
     * @param size      tyre size (e.g. 205/55R16)
     * @param season    season type (e.g. All-season)
     */
    public Tyre(String stockCode, int quantity, double price, String size, String season) {
        super(stockCode, quantity, price);
        this.size   = (size   == null) ? "" : size.trim();
        this.season = (season == null) ? "" : season.trim();
    }

    /** @return tyre size */
    public String getSize() {
        return size;
    }

    /** @param size tyre size to set */
    public void setSize(String size) {
        this.size = (size == null) ? "" : size.trim();
    }

    /** @return season type */
    public String getSeason() {
        return season;
    }

    /** @param season season type to set */
    public void setSeason(String season) {
        this.season = (season == null) ? "" : season.trim();
    }

    /**
     * Returns the stock name for this type.
     *
     * @return "Tyre"
     */
    @Override
    public String getStockName() {
        return "Tyre";
    }

    /**
     * Returns a description including size and season.
     *
     * @return description string
     */
    @Override
    public String getStockDescription() {
        return "Tyre " + size + " (" + season + ")";
    }

    /**
     * Returns formatted stock info including tyre-specific fields.
     * Calls super.toString() then appends size and season.
     *
     * @return formatted string representation of this Tyre item
     */
    @Override
    public String toString() {
        return super.toString()
             + "\nTyre Size: " + size
             + "\nSeason: "    + season;
    }
}
