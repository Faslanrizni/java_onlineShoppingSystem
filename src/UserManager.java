import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {

     static ArrayList<User> userList = new ArrayList<>();

    public static void userLogin(){


    }
    public static void userRegister(){

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


