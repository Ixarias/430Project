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
  private static final int HELP = 15;

  private UserInterface() {
    // if (yesOrNo("Look for saved data and use it?")) {
    // retrieve();
    // } else {
    warehouse = Warehouse.instance(); // Instantiate Warehouse singleton
    // }
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
    System.out.println("Enter a number between 0 and 12 as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(SHOW_CLIENTS + " to display list of clients");

  }

  public void addClient() {
    String name = getToken("Enter member name");
    String address = getToken("Enter address");
    String phone = getToken("Enter phone");
    String id = getToken("Enter ID");
    Client result;
    result = warehouse.addClient(name, address, phone, id);
    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println(result);
  }

  public void addProduct() {
    Product result;
    do {
      String name = getToken("Enter product name");
      String supplier = getToken("Enter supplier");
      Double price = Double.parseDouble(getToken("Enter price"));
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
  /*
   * public void addBooks() { Book result; do { String title =
   * getToken("Enter  title"); String bookID = getToken("Enter id"); String author
   * = getToken("Enter author"); result = library.addBook(title, author, bookID);
   * if (result != null) { System.out.println(result); } else {
   * System.out.println("Book could not be added"); } if
   * (!yesOrNo("Add more books?")) { break; } } while (true); } public void
   * issueBooks() { System.out.println("Dummy Action"); } public void renewBooks()
   * { System.out.println("Dummy Action"); }
   * 
   * public void showBooks() { Iterator allBooks = library.getBooks(); while
   * (allBooks.hasNext()){ Book book = (Book)(allBooks.next());
   * System.out.println(book.toString()); } }
   */

  public void showClients() {
    Iterator allClients = warehouse.getClients();
    while (allClients.hasNext()) {
      Client client = (Client) (allClients.next());
      System.out.println(client.toString());
    }
  }

  public void showProducts() {
    Iterator allProducts = warehouse.getProducts();
    while (allProducts.hasNext()) {
      Product product = (Product) (allProducts.next());
      System.out.println(product.toString());
    }
  }

  public void editClientAddress() {
    //Iterator allClients = warehouse.getClients();
    // get client id
    String targetId = getToken("Please enter ID of target client");
    // get new address
    String newAddress = getToken("Please enter new address of client");
    // search for client by id
    // pass information to warehouse (bool function, false if not found)
    if(warehouse.editClientAddress(targetId, newAddress)) {
      System.out.println("Address updated.");
    }
    else {
      System.out.println("Client ID " + targetId + " not found.");
    }
    /*
    while (allClients.hasNext()) {
      Client client = (Client) (allClients.next());
      String id = client.getId();
      System.out.println(id);
      if (client.equals(targetId)) {
        System.out.println("ID " + targetId + " found.");
        client.setAddress(newAddress);
        System.out.println("Address updated.");
        return;
      }

    }
    */
    // end of list reached without finding ID
    

    // set client's address to the new value

  }

  public void editClientPhone() {
    Iterator allClients = warehouse.getClients();
    // get client id
    String id = getToken("Please enter ID of target client");
    // get new phone number
    String phone = getToken("Please enter new phone number of client");
    // set client's phone number to the new value
  }

  public void editProductPrice() {
    Iterator allClients = warehouse.getProducts();
    // get client id
    String name = getToken("Please enter name of target product");
    // get new phone number
    String phone = getToken("Please enter new price for the product");
  }

  /*
   * 
   * public void returnBooks() { System.out.println("Dummy Action"); } public void
   * removeBooks() { System.out.println("Dummy Action"); } public void placeHold()
   * { System.out.println("Dummy Action"); } public void removeHold() {
   * System.out.println("Dummy Action"); } public void processHolds() {
   * System.out.println("Dummy Action"); } public void getTransactions() {
   * System.out.println("Dummy Action"); } private void save() { if
   * (library.save()) { System.out.
   * println(" The library has been successfully saved in the file LibraryData \n"
   * ); } else { System.out.println(" There has been an error in saving \n" ); } }
   */

  /*
   * private void retrieve() { try { Library tempLibrary = Library.retrieve(); if
   * (tempLibrary != null) { System.out.
   * println(" The library has been successfully retrieved from the file LibraryData \n"
   * ); library = tempLibrary; } else {
   * System.out.println("File doesnt exist; creating new library" ); library =
   * Library.instance(); } } catch(Exception cnfe) { cnfe.printStackTrace(); } }
   */

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
