import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart extends JFrame {

    private JLabel totalLabel;
    private static JLabel labelTop, labelBottom;
    private static JPanel panelBottom;
    private static JComboBox<String> categoryComboBox;
    private static JButton shoppingCartButton;
    static JTable productDataTable;
    private static DefaultTableModel productModel;
    private static TableRowSorter<DefaultTableModel> sorter;
    private static JTable shoppingCartTable;
    private static DefaultTableModel shoppingCartModel;
    private static Map<String, Integer> shoppingCartItems;  // Map to store product ID and quantity

    public ShoppingCart() {

        JFrame shoppingCartMain = new JFrame();
        shoppingCartMain.setSize(600, 600);


        /*=============================================*/JPanel panelNorth = new JPanel(new GridLayout(3, 1));/*=========================================*/


        shoppingCartMain.setTitle("Westminster Shopping Center");
        shoppingCartMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));

        /*===============================Top row of Westminster Shopping Center ===================================*/

        labelTop = new JLabel("Select Product Category  ");
        topRow.add(labelTop);

        String[] productTypes = {"Clothing", "Electronic", "All"};
        categoryComboBox = new JComboBox<>(productTypes);
        categoryComboBox.setPreferredSize(new Dimension(150, 50));
        categoryComboBox.setSelectedIndex(2);
        topRow.add(categoryComboBox);


        /*================================Top row of Westminster Shopping Center ===================================================*/



        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("", Font.BOLD, 14));
//        panelBottom.add(totalLabel);



        productDataTable = new JTable(productModel);
        productDataTable.setPreferredScrollableViewportSize(new Dimension(100, 150));

        productDataTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productDataTable.getSelectedRow();
            if (selectedRow != -1) {
                String productId = productDataTable.getValueAt(selectedRow, 0).toString();
                String productName = productDataTable.getValueAt(selectedRow, 1).toString();
                String category = productDataTable.getValueAt(selectedRow, 2).toString();
                double price = Double.parseDouble(productDataTable.getValueAt(selectedRow, 3).toString());
                int quantity = Integer.parseInt(productDataTable.getValueAt(selectedRow, 4).toString());
                String extraInformation = productDataTable.getValueAt(selectedRow, 5).toString();

                // Update the labelBottom with selected product details
                updateSelectedProductDetails(productId, productName, category, price, quantity, extraInformation);
            }
        });

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductType = (String) categoryComboBox.getSelectedItem();
                filterTable(selectedProductType);
            }
        });

        panelNorth.add("North", topRow);

        /*=======================panel center of westminsterShopping center===============================================*/

        /*===========================================================*/JPanel panelCenter = new JPanel(new BorderLayout());/*================================*/
        int horizontalPadding = 5;
        panelCenter.setBorder(BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding));

        productModel = new DefaultTableModel();
        productModel.addColumn("Product ID");
        productModel.addColumn("Name");
        productModel.addColumn("Category");
        productModel.addColumn("Price");
        productModel.addColumn("Quantity");
        productModel.addColumn("Extra Information");

        productDataTable = new JTable(productModel);
        productDataTable.setPreferredScrollableViewportSize(new Dimension(100, 150));

        productDataTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productDataTable.getSelectedRow();
            if (selectedRow != -1) {
                String productId = productDataTable.getValueAt(selectedRow, 0).toString();
                String productName = productDataTable.getValueAt(selectedRow, 1).toString();
                String category = productDataTable.getValueAt(selectedRow, 2).toString();
                double price = Double.parseDouble(productDataTable.getValueAt(selectedRow, 3).toString());
                int quantity = Integer.parseInt(productDataTable.getValueAt(selectedRow, 4).toString());
                String extraInformation = productDataTable.getValueAt(selectedRow, 5).toString();

                // Update the labelBottom with selected product details
                updateSelectedProductDetails(productId, productName, category, price, quantity, extraInformation);
            }
        });

        JScrollPane scrollPane = new JScrollPane(productDataTable);
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        sorter = new TableRowSorter<>(productModel);
        productDataTable.setRowSorter(sorter);

        panelNorth.add("Center", panelCenter);

        /*======================================*/JPanel panelBottom = new JPanel();/*=======================================*/
        BoxLayout boxLayout = new BoxLayout(panelBottom, BoxLayout.Y_AXIS);
        panelBottom.setLayout(boxLayout);

        labelBottom = new JLabel("Selected Product Details                                               ");
        labelBottom.setFont(new Font("", 1, 14));

        panelBottom.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        panelBottom.add(labelBottom);

// Create a new panel for the product details and set its layout to FlowLayout with left alignment
        JPanel productDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productDetailsPanel.add(labelBottom);

