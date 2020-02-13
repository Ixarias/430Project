import java.util.*;
import java.lang.*;
import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String supplier;
  private float price;

  public Product(String name, String supplier, float price) {
    this.name = name;
    this.supplier = supplier;
    this.price = price;
  }

  public String getSupplier() {
    return supplier;
  }

  public String getName() {
    return name;
  }

  public float getPrice() {
    return price;
  }

  public String toString() {
    return "name " + name + " supplier " + supplier + " price " + price;
  }
}