import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShoppingCart extends JFrame {
    private static JLabel labelTop,labelBottom;
    private static JComboBox<String> categoryComboBox;
    private static JButton shoppingCartButton,addToCart;
    static JTable productDataTable;
    private static DefaultTableModel productModel;
    private static TableRowSorter<DefaultTableModel> sorter;

    public ShoppingCart() {

        JFrame shoppingCartMain = new JFrame();
        shoppingCartMain.setSize(600, 600);
        JPanel panelNorth = new JPanel(new GridLayout(3, 1));
        shoppingCartMain.setTitle("Westminster Shopping Center");
        shoppingCartMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));




        /*===========================================NORTH==================================================*/

        /*============================*/
        labelTop = new JLabel("Select Product Category  ");
        topRow.add(labelTop);
        /*============================*/


        /*========dropDown for select product type==========*/

        String[] productTypes = {"Clothing", "Electronic", "All"};

        categoryComboBox = new JComboBox<>(productTypes);
        categoryComboBox.setPreferredSize(new Dimension(150, 50));
        categoryComboBox.setSelectedIndex(2);
        topRow.add(categoryComboBox);

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductType = (String) categoryComboBox.getSelectedItem();
                filterTable(selectedProductType);
            }
        });

        /*========dropDown for select product type==========*/
        /*categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductType = (String) categoryComboBox.getSelectedItem();
                WestMinisterShoppingManager manager = new WestMinisterShoppingManager();
                ArrayList<Product> productList = manager.getProductList(selectedProductType);
                updateProductTable(productList);
            }
        });*/


        /*==========shopping cart button===========*/
        shoppingCartButton = new JButton("Shopping Cart");
        JLabel gap = new JLabel();
        topRow.add(gap);
        shoppingCartButton.setSize(300, 50);
        topRow.add(shoppingCartButton);
        topRow.setPreferredSize(new Dimension(200, 100));
        /*==========shopping cart button===========*/




        panelNorth.add("North", topRow);
        /*===========================================NORTH==================================================*/



        /*========================================CENTER=======================================*/
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

        JScrollPane scrollPane = new JScrollPane(productDataTable);
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        sorter = new TableRowSorter<>(productModel);
        productDataTable.setRowSorter(sorter);

        panelNorth.add("Center", panelCenter);

        /*========================================CENTER=======================================*/

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        labelBottom = new JLabel("Selected Product Details                                               ");
        labelBottom.setFont(new Font("", 1, 14));
        panelBottom.add("North", labelBottom);


        JPanel panelBottomButton = new JPanel(new GridLayout(1, 3));
        JLabel leftLabel = new JLabel();
        panelBottomButton.add(leftLabel);


        addToCart = new JButton("Add to Shopping Cart");
        addToCart.setMinimumSize(new Dimension(150, 30));
        panelBottomButton.add(addToCart);

        JLabel rightLabel = new JLabel();
        panelBottomButton.add(rightLabel);
        panelBottom.add("South", panelBottomButton);

        panelNorth.add("South", panelBottom);

        shoppingCartMain.add(panelNorth);


        shoppingCartMain.setVisible(true);
    }
    private void filterTable(String selectedProductType) {
        RowFilter<DefaultTableModel, Object> rowFilter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                // Customize the filtering logic based on the selected product type
                if ("All".equals(selectedProductType)) {
                    return true; // Show all rows
                }

                String category = (String) entry.getValue(2); // Assuming category is at index 2
                if ("Electronic".equals(selectedProductType)) {
                    return "Electronic".equals(category);
                } else if ("Clothing".equals(selectedProductType)) {
                    return "Clothing".equals(category);
                } else {

                    return false;
                }
            }
        };

        // Apply the filter
        sorter.setRowFilter(rowFilter);
    }


    //  method to update the JTable with new product data
    public static void updateProductTable(ArrayList<Product> productList) {
        DefaultTableModel productModel = (DefaultTableModel) productDataTable.getModel();
        productModel.setRowCount(0);

        for (Product product : WestMinisterShoppingManager.getProductList()) {
            String extraInformation = "";
            String productId = product.getProductId();
            String name = product.getProductName();
            String category = product.getCategory();
            double price = product.getPrice();
            int quantity = product.getQuantity();
            if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                extraInformation =
                        ", Color: " + clothing.getColor() +"\n"+
                        ", Size: " + clothing.getSize();
            } else if (product instanceof Electronic) {
                Electronic electronic = (Electronic) product;
                extraInformation = "Brand: " + electronic.getBrand() +"\n"+
                        ", warranty period from years: " + electronic.getWarrantyPeriod();
            }


            Object[] rowData = {productId, name, category, price, quantity,extraInformation};
            productModel.addRow(rowData);
        }
        productModel.fireTableDataChanged(); /*use fireTableDataChanged() to inform the observers to refresh and display the updated information.*/
    }
}
