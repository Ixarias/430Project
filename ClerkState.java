import java.util.*;
import java.text.*;
import java.io.*;

public class ClerkState extends WarState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  // private WarContext context; not used
  private static ClerkState instance;

  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int GET_PRODUCTS = 2;
  private static final int GET_CLIENTS = 3;
  private static final int GET_OUTSTANDING_ORDERS = 4;
  private static final int SWITCH_TO_CLIENT = 5;
  private static final int GET_WAITLIST_PRODUCTS = 6;
  private static final int RECEIVE_SHIPMENT = 7;
  private static final int ACCEPT_CLIENT_PAYMENT = 8;
  private static final int SAVE = 9;
  private static final int RETRIEVE = 10;
  private static final int HELP = 11;

  private ClerkState() {
    warehouse = Warehouse.instance();
  }

  public static ClerkState instance() {
    if (instance == null)
      instance = new ClerkState();
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
	/* public void clear() { //clean up stuff
    frame.getContentPane().removeAll();
    frame.paint(frame.getGraphics());   
  }  */
  
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

  //************************** CLIENT FUNCTIONS ******************************//

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

  public void getClients() {
    Iterator<Client> allClients = warehouse.getClients();
    while (allClients.hasNext()) {
      Client client = (Client) (allClients.next());
      System.out.println(client.toString());
    }
  }

  public void getOutstandingOrders() {
    

  }

  public void acceptPayment() {
    String clientId = getToken("Please enter the ID of the client you wish to accept payment");
    if (warehouse.clientExists(clientId)) {
      Double newPayment = Double.parseDouble(getToken("Enter payment: "));
      warehouse.acceptPayment(clientId, newPayment);
    }

    else
      System.out.println("Client ID doesnt exist.");
  } 

  public void clientMenu() {
    String clientID = getToken("Enter client ID: ");
    int cID = Integer.parseInt(clientID);

    if(warehouse.instance().clientExists(clientID)) {
      (WarContext.instance()).setUser(clientID);
		  // clear();
      (WarContext.instance()).changeState(1);
    }

    else 
      System.out.println("Invalid client ID.");
  }

  //************************* PRODUCT FUNCTIONS ******************************//

  public int getWaitListProducts() {
    return 1;
  }

  public void getProducts() {
    Iterator allProducts = warehouse.getProducts();
    while (allProducts.hasNext()) {
      Product product = (Product)(allProducts.next());
      System.out.println(product.toString());
    }
  }

  public void displayWaitlist() {
    String targetProduct = getToken("Please enter Product Name to display its waitlist");
    // get product, iterate through it's waitlist and print each toString'
    Iterator waitlist;
    if (warehouse.getProductByName(targetProduct).getWaitlistItems() != null) {
      waitlist = (warehouse.getProductByName(targetProduct)).getWaitlistItems();
    } else {
      return;
    }
    while (waitlist.hasNext()) {
      WaitlistItem waitlistItem = (WaitlistItem) waitlist.next();
      System.out.println(waitlistItem.toString());
    }
  }

  public void receiveShipment() {
    String targetSupplier = getToken("Please enter Supplier Name to receive a shipment for it");
    String targetProduct = getToken("Please enter Product name");
    String targetQuantity = getToken("Please enter Quantity of product");
    warehouse.receiveShipment(targetSupplier, targetProduct, targetQuantity);
  }

  public void acceptClientPayment() {

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
	  // clear();
		(WarContext.instance()).changeState(1);
	}
    //sales clerk
    else if ((WarContext.instance()).getLogin() == WarContext.IsSalesClerk) 
	{
	  // clear();
		(WarContext.instance()).changeState(2);
	}
    //manager
    else if ((WarContext.instance()).getLogin() == WarContext.IsManager) 
	{
    // clear();
		(WarContext.instance()).changeState(3);
	}
    //error
    else 
	{
		// clear();
		(WarContext.instance()).changeState(0);}
  }

  public void help() {
    System.out.println("Enter a number corresponding to a command as indicated below:");
    System.out.println(EXIT + " to exit");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(GET_PRODUCTS + " to print products");
    System.out.println(GET_CLIENTS + " to print clients");
    System.out.println(GET_OUTSTANDING_ORDERS + " to print clients with outstanding balance");
    System.out.println(SWITCH_TO_CLIENT + " to switch to client");
    System.out.println(GET_WAITLIST_PRODUCTS + " to get products waitlist");
    System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
    System.out.println(ACCEPT_CLIENT_PAYMENT + " to accept client payment");
    System.out.println(SAVE + " to save data");
    System.out.println(RETRIEVE + " to retrieve");
    System.out.println(HELP + " for help");
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:                        addClient();
                                                break;
        case GET_PRODUCTS:                      getProducts();
                                                break;
        case GET_CLIENTS:                       getClients();
                                                break;
        case GET_OUTSTANDING_ORDERS:            getOutstandingOrders();
                                                break;
        case SWITCH_TO_CLIENT:                  clientMenu();
                                                break;
        case GET_WAITLIST_PRODUCTS:             getWaitListProducts();
                                                break;
        case RECEIVE_SHIPMENT:                  receiveShipment();
                                                break;
        case ACCEPT_CLIENT_PAYMENT:             acceptClientPayment();
                                                break;                                       
        case SAVE:                              save();
                                                break;
        case RETRIEVE:                          retrieve();
                                                break;
        case HELP:                              help();
                                                break;
      }
    }
    logout();
  }

  public void run() {
    process();
  }
}