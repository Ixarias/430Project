import java.util.*;
import java.text.*;
import java.io.*;
public class ClientState extends WarState {
  private static ClientState clientstate;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;

  // ***** REPLACE THESE CONSTANTS WTIH CORRESPONDING WAREHOUSE EQUIVALENTS *****

  // Constants to implement:
  /***************************************************************************
  show client details for THIS client (client id from WarContext)
  show list of products with sales prices
  show transactions for THIS client (client id from WarContext)
  edit this client's cart (facade provides the iterator)
  add item to this client's shopping cart
  display this client's waitlist
  logout, transitioning to previous state (either OpeningState or ClerkState)
  ****************************************************************************/
  private static final int EXIT = 0;
  private static final int SHOW_CLIENT = 1;
  private static final int SHOW_PRODUCTS = 2;
  private static final int GET_TRANSACTIONS = 3;
  private static final int ADD_TO_CART = 4;
  private static final int EDIT_CART = 5;
  // ***** might need "show cart" *****
  private static final int DISPLAY_WAITLIST = 6;
  private static final int LOGOUT = 9;
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


  // ***** REPLACE THIS WITH CLIENT OPTIONS FROM UserInterface.java *****

  // Help-Prompts to implement:
  /***************************************************************************
  show client details for THIS client (client id from WarContext)
  show list of products with sales prices
  show transactions for THIS client (client id from WarContext)
  edit this client's cart (facade provides the iterator)
  add item to this client's shopping cart
  display this client's waitlist
  logout, transitioning to previous state (either OpeningState or ClerkState)
  ****************************************************************************/

  
  public void help() {
    System.out.println("Enter a number between 0 and 12 as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(SHOW_CLIENT + " to display the details of this client");
    System.out.println(SHOW_PRODUCTS + " to display the catalog of products");
    System.out.println(ADD_TO_CART + " to add an item to this client's cart");
    System.out.println(EDIT_CART + " to edit this client's cart");
    System.out.println(DISPLAY_WAITLIST + " to display this client's waitlist");
    System.out.println(LOGOUT + " to logout of this client's menu");
    System.out.println(HELP + " for help");
  }
  

  // ***** REPLACE THESE FUNCTIONS WITH CLIENT FUNCTIONS FROM UserInterface.java *****

  // Functions to implement:
  /***************************************************************************
  show client details for THIS client (client id from WarContext)
  show list of products with sales prices
  show transactions for THIS client (client id from WarContext)
  edit this client's cart (facade provides the iterator)
  add item to this client's shopping cart
  display this client's waitlist
  logout, transitioning to previous state (either OpeningState or ClerkState)
  ****************************************************************************/

  // Must show only THIS client's details
  public void showClient() {}
  
  // Show full catalog of products with sales prices
  public void showProducts() {}

  // Must show all transactions for THIS client
  public void showTransactions() {}

  // Add item to this client's cart
  public void addToCart() {}

  // Edit this client's cart
  public void editCart() {}

  // ***** Need showCart() *****

  public void displayWaitlist() {}


  // ***** End of functions callable from ClientState UI *****


  // ***** REPLACE THIS WITH PROCESS FUNCTIONS FOR CLIENT FROM UserInterface.java *****
  // Commands to implement:
  /***************************************************************************
  show client details for THIS client (client id from WarContext)
  show list of products with sales prices
  show transactions for THIS client (client id from WarContext)
  edit this client's cart (facade provides the iterator)
  add item to this client's shopping cart
  display this client's waitlist
  logout, transitioning to previous state (either OpeningState or ClerkState)
  ****************************************************************************/
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
        // ***** Need showCart() *****
        case DISPLAY_WAITLIST:  displayWaitlist();
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
    if ((WarContext.instance()).getLogin() == WarContext.IsClerk)
       { //stem.out.println(" going to clerk \n ");
         (WarContext.instance()).changeState(1); // exit with a code 1
        }
    else if (WarContext.instance().getLogin() == WarContext.IsUser)
       {  //stem.out.println(" going to login \n");
        (WarContext.instance()).changeState(0); // exit with a code 2
       }
    else 
       (WarContext.instance()).changeState(2); // exit code 2, indicates error
  }
 
}
