import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private class Pair {
        public Product prod;
        public double price;
        public Pair(Product prod, double price) {
            this.prod = prod;
            this.price = price;
        }
        public String toString() {
            return "name: " + prod.getName() + " | quantity: " + prod.getQuantity() + " | price: " + price;
        }
    }
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
    
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void insertProduct(Product product, float price) {
        Pair newpair = new Pair(product, price);
        catalog.add(newpair);
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    public String toString() {
        return "Supplier name: " + name + " | address: " + address;
    }


}
