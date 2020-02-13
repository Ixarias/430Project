import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private List<Product> catalog = new LinkedList<Product>();

    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Supplier(String name, String address, List<Product> catalog) {
        this.name = name;
        this.address = address;
        this.catalog = catalog;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Product> getCatalog() {
        return catalog;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCatalog(List<Product> catalog) {
        this.catalog = catalog;
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    public String toString() {
        return "Supplier name " + name + " address " + address;
    }
}
