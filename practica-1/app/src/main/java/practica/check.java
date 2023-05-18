package practica;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class check {
  
  public boolean isValidURL(String url){
    try {
        new URL(url).toURI();
        return true;
    } catch (MalformedURLException e) {
        return false;
    } catch (URISyntaxException e) {
        return false;
    }
  }
  
  public static void main(String[] args) {
    System.out.println(new check().isValidURL("http://baeldung.com/"));
    System.out.println(new check().isValidURL("https://www.columbia.edu/~fdc/sample1.html"));
  }
}
