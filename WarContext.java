import java.util.*;
import java.text.*;
import java.io.*;
public class WarContext {
  
  private int currentState;
  private static Warehouse warehouse;
  private static WarContext context;
  private int currentUser;
  private String userID;
  private BufferedReader reader = new BufferedReader(new 
                                      InputStreamReader(System.in));
  public static final int IsClerk = 0;
  public static final int IsUser = 1;
  private WarState[] states;
  private int[][] nextState;

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

  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println(" The library has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new library" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }

  public void setLogin(int code)
  {currentUser = code;}

  public void setUser(String uID)
  { userID = uID;}

  public int getLogin()
  { return currentUser;}

  public String getUser()
  { return userID;}

  private WarContext() { //constructor
    System.out.println("In Libcontext constructor");
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
    // set up the FSM and transition table;
    states = new WarState[3];
    states[0] = ClerkState.instance();
    states[1] = ClientState.instance(); 
    states[2]=  OpeningState.instance();
    nextState = new int[3][3];
    nextState[0][0] = 2;nextState[0][1] = 1;nextState[0][2] = -2;
    nextState[1][0] = 2;nextState[1][1] = 0;nextState[1][2] = -2;
    nextState[2][0] = 0;nextState[2][1] = 1;nextState[2][2] = -1;
    currentState = 2;
  }

  public void changeState(int transition)
  {
    //System.out.println("current state " + currentState + " \n \n ");
    currentState = nextState[currentState][transition];
    if (currentState == -2) 
      {System.out.println("Error has occurred"); terminate();}
    if (currentState == -1) 
      terminate();
    //System.out.println("current state " + currentState + " \n \n ");
    states[currentState].run();
  }

  private void terminate()
  {
   if (yesOrNo("Save data?")) {
      if (warehouse.save()) {
         System.out.println(" The library has been successfully saved in the file LibraryData \n" );
       } else {
         System.out.println(" There has been an error in saving \n" );
       }
     }
   System.out.println(" Goodbye \n "); System.exit(0);
  }

  public static WarContext instance() {
    if (context == null) {
       System.out.println("calling constructor");
      context = new WarContext();
    }
    return context;
  }

  public void process(){
    states[currentState].run();
  }
  
  public static void main (String[] args){
    WarContext.instance().process(); 
  }


}
