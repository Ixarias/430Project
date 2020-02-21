// import java.util.*;
import java.io.*;
public class CartItem implements Serializable {
  private static final long serialVersionUID = 1L;
  private Product product;
  private int quantity;
  public CartItem(Product product, int quantity) {
    // this.product should be changed to reference the product instead of storing it
    this.product = product;
    this.quantity = quantity;
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

