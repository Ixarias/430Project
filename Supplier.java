import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private List<Pair> catalog;

    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
        this.catalog = new LinkedList<Pair>();
    }

    public Supplier(String name, String address, List<Pair> catalog) {
        this.name = name;
        this.address = address;
        this.catalog = catalog;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void insertProduct(Product product, double price) {
        Pair newpair = new Pair(this, product, price);
        catalog.add(newpair);
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public void getCatalog() {
        Iterator<Pair> cat = catalog.iterator();
        // search for supplier by name
        // (iterate until supplier.name == name)
        while (cat.hasNext()) {
            Pair thispair = cat.next();
            System.out.println(thispair.toString());
            }
    }
    public Pair getPair(String inprod) {
        Iterator<Pair> cat = catalog.iterator();
        for (int i = 0; i < i; i++) {
            Pair current = cat.next();
            if (current.prod.getName() == inprod) {
                return current;
            } 
        } 
        return null;
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    public String toString() {
        return "Supplier name: " + name + " | address: " + address;
    }


}
