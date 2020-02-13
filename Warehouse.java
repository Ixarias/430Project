import java.util.*;
import java.io.*;
public class Warehouse implements Serializable {
  private static final long serialVersionUID = 1L;

  //private Catalog catalog;
  private ProductList productList;  
  private ClientList clientList;
  private static Warehouse warehouse;
  private Warehouse() {
    //catalog = Catalog.instance();
    clientList = ClientList.instance();
    productList = ProductList.instance();
  }
  public static Warehouse instance() {
    if (warehouse == null) {
      //MemberIdServer.instance(); // instantiate all singletons
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }
  
  /*
  public Book addBook(String title, String author, String id) {
    Book book = new Book(title, author, id);
    if (catalog.insertBook(book)) {
      return (book);
    }
    return null;
  }
  */
  public Client addClient(String name, String address, String phone, String id) {
    Client client = new Client(name, address, phone, id);
    if (clientList.insertClient(client)) {
      return (client);
    }
    return null;
  }

  public Product addProduct(String name, String supplier, double price) {
    Product product = new Product(name, supplier, price);
    if (productList.insertProduct(product)) {
      return (product);
    }
    return null;
  }

/*
  public Iterator getBooks() {
      return catalog.getBooks();
  }
*/

  public Iterator getClients() {
      return clientList.getClients();
  }

  public Iterator getProducts() {
    return productList.getProducts();
}

  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      //ClientIdServer.retrieve(input);
      return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }



  public boolean editClientAddress(String targetId, String newAddress) {
    Iterator allClients = getClients();
    // search for client by id
    // (iterate until client.id == id)
    while (allClients.hasNext()) {
      Client client = (Client)(allClients.next());
      String id = client.getId();
      System.out.println(id);
      if (client.equals(targetId)) {
        System.out.println("ID " + targetId + " found.");
        client.setAddress(newAddress);
        return true;
      }
    }
    return false;
  }

  public boolean editProductPrice(String targetName, double newPrice) {
    Iterator allProducts = getProducts();
      
    while (allProducts.hasNext()) {
      Product product = (Product)(allProducts.next());
      String name = product.getName();
      System.out.println(name);
      if (product.equals(targetName)) {
        System.out.println("Name " + targetName + " found.");
        product.setPrice(newPrice);
        return true;
      }
    }
    return false;
  }

  
  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      //output.writeObject(ClientIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public String toString() {
    return /*catalog + "\n" + */ clientList.toString() + productList.toString();
  }
}
