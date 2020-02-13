import java.util.*;
import java.io.*;

public class SupplierList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Supplier> suppliers = new LinkedList<Supplier>(); // Linked list of suppliers
    private static SupplierList SupplierList;

    private SupplierList() {
    }

    public static SupplierList instance() { // check if an instance exists (singleton)
        if (SupplierList == null) {
            return (SupplierList = new SupplierList());
        } else {
            return SupplierList;
        }
    }

    public boolean insertSupplier(Supplier supplier) {
        suppliers.add(supplier);
        return true;
    }

    public Iterator getSuppliers() {
        return suppliers.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(SupplierList);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (SupplierList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (SupplierList == null) {
                    SupplierList = (SupplierList) input.readObject();
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
        return suppliers.toString();
    }
}
