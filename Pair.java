import java.util.*;
import java.io.*;

public class Pair implements Serializable {
    private static final long serialVersionUID = 1L;
    public Supplier supp;
    public Product prod;
    public double price;
    public Pair(Supplier supp, Product prod, double price) {
        this.supp = supp;
        this.prod = prod;
        this.price = price;
    }
    public String toString() {
        return "name: " + prod.getName() + " | quantity: " + prod.getQuantity() + " | price: " + price;
    }
}