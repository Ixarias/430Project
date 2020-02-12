import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private List<Product> catalogue = new LinkedList<Product>();

    public Supplier (String name, String address, List<Product> catalogue) {
        this.name = name;
        this.address = address;
        this.catalogue = catalogue;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Product> getCatalogue() {
        return catalogue;
    }

}
