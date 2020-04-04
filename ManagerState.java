import java.util.*;
import java.text.*;
import java.io.*;

public class ManagerState extends WarState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarContext context;
  private static ManagerState instance;
  private static final int EXIT = 0;
  private static final int ADD_PRODUCT = 1;
  private static final int ADD_SUPPLIER = 2;
  private static final int GET_SUPPLIERS = 3;
  private static final int DISPLAY_SUPPLIERS_OF_PRODUCT = 4;
  private static final int DISPLAY_PRODUCTS_OF_SUPPLIER = 5;
  private static final int ASSIGN_PRODUCT_TO_SUPPLIER = 6;
  private static final int CHANGE_PRODUCT_PRICE = 7;
  private static final int SWITCH_TO_SALES_CLERK = 8;
  private static final int SAVE = 9;
  private static final int RETRIEVE = 10;
  private static final int HELP = 11;
  private static final String USERNAME = "manager";

  public static ManagerState instance() {
    if (instance == null)
      instance = new ManagerState();
    return instance;
  }
  
  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
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
        int value = Integer.parseInt(getToken("Enter command or " + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number: ");
      }
    } while (true);
  }

  //**************************** MANAGER OPERATIONS **************************//

  public void addProduct() {
    Product result;

      do {
        String name = getToken("Enter product name: ");
        int quantity = getNumber("Enter quantity: ");
        Double price = Double.parseDouble(getToken("Enter Value: "));
  
        result = warehouse.addProduct(name, quantity, price);
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

  public void getSuppliers() {
      Iterator<Supplier> allSuppliers = warehouse.getSuppliers();
      while (allSuppliers.hasNext()) {
        Supplier supplier = (Supplier) (allSuppliers.next());
        System.out.println(supplier.toString());
      }
  }

  public void displaySuppliersOfProduct() {
      String targetName = getToken("Please enter Name of target product: ");
      // display
      if (!warehouse.showSuppliersOfProduct(targetName)) {
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

  public void assignProductToSupplier() {
      // get supplier name
      String targetName = getToken("Please enter Name of target supplier: ");
      // get name of product
      String productName = getToken("Please enter Name of product to be added: ");
      // get suppliers price for product
      float price = Float.parseFloat(getToken("Please enter the suppliers price of the product to be added: "));
      // search for product by name
      Iterator<Product> allProducts = warehouse.getProducts();
      Product product = (Product) (allProducts.next());
      while (allProducts.hasNext() && !(productName.equals(product.getName()))) {
        product = (Product) (allProducts.next());
      }
      if (warehouse.insertProductToSupplier(targetName, product, price)) {
        System.out.println("Product added.");
      } else {
        System.out.println("Supplier Name " + targetName + " not found.");
      }
  }

  public void changeProductPrice() {
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

  public void salesClerkMenu() {
      String sID = getToken("Please enter clerk ID: ");
      String sPW = getToken("Please enter clerk password: ");

        (WarContext.instance()).setLogin(WarContext.IsSalesClerk);
        (WarContext.instance()).setUser(sID);
		clear();
        (WarContext.instance()).changeState(2);
  }

  private void save() {
    if (warehouse.save()) {
      System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
    }
    else {
      System.out.println(" There has been an error in saving \n" );
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

  public void logout() {
    //client
    if ((WarContext.instance()).getLogin() == WarContext.IsClient) 
	{
	  clear();
		(WarContext.instance()).changeState(1);
	}
    //sales clerk
    else if ((WarContext.instance()).getLogin() == WarContext.IsSalesClerk) 
	{
	  clear();
		(WarContext.instance()).changeState(2);
	}
    //manager
    else if ((WarContext.instance()).getLogin() == WarContext.IsManager) 
	{
       clear();
		(WarContext.instance()).changeState(3);
	}
    //error
    else 
	{
		clear();
		(WarehouseContext.instance()).changeState(0);}
  }

  public void help() {
    System.out.println("Enter a number corresponding to a command as indicated below:");
    System.out.println(EXIT + " to exit");
    System.out.println(ADD_PRODUCT + " to add a product to the catalog");
    System.out.println(ADD_SUPPLIER + " to add a supplier");
    System.out.println(GET_SUPPLIERS + " to print suppliers");
    System.out.println(DISPLAY_SUPPLIERS_OF_PRODUCT + " to display products in a suppliers catalog");
    System.out.println(DISPLAY_PRODUCTS_OF_SUPPLIER + " to display products in a suppliers catalog");
    System.out.println(ASSIGN_PRODUCT_TO_SUPPLIER + " to link product to supplier");
    System.out.println(CHANGE_PRODUCT_PRICE + " to change a product price");
    System.out.println(SWITCH_TO_SALES_CLERK + " to switch to sales clerk");
    System.out.println(SAVE + " to save data");
    System.out.println(RETRIEVE + " to retrieve");
    System.out.println(HELP + " for help");
  }


  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_PRODUCT:                   addProduct();
                                            break;
        case ADD_SUPPLIER:                  addSupplier();
                                            break;
        case GET_SUPPLIERS:                 getSuppliers();
                                            break;
        case DISPLAY_SUPPLIERS_OF_PRODUCT:  displaySuppliersOfProduct();
                                            break;
        case DISPLAY_PRODUCTS_OF_SUPPLIER:  displayProductsOfSupplier();
                                            break;
        case ASSIGN_PRODUCT_TO_SUPPLIER:    assignProductToSupplier();
                                            break;
        case CHANGE_PRODUCT_PRICE:          changeProductPrice();
                                            break;
        case SWITCH_TO_SALES_CLERK:         salesClerkMenu();
                                            break;
        case SAVE:                          save();
                                            break;
        case RETRIEVE:                      retrieve();
                                            break;
        case HELP:                          help();
                                            break;
      }
    }
    logout();
  }
  public void run() {
    process();
  }
}