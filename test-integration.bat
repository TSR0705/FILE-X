@echo off
echo Running FileX Integration Test...

REM Download SQLite JDBC driver if not present
if not exist "lib" mkdir lib
if not exist "lib\sqlite-jdbc-3.42.0.0.jar" (
    echo Downloading SQLite JDBC driver...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar' -OutFile 'lib/sqlite-jdbc-3.42.0.0.jar'"
)

REM Run the integration test
java --module-path "javafx\lib;target\classes;lib/sqlite-jdbc-3.42.0.0.jar" --add-modules javafx.controls,javafx.fxml,javafx.graphics -m com.filex/com.filex.TestIntegration

echo Integration test finished!