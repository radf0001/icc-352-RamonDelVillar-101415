**PRESENTACION:**
Pontificia Universidad Católica Madre y Maestra 
Facultad de Ciencias de la Ingeniería. 
Departamento de Ingeniería en Ciencias de la Computación. 

CSTI-1830-4818
 
Practica:
Practica #2.

Tema: 
CRUD. Creación de Blog en Sesión.
 
Presentado por: 
Ramon Del Villar.

Matricula: 
1014-1505. 
 
Presentado al profesor: 
Ing. Carlos Camacho. 

Asignatura: 
Programación Web, 
ICC-352.

Fecha de Entrega: 
Lunes, 29 de mayo del 2023. 


**INTRODUCCION**
Esta tarea consiste en realizar un CRUD de un blog. CRUD es el acrónimo de Create (Crear), Read (Leer), Update (Actualizar) y Delete (Borrar). Este concepto se utiliza para describir las cuatro operaciones básicas que pueden realizarse en la mayoría de las bases de datos y sistemas de gestión de información.
Para realizar esta tarea me apoye de la tarea #1 ya que esta tarea 2 también esta relacionada al uso de básico de las llamadas de peticiones en una aplicación web.
También hice uso de dos herramientas muy importantes como lo son el uso de plantillas que utilice apache velocity y de un framework que trabaje en la vista que utilice Tailwind CSS.
Tailwind es un framework CSS que nos proporciona clases de utilidad de un solo propósito que son opinables en su mayor parte, y que nos ayudan a diseñar nuestras páginas web desde dentro de nuestro marcado o archivos .js/.jsx/.ts/.tsx.
Velocity es un motor de plantillas basado en Java. Le permite a los diseñadores de páginas hacer referencia a métodos definidos dentro del código Java. Los diseñadores Web pueden trabajar en paralelo con los programadores Java para desarrollar sitios de acuerdo al modelo de Modelo-Vista-Controlador (MVC), permitiendo que los diseñadores se concentren únicamente en crear un sitio bien diseñado y que los programadores se encarguen solamente de escribir código de primera calidad. Velocity separa el código Java de las páginas Web, haciendo el sitio más mantenible a largo plazo y presentando una alternativa viable a Java Server Pages (JSP) o PHP.
Los objetivos de esta tarea son:
•	Manejo de la librería Javalin., en peticiones, respuesta y uso de la plantilla.
•	Uso del contexto de sesión en las aplicaciones.
•	Uso de Plantillas.
•	Uso de seguridad en nuestros recursos web.
Ambos objetivos fueron cumplidos de manera exitosa ya que utilice gradle como gestor de proyectos en java, Tailwind como Framework para la vista, Velocity como motro de plantillas y Javalin como framework.

**DESARROLLO**
Para probar la funcionalidad de la librería Javalin y del protocolo HTTP, estaremos implementando un blog permita realizar artículos, incluir etiquetas a los artículos y asociar comentario a los artículos; los comentarios, solo pueden ser realizados por usuarios que estén autentificados. El modelo propuesto de objetos está comprendido por Artículo, Usuario, Comentario y Etiqueta, ver imagen para ver los campos y la relación propuesta, tienen libertad en incluir nuevos elementos.
Las funcionalidades que estaremos implementado son:

**1. Toda la información será persistida en memoria, pueden crear colecciones para almacenar todas las entidades que estaremos utilizando.** En esta parte almacene todo lo utilizado en memoria.

**2. Una vez inicializado la aplicación, se estará creando un usuario por defecto con permiso administrador. El perfil administrador permite tener acceso a todas las funcionalidades de la aplicación, en esta etapa tiene el permiso para la creación de nuevos usuarios. El permiso de autor permite que dicho usuario pueda crear nuevos artículos.**
Al iniciar la aplicación creo por defecto un usuario de administrador:
Usuario: admin
Contraseña: admin
Como indica la tarea hice que los administradores tengan acceso a toda la aplicación, los autores solo tiene acceso al crud de los artículos, los usuarios que no son administradores ni autores solo pueden ver los artículos sin poder comentar.

**3. La vista de inicio debe visualizar todos los artículos creados del último al primero, es decir el artículo más nuevo se presenta primero que el más viejo, las etiquetas asociadas a un artículo deben visualizarse en ese pequeño resumen del index. El texto a presentar deben ser los primeros 70 caracteres, e incluir una enlace que direcciones directamente a la lectura completa del artículo, en dicha pagina se podrá leer todo el artículo y visualizar todos los comentarios asociados.** Los artículos al crearse se ingresan de primero en una lista de artículos que tengo. Las etiquetas son creadas por mi y pueden ser seleccionadas en los artículos, estas también se muestran en el pequeño resumen del index. Para obtener los 70 primeros caracateres utilizo una función sencilla creada por mí, al presionar un articulo puede verlo completo, su título, autor, fecha, etiquetas y comentarios.

**4. En la vista del artículo, debe estar un formulario para incluir nuevos comentarios, únicamente los usuarios autentificados pueden agregar comentarios a un artículo. El usuario administrador o autor, pueden borrar un artículo que encuentre no apropiado.** En esta parte todos los usuarios registrados pueden comentar un articulo y solo los admin y autores pueden borrar un articulo.

**5. El usuario administrador o autor puede crear o modificar un artículo, en el mismo deben indicar el titulo, el cuerpo del artículo y las etiquetas asociadas. Las etiquetas son descripciones separado por coma, que estarán asociados a una ese artículo y disponible para otros. No deben duplicarse artículos existentes en el modelo de persistencia.** Solo los admin y autores pueden crear o modificar un articulo y manejo con el hecho de que no se dupliquen los artículos. Los artículos no se duplican dependiendo del cuerpo, di ya existe un articulo con ese mismo cuerpo no se puede registrar.

**6. La parte visualizar del proyecto estará manejada utilizando BootStrap o cualquier otro framework que trabaje en la vista, la presentación es un tema importante en el desarrollo de esta práctica.** Como ya dije anteriormente utilice Tailwind y obtuve una plantilla de Tailwind ya hecha en la que aplique todo lo pedido en esta tarea, esto con el objetivo de no hacer la parte visual desde cero como indico el profe que podíamos hacer.

**7. Se debe implementar los filtros para el control de los accesos no autorizados a funcionalidades del sistema a usuarios no autorizados.** Aplique todos los filtros necesarios para el manejo de los usuarios admin y autor.

Link al control de versiones de mi practica: https://github.com/radf0001/icc-352-RamonDelVillar-101415/tree/main/practica-2 

**CONCLUSION**
En esta segunda practica pude aprender cosas como el manejo de motor de plantillas, framework para la vista y trabajar más a fondo con nuestro framework Javalin.
El tema de esta practica puede ser aplicado en muchos casos como en el desarrollo web ya que cosas como motor de plantillas, framework para la vista y framework como Javalin hacen esta tarea de desarrollo web más fácil y mas entendible.
Para concluir puedo decir que mi experiencia personal con la practica fue muy buena ya que al tener que realizarla yo desde cero, investigando, resolviendo los errores que se me presentaban pude aprender más sobre como trabajar con un motor de plantillas, framework para la vista y Javalin. Creo que la parte con la que mas aprendí fue al tener que resolver errores que se me presentaban, ya que tuve que investigar sobre la documentación de cada uno de los elementos utilizados. 

**BIBLIOGRAFIA**
The Apache Software Foundation. (2020). Velocity Engine. https://velocity.apache.org/ 
Javalin. (2023). Javalin Documentation. https://javalin.io/documentation 
Tailwind. (2023). Tailwind Documentation. https://v2.tailwindcss.com/docs 
