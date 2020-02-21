// import java.util.*;
import java.io.*;
public class CartItem implements Serializable {
  private static final long serialVersionUID = 1L;
  private Client client;
  private Product product;
  private int quantity;
  public CartItem(Client client, Product product, int quantity) {
    // this.product should be changed to reference the product instead of storing it
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
    return "name: " + product.getName()+ " | quantity: " + quantity + " | price: " + getPrice();
  }
}

