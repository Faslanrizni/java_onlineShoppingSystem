import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {

    static ArrayList<User> userList = new ArrayList<>();
    private static User currentUser; // Declare currentUser at the class level

    public static boolean userLogin(){
        Scanner input = new Scanner(System.in);

        boolean logging = false;
        while (!logging){
            System.out.print("Enter the userName: ");
            String customerUser = input.nextLine();

            System.out.print("Enter the password: ");
            String customerPassword = input.nextLine();

            User loginCustomer = new User(customerUser, customerPassword);

            for (User customer: userList){
                if (customer.getUserName().equals(customerUser) && customer.getPassword().equals(customerPassword)){
                    currentUser = loginCustomer; // Set currentUser after login
                    logging = true;

                    break;
                }
            }

            if (!logging) {
                System.out.println("Please try again.");
            }
        }

        if (logging) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Exiting.");
        }

        return logging;
    }

    public static boolean userRegister(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the usename for register: ");
        String customerUser = input.nextLine();
        System.out.print("Enter the password for register: ");
        String customerPassword = input.nextLine();

        User customer = new User(customerUser, customerPassword);
        userList.add(customer);
        currentUser = customer; // Set currentUser after registration

        System.out.println("Registered successfully");
        return true;
    }

    public static boolean ManagerLogin(){
        Scanner input = new Scanner(System.in);
        User managerUser = new User("Manager","123456");
        userList.add(managerUser);
        boolean logging = false;
        while (!logging) {
            System.out.print("Enter the password: ");
            String managerPassword = input.nextLine();
            System.out.print("Enter user name: ");
            String managerUserName = input.nextLine();
            for (User manager : userList) {
                if (manager.getUserName().equals(managerUserName) && manager.getPassword().equals(managerPassword)) {
                    logging = true;
                    currentUser = manager; // Set currentUser after manager login
                    break;
                }
            }

            if (!logging) {
                System.out.println("Please try again.");
            }
        }

        if (logging) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Exiting.");
        }

        return logging;
    }
    public static void updateUserPurchaseHistory(User user, String productInfo) {
        user.getPurchaseHistory().addPurchaseHistory(productInfo);
    }
    public static User getCurrentUser(){
        return currentUser;
    }
}
