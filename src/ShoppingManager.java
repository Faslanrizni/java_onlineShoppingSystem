import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public interface ShoppingManager {
     void addProduct(Product product);
    void removeProduct();
    void printProducts(ArrayList<Product> productListForPrint);
    void saveProducts(String filename);
    public boolean userOption() throws IOException;


}
