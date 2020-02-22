import java.util.*;
import java.io.*;
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  //private String supplier; // There could be more than one supplier, so we'll have to connect them in a different operation
  private double price;
  private int quantity; // Quantity available
  private List<WaitlistItem> waitlist; // List of WaitlistItems (Product-Client-Quantity pairings)


  public Product(String name, int quantity, double price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.waitlist = new LinkedList<WaitlistItem>();
  }

  public String getName() {
    return name;
  }
  public int getQuantity() { // Quantity available
    return quantity;
  }
  public double getPrice() {
    return price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /*
  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }
  */
  public void setPrice(double price) {
    this.price = price;
  }

  public boolean equals(String name) {
    return this.name.equals(name);
  }
  public String toString() {
      return "name: " + name + " | quantity available: " + quantity + " | price: " + price;
  }
  public String suppliersToString() {
    return "name: " + name + " | quantity available: " + quantity;
  }

  // Wait list
  public boolean addToWaitlist(WaitlistItem waitlistItem) {
    waitlist.add(waitlistItem);
    return true; // if successful
  }

  public Iterator<WaitlistItem> getWaitlistItems() {
    if (waitlist.isEmpty()) {
      System.out.println("There are no items on the waitlist for product: " + this.name);
      return null;
    } else {
      return waitlist.iterator();
    }
  }
}