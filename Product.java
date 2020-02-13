import java.util.*;
import java.lang.*;
import java.io.*;
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String supplier;
  private double price;


  public Product(String name, String supplier, double price) {
    this.name = name;
    this.supplier = supplier;
    this.price = price;
  }

  public String getName() {
    return name;
  }
  public String getSupplier() {
    return supplier;
  }
  public double getPrice() {
    return price;
  }

  public void setName(String name) {
    this.name = name;
  }
  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }
  public void setPrice(double price) {
    this.price = price;
  }

  public boolean equals(String name) {
    return this.name.equals(name);
  }
  public String toString() {
      return "name " + name + " supplier " + supplier + " price " + price;
  }
}