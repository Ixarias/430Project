import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private static final String CLIENT_STRING = "C";
  // private List booksBorrowed = new LinkedList();
  private List<CartItem> cart = new LinkedList<CartItem>(); // Cart containing items and quantities
  private List<Transaction> transactions = new LinkedList<Transaction>();

  public Client(String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public String getAddress() {
    return address;
  }

  public String getId() {
    return id;
  }

  public void setName(String newName) {
    name = newName;
  }

  public void setAddress(String newAddress) {
    address = newAddress;
  }

  public void setPhone(String newPhone) {
    phone = newPhone;
  }

  public boolean equals(String id) {
    return this.id.equals(id);
  }

  public boolean addToCart(CartItem item) {
    cart.add(item);
    return true;
  }

  public Iterator<CartItem> getCartItems() {
    return cart.iterator();
  }

  public Iterator getTransactions(Calendar date) {
    List result = new LinkedList();
    for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
      Transaction transaction = (Transaction) iterator.next();
      if (transaction.onDate(date)) {
        result.add(transaction);
      }
    }
    return (result.iterator());
  }

  public String toString() {
    return "Client name: " + name + " | address: " + address + " | id: " + id + " | phone: " + phone;
  }

  public void processOrder() { // For each item in cart, subtract requested from available quantity. Leftover requested will be waitlisted
    double totalPrice = 0;
    Iterator<CartItem> carti = cart.iterator();

  // for (each item in cart) {
    while (carti.hasNext()) {
      CartItem item = (CartItem) (carti.next());
    
      //  get price from product
      double price = item.getProduct().getPrice(); // Price for one of this item
      String itemName = item.getProduct().getName();
      System.out.println("Processing item: " + itemName);
      //  qty available of product (product waitlist the rest)
      int quantAvailable = item.getProduct().getQuantity(); // Get the quantity available from the Product in the cart
      int quantRequested = item.getQuantity(); // Get the quantity requested by the Order

      System.out.println("available: " + quantAvailable); // for debug
      System.out.println("requested: " + quantRequested);

      // if enough are available:
      if (quantRequested <= quantAvailable) { // Process one item at a time until all that remains are out-of-stock
        totalPrice += (price * quantRequested);
        quantAvailable -= quantRequested;
        quantRequested = 0;
        item.getProduct().setQuantity(quantAvailable); // Update quantity available in catalog
        item.setQuantity(quantRequested);
        System.out.println("CartItem available. Added to order");
      }
      else { // if not enough are available
        totalPrice += (price * quantAvailable);
        quantRequested -= quantAvailable;  // quantRequested now represents the amount leftover to be waitlisted
        quantAvailable = 0; // Out of stock
        item.getProduct().setQuantity(quantAvailable); // Update in-stock quantity to 0
        item.setQuantity(quantRequested); // Update in-cart quantity to how much remains (to be waitlisted)
      }
      System.out.println("available: " + quantAvailable);
      System.out.println("requested: " + quantRequested);
      // Need to wait-list the remaining quantity (quantRequested - quantAvailable)
      if (quantRequested > 0) {
        // add product/quantity to wait-list ***Still need to implement waitlist
        System.out.println("Adding " + quantRequested + " of " + itemName + " to waitlist (NOT IMPLEMENTED)");
        item.setQuantity(0); // Reset in-cart quantity
        // addToWaitlist(quantRequested);
      }
      System.out.println("Total price = " + totalPrice);
      //  (ship product)  
      //  Create an invoice line with productqty, date, cost  (CartItem.toString()?)
      //  Record waitlist entry if needed 
    }
  // Generate grand total 
  // Creates invoice
  }
}
