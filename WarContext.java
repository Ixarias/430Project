import java.util.*;
import java.io.*;

public class WarContext {
  private int currentState;
  private static Warehouse warehouse;
  private static WarContext context;
  private int currentUser;
  private String userID;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  public static final int IsLogin = 0;
  public static final int IsClient = 1;
  public static final int IsClerk = 2;
  public static final int IsManager = 3;
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
        System.out.println("The warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new warehouse");
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
        cnfe.printStackTrace();
      }
  }

  public void setLogin(int code) {
    currentUser = code;
  }
  public void setUser(String uID) {
    userID = uID;
  }
  public int getLogin() {
    return currentUser;
  }
  public String getUser() {
    return userID;
  }

  private WarContext() { //constructor
    System.out.println("In Warehouse context constructor.");
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
    // set up the FSM and transition table;
    states = new WarState[4];
    states[0] = OpeningState.instance();            //0 login state
    states[1] = ClientState.instance();           //1 user state
    states[2] = ClerkState.instance();       //2 clerk state
    states[3] = ManagerState.instance();          //3 manager state
    nextState = new int[4][4];                    //-2 = error, -1 = logout
    nextState[0][0] = -1;nextState[0][1] = 1;nextState[0][2] = 2;nextState[0][3] = 3;
    nextState[1][0] = 0;nextState[1][1] = 0;nextState[1][2] = 2;nextState[1][3] = 3;
    nextState[2][0] = 0;nextState[2][1] = 1;nextState[2][2] = 0;nextState[2][3] = 3;
    nextState[3][0] = 0;nextState[3][1] = 1;nextState[3][2] = 2;nextState[3][3] = 0;
    currentState = 0;
  }

  public void changeState(int transition) {
    currentState = nextState[currentState][transition];
    if (currentState == -2)
      {System.out.println("Error has occurred"); terminate();}
    if (currentState == -1)
      terminate();
    states[currentState].run();
  }

  private void terminate()
  {
   if (yesOrNo("Save data?")) {
      if (warehouse.save()) {
         System.out.println("The warehouse has been successfully saved in the file WarehouseData.\n");
       } else {
         System.out.println("There has been an error in saving.\n");
       }
     }
   System.out.println("Goodbye.\n"); System.exit(0);
  }

  public static WarContext instance() {
    if (context == null) {
        System.out.println("calling constructor...");
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
