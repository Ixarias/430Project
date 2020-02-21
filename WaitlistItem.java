// import java.util.*;
import java.io.*;
public class WaitlistItem implements Serializable {
  private static final long serialVersionUID = 1L;
  private Client client;
  private Product product;
  private int quantity;

  // Constructor
  public WaitlistItem(Client client, Product product, int quantity) {
    this.client = client;
    this.product = product;
    this.quantity = quantity;
    System.out.println(toString());
  }

  public Client getClient() {
    return client;
  }

  public Product getProduct() {
    return product;
  }
  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int q) {
    quantity = q;
  }

  public double getPrice() {
    return product.getPrice() * quantity;
  }

  public String toString() {
    return "product name: " + product.getName() + " for client ID: " + client.getId()
    + " | quantity needed: " + quantity + " | price: " + getPrice();
  }
}

