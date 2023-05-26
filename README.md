# Plant Planner

<p align="center">
  <img src="https://github.com/anton-gendra/plant-planner/assets/78569753/18fe0fa8-d716-433a-bed1-34d29fe6246f" alt="Logo de la aplicación" width="128" height="128">
</p>

Plant Planner es una aplicación móvil para Android que te permitirá gestionar tus plantas y compartirlas con tus amigos. Entre sus funciones más destacadas están la detección del tipo de planta mediante técnicas de IA, la gestión de las plantas y una red social integrada al estilo instagram, pero centrada en tus plantas y las de tus amigos. Con ello, se busca que el cuidado de las plantas sea más sencillo y divertido para todo el mundo.

En la **Wiki** de este repositorio se encuentra una entrada para cada tarea semanal de la práctica, además de este Readme con información más general sobre la aplicación.

## Tecnologías

Principalmente, Plant Planner utiliza las siguientes tecnologías:

* [Kotlin](https://kotlinlang.org/)
* [Python](https://www.python.org/downloads/)
* [FastAPI](https://fastapi.tiangolo.com/)
* [Docker](https://www.docker.com/)

## BackEnd

Desde la carpeta de backend/, que se encuentra en el root del proyecto, se encuentran los ficheros Dockerfile y docker-compose.yml. Desde este directorio, se ejecutará el típico comando para levantar contenedores de docker mediante docker-compose: `docker-compose up`. Es relecante mencionar que primero se debe levantar el contenedor de base de datos y posteriormente el de la aplicación. En caso de que ocurra algún error, es importante considerar que esta puede ser una de las causas.
Después de haber realizado este paso, se deben crear las bases de datos que usará la aplicación. Por cuestión de tiempo esto quedó implementado de una manera un tanto rudimentaria. El proceso para eso sería el siguiente:
- Entrar en el docker de la aplicación: `docker exec -it backend-plant-planner-backend-1 bash`
- Ejecutar el script de creación de tablas: `python app/scripts/create_tables.py`

## Demos de los prototipos

A continuación se muestra el flujo de la aplicación en dos protipos creados con la herramiento [Marvel](https://marvelapp.com/), tanto para móvil como para tablet.

### Prototipo para móvil

https://user-images.githubusercontent.com/76788065/222538061-3d7645ec-8ec6-44fa-b9a4-6280b5a9ef2f.mov

### Prototipo para tablet

https://user-images.githubusercontent.com/76788065/231894874-e53f4fdc-f96b-4308-bce4-715705408515.mp4



