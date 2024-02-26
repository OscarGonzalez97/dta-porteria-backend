Agregar el JSON de autenticación de Firestore a la
raiz del proyecto con el nombre "firebaseAccountInfo.json".

Para el login tambien se necesita las credenciales de la aplicacion de javascript en un archivo llamado firebaseJsApp(este es el que se importa en el template luego). Se deja un ejemplo en el repositorio

Por ultimo de debe configurar correctamente la base de datos correcta en *src/main/java/com/roshka/dtaporteria/config/FirebaseInitializer.java* en la linea de `.setDatabaseUrl` se debe poner la url de la base de datos correcta

