package Assignment;

import javax.swing.*;
import javax.swing.DefaultListCellRenderer;
import java.awt.*;

/**
 * ShopGUI - A graphical user interface (GUI) application for the accessories
 * shop stock management system.
 *
 * <p>This class extends {@link JFrame} and serves as the main entry point for
 * the GUI-based application required by the project brief. It allows shop staff
 * to manage stock items interactively without writing any code, demonstrating
 * all core object-oriented operations through a visual interface.</p>
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Create multiple {@link StockItem} objects of any subclass type
 *       ({@link NavSys}, {@link Tyre}, {@link CarBattery}, {@link OilFilter})</li>
 *   <li>Add stock units to any selected item</li>
 *   <li>Sell stock units from any selected item</li>
 *   <li>Change the price (excluding VAT) of any selected item</li>
 *   <li>Display updated item details after every operation</li>
 *   <li>All validation error messages are surfaced in the GUI output panel,
 *       not silently swallowed or only printed to {@code System.out}</li>
 * </ul>
 *
 * <h2>Design decisions and fixes applied:</h2>
 * <ol>
 *   <li>{@link #doSell()} returns early on failure so the "Updated item" message
 *       is never shown after an unsuccessful sell operation.</li>
 *   <li>{@link #doAdd()} mirrors {@link StockItem} validation so errors appear
 *       in the GUI output panel rather than only on the console.</li>
 *   <li>{@link #doSell()} mirrors {@link StockItem} validation for the same reason.</li>
 *   <li>{@link #doPrice()} does not use floating-point {@code ==} comparison,
 *       avoiding an unreliable sanity check.</li>
 *   <li>Duplicate stock code detection prevents the same code being registered twice.</li>
 *   <li>Extra fields are validated as non-empty for types that require them
 *       ({@link Tyre}, {@link OilFilter}, {@link CarBattery}).</li>
 *   <li>{@code amountField} and {@code newPriceField} are cleared after each
 *       successful operation to prevent accidental re-use.</li>
 *   <li>An empty stock list displays a placeholder message to guide the user.</li>
 * </ol>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     StockItem
 * @see     NavSys
 * @see     Tyre
 * @see     CarBattery
 * @see     OilFilter
 */
public class ShopGUI extends JFrame {

    // -----------------------------------------------------------------------
    // GUI Component Fields
    // -----------------------------------------------------------------------

    /** The data model backing the stock list; holds all created {@link StockItem} instances. */
    private final DefaultListModel<StockItem> model = new DefaultListModel<>();

    /** The visual list component that displays all stock items. */
    private final JList<StockItem> list = new JList<>(model);

    /**
     * The scrollable text area where operation results, item details,
     * and error messages are displayed to the user.
     */
    private final JTextArea output = new JTextArea(14, 45);

    // --- Create item controls -----------------------------------------------

