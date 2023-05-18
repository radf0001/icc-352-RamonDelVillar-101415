package practica;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class practice1 {
   public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);
    System.out.print("Ingrese el URL: ");  
    String url = entrada.nextLine();
    HttpClient cliente = clienteHTTP();
    HttpResponse<String> response = get(cliente, url);
    if(response != null){
      int status = response.statusCode();                            
      String contenType = response.headers().allValues("content-type").toString();
      String body = response.body();
      System.out.println("Content Type: " + contenType);
      if(contenType.contains("html")){
         Document doc = Jsoup.parse(body);
         infoHTML(doc);
         Elements formsPost = doc.select("form[method*=post]");
         System.out.println("Cantidad de Formularios metodo POST: "+formsPost.size());
         for(Element forms:formsPost) {
            post(cliente, url);
            Elements inputs = forms.getElementsByTag("input");
            System.out.println(" Cantidad de Input en ese formulario: "+forms.getElementsByTag("input").size()); 
            for(Element input:inputs){
               System.out.println("    <input type=\""+input.attr("type")+"\" />");
            }
         }
      }
   }
}

   public static HttpClient clienteHTTP() {
      HttpClient httpClient = HttpClient.newBuilder()
         .version(HttpClient.Version.HTTP_2)
         .connectTimeout(Duration.ofSeconds(10))
         .build();
      return httpClient; 
   }


   public static HttpResponse<String> get(HttpClient cliente, String url) {
      try {
         HttpRequest request = HttpRequest.newBuilder()
         .GET()
         .uri(URI.create(url))
         .build();                              
         HttpResponse<String> response = cliente.send(request,
         HttpResponse.BodyHandlers.ofString()); 
         return response;
      } catch (IOException | InterruptedException e) {
         System.out.println("Ocurrio un error de tipo" + e.getClass().getName());
      }
      return null;
   }

   public static HttpResponse<String> post(HttpClient cliente, String url) {
      Map<String, String> formData = new HashMap<>();
      formData.put("asignatura", "practica1");
      try {
         HttpRequest request = HttpRequest.newBuilder()
         .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
         .uri(URI.create(url))
         .header("matricula-id", "1014-1505")
         .build();                             
         HttpResponse<String> response = cliente.send(request,
         HttpResponse.BodyHandlers.ofString());
         // System.out.println(response.statusCode()); 
         return response;
      } catch (IOException | InterruptedException e) {
         System.out.println("Ocurrio un error de tipo" + e.getClass().getName());
      }
      return null;
   }

   public static void infoHTML(Document doc) {
      Elements parraf = doc.select("p");
      Elements imgs = doc.select("img");
      Elements formsGet = doc.select("form[method*=get]");
      System.out.println("Cantidad de Parrafos: "+parraf.size());
      System.out.println("Cantidad de Imagenes: "+imgs.size());
      System.out.println("Cantidad de Formularios metodo GET: "+formsGet.size());
      for(Element forms:formsGet) {
         Elements inputs = forms.getElementsByTag("input");
         System.out.println(" Cantidad de Input en ese formulario: "+forms.getElementsByTag("input").size());
         for(Element input:inputs){
            System.out.println("    <input type=\""+input.attr("type")+"\" />");
         }
       }
   }

   private static String getFormDataAsString(Map<String, String> formData) {
      StringBuilder formBodyBuilder = new StringBuilder();
      for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
          if (formBodyBuilder.length() > 0) {
              formBodyBuilder.append("&");
          }
          formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
          formBodyBuilder.append("=");
          formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
      }
      return formBodyBuilder.toString();
  }
}
