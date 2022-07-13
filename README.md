Para construir el proyecto y poder ejecutarlo con CMD (Windows):
Dirigirse a la carpeta "src" dentro del proyecto con la consola y ejecutar los siguientes comandos (compila, copia archivos de recursos y crear el .jar ejecutable):

javac -d bin codigo/Main.java
mkdir bin\META-INF
xcopy META-INF bin\META-INF /v /y
mkdir bin\recursos
xcopy recursos bin\recursos /v /y /s
cd bin
jar cfm 2048.jar META-INF/MANIFEST.mf .

Al final se genera un ejecutable .jar dentro de la carpeta "bin".