    /**
     * Drop-down box allowing the user to select which type of stock item
     * to create. Options correspond to all defined subclasses of {@link StockItem}.
     */
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{
            "StockItem", "NavSys", "Tyre", "CarBattery", "OilFilter"
    });

    /** Text field for entering the unique stock code of a new item (e.g. {@code "NS101"}). */
    private final JTextField codeField  = new JTextField(10);

    /** Text field for entering the initial quantity in stock for a new item. */
    private final JTextField qtyField   = new JTextField(5);

    /** Text field for entering the price per unit excluding VAT for a new item. */
    private final JTextField priceField = new JTextField(7);

    /**
     * First extra input field, used for subclass-specific attributes:
     * tyre size, battery voltage, or oil filter type, depending on the
     * item type selected in {@link #typeBox}.
     */
    private final JTextField extra1Field = new JTextField(12);

    /**
     * Second extra input field, used for subclass-specific attributes:
     * tyre season, battery capacity (Ah), or compatible car model, depending
     * on the item type selected in {@link #typeBox}.
     */
    private final JTextField extra2Field = new JTextField(12);

    // --- Operation controls -------------------------------------------------

    /**
     * Text field for entering the number of units to add or sell during
     * a stock operation. Cleared automatically after each successful operation.
     */
    private final JTextField amountField   = new JTextField(5);

    /**
     * Text field for entering the new price per unit (excluding VAT) when
     * changing an item's price. Cleared automatically after each successful
     * operation.
     */
    private final JTextField newPriceField = new JTextField(7);

    // --- State flag ---------------------------------------------------------

    /**
     * Guard flag used to prevent the {@link javax.swing.event.ListSelectionListener}
     * from firing a duplicate log entry when {@link DefaultListModel#set(int, Object)}
     * is called internally by {@link #refreshSelected()}.
     *
     * <p>Set to {@code true} immediately before calling {@code model.set()},
     * and reset to {@code false} immediately afterwards.</p>
     */
    private boolean suppressSelectionLog = false;


    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructs and displays the {@code ShopGUI} application window.
     *
     * <p>Initialises the {@link JFrame}, builds and arranges all sub-panels
     * using {@link BorderLayout}, configures the output text area, and makes
     * the window visible on screen. The window is centred relative to the
     * screen and enforces a minimum size equal to its packed preferred size.</p>
     */
    public ShopGUI() {
        super("Accessories Shop - Stock Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        add(buildLeftPanel(),  BorderLayout.WEST);
        add(buildRightPanel(), BorderLayout.CENTER);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setVisible(true);

        log("GUI started. Create items, select one, then add / sell / change price.\n");
        updateExtraHints();
    }


    // -----------------------------------------------------------------------
    // Panel Builders
    // -----------------------------------------------------------------------

    /**
     * Builds and returns the left panel of the application window.
     *
     * <p>The left panel contains:</p>
     * <ul>
     *   <li>A scrollable {@link JList} displaying all stock items with a custom
     *       cell renderer that shows the stock code, name, quantity, and price
     *       on a single line per item.</li>
     *   <li>A "Show Details" button that prints full item details to the output
     *       panel for the currently selected item.</li>
     *   <li>A "Clear Output" button that empties the output text area.</li>
     * </ul>
     *
     * <p>A {@link javax.swing.event.ListSelectionListener} is attached to the
     * list so that selecting an item automatically logs its details, unless
     * {@link #suppressSelectionLog} is {@code true}.</p>
     *
     * @return a fully configured {@link JPanel} for the left side of the window
     */
    private JPanel buildLeftPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createTitledBorder("Stock List"));
        p.setPreferredSize(new Dimension(340, 400));

        // Custom cell renderer: displays one clean, informative line per item
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof StockItem s) {
                    setText("[" + s.getStockCode() + "] " + s.getStockName()
                            + "  |  Qty: " + s.getQuantityInStock()
                            + "  |  £" + String.format("%.2f", s.getPriceWithoutVAT()));
                }
                return this;
            }
        });

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && list.getSelectedValue() != null
                    && !suppressSelectionLog) {
                log("Selected item:\n" + list.getSelectedValue() + "\n\n");
            }
        });

        p.add(new JScrollPane(list), BorderLayout.CENTER);

        // Button row pinned to the bottom of the stock list panel
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));

        JButton showBtn = new JButton("Show Details");
        showBtn.addActionListener(e -> {
            StockItem s = getSelected();
            if (s != null) log("Details:\n" + s + "\n\n");
        });

        JButton clearBtn = new JButton("Clear Output");
        clearBtn.addActionListener(e -> output.setText(""));

        btnRow.add(showBtn);
        btnRow.add(clearBtn);
        p.add(btnRow, BorderLayout.SOUTH);

        return p;
    }

    /**
     * Builds and returns the right panel of the application window.
     *
     * <p>The right panel is arranged vertically using {@link BoxLayout} and
     * contains three sub-sections stacked from top to bottom:</p>
     * <ol>
     *   <li>The "Create Item" form panel (see {@link #buildCreatePanel()})</li>
     *   <li>The "Operations" panel (see {@link #buildOpsPanel()})</li>
     *   <li>The scrollable output text area where all log messages are shown</li>
     * </ol>
     *
     * @return a fully configured {@link JPanel} for the right side of the window
     */
    private JPanel buildRightPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(buildCreatePanel());
        p.add(Box.createVerticalStrut(8));
        p.add(buildOpsPanel());
        p.add(Box.createVerticalStrut(8));

        JPanel outPanel = new JPanel(new BorderLayout());
        outPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outPanel.add(new JScrollPane(output), BorderLayout.CENTER);

        p.add(outPanel);
        return p;
    }

    /**
     * Builds and returns the "Create Item" form panel.
     *
     * <p>This panel uses a {@link GridBagLayout} to present labelled input
     * fields for creating a new stock item of any supported type. Fields
     * include stock code, initial quantity, price (excluding VAT), and two
     * optional "Extra" fields whose purpose changes dynamically based on
     * the type selected in {@link #typeBox} (managed by
     * {@link #updateExtraHints()}).</p>
     *
     * <p>Clicking "Create and Add" invokes {@link #createItem()}, which
     * validates all inputs and adds the new item to the stock list.</p>
     *
     * @return a fully configured {@link JPanel} for item creation
     */
    private JPanel buildCreatePanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Create Item"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;

        int row = 0;

        c.gridx = 0; c.gridy = row; p.add(new JLabel("Type:"), c);
        c.gridx = 1; p.add(typeBox, c);
        typeBox.addActionListener(e -> updateExtraHints());

        row++;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("Code:"), c);
        c.gridx = 1; p.add(codeField, c);

        row++;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("Quantity:"), c);
        c.gridx = 1; p.add(qtyField, c);

        row++;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("Price (no VAT):"), c);
        c.gridx = 1; p.add(priceField, c);

        row++;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("Extra 1:"), c);
        c.gridx = 1; p.add(extra1Field, c);

        row++;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("Extra 2:"), c);
        c.gridx = 1; p.add(extra2Field, c);

        row++;
        JButton createBtn = new JButton("Create & Add");
        createBtn.addActionListener(e -> createItem());
        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        p.add(createBtn, c);

        return p;
    }

    /**
     * Builds and returns the "Operations" panel.
     *
     * <p>This panel provides the following actions, each of which operates on
     * the item currently selected in the stock list:</p>
     * <ul>
     *   <li><b>Add Stock</b> — increases stock by the amount entered in
     *       {@link #amountField} (calls {@link #doAdd()})</li>
     *   <li><b>Sell Stock</b> — decreases stock by the amount entered in
     *       {@link #amountField} (calls {@link #doSell()})</li>
     *   <li><b>Change Price</b> — updates the price (excluding VAT) to the
     *       value entered in {@link #newPriceField} (calls {@link #doPrice()})</li>
     *   <li><b>Refresh Details</b> — re-logs the selected item's current
     *       details without making any changes (calls {@link #refreshSelected()})</li>
     * </ul>
     *
     * @return a fully configured {@link JPanel} for stock operations
     */
    private JPanel buildOpsPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Operations (Select an item first)"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;

        int row = 0;

        c.gridx = 0; c.gridy = row; p.add(new JLabel("Amount:"), c);
        c.gridx = 1; p.add(amountField, c);

        JButton addBtn = new JButton("Add Stock");
        addBtn.addActionListener(e -> doAdd());
        c.gridx = 2; p.add(addBtn, c);

        JButton sellBtn = new JButton("Sell Stock");
        sellBtn.addActionListener(e -> doSell());
        c.gridx = 3; p.add(sellBtn, c);

        row++;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("New Price:"), c);
        c.gridx = 1; p.add(newPriceField, c);

        JButton priceBtn = new JButton("Change Price");
        priceBtn.addActionListener(e -> doPrice());
        c.gridx = 2; p.add(priceBtn, c);

        JButton refreshBtn = new JButton("Refresh Details");
        refreshBtn.addActionListener(e -> refreshSelected());
        c.gridx = 3; p.add(refreshBtn, c);

        return p;
    }


    // -----------------------------------------------------------------------
    // Extra Field Hint Management
    // -----------------------------------------------------------------------

    /**
     * Updates the tooltip text and enabled state of {@link #extra1Field} and
     * {@link #extra2Field} based on the item type currently selected in
     * {@link #typeBox}.
     *
     * <p>This method is called whenever the type selection changes, ensuring
     * the user always sees contextually correct guidance for what to enter:</p>
     * <ul>
     *   <li><b>Tyre</b>       — Extra 1: tyre size (e.g. {@code "205/55R16"});
     *                           Extra 2: season (e.g. {@code "All-season"})</li>
     *   <li><b>CarBattery</b> — Extra 1: voltage in volts (e.g. {@code "12"});
     *                           Extra 2: capacity in amp-hours (e.g. {@code "60"})</li>
     *   <li><b>OilFilter</b>  — Extra 1: filter type (e.g. {@code "Spin-on"});
     *                           Extra 2: compatible car model
     *                           (e.g. {@code "Ford Fiesta 1.0 EcoBoost"})</li>
     *   <li><b>StockItem / NavSys</b> — Both fields are disabled and labelled
     *                           as not required.</li>
     * </ul>
     */
    private void updateExtraHints() {
        String type = (String) typeBox.getSelectedItem();
        if (type == null) return;

        switch (type) {
            case "Tyre" -> {
                extra1Field.setToolTipText("Tyre size (e.g. 205/55R16)");
                extra2Field.setToolTipText("Season (e.g. All-season)");
                extra1Field.setEnabled(true);
                extra2Field.setEnabled(true);
            }
            case "CarBattery" -> {
                extra1Field.setToolTipText("Voltage (e.g. 12)");
                extra2Field.setToolTipText("Capacity Ah (e.g. 60)");
                extra1Field.setEnabled(true);
                extra2Field.setEnabled(true);
            }
            case "OilFilter" -> {
                extra1Field.setToolTipText("Filter type (e.g. Spin-on)");
                extra2Field.setToolTipText("Compatible model (e.g. Ford Fiesta 1.0 EcoBoost)");
                extra1Field.setEnabled(true);
                extra2Field.setEnabled(true);
            }
            default -> {
                // StockItem and NavSys do not use the extra fields
                extra1Field.setToolTipText("Not used for this type");
                extra2Field.setToolTipText("Not used for this type");
                extra1Field.setEnabled(false);
                extra2Field.setEnabled(false);
            }
        }
    }


    // -----------------------------------------------------------------------
    // Item Creation
    // -----------------------------------------------------------------------

    /**
     * Reads and validates all GUI input fields, creates the appropriate
     * {@link StockItem} subclass instance, adds it to the list model, and
     * logs a confirmation message to the output panel.
     *
     * <p>Validation rules enforced before creation:</p>
     * <ul>
     *   <li>Stock code must not be blank.</li>
     *   <li>Stock code must be unique — no duplicate codes are permitted.</li>
     *   <li>Both extra fields must be non-empty for {@link Tyre} and
     *       {@link OilFilter}.</li>
     *   <li>Both extra fields must be non-empty and numeric for
     *       {@link CarBattery}.</li>
     *   <li>Quantity and price must be parseable as {@code int} and
     *       {@code double} respectively.</li>
     * </ul>
     *
     * <p>On success, {@link #clearCreateFields()} is called to reset the form
     * for the next entry. On failure, an error dialog is shown to the user.</p>
     */
    private void createItem() {
        try {
            String type  = (String) typeBox.getSelectedItem();
            String code  = codeField.getText().trim();
            int    qty   = Integer.parseInt(qtyField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            // Validate: stock code must not be blank
            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Stock code must not be blank.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate: prevent duplicate stock codes (case-insensitive)
            for (int i = 0; i < model.size(); i++) {
                if (model.get(i).getStockCode().equalsIgnoreCase(code)) {
                    JOptionPane.showMessageDialog(this,
                            "A stock item with code \"" + code + "\" already exists.",
                            "Duplicate Code", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validate: Tyre and OilFilter both require both extra fields
            if ("Tyre".equals(type) || "OilFilter".equals(type)) {
                if (extra1Field.getText().trim().isEmpty()
                        || extra2Field.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please fill in both Extra fields for a " + type + ".",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validate: CarBattery requires both extra fields as numeric values
            if ("CarBattery".equals(type)) {
                if (extra1Field.getText().trim().isEmpty()
                        || extra2Field.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter Voltage and Capacity for a CarBattery.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Instantiate the correct subclass based on the selected type
            StockItem item;

            switch (type) {
                case "NavSys"     -> item = new NavSys(code, qty, price);
                case "Tyre"       -> item = new Tyre(code, qty, price,
                                                     extra1Field.getText().trim(),
                                                     extra2Field.getText().trim());
                case "CarBattery" -> {
                    int v  = Integer.parseInt(extra1Field.getText().trim());
                    int ah = Integer.parseInt(extra2Field.getText().trim());
                    item = new CarBattery(code, qty, price, v, ah);
                }
                case "OilFilter"  -> item = new OilFilter(code, qty, price,
                                                          extra1Field.getText().trim(),
                                                          extra2Field.getText().trim());
                default           -> item = new StockItem(code, qty, price);
            }

            model.addElement(item);
            log("Created item:\n" + item + "\n\n");
            clearCreateFields();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not create item: " + ex.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Unexpected error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears all input fields in the "Create Item" panel and resets
     * {@link #typeBox} to its first option ({@code "StockItem"}).
     *
     * <p>Called automatically by {@link #createItem()} after a successful
     * item creation, so the form is immediately ready for the next entry.</p>
     */
    private void clearCreateFields() {
        codeField.setText("");
        qtyField.setText("");
        priceField.setText("");
        extra1Field.setText("");
        extra2Field.setText("");
        typeBox.setSelectedIndex(0);
        updateExtraHints();
    }


    // -----------------------------------------------------------------------
    // Stock Operations
    // -----------------------------------------------------------------------

    /**
     * Returns the {@link StockItem} currently selected in the stock list,
     * or {@code null} if no item is selected.
     *
     * <p>If nothing is selected, a user-friendly message dialog is shown
     * prompting the user to select an item first. All operation methods
     * ({@link #doAdd()}, {@link #doSell()}, {@link #doPrice()}) call this
     * method and return immediately if the result is {@code null}.</p>
     *
     * @return the currently selected {@link StockItem}, or {@code null} if
     *         the selection is empty
     */
    private StockItem getSelected() {
        StockItem s = list.getSelectedValue();
        if (s == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an item from the stock list first.");
        }
        return s;
    }

    /**
     * Handles the "Add Stock" button action.
     *
     * <p>Reads the integer value from {@link #amountField} and validates it
     * against the same rules enforced by {@link StockItem#addStock(int)},
     * surfacing any errors in the GUI output panel rather than only on the
     * console. If validation passes, {@link StockItem#addStock(int)} is called
     * and {@link #refreshSelected()} updates the display.</p>
     *
     * <p>Validation rules checked:</p>
     * <ul>
     *   <li>Amount must be &ge; 1.</li>
     *   <li>Resulting stock level must not exceed {@link StockItem#MAX_STOCK}.</li>
     * </ul>
     */
    private void doAdd() {
        StockItem s = getSelected();
        if (s == null) return;

        try {
            int amount = Integer.parseInt(amountField.getText().trim());

            // Mirror StockItem validation so errors appear in the GUI output panel
            if (amount < 1) {
                log("Error: Amount to add must be greater than or equal to one.\n\n");
                return;
            }
            if (s.getQuantityInStock() + amount > StockItem.MAX_STOCK) {
                log("Error: Stock cannot exceed " + StockItem.MAX_STOCK + ". "
                        + "Current stock: " + s.getQuantityInStock()
                        + ", tried to add: " + amount + ".\n\n");
                return;
            }

            s.addStock(amount);
            amountField.setText("");
            refreshSelected();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a whole number.");
        }
    }

    /**
     * Handles the "Sell Stock" button action.
     *
     * <p>Reads the integer value from {@link #amountField} and validates it
     * against the same rules enforced by {@link StockItem#sellStock(int)},
     * surfacing any errors in the GUI output panel. Returns early on any
     * failure so the "Updated item" confirmation message is never displayed
     * after an unsuccessful sell operation.</p>
     *
     * <p>Validation rules checked:</p>
     * <ul>
     *   <li>Amount must be &ge; 1.</li>
     *   <li>Amount must not exceed the current stock level.</li>
     *   <li>{@link StockItem#sellStock(int)} must return {@code true}.</li>
     * </ul>
     */
    private void doSell() {
        StockItem s = getSelected();
        if (s == null) return;

        try {
            int amount = Integer.parseInt(amountField.getText().trim());

            // Mirror StockItem validation so errors appear in the GUI output panel
            if (amount < 1) {
                log("Error: Amount to sell must be greater than or equal to one.\n\n");
                return;
            }
            if (amount > s.getQuantityInStock()) {
                log("Error: Not enough stock. Available: " + s.getQuantityInStock()
                        + ", tried to sell: " + amount + ".\n\n");
                return;
            }

            boolean ok = s.sellStock(amount);
            if (!ok) {
                log("Sell failed: not enough stock or invalid amount.\n\n");
                return;
            }

            amountField.setText("");
            refreshSelected();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a whole number.");
        }
    }

    /**
     * Handles the "Change Price" button action.
     *
     * <p>Reads the {@code double} value from {@link #newPriceField}, validates
     * that it is non-negative, then calls
     * {@link StockItem#setPriceWithoutVAT(double)} on the selected item.
     * Floating-point {@code ==} comparison is deliberately avoided to prevent
     * unreliable equality checks. All error messages are logged to the GUI
     * output panel.</p>
     *
     * <p>Validation rules checked:</p>
     * <ul>
     *   <li>New price must be a valid {@code double}.</li>
     *   <li>New price must be &ge; 0.</li>
     * </ul>
     */
    private void doPrice() {
        StockItem s = getSelected();
        if (s == null) return;

        try {
            double p = Double.parseDouble(newPriceField.getText().trim());

            if (p < 0) {
                log("Error: Price must be greater than or equal to 0.\n\n");
                return;
            }

            s.setPriceWithoutVAT(p);
            newPriceField.setText("");
            refreshSelected();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "New price must be a valid number (e.g. 100.99).",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Refreshes the currently selected item in the {@link JList} and logs
     * its updated details to the output panel.
     *
     * <p>Calls {@link DefaultListModel#set(int, Object)} to notify the list
     * that the item's displayed data has changed (e.g. after a stock or price
     * update). {@link #suppressSelectionLog} is set to {@code true} before
     * this call and {@code false} immediately afterwards to prevent the
     * attached {@link javax.swing.event.ListSelectionListener} from firing a
     * duplicate log entry.</p>
     */
    private void refreshSelected() {
        int idx = list.getSelectedIndex();
        if (idx < 0) return;

        StockItem s = model.get(idx);

        suppressSelectionLog = true;
        model.set(idx, s);
        suppressSelectionLog = false;

        log("Updated item:\n" + s + "\n\n");
    }


    // -----------------------------------------------------------------------
    // Output Logging
    // -----------------------------------------------------------------------

    /**
     * Appends a message to the {@link #output} text area and automatically
     * scrolls the view to the bottom so the most recent entry is always visible.
     *
     * @param msg the message string to append; may contain newline characters
     */
    private void log(String msg) {
        output.append(msg);
        output.setCaretPosition(output.getDocument().getLength());
    }


    // -----------------------------------------------------------------------
    // Application Entry Point
    // -----------------------------------------------------------------------

    /**
     * Application entry point for the stock management GUI.
     *
     * <p>Constructs the {@link ShopGUI} window on the
     * <em>Event Dispatch Thread</em> (EDT) using
     * {@link SwingUtilities#invokeLater(Runnable)}, which is the correct and
     * thread-safe way to initialise Swing components in Java.</p>
     *
     * @param args command-line arguments; not used by this application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShopGUI::new);
    }
}
