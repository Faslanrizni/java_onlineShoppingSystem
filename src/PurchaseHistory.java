import java.util.ArrayList;
import java.util.List;

public class PurchaseHistory {
    private final List<String> purchases; // List to store purchase details
    private boolean firstPurchase; // Flag to track the first purchase status

    public PurchaseHistory() {
        this.purchases = new ArrayList<>();
        this.firstPurchase = true; // Initializing as true for the first purchase
    }

    public List<String> getPurchases() {
        return purchases;
    }

    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }


    // Method to add a purchase to the history
    public void addPurchaseHistory(String purchaseDetails) {
        purchases.add(purchaseDetails);
    }
}