// Add productDetailsPanel to panelBottom
        panelBottom.add(productDetailsPanel);

// Create a new panel for the button and set its layout to FlowLayout with center alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        shoppingCartButton = new JButton("Add to Shopping Cart");
        buttonPanel.add(shoppingCartButton);

// Add buttonPanel to panelBottom and set alignment to center
        panelBottom.add(Box.createVerticalGlue());  // To push the button to the bottom
        panelBottom.add(buttonPanel);
        panelBottom.add(Box.createVerticalGlue());  // To push the button to the center

        panelNorth.add("South", panelBottom);
        shoppingCartMain.add(panelNorth);
        shoppingCartMain.setVisible(true);





        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartFrame();
            }
        });




        shoppingCartItems = new HashMap<>();  // Initialize the shopping cart items

        productModel.fireTableDataChanged();
    }

    private void updateSelectedProductDetails(String productId, String productName, String category, double price, int quantity, String extraInformation) {
        labelBottom.setText("<html>Selected Product Details <br>" +
                "Product ID: " + productId + "<br>" +
                "Product Name: " + productName + "<br>" +
                "Category: " + category + "<br>" +
                "Price: " + price + "<br>" +
                "Items available : " + quantity + "<br>" +
                "Extra Information: " + extraInformation + "</html>");
    }

    private void openShoppingCartFrame() {



        if (shoppingCartModel == null) {
            shoppingCartModel = new DefaultTableModel();
            shoppingCartModel.addColumn("Product Information");
            shoppingCartModel.addColumn("Quantity");
            shoppingCartModel.addColumn("Price");

            shoppingCartTable = new JTable(shoppingCartModel);
        }

        // Check if the shopping cart frame is already open
        if (shoppingCartTable.getParent() == null) {
            ShoppingCartFrame shoppingCartFrame = new ShoppingCartFrame("Shopping Cart");
            shoppingCartFrame.setSize(600, 600);
            shoppingCartFrame.getContentPane().add(new JScrollPane(shoppingCartTable));
            shoppingCartFrame.addTotalLabel(totalLabel);  // Pass the totalLabel to ShoppingCartFrame

            // Set the table model and repaint
            shoppingCartTable.setModel(shoppingCartModel);
            shoppingCartTable.repaint();

            shoppingCartFrame.setVisible(true);
        }

        // Check if productDataTable is not null
        if (productDataTable != null) {
            int selectedRow = productDataTable.getSelectedRow();

            if (selectedRow != -1) {
                String productId = productDataTable.getValueAt(selectedRow, 0).toString();
                String productName = productDataTable.getValueAt(selectedRow, 1).toString();
                String category = productDataTable.getValueAt(selectedRow, 2).toString();
                double price = (Double.parseDouble(productDataTable.getValueAt(selectedRow, 3).toString()));

                // Update the shopping cart items
                if (shoppingCartItems.containsKey(productId)) {
                    int currentQuantity = shoppingCartItems.get(productId);
                    shoppingCartItems.put(productId, currentQuantity + 1);

                    // Subtract one from the product quantity in the product table
                    int productTableSelectedRow = productDataTable.convertRowIndexToModel(selectedRow);
                    int currentProductQuantity = (int) productModel.getValueAt(productTableSelectedRow, 4);
                    if (currentProductQuantity > 0) {
                        productModel.setValueAt(currentProductQuantity - 1, productTableSelectedRow, 4);
                    }

                } else {
                    shoppingCartItems.put(productId, 1);

                    // Subtract one from the product quantity in the product table
                    int productTableSelectedRow = productDataTable.convertRowIndexToModel(selectedRow);
                    int currentProductQuantity = (int) productModel.getValueAt(productTableSelectedRow, 4);
                    if (currentProductQuantity > 0) {
                        productModel.setValueAt(currentProductQuantity - 1, productTableSelectedRow, 4);
                    }
                }

                // Check if the product already exists in the cart
                boolean productExists = false;
                for (int i = 0; i < shoppingCartModel.getRowCount(); i++) {
                    String existingProductId = shoppingCartModel.getValueAt(i, 0).toString().split(" - ")[0];
                    if (productId.equals(existingProductId)) {
                        productExists = true;
                        int currentQuantity = (int) shoppingCartModel.getValueAt(i, 1);
                        shoppingCartModel.setValueAt(currentQuantity + 1, i, 1);

                        // Update the total price in the existing row
                        double currentTotalPrice = (double) shoppingCartModel.getValueAt(i, 2);
                        double totalPrice = shoppingCartItems.get(productId) * price;
                        shoppingCartModel.setValueAt(totalPrice, i, 2);

                        break;
                    }
                }

                // If the product is not in the cart, add a new row
                if (!productExists) {
                    String productInformation = productId + " - " + productName + " (" + category + ")";
                    Object[] rowData = {productInformation, shoppingCartItems.get(productId), price};
                    shoppingCartModel.addRow(rowData);
                }

                shoppingCartTable.setModel(shoppingCartModel);
                shoppingCartTable.repaint();

                // Calculate total price based on quantity and price
                double totalPrice = calculateTotalPrice();

                // Calculate discounts and final total
                double firstPurchaseDiscount = 0.0;
                double categoryDiscount = 0.0;

                // Check if it's the user's first purchase
                if (shoppingCartItems.isEmpty()) {
                    firstPurchaseDiscount = 0.10; // 10% first purchase discount
                }

                // Check if there are at least three products of the same category
                // Check if there are at least three products of the same category
                if (productDataTable != null) {
                    String selectedCategory = productDataTable.getValueAt(selectedRow, 2).toString();
                    int categoryCount = 0;

                    for (int i = 0; i < productDataTable.getRowCount(); i++) {
                        String categoryOf = productDataTable.getValueAt(i, 2).toString();
                        if (selectedCategory.equals(categoryOf)) {
                            categoryCount++;
                            if (categoryCount >= 3) {
                                categoryDiscount = 0.20; // 20% discount for buying at least three products of the same category
                                break;
                            }
                        }
                    }
                }

                // Calculate final total after applying discounts
                double totalDiscount = firstPurchaseDiscount + categoryDiscount;
                double discountedAmount = totalPrice * totalDiscount;
                double finalTotal = totalPrice - discountedAmount;

                // Display discounts and final total below the shopping cart table
                String discountsText = String.format("Discounts:\nFirst Purchase Discount: %.2f%%\nCategory Discount: %.2f%%\n", firstPurchaseDiscount * 100, categoryDiscount * 100);
                String finalTotalText = String.format("Final Total: $%.2f (%.2f%% off)", finalTotal, totalDiscount * 100);

                // Display selected details below the shopping cart table
                String selectedDetails = labelBottom.getText();
                labelBottom.setText(selectedDetails + "\n\n" + discountsText + "\n" + finalTotalText);

                totalLabel.setText("Total: $" + String.format("%.2f", finalTotal));




                // Update the total label
                totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));
            }
        }
    }


    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (int i = 0; i < shoppingCartModel.getRowCount(); i++) {
            double price = (double) shoppingCartModel.getValueAt(i, 2);
            totalPrice += price;
        }
        return totalPrice;
    }

    private void filterTable(String selectedProductType) {
        RowFilter<DefaultTableModel, Object> rowFilter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                if ("All".equals(selectedProductType)) {
                    return true;
                }

                String category = (String) entry.getValue(2);
                if ("Electronic".equals(selectedProductType)) {
                    return "Electronic".equals(category);
                } else if ("Clothing".equals(selectedProductType)) {
                    return "Clothing".equals(category);
                } else {
                    return false;
                }
            }
        };

        sorter.setRowFilter(rowFilter);
    }

    public static void updateProductTable(ArrayList<Product> productList) {
        DefaultTableModel productModel = (DefaultTableModel) productDataTable.getModel();
        productModel.setRowCount(0);

        for (Product product : productList) {
            String extraInformation = "";
            String productId = product.getProductId();
            String name = product.getProductName();
            String category = product.getCategory();
            double price = product.getPrice();
            int quantity = product.getQuantity();
            if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                extraInformation =
                        ", Color: " + clothing.getColor() + "\n" +
                                ", Size: " + clothing.getSize();
            } else if (product instanceof Electronic) {
                Electronic electronic = (Electronic) product;
                extraInformation = "Brand: " + electronic.getBrand() + "\n" +
                        ", warranty period from years: " + electronic.getWarrantyPeriod();
            }

            // Check if the product is in the shopping cart
            if (shoppingCartItems.containsKey(productId)) {
                quantity -= shoppingCartItems.get(productId);
            }


            Object[] rowData = {productId, name, category, price,quantity, extraInformation};
            productModel.addRow(rowData);
        }
        productModel.fireTableDataChanged();
    }
}

class ShoppingCartFrame extends JFrame {
    private JLabel totalLabel;
    private JLabel FirstPurchaseDiscount;

    public ShoppingCartFrame(String title) {
        super(title);

    }

    public void addTotalLabel(JLabel totalLabel) {
        this.totalLabel = totalLabel;
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panelBottom.add("Center", totalLabel);

        this.add(panelBottom, BorderLayout.SOUTH);
    }
}
