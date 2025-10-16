@echo off
echo Running FileX Simple Test...

REM Run the simple test
java --module-path "javafx\lib;target\classes;lib/sqlite-jdbc-3.42.0.0.jar" --add-modules javafx.controls,javafx.fxml,javafx.graphics -m com.filex/com.filex.TestSimple

echo Simple test finished!