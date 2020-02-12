import java.util.*;
import java.lang.*;
import java.io.*;
public class ProductList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List products = new LinkedList();
  private static ProductList productlist;
  private ProductList() {
  }
  public static ProductList instance() {
    if (productlist == null) {
      return (productlist = new ProductList());
    } else {
      return productlist;
    }
  }
  
  public boolean insertProduct(Product product) {
    products.add(product);
    return true;
  }
  public Iterator getProducts() {
    return products.iterator();
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(productlist);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (productlist != null) {
        return;
      } else {
        input.defaultReadObject();
        if (productlist == null) {
            productlist = (Productlist) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      System.out.println("in Catalog readObject \n" + ioe);
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return products.toString();
  }
}