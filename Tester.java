import java.util.*;
import java.text.*;
import java.io.*;
public class Tester {
  public static String getToken(String prompt) {
		do {
		  try {
			System.out.println(prompt);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = reader.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
			if (tokenizer.hasMoreTokens()) {
				return tokenizer.nextToken();
			}
		  } 
		  catch (IOException ioe) {
			System.exit(0);
		  }
		} while (true);
  }
  private static boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
			return true;
  }
  public static void main(String[] s) {
	
	 float price1 = 12.00;
	 float price2 = 15.00;
	
	 Product b1 = new Product("product1", "supplier1", price1);
     Product b2 = new Product("product2", "supplier2", price2);
     
     ProductList productlist = ProductList.instance();
	 productlist.insertProduct(b1);
     productlist.insertProduct(b2);
     
     Iterator product = productlist.getProducts();
     System.out.println("List of products");

     while (product.hasNext()){
       System.out.println(product.next());
     }

  }
}
