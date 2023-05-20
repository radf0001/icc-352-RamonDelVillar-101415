package main.java.asignacion.aula;

public class indexController {
  
  @GetMapping("/")
  public String index(){

    return "index";
  }

  @PostMapping("/LoginServlet")
  public String userRegistration(@ModelAttribute User user){

    return "index";
  }
}
