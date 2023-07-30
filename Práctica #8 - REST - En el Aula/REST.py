import requests

num_seleccion = 0

while num_seleccion != 5:
    num_seleccion = input("""Seleccione con un numero:
    1. Listar Todos
    2. Listar Uno
    3. Agregar
    4. Eliminar
    Seleccion: """)
    num_seleccion = int(num_seleccion)

    if num_seleccion == 1:
        seleccion = requests.get("http://localhost:7000/api/estudiante/")
        print(seleccion.text)
    elif num_seleccion == 2:
        mat = input("Ingrese la matricula o ID del estudiante a listar: ")
        seleccion = requests.get("http://localhost:7000/api/estudiante/" + mat)
        if seleccion.text == "Server Error":
            print(f'Estudiante {mat} no fue encontrado o no existe')
        else:
            print(seleccion.text)
    elif num_seleccion == 3:
        nombre = input("Nombre: ")
        mat = input("Matricula o ID: ")
        carrera = input("Carrera: ")
        json_estudiante = {
            "matricula": mat,
            "nombre": nombre,
            "carrera": carrera,
        }
        seleccion = requests.post("http://localhost:7000/api/estudiante/", json=json_estudiante)
        if seleccion.text == "Server Error":
            print(f'Estudiante no pudo ser creado')
        else:
            print(f'Estudiante {mat} creado con exito')
    elif num_seleccion == 4:
        mat = input("Ingrese la matricula o ID del estudiante a eliminar: ")
        seleccion = requests.delete("http://localhost:7000/api/estudiante/" + mat)

        if seleccion.text == "Server error" or seleccion.text == "false":
            print(f'Estudiante {mat} no fue encontrado o no existe')
        else:
            print(f'Estudiante {mat} eliminado con exito')
    elif num_seleccion == 5:
        break
    print()

