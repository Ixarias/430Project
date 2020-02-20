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

  public void processCart() {
    Iterator<CartItem> carti = cart.iterator();

  // for (each item in cart) {
  //  get price from product
  //  qty available of product (product waitlist the rest)
  //  (ship product)  
  //  Create an invoice line with productqty, date, cost
  //  Record waitlist entry if needed 
  //  }
  // Generate grand total 
  // Creates invoice
  }
}
