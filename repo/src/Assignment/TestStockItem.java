package Assignment;

/**
 * TestStockItem - A test class for the base {@link StockItem} class (Step 1).
 *
 * <p>This class systematically exercises all core operations and error-handling
 * paths of the {@link StockItem} class as specified by the project brief,
 * verifying both the happy-path behaviour (successful operations) and the
 * boundary/error-path behaviour (invalid inputs that must trigger error
 * messages).</p>
 *
 * <p>Each task below corresponds directly to a sample run described in the
 * Step 1 section of the assessment brief. The four error-path tests at the
 * end verify that {@link StockItem} correctly rejects invalid inputs and
 * prints suitable error messages in every case.</p>
 *
 * <h2>Happy-path tasks performed:</h2>
 * <ol>
 *   <li><b>Task 1</b> — Create a {@link StockItem} with stock code
 *       {@code "W101"}, quantity {@code 10}, and price {@code £99.99};
 *       print initial item details.
 *       <br>Expected: {@code Total unit in stock: 10},
 *       {@code Price With VAT: 117.48825}</li>
 *   <li><b>Task 2</b> — Add {@code 10} more units to stock; print updated
 *       details.
 *       <br>Expected: {@code Total unit in stock: 20}</li>
 *   <li><b>Task 3</b> — Sell {@code 2} units; print updated details.
 *       <br>Expected: {@code Total unit in stock: 18}</li>
 *   <li><b>Task 4</b> — Change the price to {@code £100.99} per unit
 *       (excluding VAT); print updated details.
 *       <br>Expected: {@code Price Without VAT: 100.99}</li>
 * </ol>
 *
 * <h2>Error-path tests performed:</h2>
 * <ul>
 *   <li><b>Add zero units</b> — {@code addStock(0)} must print:
 *       {@code "The error was: Increased item must be greater than or equal to one"}</li>
 *   <li><b>Sell zero units</b> — {@code sellStock(0)} must print:
 *       {@code "The error was: Sell amount must be greater than or equal to one"}</li>
 *   <li><b>Negative price</b> — {@code setPriceWithoutVAT(-5.00)} must print:
 *       {@code "The error was: Price must be greater than or equal to 0"}</li>
 *   <li><b>Exceed MAX_STOCK</b> — {@code addStock(100)} when stock is already
 *       at {@code 18} would push the total to {@code 118}, exceeding
 *       {@link StockItem#MAX_STOCK}; must print:
 *       {@code "The error was: Stock cannot exceed 100"}</li>
 * </ul>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     StockItem
 */
public class TestStockItem {

    /**
     * Entry point for the {@link StockItem} test sequence.
     *
     * <p>Executes all four happy-path tasks followed by all four error-path
     * tests in order, printing stock item details and error messages to the
     * console. Output should be compared against the expected values documented
     * in the Step 1 sample run of the assessment brief to verify correctness.</p>
     *
     * <p>No assertions framework is used; correctness is verified by inspecting
     * the console output against the expected values. All error messages are
     * produced internally by {@link StockItem} — this test class simply
     * triggers the conditions that cause them.</p>
     *
     * @param args command-line arguments; not used by this test class
     */
    public static void main(String[] args) {

        // --------------------------------------------------------------------
        // Task 1 — Create a StockItem: code "W101", qty 10, price £99.99
        // Expected output: Stock Type: Unknown Stock Name
        //                  Description: Unknown Stock Description
        //                  Stock Code: W101
        //                  Price Without VAT: 99.99
        //                  Price With VAT: 117.48825
        //                  Total unit in stock: 10
        // --------------------------------------------------------------------
        StockItem item = new StockItem("W101", 10, 99.99);
        System.out.println("Printing stock item information");
        System.out.println(item);

        // --------------------------------------------------------------------
        // Task 2 — Add 10 more units to stock
        // Expected output: Total unit in stock: 20
        // --------------------------------------------------------------------
        item.addStock(10);
        System.out.println("\nPrinting stock item information");
        System.out.println(item);

        // --------------------------------------------------------------------
        // Task 3 — Sell 2 units from stock
        // Expected output: Total unit in stock: 18
        // --------------------------------------------------------------------
        item.sellStock(2);
        System.out.println("\nPrinting stock item information");
        System.out.println(item);

        // --------------------------------------------------------------------
        // Task 4 — Change price to £100.99 per unit (excluding VAT)
        // Expected output: Price Without VAT: 100.99
        //                  Price With VAT: 118.66324999999999
        // Note: stock quantity remains unchanged at 18
        // --------------------------------------------------------------------
        item.setPriceWithoutVAT(100.99);
        System.out.println("\nPrinting stock item information");
        System.out.println(item);

        // --------------------------------------------------------------------
        // Error path 1 — Attempt to add 0 units (amount < 1, invalid)
        // Expected console output:
        //   "The error was: Increased item must be greater than or equal to one"
        // Stock level must remain unchanged at 18
        // --------------------------------------------------------------------
        item.addStock(0);

        // --------------------------------------------------------------------
        // Error path 2 — Attempt to sell 0 units (amount < 1, invalid)
        // Expected console output:
        //   "The error was: Sell amount must be greater than or equal to one"
        // Stock level must remain unchanged at 18
        // --------------------------------------------------------------------
        item.sellStock(0);

        // --------------------------------------------------------------------
        // Error path 3 — Attempt to set a negative price (invalid)
        // Expected console output:
        //   "The error was: Price must be greater than or equal to 0"
        // Price must remain unchanged at £100.99
        // --------------------------------------------------------------------
        item.setPriceWithoutVAT(-5.00);

        // --------------------------------------------------------------------
        // Error path 4 — Attempt to add 100 units when stock is already at 18
        // Total would be 118, which exceeds MAX_STOCK (100) — invalid
        // Expected console output:
        //   "The error was: Stock cannot exceed 100"
        // Stock level must remain unchanged at 18
        // --------------------------------------------------------------------
        item.addStock(100);
    }
}
