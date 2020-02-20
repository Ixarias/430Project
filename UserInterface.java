import java.util.*;
import java.text.*;
import java.io.*;
public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int SHOW_CLIENTS = 2;
  private static final int EDIT_CLIENT_ADDRESS = 3;
  private static final int EDIT_CLIENT_PHONE = 4;
  private static final int ADD_PRODUCT = 5;
  private static final int SHOW_PRODUCTS = 6;
  private static final int EDIT_PRODUCT_PRICE = 7;
  private static final int ADD_SUPPLIER = 8;
  private static final int SHOW_SUPPLIERS = 9;
  private static final int EDIT_SUPPLIERS_ADDRESS = 10;
  private static final int ADD_PRODUCT_TO_SUPPLIER = 11;
  private static final int DISPLAY_PRODUCTS_OF_SUPPLIER = 12;
  private static final int ADD_TO_CART = 13;
  private static final int DISPLAY_CART = 14;
  private static final int SAVE = 18;
  private static final int RETRIEVE = 19;
  private static final int HELP = 20;

  private UserInterface() {
    if (yesOrNo("Look for saved data and use it?")) {
    retrieve();
    } else {
      warehouse = Warehouse.instance(); // Instantiate Warehouse singleton
    }
  }

  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }

  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }

  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }

  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number corresponding to a command as indicated below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(SHOW_CLIENTS + " to display list of clients");
    System.out.println(EDIT_CLIENT_ADDRESS + " to edit the address of a client");
    System.out.println(EDIT_CLIENT_PHONE + " to edit the phone number of a client");
    System.out.println(ADD_PRODUCT + " to add a product to the catalog");
    System.out.println(SHOW_PRODUCTS + " to display the list of products in the catalog");
    System.out.println(EDIT_PRODUCT_PRICE + " to edit the sales price of a product");
    System.out.println(ADD_SUPPLIER + " to add a supplier");
    System.out.println(SHOW_SUPPLIERS + " to display the list of suppliers");
    System.out.println(EDIT_SUPPLIERS_ADDRESS + " to edit the address of a supplier");
    System.out.println(ADD_PRODUCT_TO_SUPPLIER + " to add products to a suppliers catalog");
    System.out.println(DISPLAY_PRODUCTS_OF_SUPPLIER + " to display products in a suppliers catalog");
    System.out.println(ADD_TO_CART + " to add a product to a client's cart");
    System.out.println(DISPLAY_CART + " to view the items in a client's cart");
    System.out.println(SAVE + " to save changes to a file");
    System.out.println(RETRIEVE + " to retrieve");
    System.out.println(HELP + " for help");    
  }

  public void addClient() {
    String name = getToken("Enter client name: ");
    String address = getToken("Enter address: ");
    String phone = getToken("Enter phone number: ");
    //String id = getToken("Enter ID: ");
    Client result;
    result = warehouse.addClient(name, address, phone);
    if (result == null) {
      System.out.println("Could not add member");
    }
    else {
    System.out.println(result);
    }
  }

  public void addProduct() {
    Product result;

    do {
      String name = getToken("Enter product name: ");
      String supplier = getToken("Enter supplier: ");
      Double price = Double.parseDouble(getToken("Enter Value: "));

      result = warehouse.addProduct(name, supplier, price);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Product could not be added");
      }
      if (!yesOrNo("Add more Products?")) {
        break;
      }
    } while (true);
  }

  public void addSupplier() {
      String name = getToken("Enter supplier name: ");
      String address = getToken("Enter address: ");
      Supplier result = warehouse.addSupplier(name, address);
      if (result == null) {
        System.out.println("Unable to add supplier");
      } else {
        System.out.println(result);
      }
  }

  public void showClients() {
    Iterator<Client> allClients = warehouse.getClients();
    while (allClients.hasNext()) {
      Client client = (Client) (allClients.next());
      System.out.println(client.toString());
    }
  }

  public void showProducts() {
    Iterator<Product> allProducts = warehouse.getProducts();
    while (allProducts.hasNext()){
        Product product = (Product)(allProducts.next());
        System.out.println(product.toString());
    }
  }

  public void showSuppliers() {
    Iterator<Supplier> allSuppliers = warehouse.getSuppliers();
    while (allSuppliers.hasNext()) {
      Supplier supplier = (Supplier) (allSuppliers.next());
      System.out.println(supplier.toString());
    }
  }

  public void editClientAddress() {
    //Iterator allClients = warehouse.getClients();
    // get client id
    String targetId = getToken("Please enter ID of target client: ");
    // get new address
    String newAddress = getToken("Please enter new address of client: ");
    // search for client by id
    // pass information to warehouse (bool function, false if not found)
    if(warehouse.editClientAddress(targetId, newAddress)) {
      System.out.println("Address updated.");
    }
    else {
      System.out.println("Client ID " + targetId + " not found.");
    }
  }

  public void editClientPhone() {
    // get client id
    String targetId = getToken("Please enter ID of target client: ");
    // get new phone number
    String newPhone = getToken("Please enter new phone number of client: ");
    // set client's phone number to the new value
    if(warehouse.editClientPhone(targetId, newPhone)) {
      System.out.println("Phone number updated.");
    }
    else {
      System.out.println("Client ID " + targetId + " not found.");
    }
  }

  public void editProductPrice() {
    //Iterator allProducts = warehouse.getProducts();
  
    String name = getToken("Please enter name of target product: ");
    Double price = Double.parseDouble(getToken("Enter new product price: "));

    if(warehouse.editProductPrice(name, price)) {
      System.out.println("Price updated.");
    }
    else {
      System.out.println("Product name " + name + " not found.");
    }
  }

  public void editSupplierAddress() {
    // get supplier name
    String targetName = getToken("Please enter Name of target supplier: ");
    // get new address
    String newAddress = getToken("Please enter new address of supplier: ");
    // search for supplier by name
    // pass information to warehouse (bool function, false if not found)
    if (warehouse.editSupplierAddress(targetName, newAddress)) {
      System.out.println("Address updated.");
    } else {
      System.out.println("Supplier Name " + targetName + " not found.");
    }
  }

  public void addProductToSupplier() {
    // get supplier name
    String targetName = getToken("Please enter Name of target supplier: ");
    // get name of product
    String productName = getToken("Please enter Name of product to be added: ");
    // search for product by name
    Iterator<Product> allProducts = warehouse.getProducts();
    Product product = (Product) (allProducts.next());
    while (allProducts.hasNext() && !(productName.equals(product.getName()))) {
      product = (Product) (allProducts.next());
    }
    if (warehouse.insertProductToSupplier(targetName, product)) {
      System.out.println("Product added.");
    } else {
      System.out.println("Supplier Name " + targetName + " not found.");
    }
  }

  public void displayProductsOfSupplier() {
    String targetName = getToken("Please enter Name of target supplier: ");
    // display
    if (!warehouse.showProductsOfSupplier(targetName)) {
      System.out.println("Supplier Name " + targetName + " not found.");
    }
  }

  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new warehouse" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }

  private void save() {
    System.out.println("Save selected. Saving to file WarehouseData...");
    if (warehouse.save()) {
      System.out.println("Save successful");
    }
    else {
      System.out.println("Error: Save failed");
    }
  }

  private void addToCart() {  // Will need: clientId, productName, quantity
    System.out.println("Add to Cart selected.");
    String clientId = getToken("Enter Client ID");
    String productName = getToken("Enter Product Name");
    int quantity = getNumber("Enter quantity");

    // Check if client exists
    if (!warehouse.clientExists(clientId)) {
      System.out.println("Error: client not found");
      return; // Stop here if not found
    }
    else {
      System.out.println("ID " + clientId + " found.");
    }

    // Check if product exists
    if (!warehouse.productExists(productName)) {
      System.out.println("Error: product not found");
      return; // Stop here if not found
    }
    else {
      System.out.println("Name " + productName + " found.");
    }

    // Next, instantiate a CartItem object and add it to the Client's cart (CartItem list)
    if (warehouse.addToCart(clientId, productName, quantity)) {
      System.out.println("Successfully added item to cart");
    }
    else {
      System.out.println("Error: failed to add item to cart");
    }
  }

  public void displayCart() {
    String clientId = getToken("Please enter ID of target client: ");
    //warehouse.displayCart(clientId);

    Iterator<CartItem> cart = warehouse.getClientById(clientId).getCartItems();
    while (cart.hasNext()) {
      CartItem item = (CartItem) (cart.next());
      System.out.println(item.toString());
    }

  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
      case ADD_CLIENT:
        addClient();
        break;
      case SHOW_CLIENTS:
        showClients();
        break;
      case EDIT_CLIENT_ADDRESS:
        editClientAddress();
        break;
      case EDIT_CLIENT_PHONE:
        editClientPhone();
        break;
      case ADD_PRODUCT:
        addProduct();
        break;
      case SHOW_PRODUCTS:
        showProducts();
        break;
      case EDIT_PRODUCT_PRICE:
        editProductPrice();
        break;
      case ADD_SUPPLIER:
        addSupplier();
        break;
      case SHOW_SUPPLIERS:
        showSuppliers();
        break;
      case EDIT_SUPPLIERS_ADDRESS:
        editSupplierAddress();
        break;
      case ADD_PRODUCT_TO_SUPPLIER:
        addProductToSupplier();
        break;
      case DISPLAY_PRODUCTS_OF_SUPPLIER:
        displayProductsOfSupplier();
        break;
      case ADD_TO_CART:
        addToCart(); // Dummy function; will need arguments
        break;
      case DISPLAY_CART:
        displayCart();
        break;
      case SAVE:
        save();
        break;
      case RETRIEVE:
        retrieve();
        break;
      case HELP:
        help();
        break;
      }
    }
  }

  public static void main(String[] s) {
    UserInterface.instance().process();
  }
}
