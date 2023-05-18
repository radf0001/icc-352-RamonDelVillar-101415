package practica;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class practice1 {
   public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);
    System.out.print("Ingrese el URL: ");  
    String url = entrada.nextLine();
    HttpResponse<String> response = clienteHTTP(url);
    if(response != null){
      int status = response.statusCode();                            
      String contenType = response.headers().allValues("content-type").toString();
      String body = response.body();
      System.out.println("Content Type: " + contenType);
      if(contenType.contains("html")){
        Document html = Jsoup.parse(body);
        System.out.println(html);
      
      }
    }
   }

   public static HttpResponse<String> clienteHTTP(String url) {
    HttpClient httpClient = HttpClient.newBuilder()
       .version(HttpClient.Version.HTTP_2)
       .connectTimeout(Duration.ofSeconds(10))
       .build(); 
       try {
          HttpRequest request = HttpRequest.newBuilder()
          .GET()
          .uri(URI.create(url))
          .build();                              
          HttpResponse<String> response = httpClient.send(request,
          HttpResponse.BodyHandlers.ofString()); 
          return response;
    } catch (IOException | InterruptedException e) {
       System.out.println("Ocurrio un error de tipo" + e.getClass().getName());
    }
    return null;
 }
}
