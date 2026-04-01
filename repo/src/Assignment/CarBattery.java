package Assignment;

/**
 * CarBattery - A subclass of {@link StockItem} representing a car battery
 * stocked and sold by the accessories shop.
 *
 * <p>This class extends {@link StockItem} with two additional private instance
 * variables specific to car batteries: {@code voltage} (measured in volts) and
 * {@code capacityAh} (measured in amp-hours). It overrides the stock name,
 * description, and string representation methods inherited from
 * {@link StockItem} to provide battery-specific information.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *     CarBattery battery = new CarBattery("CB001", 15, 49.99, 12, 60);
 *     battery.addStock(5);
 *     battery.sellStock(2);
 *     System.out.println(battery);
 * </pre>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     StockItem
 */
public class CarBattery extends StockItem {

    // -----------------------------------------------------------------------
    // Instance Variables
    // -----------------------------------------------------------------------

    /** The voltage of this battery in volts (e.g. 12 for a standard car battery). */
    private int voltage;

    /** The capacity of this battery in amp-hours (e.g. 60 Ah). */
    private int capacityAh;


    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructs a new {@code CarBattery} stock item with the specified
     * attributes.
     *
     * <p>Calls the {@link StockItem} superclass constructor via {@code super()}
     * to initialise the stock code, quantity, and price fields. The
     * battery-specific fields {@code voltage} and {@code capacityAh} are then
     * initialised from the remaining parameters.</p>
     *
     * @param stockCode  the fixed, unique stock code for this item (e.g. "CB001")
     * @param quantity   the initial number of units held in stock; must be &ge; 1
     * @param price      the price per unit excluding VAT (e.g. 49.99)
     * @param voltage    the battery voltage in volts (e.g. 12)
     * @param capacityAh the battery capacity in amp-hours (e.g. 60)
     */
    public CarBattery(String stockCode, int quantity, double price,
                      int voltage, int capacityAh) {
        super(stockCode, quantity, price);
        this.voltage    = voltage;
        this.capacityAh = capacityAh;
    }


    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the voltage of this car battery.
     *
     * @return the voltage in volts (e.g. 12)
     */
    public int getVoltage() {
        return voltage;
    }

    /**
     * Sets a new voltage value for this car battery.
     *
     * @param voltage the new voltage in volts to assign (e.g. 12 or 24)
     */
    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    /**
     * Returns the capacity of this car battery in amp-hours.
     *
     * @return the capacity in amp-hours (e.g. 60)
     */
    public int getCapacityAh() {
        return capacityAh;
    }

    /**
     * Sets a new capacity value for this car battery.
     *
     * @param capacityAh the new capacity in amp-hours to assign (e.g. 60 or 75)
     */
    public void setCapacityAh(int capacityAh) {
        this.capacityAh = capacityAh;
    }


    // -----------------------------------------------------------------------
    // Overridden Methods from StockItem
    // -----------------------------------------------------------------------

    /**
     * Returns the display name for this category of stock item.
     *
     * <p>Overrides {@link StockItem#getStockName()} to return a descriptive
     * name that correctly identifies this item as a car battery rather than the
     * generic "Unknown Stock Name" provided by the superclass.</p>
     *
     * @return the string {@code "Car Battery"}
     */
    @Override
    public String getStockName() {
        return "Car Battery";
    }

    /**
     * Returns a human-readable description of this specific battery,
     * incorporating its voltage and amp-hour capacity.
     *
     * <p>Overrides {@link StockItem#getStockDescription()} to replace the
     * generic "Unknown Stock Description" with meaningful battery
     * specifications.</p>
     *
     * @return a formatted description string, e.g. {@code "12V 60Ah Battery"}
     */
    @Override
    public String getStockDescription() {
        return voltage + "V " + capacityAh + "Ah Battery";
    }

    /**
     * Returns a full, formatted string representation of this {@code CarBattery}
     * item, including all fields from the superclass and the battery-specific
     * voltage and capacity details.
     *
     * <p>Calls {@link StockItem#toString()} via {@code super.toString()} to
     * retrieve the standard stock information (stock code, name, description,
     * quantity, price before and after VAT), then appends the battery-specific
     * voltage and capacity on new lines.</p>
     *
     * <p>Example output:</p>
     * <pre>
     *     Stock Type: Car Battery
     *     Description: 12V 60Ah Battery
     *     Stock Code: CB001
     *     Price Without VAT: 49.99
     *     Price With VAT: 58.74
     *     Total Units in Stock: 15
     *     Voltage: 12V
     *     Capacity: 60Ah
     * </pre>
     *
     * @return a complete, formatted string representation of this item
     */
    @Override
    public String toString() {
        return super.toString()
             + "\nVoltage: "  + voltage    + "V"
             + "\nCapacity: " + capacityAh + "Ah";
    }
}
