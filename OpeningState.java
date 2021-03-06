import java.util.*;
import java.text.*;
import java.io.*;

public class OpeningState extends WarState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private WarContext context;
  private static OpeningState instance;
  private static final int EXIT = 0;
  private static final int CLIENT_LOGIN = 1;
  private static final int SALES_CLERK_LOGIN = 2;
  private static final int MANAGER_LOGIN = 3;
  private static final int HELP = 4;

  public static OpeningState instance() {
    if (instance == null) {
      instance = new OpeningState();
    }
    return instance;
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" ));
        if ((value >= EXIT) && (value <= HELP)) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
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

  private void clientLogin() {
    String clientID = getToken("Enter client ID: ");
    //int cID = Integer.parseInt(clientID);

    (WarContext.instance()).setLogin(WarContext.IsClient);
    (WarContext.instance()).setUser(clientID);
    (WarContext.instance()).changeState(CLIENT_LOGIN);
  }

  private void salesClerkLogin() {
    String sID = getToken("Enter sales clerk ID: ");
    
    (WarContext.instance()).setLogin(WarContext.IsClerk);
    (WarContext.instance()).setUser(sID);
    (WarContext.instance()).changeState(SALES_CLERK_LOGIN);
  }

  private void managerLogin() {
    String mID = getToken("Enter manager ID: ");
    
    (WarContext.instance()).setLogin(WarContext.IsManager);
    (WarContext.instance()).setUser(mID);
    (WarContext.instance()).changeState(MANAGER_LOGIN);
  }

  public void help() {
    System.out.println("--LOGIN MENU--");
    System.out.println(EXIT + " to exit the program.");
    System.out.println(CLIENT_LOGIN + " Client login. (ID = C1, C2, etc. and pass = integers 1,2,3...)");
    System.out.println(SALES_CLERK_LOGIN + " Sales clerk login. (ID and pass = salesclerk)");
    System.out.println(MANAGER_LOGIN + " Manager login. (ID and pass = manager)");
    System.out.println(HELP + " to display this menu.");
    System.out.println("Enter a number between 0 and 4.");
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case CLIENT_LOGIN:      clientLogin();
                                break;
        case SALES_CLERK_LOGIN: salesClerkLogin();
                                break;
        case MANAGER_LOGIN:     managerLogin();
                                break;
        case HELP:              help();
                                break;
        default:                System.out.println("Invalid choice.");
      }
    }
    (WarContext.instance()).changeState(EXIT);
  }

  public void run() {
      process();
  }
}