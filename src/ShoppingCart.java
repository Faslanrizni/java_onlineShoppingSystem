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
    private JFrame shoppingCart;

    private Map<String, ArrayList<String>> purchaseHistory;  // User ID to list of purchased products


    private static JLabel labelTop, labelBottom;
    private static JPanel panelBottom;
    private static JComboBox<String> categoryComboBox;
    private static JButton shoppingCartButton,AddToShoppingCartButton;
    static JTable productDataTable;
    private static DefaultTableModel productModel;
    private static TableRowSorter<DefaultTableModel> sorter;
    private static JTable shoppingCartTable;
    private static DefaultTableModel shoppingCartModel;
    private static Map<String, Integer> shoppingCartItems;  // Map to store product ID and quantity

    private JLabel totalLabel;
    private static JLabel finalTotalLabel;
    private static JLabel firstPurchaseDiscountLabel;
    private static JLabel sameCategoryDiscountLabel;
    private Map<String, Integer> categoryCounts;  // Map to store category and quantity



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
        shoppingCartButton = new JButton("Shopping Cart");
        topRow.add(shoppingCartButton);

        shoppingCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productDataTable.getSelectedRow();
                if (selectedRow != -1) {
                    String productId = productDataTable.getValueAt(selectedRow, 0).toString();
                    String productName = productDataTable.getValueAt(selectedRow, 1).toString();
                    String category = productDataTable.getValueAt(selectedRow, 2).toString();
                    double price = Double.parseDouble(productDataTable.getValueAt(selectedRow, 3).toString());

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



                    // Update the total label
                    double totalPrice = calculateTotalPrice();
                    totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));
                }
            }
        });


        /*================================Top row of Westminster Shopping Center ===================================================*/



        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("", Font.BOLD, 14));






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
        AddToShoppingCartButton = new JButton("Add to Shopping Cart");
        buttonPanel.add(AddToShoppingCartButton);

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
//                applyDiscounts();
            }
        });




        shoppingCartItems = new HashMap<>();  // Initialize the shopping cart items
        purchaseHistory = new HashMap<>();
        categoryCounts = new HashMap<>();


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
        if (shoppingCart == null || !shoppingCart.isVisible()) {
            shoppingCart = new JFrame("Shopping Cart");
            shoppingCart.setSize(500, 500);

            if (shoppingCartItems == null) {
                shoppingCartItems = new HashMap<>();
            }

            JPanel cartPanel = new JPanel(new GridLayout(2, 1));
            shoppingCart.add(cartPanel);
            String[] columns = {"Product", "Quantity", "Price"};

            shoppingCartModel = new DefaultTableModel(columns, 0);
            JTable cartTable = new JTable(shoppingCartModel);
            JScrollPane scrollPane = new JScrollPane(cartTable);
            cartPanel.add(scrollPane, BorderLayout.CENTER);



            // Create a new panel for additional labels
            JPanel labelsPanel = new JPanel();
            labelsPanel.setLayout(new GridLayout(5, 1));  // Updated layout to GridLayout

            totalLabel = new JLabel("Total: $0.00");
            labelsPanel.add(totalLabel);



            firstPurchaseDiscountLabel = new JLabel("First Purchase Discount: 0%");
            labelsPanel.add(firstPurchaseDiscountLabel);

            sameCategoryDiscountLabel = new JLabel("Same Category Discount: 0%");
            labelsPanel.add(sameCategoryDiscountLabel);

            finalTotalLabel = new JLabel("Final Total: $0.00");
            labelsPanel.add(finalTotalLabel);

            cartPanel.add(labelsPanel, BorderLayout.SOUTH);  // Set labelsPanel to SOUTH

            AddToShoppingCartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = productDataTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String productId = productDataTable.getValueAt(selectedRow, 0).toString();
                        String productName = productDataTable.getValueAt(selectedRow, 1).toString();
                        String category = productDataTable.getValueAt(selectedRow, 2).toString();
                        double price = Double.parseDouble(productDataTable.getValueAt(selectedRow, 3).toString());

                        // Add this before updating the shopping cart model
                        String categoryInCart = categoryComboBox.getSelectedItem().toString();
                        categoryCounts.put(categoryInCart, categoryCounts.getOrDefault(categoryInCart, 0) + 1);


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

                        // Update the total label
                        double totalPrice = calculateTotalPrice();
                        totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));

                        firstPurchaseDiscountLabel.setText("First purchase discount(10%): ");

                        double sameCategory = SameCategoryDiscount(calculateTotalPrice());
                        sameCategoryDiscountLabel.setText("Three items in same category discount(20%): " +"-"+sameCategory);
                        finalTotalLabel.setText("final Total: " +(calculateTotalPrice()-sameCategory));

                    }
                }
            });


            shoppingCart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            shoppingCart.add(cartPanel);
            shoppingCart.setVisible(true);
        } else {
            shoppingCart.toFront(); // Bring the existing shopping cart frame to the front
        }
    }


        /*   private void applyDiscounts() {
        double totalPrice = calculateTotalPrice();

        double firstPurchaseDiscount = 0.10;  // 10%


        // Check for the first purchase
        if (purchaseHistory.isEmpty()) {

            double discountAmount = totalPrice * firstPurchaseDiscount;
            totalPrice -= discountAmount;
            firstPurchaseDiscountLabel.setText("First Purchase Discount: $" + String.format("%.2f", discountAmount));
        } else {
            firstPurchaseDiscountLabel.setText("First Purchase Discount: $0.00");
        }

        // Calculate the discount for the same category


         // Check if there is any discount

        // Update the final total label

        finalTotalLabel.setText("Final Total: $" + String.format("%.2f", totalPrice+()));
    }*/





    // Calculate total price for products of a specific category in the shopping cart
    private double calculateCategoryTotalPrice(String category) {
        double categoryTotalPrice = 0.0;
        for (int i = 0; i < shoppingCartModel.getRowCount(); i++) {
            String productInfo = shoppingCartModel.getValueAt(i, 0).toString();
            if (productInfo.contains(category)) {
                double price = (double) shoppingCartModel.getValueAt(i, 2);
                int quantity = (int) shoppingCartModel.getValueAt(i, 1);
                categoryTotalPrice += price * quantity;
            }
        }
        return categoryTotalPrice;
    }



    private void updatePurchaseHistory(String userId, String productInfo) {
        if (!purchaseHistory.containsKey(userId)) {
            purchaseHistory.put(userId, new ArrayList<>());
        }
        purchaseHistory.get(userId).add(productInfo);
    }

    private double getProductPrice(String productInfo) {
        for (int i = 0; i < productDataTable.getRowCount(); i++) {
            String rowProductInfo = productDataTable.getValueAt(i, 0).toString();
            if (rowProductInfo.equals(productInfo)) {
                return Double.parseDouble(productDataTable.getValueAt(i, 3).toString());
            }
        }
        return 0.0;
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

            Object[] rowData = {productId, name, category, price, quantity, extraInformation};
            productModel.addRow(rowData);
        }
        productModel.fireTableDataChanged();
    }
    private double SameCategoryDiscount(double total) {
        double discountedValue=0;
        for (int i = 0; i < shoppingCartModel.getRowCount(); i++) {
            int quantity = (int) shoppingCartModel.getValueAt(i, 1);
            if (quantity>=3){
                discountedValue=(total*0.2);
            }
        }
        return discountedValue;
    }




}


