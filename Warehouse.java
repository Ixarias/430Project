import java.util.*;
import java.io.*;
public class Warehouse implements Serializable {
  private static final long serialVersionUID = 1L;

  //private Catalog catalog;
  private ProductList productList;  
  private ClientList clientList;
  private SupplierList supplierList;
  private InvoiceList invoiceList;
  private static Warehouse warehouse;
  private Warehouse() {
    //catalog = Catalog.instance();
    clientList = ClientList.instance();
    productList = ProductList.instance();
    supplierList = SupplierList.instance();
    invoiceList = InvoiceList.instance();
  }
  public static Warehouse instance() {
    if (warehouse == null) {
      //ClientIdServer.instance(); // instantiate all singletons
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

  public Product addProduct(String name, int quantity, double price) {
    Product product = new Product(name, quantity, price);
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

  public boolean clientExists(String targetId) {
    Iterator<Client> allClients = getClients();
    while (allClients.hasNext()) {
      Client client = (Client)(allClients.next());
      String id = client.getId();
      System.out.println(id);
      if (client.equals(targetId)) {
        //System.out.println("ID " + targetId + " found.");
        return true;
      }
    }
    return false;
  }

  public boolean productExists(String targetName) {
    Iterator<Product> allProducts = getProducts();
    while (allProducts.hasNext()) {
      Product product = (Product)(allProducts.next());
      //String name = product.getName();
      //System.out.println(name);
      if (product.equals(targetName)) {
        //System.out.println("Name " + targetName + " found.");
        return true;
      }
    }
    return false;
  }

  public Product getProductByName(String targetName) {
    Iterator<Product> allProducts = getProducts();
    while (allProducts.hasNext()) {
      Product product = (Product)(allProducts.next());
      if (product.equals(targetName)) {
        //System.out.println("Name " + targetName + " found.");
        return product;
      }
    }
    return null;
  }

  public Supplier getSupplierByName(String targetName) {
    Iterator<Supplier> allSuppliers = getSuppliers();
    while (allSuppliers.hasNext()) {
      Supplier supplier = (Supplier) (allSuppliers.next());
      if (supplier.equals(targetName)) {
        //System.out.println("Supplier " + targetName + " found.");
        return supplier;
      }
    }
    return null;
  }

  public Client getClientById(String targetId) {
    Iterator<Client> allClients = getClients();
    while (allClients.hasNext()) {
      Client client = (Client)(allClients.next());
      //String name = client.getName();
      //System.out.println(name);
      if (client.equals(targetId)) {
        //System.out.println("Client ID " + targetId + " found.");
        return client;
      }
    }
    return null;
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
        //System.out.println("Name " + targetName + " found.");
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
        //System.out.println("Name " + targetName + " found.");
        supplier.setAddress(newAddress);
        return true;
      }
    }
    return false;
  }

  public boolean insertProductToSupplier(String targetName, Product product, double price) {
    Iterator<Supplier> allSuppliers = getSuppliers();
    // search for supplier by name
    // (iterate until supplier.name == name)
    while (allSuppliers.hasNext()) {
      Supplier supplier = (Supplier) (allSuppliers.next());
      String name = supplier.getName();
      System.out.println(name);
      if (supplier.equals(targetName)) {
        //System.out.println("Name " + targetName + " found.");
        supplier.insertProduct(product, price);
        return true;
      }
    }
    return false;
  }

  public boolean showSuppliersOfProduct(String targetName) {
    Iterator<Product> allProducts = getProducts();
    // search for product by name
    // (iterate until product.name == name)
    while (allProducts.hasNext()) {
      Product product = (Product) (allProducts.next());
      // String name = product.getName();
      // System.out.println(name);
      if (product.equals(targetName)) {
        //System.out.println("Name " + targetName + " found.");
        product.getCatalog();
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
      // String name = supplier.getName();
      // System.out.println(name);
      if (supplier.equals(targetName)) {
        //System.out.println("Name " + targetName + " found.");
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

  public boolean addToCart(String clientId, String productName, int quantity) {
    //System.out.println("Dummy function");
    // Client and Product both exist, verified before this function is called
    // First, get the Product so that it can be passed into the CartItem constructor
    Product product = getProductByName(productName);
    // Create a CartItem from Product and quantity, and then add this CartItem to Client.cart
    Client client = getClientById(clientId);
    CartItem item = new CartItem(client, product, quantity);
    client.addToCart(item);
    return true;
  }

  public void displayCart(String clientId) {
    Client client = getClientById(clientId);
    Iterator<CartItem> cart = client.getCartItems();
    // Display ALL items
    while (cart.hasNext()) {
        CartItem cartItem = (CartItem) (cart.next());
        String itemString = cartItem.toString();
        System.out.println(itemString);
      }
  }

  public boolean inCart(String clientId, String targetName) { // Checks if item in client's cart
    Client client = getClientById(clientId);
    return client.inCart(targetName);
  }

  public void editCart(String clientId, String productName, int newQuant) {
    Client client = getClientById(clientId);
    client.editCart(productName, newQuant);
  }

  public boolean processOrder(String clientId) {
    Client client = getClientById(clientId);
    Invoice invoice = client.processOrder();
    invoiceList.insertInvoice(invoice);

    return true; // if successful
  }

  public Iterator<Transaction> getTransactions(String clientID, Calendar date) {
    Client client = clientList.search(clientID);
    if (client == null) {
      return (null);
    }
    return client.getTransactions(date);
  }

  public void acceptPayment(String clientID, Double payment) {
    double balance = 0;
    
    Client client = getClientById(clientID);
    balance = client.getBalance();

    System.out.println("Client ID: " + clientID + " Balance: " + balance);

    balance = balance - payment;
    client.setBalance(balance);
    System.out.println("Client ID: " + clientID + " Balance: " + balance);
  }

  public void receiveShipment(String supp, String prod, String quan) {
    if (warehouse.getSupplierByName(supp) != null) {
      Supplier supplier = warehouse.getSupplierByName(supp);
      // check if product exists from Supplier
      if (supplier.getPair(prod) != null) {
        // Update warehouse quantity
        getProductByName(prod).addQuantity(Integer.parseInt(quan));
      } else {
        System.out.println("Product not found");
      } 
    } else {
      System.out.println("Supplier not found");
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
    return clientList.toString() + productList.toString() + supplierList.toString();
  }

}