// ***** Still need: implement "showWaitlist()" function

import java.util.*;
import java.text.*;
import java.io.*;
public class ClientState extends WarehouseState {
  private static ClientState clientstate;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  private static final int EXIT = 0;
  private static final int SHOW_CLIENT = 1;
  private static final int SHOW_PRODUCTS = 2;
  private static final int GET_TRANSACTIONS = 3;
  private static final int ADD_TO_CART = 4;
  private static final int EDIT_CART = 5;
  private static final int SHOW_CART = 6;
  private static final int SHOW_WAITLIST = 7;
  private static final int HELP = 10;

  // Constructor
  private ClientState() {
    warehouse = Warehouse.instance();
  }

  // Singleton guard
  public static ClientState instance() {
    if (clientstate == null) {
      return clientstate = new ClientState();
    } else {
      return clientstate;
    }
  }

  // Input/Output functions

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
    System.out.println(EXIT + " to Logout/Exit State\n");
    System.out.println(SHOW_CLIENT + " to display the details of this client");
    System.out.println(SHOW_PRODUCTS + " to display the catalog of products");
    System.out.println(ADD_TO_CART + " to add an item to this client's cart");
    System.out.println(EDIT_CART + " to edit this client's cart");
    System.out.println(SHOW_CART + " to display this client's cart");
    System.out.println(SHOW_WAITLIST + " to display this client's waitlist");
    System.out.println(HELP + " for help");
  }

  // Show only THIS client's details
  public void showClient() {
    String id = WarehouseContext.instance().getUser();
    Iterator<Client> allClients = warehouse.getClients();
    while (allClients.hasNext()) {
      Client client = (Client) (allClients.next());
      if (client.getId().equals(id)) {
        System.out.println(client.toString());
        return;
      }
    }
  }
  
  // Show full catalog of products with sales prices
  public void showProducts() {
    Iterator<Product> allProducts = warehouse.getProducts();
    while (allProducts.hasNext()) {
      Product product = (Product) (allProducts.next());
      System.out.println(product.toString());
    }
  }

  // Must show all transactions for THIS client
  public void showTransactions() {
    Iterator result;
    String clientId = WarehouseContext.instance().getUser();
    Calendar date = getDate("Please enter the date for which you want records as mm/dd/yy");
    result = warehouse.getTransactions(clientId, date);
    if (result == null) {
      System.out.println("Invalid Member ID");
    } else {
      while (result.hasNext()) {
        Transaction transaction = (Transaction) result.next();
        System.out.println(transaction.getType() + "   " + transaction.getTitle() + "\n");
      }
      System.out.println("\n  There are no more transactions \n");
    }
  }

  // Add item to this client's cart
  public void addToCart() {
    System.out.println("Add to Cart selected.");
    String clientId = WarehouseContext.instance().getUser();
    String productName = getToken("Enter Product Name");
    int quantity = getNumber("Enter quantity");

    // Check if client exists
    if (!warehouse.clientExists(clientId)) {
      System.out.println("Error: client not found");
      return; // Stop here if not found
    } else {
      System.out.println("ID " + clientId + " found.");
    }

    // Check if product exists
    if (!warehouse.productExists(productName)) {
      System.out.println("Error: product not found");
      return; // Stop here if not found
    } else {
      System.out.println("Name " + productName + " found.");
    }

    // Next, instantiate a CartItem object and add it to the Client's cart (CartItem
    // list)
    if (warehouse.addToCart(clientId, productName, quantity)) {
      System.out.println("Successfully added item to cart");
    } else {
      System.out.println("Error: failed to add item to cart");
    }
  }

  // Edit this client's cart
  public void editCart() {
    String clientId = WarehouseContext.instance().getUser();
    if (warehouse.clientExists(clientId)) {  // Check if client exists
      warehouse.displayCart(clientId);
      String productName = getToken("Please enter the name of the product in the cart");
      if (warehouse.inCart(clientId, productName)) {  // Check if product exists in the client's cart **** CHANGE CODE TO REFLECT THIS ****
        int newQuant = getNumber("Please enter a new quantity for " + productName + ", or 0 to remove");
        warehouse.editCart(clientId, productName, newQuant);
      }
    }
    else {
      System.out.println("ID not found");
    }
  }

  public void showCart() {
    String clientId = WarehouseContext.instance().getUser();
    // warehouse.displayCart(clientId);

    Iterator cart = (warehouse.getClientById(clientId)).getCartItems();
    while (cart.hasNext()) {
      CartItem item = (CartItem) cart.next();
      System.out.println(item.toString());
    }
  }

  public void showWaitlist() {
    // *************** STILL NEED ******************
    // Waitlists are stored per-product, so we'll need to 
    //  loop through all products, checking to see if this
    //  client's ID is present in any product waitlists
    //  and displaying the requested quantity for each
  }


  // ***** End of functions callable from ClientState UI *****

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {

        case SHOW_CLIENT:       showClient();
                                break;
        case SHOW_PRODUCTS:     showProducts();
                                break;
        case GET_TRANSACTIONS:  showTransactions();
                                break;
        case ADD_TO_CART:       addToCart();
                                break;
        case EDIT_CART:         editCart();
                                break;
        case SHOW_CART:         showCart();
                                break;
        case SHOW_WAITLIST:  showWaitlist();
                                break;
        case HELP:              help();
                                break;
      }
    }
    logout();  // Logout must occur OUTSIDE the loop, as shown
  }
  
  // Start prompts for ClientState
  public void run() {
    process();
  }

  public void logout()
  {
    if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClerk)
       { //stem.out.println(" going to clerk \n ");
         (WarehouseContext.instance()).changeState(1); // exit with a code 1
        }
    else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsUser)
       {  //stem.out.println(" going to login \n");
        (WarehouseContext.instance()).changeState(0); // exit with a code 2
       }
    else 
       (WarehouseContext.instance()).changeState(2); // exit code 2, indicates error
  }
 
}
