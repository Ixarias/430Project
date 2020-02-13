import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private List<Product> catalog = new LinkedList<Product>();

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

    public List<Product> getCatalogue() {
        return catalog;
    }

}
