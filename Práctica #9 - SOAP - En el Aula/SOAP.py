from suds.client import Client
cliente_soap = Client("http://localhost:7000/ws/EstudianteWebServices?wsdl")
num_seleccion = 0

while num_seleccion != 5:
    num_seleccion = int(input("""Seleccione con un numero:
    1. Listar Todos
    2. Listar Uno
    3. Agregar
    4. Eliminar
    Seleccion: """))

    if num_seleccion == 1:
        seleccion = cliente_soap.service.getListaEstudiante()
        for i in seleccion:
            print(i)
    elif num_seleccion == 2:
        mat = input("Ingrese la matricula o ID del estudiante a listar: ")
        seleccion = cliente_soap.service.getEstudiante(int(mat))
        if seleccion is None:
            print(f'Estudiante {mat} no fue encontrado o no existe')
        else:
            print(seleccion)
    elif num_seleccion == 3:
        nombre = input("Nombre: ")
        mat = input("Matricula o ID: ")
        carrera = input("Carrera: ")
        json_estudiante = {
            "matricula": mat,
            "nombre": nombre,
            "carrera": carrera,
        }
        seleccion = cliente_soap.service.crearEstudiante(json_estudiante)
        print(f'Estudiante {mat} creado con exito')
    elif num_seleccion == 4:
        mat = input("Ingrese la matricula o ID del estudiante a eliminar: ")
        try:
            seleccion = cliente_soap.service.eliminarEstudiante(mat)
            print(f'Estudiante {mat} eliminado con exito {seleccion}')
        except Exception as e:
            print(f'Estudiante {mat} no fue encontrado o no existe')
    elif num_seleccion == 5:
        break
    print()
