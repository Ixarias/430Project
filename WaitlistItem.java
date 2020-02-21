// import java.util.*;
import java.io.*;
public class WaitlistItem implements Serializable {
  private static final long serialVersionUID = 1L;
  private Client client;
  private Product product;
  private int quantity;
  public WaitlistItem(Client client, Product product, int quantity) {
    this.client = client;
    this.product = product;
    this.quantity = quantity;
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
    return "for client ID: " + client.getId() + "product name: " + product.getName() 
    + " | quantity needed: " + quantity + " | price: " + getPrice();
  }
}

