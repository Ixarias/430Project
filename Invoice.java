import java.util.*;
import java.io.*;

public class Invoice implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private double grandTotal;
  private static final String INVOICE_STRING = "I";
  private List<CartItem> cart;

  public Invoice(double grandTotal, List<CartItem> cart) {
    this.id = INVOICE_STRING + (InvoiceIdServer.instance()).getId();
    this.grandTotal = grandTotal;
    this.cart = cart;
  }

  public String getId() {
    return id;
  }

  public double getTotal() {
    return grandTotal;
  }

  public void setTotal(double newTotal) {
    grandTotal = newTotal;
  }    

  public boolean equals(String id) {
    return this.id.equals(id);
  }

  public void invoicePrdouble() { 
    Iterator<CartItem> carti = cart.iterator();
    // Display ALL items
    while (carti.hasNext()) {
      CartItem cartItem = (CartItem) (carti.next());
      String itemString = cartItem.toString();
      System.out.println(itemString);
    }
    System.out.println("Invoice id: " + id + " | Grand Total = " + grandTotal);
  }
}