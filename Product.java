import java.util.*;
import java.io.*;
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  //private String supplier; // There could be more than one supplier, so we'll have to connect them in a different operation
  private double price;
  private int quantity; // Quantity available
  private List<WaitlistItem> waitlist; // List of WaitlistItems (Product-Client-Quantity pairings)
  private List<Pair> catalog;


  public Product(String name, int quantity, double price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.waitlist = new LinkedList<WaitlistItem>();
    this.catalog = new LinkedList<Pair>();
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
  public void getCatalog() {
    Iterator<Pair> cat = catalog.iterator();
    // search for supplier by name
    // (iterate until supplier.name == name)
    while (cat.hasNext()) {
        Pair thispair = cat.next();
        System.out.println(thispair.toString());
        }
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public void addQuantity(int quantity) {
    this.quantity += quantity;
  }

  public void insertSupplier(Supplier supp, double price) {
    Pair newpair = new Pair(supp, this, price);
    catalog.add(newpair);
  }

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