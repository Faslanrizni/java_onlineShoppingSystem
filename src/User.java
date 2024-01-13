import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private String password;
    private PurchaseHistory purchaseHistory;
    private boolean firstPurchase;
    private boolean firstPurchaseDiscountApplied;



    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.purchaseHistory =  new PurchaseHistory();
        this.firstPurchaseDiscountApplied = false;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setPurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }

    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    public boolean isFirstPurchaseDiscountApplied() {
        return firstPurchaseDiscountApplied;
    }

    public void setFirstPurchaseDiscountApplied(boolean firstPurchaseDiscountApplied) {
        this.firstPurchaseDiscountApplied = firstPurchaseDiscountApplied;
    }
}

