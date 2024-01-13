import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;

class WestMinisterShoppingManagerTest {
    WestMinisterShoppingManager shoppingManager = null;

    @BeforeEach
    void setUp() {
        shoppingManager = new WestMinisterShoppingManager();
    }

    @Test
    void addProduct() {
        Product product = new Clothing("CD123", "Sample Clothing", 29.99, 10, "M", "Blue");
        shoppingManager.addProduct(product);
    }

    @Test
    void removeProduct() {
        // Add a sample product to the list
        Product product = new Clothing("CD123", "Sample Clothing", 29.99, 10, "M", "Blue");
        shoppingManager.addProduct(product);

        // Mock user input to remove the product
        String userInput = "CD123\n";
        InputStream originalSystemIn = System.in; // Save original System.in
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        // Test removing the product directly without user input
        shoppingManager.removeProduct(); // Call the removeProduct method without parameters

        // Ensure that the list remains unchanged
        assertFalse(shoppingManager.getProductList().contains(product),
                "Product should be removed from the list");

        // Clean up
        System.setIn(originalSystemIn); // Reset System.in
    }

    @Test
    void printProducts() {
        // Add a sample product to the list
        Product product = new Clothing("CD123", "Sample Clothing", 29.99, 10, "M", "Blue");
        shoppingManager.addProduct(product);

        // Test removing the product
        shoppingManager.removeProduct();
        assertFalse(shoppingManager.getProductList().contains(product), "Product should be removed from the list");

        // Test removing a non-existing product
        shoppingManager.removeProduct(); // Call the removeProduct method without parameters
        // Ensure that the list remains unchanged
        assertFalse(shoppingManager.getProductList().contains(product),
                "Product should not be removed if it doesn't exist in the list");
    }
}
