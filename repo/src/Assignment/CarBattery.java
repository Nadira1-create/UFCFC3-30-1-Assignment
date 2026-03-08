package Assignment;

/**
 * Step 3 invented subclass: CarBattery.
 * Represents a car battery sold by the accessories shop.
 * Adds private instance variables: voltage and capacityAh.
 */
public class CarBattery extends StockItem {

    private int voltage;     // e.g. 12
    private int capacityAh;  // e.g. 60

    /**
     * Constructs a CarBattery stock item.
     *
     * @param stockCode  fixed stock code
     * @param quantity   initial quantity in stock
     * @param price      price per unit without VAT
     * @param voltage    battery voltage in volts (e.g. 12)
     * @param capacityAh battery capacity in amp-hours (e.g. 60)
     */
    public CarBattery(String stockCode, int quantity, double price, int voltage, int capacityAh) {
        super(stockCode, quantity, price);
        this.voltage    = voltage;
        this.capacityAh = capacityAh;
    }

    /** @return voltage */
    public int getVoltage() {
        return voltage;
    }

    /** @param voltage voltage to set */
    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    /** @return capacity in amp-hours */
    public int getCapacityAh() {
        return capacityAh;
    }

    /** @param capacityAh capacity in amp-hours to set */
    public void setCapacityAh(int capacityAh) {
        this.capacityAh = capacityAh;
    }

    /**
     * Returns the stock name for this type.
     *
     * @return "Car battery"
     */
    @Override
    public String getStockName() {
        return "Car battery";
    }

    /**
     * Returns a description including voltage and capacity.
     *
     * @return description string
     */
    @Override
    public String getStockDescription() {
        return voltage + "V " + capacityAh + "Ah battery";
    }

    /**
     * Returns formatted stock info including battery-specific fields.
     * Calls super.toString() then appends voltage and capacity.
     *
     * @return formatted string representation of this CarBattery item
     */
    @Override
    public String toString() {
        return super.toString()
             + "\nVoltage: "  + voltage    + "V"
             + "\nCapacity: " + capacityAh + "Ah";
    }
}
