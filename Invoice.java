import java.util.*;
import java.io.*;

public class Invoice implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private int grandTotal;
  private static final String INVOICE_STRING = "I";

  public Invoice(String id, int grandTotal) {
    this.id = INVOICE_STRING + (InvoiceIdServer.instance()).getId();
    this.grandTotal = grandTotal;
  }

  public String getId() {
    return id;
  }

  public int getTotal() {
    return grandTotal;
  }

  public void setTotal(int newTotal) {
    grandTotal = newTotal;
  }    

  public boolean equals(String id) {
    return this.id.equals(id);
  }

  public String toString() {
    return "Invoice id: " + id + " | Grand Total = " + grandTotal;
  }
}