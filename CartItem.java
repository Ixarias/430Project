// import java.util.*;
import java.io.*;
public class CartItem {
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

  public String toString() {
    return "name: " + product.getName()+ " | quantity: " + quantity + " | price: " + getPrice();
  }
}

