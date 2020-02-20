import java.util.*;
import java.io.*;
public class CartItem implements Serializable {
  private Product product;
  private int quantity;
  public CartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }
  public Product getProduct() {
    return product;
  }
  public int getQuantity() {
    return quantity;
  }

  public double getPrice() {
    return product.getPrice() * quantity;
  }

}

