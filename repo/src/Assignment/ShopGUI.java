package Assignment;

import javax.swing.*;
import javax.swing.DefaultListCellRenderer;
import java.awt.*;

/**
 * GUI-based application (required by brief).
 *
 * Features:
 * - Create multiple StockItem objects (including all subclasses)
 * - Add stock, sell stock, change price
 * - Display updated item details after every operation
 * - All error messages are shown in the GUI output panel (not just System.out)
 *
 * Fixes applied vs. original:
 * 1. doSell() now returns early on failure so "Updated item" is never shown after a failed sell.
 * 2. doAdd() now catches StockItem validation conditions and logs errors to the GUI panel.
 * 3. doSell() now catches StockItem validation conditions and logs errors to the GUI panel.
 * 4. doPrice() no longer uses floating-point == comparison (removed unreliable sanity check).
 * 5. Duplicate stock code detection added.
 * 6. Extra fields validated (non-empty) for types that require them (Tyre, OilFilter).
 * 7. amountField and newPriceField are cleared after successful operations.
 * 8. Empty stock list shows a placeholder message to guide the user.
 */
public class ShopGUI extends JFrame {

    private final DefaultListModel<StockItem> model = new DefaultListModel<>();
    private final JList<StockItem> list = new JList<>(model);
    private final JTextArea output = new JTextArea(14, 45);

    // Create item controls
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{
            "StockItem", "NavSys", "Tyre", "CarBattery", "OilFilter"
    });

    private final JTextField codeField  = new JTextField(10);
    private final JTextField qtyField   = new JTextField(5);
    private final JTextField priceField = new JTextField(7);

    // Extra fields for subclasses
    private final JTextField extra1Field = new JTextField(12);
    private final JTextField extra2Field = new JTextField(12);

    // Operation controls
    private final JTextField amountField   = new JTextField(5);
    private final JTextField newPriceField = new JTextField(7);

    // Guard flag to prevent double-logging when model.set() fires the selection listener
    private boolean suppressSelectionLog = false;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs and displays the ShopGUI window.
     * Builds all panels and initialises the GUI components.
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

    // -------------------------------------------------------------------------
    // Panel builders
    // -------------------------------------------------------------------------

    /**
     * Builds the left panel containing the stock list and control buttons.
     *
     * @return configured JPanel for the left side
     */
    private JPanel buildLeftPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createTitledBorder("Stock List"));
        p.setPreferredSize(new Dimension(340, 400));

        // Custom cell renderer: one clean line per item
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

        // Button row at bottom of the list panel
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
     * Builds the right panel containing create item form, operations, and output area.
     *
     * @return configured JPanel for the right side
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
     * Builds the "Create Item" panel with fields for all item types.
     *
     * @return configured JPanel for item creation
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
     * Builds the "Operations" panel for add stock, sell stock, and change price.
     *
     * @return configured JPanel for stock operations
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

    // -------------------------------------------------------------------------
    // Tooltip / enable hints for extra fields based on selected type
    // -------------------------------------------------------------------------

    /**
     * Updates the extra field labels and enabled state based on the selected item type.
     * Tyre uses size and season; CarBattery uses voltage and capacityAh;
     * OilFilter uses filter type and compatible model.
     * StockItem and NavSys do not use extra fields.
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
                // StockItem and NavSys do not use extra fields
                extra1Field.setToolTipText("Not used for this type");
                extra2Field.setToolTipText("Not used for this type");
                extra1Field.setEnabled(false);
                extra2Field.setEnabled(false);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Create item
    // -------------------------------------------------------------------------

    /**
     * Reads GUI fields, validates input, creates the appropriate StockItem subclass,
     * adds it to the list model, and logs confirmation.
     * Shows error dialogs for invalid input or duplicate stock codes.
     */
    private void createItem() {
        try {
            String type  = (String) typeBox.getSelectedItem();
            String code  = codeField.getText().trim();
            int    qty   = Integer.parseInt(qtyField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            // Validate: code must not be blank
            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Stock code must not be blank.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Prevent duplicate stock codes
            for (int i = 0; i < model.size(); i++) {
                if (model.get(i).getStockCode().equalsIgnoreCase(code)) {
                    JOptionPane.showMessageDialog(this,
                            "A stock item with code \"" + code + "\" already exists.",
                            "Duplicate Code", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validate extra fields are not blank for types that need them
            if ("Tyre".equals(type) || "OilFilter".equals(type)) {
                if (extra1Field.getText().trim().isEmpty()
                        || extra2Field.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please fill in both Extra fields for a " + type + ".",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if ("CarBattery".equals(type)) {
                if (extra1Field.getText().trim().isEmpty()
                        || extra2Field.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter Voltage and Capacity for a CarBattery.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            StockItem item;

            switch (type) {
                case "NavSys"  -> item = new NavSys(code, qty, price);
                case "Tyre"    -> item = new Tyre(code, qty, price,
                                                   extra1Field.getText().trim(),
                                                   extra2Field.getText().trim());
                case "CarBattery" -> {
                    int v  = Integer.parseInt(extra1Field.getText().trim());
                    int ah = Integer.parseInt(extra2Field.getText().trim());
                    item = new CarBattery(code, qty, price, v, ah);
                }
                case "OilFilter" -> item = new OilFilter(code, qty, price,
                                                          extra1Field.getText().trim(),
                                                          extra2Field.getText().trim());
                default -> item = new StockItem(code, qty, price);
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
     * Clears all fields in the Create Item panel and resets the type dropdown.
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

    // -------------------------------------------------------------------------
    // Operations
    // -------------------------------------------------------------------------

    /**
     * Returns the currently selected StockItem from the list.
     * Shows a message dialog if nothing is selected.
     *
     * @return selected StockItem, or null if none selected
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
     * Handles the Add Stock button.
     * Validates amount in the GUI first, then calls addStock().
     * Error conditions are logged to the GUI output panel — not just System.out.
     */
    private void doAdd() {
        StockItem s = getSelected();
        if (s == null) return;

        try {
            int amount = Integer.parseInt(amountField.getText().trim());

            // Mirror StockItem validation here so errors appear in the GUI
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
     * Handles the Sell Stock button.
     * Returns early on failure so "Updated item" is never shown after a failed sell.
     * Error conditions are logged to the GUI output panel.
     */
    private void doSell() {
        StockItem s = getSelected();
        if (s == null) return;

        try {
            int amount = Integer.parseInt(amountField.getText().trim());

            // Mirror StockItem validation here so errors appear in the GUI
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
     * Handles the Change Price button.
     * Validates price is non-negative, calls setPriceWithoutVAT(), then refreshes.
     * All error messages go to the GUI output panel.
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
     * Refreshes the selected item in the JList and logs its updated details.
     * Uses suppressSelectionLog to prevent the ListSelectionListener
     * from firing a duplicate log entry when model.set() is called.
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

    // -------------------------------------------------------------------------
    // Logging
    // -------------------------------------------------------------------------

    /**
     * Appends a message to the output text area and scrolls to the bottom.
     *
     * @param msg message to display
     */
    private void log(String msg) {
        output.append(msg);
        output.setCaretPosition(output.getDocument().getLength());
    }

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    /**
     * Application entry point.
     * Launches the GUI on the Event Dispatch Thread.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShopGUI::new);
    }
}
