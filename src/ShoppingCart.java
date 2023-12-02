import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> usersCart = new ArrayList<>();
    public ShoppingCart() {

    }
    public ShoppingCart(ArrayList<Product> usersCart) {
        this.usersCart = usersCart;
    }



    public void addToCart(){}

    public void removeFromCart(){}

    public void calculateTotalCost(){}

    public ArrayList<Product> getUsersCart() {
        return usersCart;
    }

    public void setUsersCart(ArrayList<Product> usersCart) {
        this.usersCart = usersCart;
    }
}
