package practica;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
   public static void main(String[] args) throws MalformedURLException, URISyntaxException{
    try (Scanner entrada = new Scanner(System.in)) {
      System.out.print("Ingrese el URL: ");
      String url = "";
      if(entrada.hasNextLine()){
         url=entrada.nextLine();
      }
      HttpClient cliente = clienteHTTP();
      HttpResponse<String> response = get(cliente, url);
      if(response != null){
         int status = response.statusCode();                            
         String contenType = response.headers().allValues("content-type").toString();
         String body = response.body();
         System.out.println("\nStatus Code: " + status);
         System.out.println("\nContent Type: " + contenType);
         if(contenType.contains("html")){
            Document doc = Jsoup.parse(body);
            infoHTML(doc, cliente, url);
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

   public static HttpResponse<String> post(HttpClient cliente, String url, String action) throws MalformedURLException, URISyntaxException {
      try {
         URL urlPH = new URL(url);
         String urlDef = urlPH.getProtocol() +"://"+ urlPH.getHost()+"/"+action;
         if(isValidURL(action))
            url = action;
         else if(isValidURL(urlDef))
            url = urlDef;
      } catch (MalformedURLException e) {
         e.printStackTrace();
      }
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
         System.out.println("       Status de peticion al servidor:" + response.statusCode() + "\n"); 
         return response;
      } catch (IOException | InterruptedException e) {
         System.out.println("Ocurrio un error de tipo" + e.getClass().getName());
      }
      return null;
   }

   public static void infoHTML(Document doc, HttpClient cliente, String url) throws MalformedURLException, URISyntaxException {
      Elements parraf = doc.select("p");
      Elements imgs = doc.select("img");
      Elements formsGet = doc.select("form[method*=get]");
      Elements formsPost = doc.select("form[method*=post]");
      Elements forms = doc.select("form");
      System.out.println("\nCantidad de Lineas: "+doc.html().lines().count());
      System.out.println("\nCantidad de Parrafos: "+parraf.size());
      System.out.println("\nCantidad de Imagenes: "+imgs.size());

      System.out.println("\nCantidad de Formularios: "+forms.size());
      System.out.println(" Cantidad de Formularios metodo GET: "+formsGet.size());
      System.out.println(" Cantidad de Formularios metodo POST: "+formsPost.size() + "\n");
      for(Element form:forms) {
         Elements inputs = form.getElementsByTag("input");
         System.out.println("    Cantidad de Input en este formulario: "+form.getElementsByTag("input").size());
         System.out.println("       <form" + form.attributes() + ">");
         for(Element input:inputs){
            System.out.println("          "+input);
         }
         if(form.attr("method").equalsIgnoreCase("post")){
            System.out.println("       </form>");
            post(cliente, url, form.attr("action"));
         } else{
            System.out.println("       </form>\n");
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

  static boolean isValidURL(String url) throws MalformedURLException, URISyntaxException {
      try {
         new URL(url).toURI();
         return true;
      } catch (MalformedURLException e) {
         return false;
      } catch (URISyntaxException e) {
         return false;
      }
   }
}


