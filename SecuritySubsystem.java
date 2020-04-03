import java.util.*;
import java.text.*;
import java.io.*;

public class SecuritySubsystem {
  private WarehouseContext warehouseContext;
  private Warehouse warehouse;
  private static SecuritySubsystem instance;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  private SecuritySubsystem() {
    super();
    warehouseContext = WarehouseContext.instance();
    warehouse = Warehouse.instance();
  }

  public static SecuritySubsystem instance() {
    if (instance == null)
      instance = new SecuritySubsystem();
    return instance;
  }

  public boolean validateClient(int id, int pass) {
    Client client = null;
    Iterator allClients = warehouse.getClients();

    while (allClients.hasNext()) {
      Client currClient = (Client)allClients.next();
      if (currClient.getID() == id) {
        client = currClient;
        break;
      }
    }
    if (client == null) {
      System.out.println("Invalid client ID.");
      return false;
    }
    if (id != pass) {
      System.out.println("Invalid password.");
      return false;
    }
    return true;
  }

  public boolean validateSalesClerk(String id, String pass) {
    if ((id.equals("salesclerk")) && (pass.equals("salesclerk")))
      return true;
    else {
      System.out.println("Invalid sales clerk username or password.");
      return false;
    }
  }

  public boolean validateManager(String id, String pass) {
    if ((id.equals("manager")) && (pass.equals("manager")))
      return true;
    else {
      System.out.println("Invalid manager username or password.");
      return false;
    }
  }

  public boolean verifyPassword(String id, String pass) {
    if (id.equals("manager")) {
      if (pass.equals("manager"))
        return true;
      else {
        System.out.println("Invalid password.");
        return false;
      }
    }
    return false;
  }
}
