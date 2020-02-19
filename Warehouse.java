import java.util.*;
import java.io.*;
public class Warehouse implements Serializable {
  private static final long serialVersionUID = 1L;

  //private Catalog catalog;
  private ProductList productList;  
  private ClientList clientList;
  private SupplierList supplierList;
  private static Warehouse warehouse;
  private Warehouse() {
    //catalog = Catalog.instance();
    clientList = ClientList.instance();
    productList = ProductList.instance();
    supplierList = SupplierList.instance();
  }
  public static Warehouse instance() {
    if (warehouse == null) {
      //MemberIdServer.instance(); // instantiate all singletons
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }
  
  public Client addClient(String name, String address, String phone) {
    Client client = new Client(name, address, phone);
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

  public Supplier addSupplier(String name, String address) {
    Supplier supplier = new Supplier(name, address);
    if (supplierList.insertSupplier(supplier)) {
      return (supplier);
    }
    return null;
  }

  public Iterator<Client> getClients() {
      return clientList.getClients();
  }

  public Iterator<Product> getProducts() {
    return productList.getProducts();
}

  public Iterator<Supplier> getSuppliers() {
    return supplierList.getSuppliers();
  }

  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input); 
      input.close();
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
    Iterator<Client> allClients = getClients();
    // search for client by id
    // (iterate until client.id == id)
    while (allClients.hasNext()) {
      Client client = (Client)(allClients.next());
      String id = client.getId();
      System.out.println(id);
      if (client.equals(targetId)) {
        //System.out.println("ID " + targetId + " found.");
        client.setAddress(newAddress);
        return true;
      }
    }
    return false;
  }

  public boolean editClientPhone(String targetId, String newPhone) {
    Iterator<Client> allClients = getClients();
    // search for client by id
    // (iterate until client.id == id)
    while (allClients.hasNext()) {
      Client client = (Client)(allClients.next());
      String id = client.getId();
      System.out.println(id);
      if (client.equals(targetId)) {
        //System.out.println("ID " + targetId + " found.");
        client.setPhone(newPhone);
        return true;
      }
    }
    return false;
  }

  public boolean editProductPrice(String targetName, double newPrice) {
    Iterator<Product> allProducts = getProducts();
      
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

  public boolean editSupplierAddress(String targetName, String newAddress) {
    Iterator<Supplier> allSuppliers = getSuppliers();
    // search for supplier by name
    // (iterate until supplier.name == name)
    while (allSuppliers.hasNext()) {
      Supplier supplier = (Supplier) (allSuppliers.next());
      String name = supplier.getName();
      System.out.println(name);
      if (supplier.equals(targetName)) {
        System.out.println("Name " + targetName + " found.");
        supplier.setAddress(newAddress);
        return true;
      }
    }
    return false;
  }

  public boolean insertProductToSupplier(String targetName, Product product) {
    Iterator<Supplier> allSuppliers = getSuppliers();
    // search for supplier by name
    // (iterate until supplier.name == name)
    while (allSuppliers.hasNext()) {
      Supplier supplier = (Supplier) (allSuppliers.next());
      String name = supplier.getName();
      System.out.println(name);
      if (supplier.equals(targetName)) {
        System.out.println("Name " + targetName + " found.");
        supplier.insertProduct(product);
        return true;
      }
    }
    return false;
  }

  public boolean showProductsOfSupplier(String targetName) {
    Iterator<Supplier> allSuppliers = getSuppliers();
    // search for supplier by name
    // (iterate until supplier.name == name)
    while (allSuppliers.hasNext()) {
      Supplier supplier = (Supplier) (allSuppliers.next());
      String name = supplier.getName();
      System.out.println(name);
      if (supplier.equals(targetName)) {
        System.out.println("Name " + targetName + " found.");
        supplier.getCatalog();
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
      output.writeObject(ClientIdServer.instance());
      output.close();
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }

  public boolean addToCart(String clientId, String productId, int quantity) {
    System.out.println("Dummy function");
    return true;
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
    return clientList.toString() + productList.toString() + supplierList.toString();
  }
}
