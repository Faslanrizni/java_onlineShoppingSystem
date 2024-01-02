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
        JPanel panelNorth = new JPanel(new GridLayout(3, 1));
        shoppingCartMain.setTitle("Westminster Shopping Center");
        shoppingCartMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
/*================*/
        labelBottom = new JLabel("Selected Product Details                                               ");
        labelBottom.setFont(new Font("", 1, 14));

        panelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        panelBottom.add("North", labelBottom);

        /*================*/
        labelTop = new JLabel("Select Product Category  ");
        topRow.add(labelTop);

        String[] productTypes = {"Clothing", "Electronic", "All"};
        categoryComboBox = new JComboBox<>(productTypes);
        categoryComboBox.setPreferredSize(new Dimension(150, 50));
        categoryComboBox.setSelectedIndex(2);
        topRow.add(categoryComboBox);

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
        });;


       /* productDataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
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
            }
        });
*/
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductType = (String) categoryComboBox.getSelectedItem();
                filterTable(selectedProductType);
            }
        });

        panelNorth.add("North", topRow);

        JPanel panelCenter = new JPanel(new BorderLayout());
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

        panelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelBottom.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        labelBottom = new JLabel("Selected Product Details                                               ");
        labelBottom.setFont(new Font("", 1, 14));
        panelBottom.add("North", labelBottom);

        JPanel panelBottomButton = new JPanel(new GridLayout(1, 3));
        JLabel leftLabel = new JLabel();
        panelBottomButton.add(leftLabel);

        shoppingCartButton = new JButton("Shopping Cart");
        JLabel gap = new JLabel();
        topRow.add(gap);
        shoppingCartButton.setSize(600, 600);
        panelBottomButton.add(shoppingCartButton);
        topRow.setPreferredSize(new Dimension(200, 100));

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartFrame();
            }
        });

        JLabel rightLabel = new JLabel();
        panelBottomButton.add(rightLabel);
        panelBottom.add("South", panelBottomButton);

        panelNorth.add("South", panelBottom);
        shoppingCartMain.add(panelNorth);
        shoppingCartMain.setVisible(true);

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
            shoppingCartModel.addColumn("Product ID");
            shoppingCartModel.addColumn("Name");
            shoppingCartModel.addColumn("Category");
            shoppingCartModel.addColumn("Price");
            shoppingCartModel.addColumn("Quantity");
            shoppingCartModel.addColumn("Extra Information");

            shoppingCartTable = new JTable(shoppingCartModel);

        }
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(400, 300);
        shoppingCartFrame.getContentPane().add(new JScrollPane(shoppingCartTable));

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
            } else {
                shoppingCartItems.put(productId, 1);
            }

            // Check if the product already exists in the cart
            boolean productExists = false;
            for (int i = 0; i < shoppingCartModel.getRowCount(); i++) {
                if (productId.equals(shoppingCartModel.getValueAt(i, 0))) {
                    productExists = true;
                    int currentQuantity = (int) shoppingCartModel.getValueAt(i, 4);
                    shoppingCartModel.setValueAt(currentQuantity + 1, i, 4);
                    break;
                }
            }

            // If the product is not in the cart, add a new row
            if (!productExists) {
                Object[] rowData = {productId, productName, category, price, shoppingCartItems.get(productId), "Extra Info"};
                shoppingCartModel.addRow(rowData);
            }
        }

        shoppingCartFrame.setVisible(true);
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

            Object[] rowData = {productId, name, category, price, quantity, extraInformation};
            productModel.addRow(rowData);
        }
        productModel.fireTableDataChanged();
    }

}
