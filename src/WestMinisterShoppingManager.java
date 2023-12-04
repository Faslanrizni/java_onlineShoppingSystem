import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WestMinisterShoppingManager implements ShoppingManager {
    ArrayList<Product> productList = new ArrayList<>();
  /*  ArrayList<Clothing> clothList = new ArrayList<>();
    ArrayList<Electronic> ElectronicList = new ArrayList<>();*/

    public WestMinisterShoppingManager(ArrayList<Product> productList) {
        this.productList = productList;
    }

    private static final int MAX_PRODUCTS = 50;
    Scanner input = new Scanner(System.in);

    public WestMinisterShoppingManager() {

    }

    @Override
    public void addProduct(Product product) {

        if (productList.size() < MAX_PRODUCTS) {
            productList.add(product);
            System.out.println(product.getProductName() + " added successfully!");
            System.out.println(productList);
        } else {
            System.out.println("Maximum product limit reached. Cannot add more products.");
        }

    }
    @Override
    public void removeProduct() {
        System.out.println("enter the product id for remove product: ");
        String removeProductId = input.nextLine();
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(removeProductId)) {
                iterator.remove();
                System.out.println("Product deleted: " + product.getProductName() + " (ID: " + removeProductId + ")");
                System.out.println("Total products left in the system: " + productList.size());
                return;
            }
        }
        System.out.println("Product with ID " + removeProductId + " not found.");
    }
    @Override
    public void printProducts(ArrayList<Product> productList){
        for (int i = 0; i <productList.size() ; i++) {
            System.out.println(productList.get(i));
        }
        }
    @Override
    public void saveProducts(String filename) {

    }

    @Override
    public boolean userOption() throws IOException {
        Product product = new Product() {
        };
        Scanner input = new Scanner(System.in);

        boolean keepGoing = false;
        System.out.println("1)Add New product \n" +
                "2) Delete product \n" +
                "3) print the list of product \n" +
                "4) Save in a file \n" +
                " 0) Quit "
        );
        System.out.println("--------------------------------------------------");
        int userOptions;
        while (true){
            String userInput = input.nextLine();
            try {
                userOptions = Integer.parseInt(userInput);
                if(userOptions >= 0 && userOptions <=4){
                    break;
                }else {
                    System.out.print("Enter a valid number: ");   // display when input range is incorrect
                }

            }catch (NumberFormatException e){
                System.out.print("Enter a valid number : ");   // display when user entered a sting input
            }
        }

        switch (userOptions) {
            case 1:
                System.out.println("Press 1 to add Clothing Product");
                System.out.println("Press 2 to add Electronics product");
//                int userInput2 = input.nextInt();
//                input.nextLine();
                int userOptions1;
                while (true){
                    String userInput = input.nextLine();
                    try {
                        userOptions1 = Integer.parseInt(userInput);
                        if(userOptions1 >= 0 && userOptions1 <=2){
                            break;
                        }else {
                            System.out.print("Enter a valid number: ");   // display when input range is incorrect
                        }


                    }catch (NumberFormatException e){
                        System.out.print("Enter a valid number : ");   // display when user entered a sting input
                    }
                }

                String productId = getProductId();

                System.out.println("Enter Product Name");
                String productName = input.nextLine();
                product.setProductName(productName);


                System.out.println("Enter Product price");
                double productPrice;
                while (true){
                   /* Double managerInputPrice = input.nextDouble();*/
                    try {
                        productPrice = Double.parseDouble(input.next());
                        if(productPrice <= 0 ){
                            System.out.println("Enter a positive Number: ");
                            continue;
                        }
                        break;
                    }catch (NumberFormatException e){
                        System.out.print("Enter double value : ");   // display when user entered a sting input
                    }
                }
//                double productPrice = input.nextDouble();
                product.setPrice(productPrice);
                input.nextLine();

                System.out.println("Enter Product quantity");
                int quantity = input.nextInt();
                product.setQuantity(quantity);
                input.nextLine();

                switch (userOptions1){
                    case 1:
                        Clothing clothing = new Clothing();
                        System.out.println("Insert the Size('XXL','XL','L','M')");
                        String size = input.nextLine();
                        clothing.setSize(size);

                        input.nextLine();

                        System.out.println("Insert the Color");
                        String color = input.nextLine();
                        clothing.setColor(color);

                        Clothing c = new Clothing(productId,productName,productPrice,quantity,size,color);
                        this.addProduct(c);
                        break;

                    case 2:
                        Electronic electronic = new Electronic();

                        System.out.println("Enter the brand ");
                        String brand = input.nextLine();
                        electronic.setBrand(brand);
                        input.nextLine();

                        System.out.println("Enter the warranty period from years");
                        int warrantyPeriod = input.nextInt();
                        electronic.setWarrantyPeriod(warrantyPeriod);

                        Electronic e = new Electronic(productId,productName,productPrice,quantity,brand,warrantyPeriod);
                        this.addProduct(e);
                }
                break;
            case 2:
                removeProduct();
                break;
            case 3:
                printProducts(productList);
                break;
            case 4:
                save();
                break;
            case 0:
                System.out.println("Have a good day!!..");
                System.exit(0);

            default:
                System.out.println("invalid input enter valid number");
        }
        return keepGoing;

    }

    public void save() throws IOException {
        FileWriter fileWriter = new FileWriter("save.txt");
        fileWriter.write(String.valueOf(productList));
    }
    public static void load() throws IOException {
        FileReader fr = new FileReader("save.txt");
        int code = fr.read();
        while (code != -1) {
            System.out.print((char) code);

            code = fr.read();
        }
    }

    private boolean validateID(String id) {
        // Two alphabetical letters followed by numeric digits
        if (id.length() == 5) {
            char firstChar = id.charAt(0);
            char secondChar = id.charAt(1);

            if (Character.isLetter(firstChar) && Character.isLetter(secondChar)) {
                String numericPart = id.substring(2);

                if (numericPart.matches("\\d{3}")) {
                    // Check if the remaining characters are numeric
                    return true;
                }
            }
        }

        return false;
    }
    public String getProductId(){
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Enter product Id( Eg: SD123 Two alphabetical letters followed by 3 numeric digit  )");
            String productId = input.nextLine();
            if (!validateID(productId)){
                System.out.println("Invalid Id");
                continue;
            }
            else if (isAllReadyExistsProductID(productId)){
                System.out.println("product id is already exist");
                continue;
            }
            return productId;
        }while (true);
    }

    private boolean isAllReadyExistsProductID(String id){
        for (Product productDetail : productList){
            if(productDetail.getProductId().equals(id)){
              return true;
            }
        }
        return false;
    }
    public static int MainOption() throws IOException {
        Scanner input = new Scanner(System.in);

        boolean keepGoing = false;
        System.out.println("1)Manager Console \n" +
                "2) User Console \n" +
                " 0) Quit "
        );
        System.out.println("--------------------------------------------------");
        int userOptions;
        while (true){
            String userInput = input.nextLine();
            try {
                userOptions = Integer.parseInt(userInput);
                if(userOptions >= 1 && userOptions <=2){
                   /* System.out.println("Enter valid user option");*/
                    break;
                }else {
                    System.out.print("Enter a valid number: ");   // display when input range is incorrect
                }

            }catch (NumberFormatException e){
                System.out.print("Enter a valid number : ");   // display when user entered a sting input
            }
        }
        switch (userOptions){
            case 1 :{
                ShoppingManager shoppingManager = new WestMinisterShoppingManager();
                boolean keepGoing1 = false;
                while (!keepGoing1){
                    keepGoing1 = shoppingManager.userOption();
                }
            }
            case 2:{
                System.out.println("User Console");
                break;
            }
            case 0:
                System.exit(0);
        }
      return userOptions;

    }

    public static void main(String[] args) throws IOException {
      /*  ShoppingManager shoppingManager = new WestMinisterShoppingManager();
        boolean keepGoing = false;
        while (!keepGoing){
            keepGoing = shoppingManager.userOption();
        }*/
        MainOption();
    }

}
