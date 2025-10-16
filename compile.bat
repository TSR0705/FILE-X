@echo off
echo Compiling FileX project...

REM Create output directory
if not exist "target" mkdir target
if not exist "target\classes" mkdir target\classes

REM Compile Java files
javac --module-path "javafx\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -d target/classes src/main/java/module-info.java src/main/java/com/filex/*.java src/main/java/com/filex/theme/*.java src/main/java/com/filex/model/*.java src/main/java/com/filex/util/*.java src/main/java/com/filex/service/*.java src/main/java/com/filex/controller/*.java

echo Compilation completed!