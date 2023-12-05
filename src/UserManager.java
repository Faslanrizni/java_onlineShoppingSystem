import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {

     static ArrayList<User> userList = new ArrayList<>();

    public static boolean userLogin(){
        Scanner input = new Scanner(System.in);

        boolean logging = false;
        while (!logging){
            System.out.println("Enter the userName");;
            String customerUser = input.nextLine();

            System.out.println("Enter the password");
            String customerPassword = input.nextLine();

            User loginCustomer = new User(customerUser, customerPassword);
            userList.add(loginCustomer);

            for (User customer: userList){
                if (customer.getUserName().equals(customerUser) && customer.getPassword().equals(customerPassword)){
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
    public static void userRegister(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the use name for register");
        String customerUser = input.nextLine();
        System.out.println("Enter the password for register");
        String customerPassword = input.nextLine();

        User customer = new User(customerUser,customerPassword);
        userList.add(customer);
        System.out.println("Registered successfully");
    }
    public static boolean ManagerLogin(){
        Scanner input = new Scanner(System.in);
        User managerUser = new User("Manager","123456");
        userList.add(managerUser);
        boolean logging = false;
        while (!logging) {
            System.out.println("Enter the password");
            String managerPassword = input.nextLine();
            System.out.println("Enter user name");
            String managerUserName = input.nextLine();
            for (User manager : userList) {
                if (manager.getUserName().equals(managerUserName) && manager.getPassword().equals(managerPassword)) {
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



    }


