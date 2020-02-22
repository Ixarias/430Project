import java.util.*;
import java.io.*;

public class InvoiceList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Invoice> invoices = new LinkedList<Invoice>(); // Linked list of invoices
    private static InvoiceList InvoiceList;

    private InvoiceList() {
    }

    public static InvoiceList instance() { // check if an instance exists (singleton)
        if (InvoiceList == null) {
            return (InvoiceList = new InvoiceList());
        } else {
            return InvoiceList;
        }
    }

    public boolean insertInvoice(Invoice invoice) {
        invoices.add(invoice);
        return true;
    }

    public Iterator<Invoice> getInvoices() {
        return invoices.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(InvoiceList);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (InvoiceList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (InvoiceList == null) {
                    InvoiceList = (InvoiceList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return invoices.toString();
    }
}