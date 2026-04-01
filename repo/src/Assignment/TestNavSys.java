package Assignment;

/**
 * TestNavSys - A test class for the {@link NavSys} subclass (Step 2).
 *
 * <p>This class systematically exercises all core operations of the
 * {@link NavSys} class as specified by the project brief, verifying that
 * inherited behaviour from {@link StockItem} works correctly when called
 * on a subclass instance. Each task below corresponds directly to a sample
 * run described in the Step 2 section of the assessment brief.</p>
 *
 * <h2>Test tasks performed:</h2>
 * <ol>
 *   <li><b>Task 1</b> — Create a {@link NavSys} instance with stock code
 *       {@code "NS101"}, quantity {@code 10}, and price {@code 99.99};
 *       print initial item details.</li>
 *   <li><b>Task 2</b> — Add {@code 10} more units to stock; print updated
 *       details (expected quantity: {@code 20}).</li>
 *   <li><b>Task 3</b> — Sell {@code 2} units; print updated details
 *       (expected quantity: {@code 18}).</li>
 *   <li><b>Task 4</b> — Change the price to {@code 100.99} per unit (excluding
 *       VAT); print updated details (expected VAT price: {@code 118.66325}).</li>
 *   <li><b>Task 5</b> — Attempt to add {@code 0} units, which is invalid;
 *       verifies that the error message required by the brief is printed:
 *       {@code "The error was: Increased item must be greater than or equal to one"}.</li>
 * </ol>
 *
 * <p>This test also implicitly verifies polymorphism and dynamic method binding:
 * calling {@link NavSys#toString()} on the instance invokes the overridden
 * {@link NavSys#getStockName()} and {@link NavSys#getStockDescription()}
 * methods, producing navigation system-specific output rather than the
 * generic {@link StockItem} placeholders.</p>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     NavSys
 * @see     StockItem
 */
public class TestNavSys {

    /**
     * Entry point for the {@link NavSys} test sequence.
     *
     * <p>Executes all five test tasks in order, printing stock item details
     * to the console after each operation so that the output can be compared
     * against the expected sample run in the project brief.</p>
     *
     * <p>No assertions framework is used; correctness is verified by
     * inspecting the console output against the expected values documented
     * in the assessment brief (Step 2 sample run).</p>
     *
     * @param args command-line arguments; not used by this test class
     */
    public static void main(String[] args) {

        // --------------------------------------------------------------------
        // Task 1 — Create a NavSys instance: code "NS101", qty 10, price 99.99
        // Expected output: Stock Type: Navigation system,
        //                  Description: GeoVision Sat Nav, qty 10,
        //                  Price Without VAT: 99.99, Price With VAT: 117.48825
        // --------------------------------------------------------------------
        NavSys nav = new NavSys("NS101", 10, 99.99);
        System.out.println("Printing stock item information");
        System.out.println(nav);

        // --------------------------------------------------------------------
        // Task 2 — Add 10 more units to stock
        // Expected output: Total unit in stock: 20
        // --------------------------------------------------------------------
        nav.addStock(10);
        System.out.println("\nPrinting stock item information");
        System.out.println(nav);

        // --------------------------------------------------------------------
        // Task 3 — Sell 2 units from stock
        // Expected output: Total unit in stock: 18
        // --------------------------------------------------------------------
        nav.sellStock(2);
        System.out.println("\nPrinting stock item information");
        System.out.println(nav);

        // --------------------------------------------------------------------
        // Task 4 — Change price to £100.99 per unit (excluding VAT)
        // Expected output: Price Without VAT: 100.99,
        //                  Price With VAT: 118.66324999999999
        // --------------------------------------------------------------------
        nav.setPriceWithoutVAT(100.99);
        System.out.println("\nPrinting stock item information");
        System.out.println(nav);

        // --------------------------------------------------------------------
        // Task 5 — Attempt to add 0 units (invalid amount, brief requires error)
        // Expected console output:
        //   "The error was: Increased item must be greater than or equal to one"
        // --------------------------------------------------------------------
        nav.addStock(0);
    }
}
