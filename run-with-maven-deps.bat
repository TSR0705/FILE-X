@echo off
echo Running FileX with Maven dependencies...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17+ and make sure it's in your PATH
    pause
    exit /b 1
)

REM Set Maven repository path
set MAVEN_REPO=C:\Users\tanma\.m2\repository
set JAVAFX_VERSION=21.0.6

REM Check if Maven repository exists
if not exist "%MAVEN_REPO%" (
    echo Error: Maven repository not found
    pause
    exit /b 1
)

REM Set JavaFX JAR paths
set JAVAFX_CONTROLS=%MAVEN_REPO%\org\openjfx\javafx-controls\%JAVAFX_VERSION%\javafx-controls-%JAVAFX_VERSION%.jar
set JAVAFX_FXML=%MAVEN_REPO%\org\openjfx\javafx-fxml\%JAVAFX_VERSION%\javafx-fxml-%JAVAFX_VERSION%.jar
set JAVAFX_GRAPHICS=%MAVEN_REPO%\org\openjfx\javafx-graphics\%JAVAFX_VERSION%\javafx-graphics-%JAVAFX_VERSION%.jar
set JAVAFX_BASE=%MAVEN_REPO%\org\openjfx\javafx-base\%JAVAFX_VERSION%\javafx-base-%JAVAFX_VERSION%.jar

REM Check if all JavaFX JARs exist
if not exist "%JAVAFX_CONTROLS%" (
    echo Error: javafx-controls JAR not found at %JAVAFX_CONTROLS%
    pause
    exit /b 1
)

if not exist "%JAVAFX_FXML%" (
    echo Error: javafx-fxml JAR not found at %JAVAFX_FXML%
    pause
    exit /b 1
)

if not exist "%JAVAFX_GRAPHICS%" (
    echo Error: javafx-graphics JAR not found at %JAVAFX_GRAPHICS%
    pause
    exit /b 1
)

if not exist "%JAVAFX_BASE%" (
    echo Error: javafx-base JAR not found at %JAVAFX_BASE%
    pause
    exit /b 1
)

echo All JavaFX dependencies found.
echo.

REM Create target directory if it doesn't exist
if not exist "target\classes" mkdir "target\classes"

REM Copy resources to target/classes
echo Copying resources...
xcopy /E /Y /Q "src\main\resources\*" "target\classes\" >nul

REM Compile the Java files
echo Compiling Java files...
javac -cp "target/classes;%JAVAFX_CONTROLS%;%JAVAFX_FXML%;%JAVAFX_GRAPHICS%;%JAVAFX_BASE%" -d "target/classes" src/main/java/com/filex/*.java src/main/java/com/filex/controller/*.java src/main/java/com/filex/model/*.java src/main/java/com/filex/service/*.java src/main/java/com/filex/theme/*.java src/main/java/com/filex/util/*.java

if %errorlevel% neq 0 (
    echo.
    echo Error: Compilation failed
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.

echo Running FileX application...
java -cp "target/classes;%JAVAFX_CONTROLS%;%JAVAFX_FXML%;%JAVAFX_GRAPHICS%;%JAVAFX_BASE%" --module-path "%MAVEN_REPO%\org\openjfx\javafx-controls\%JAVAFX_VERSION%;%MAVEN_REPO%\org\openjfx\javafx-fxml\%JAVAFX_VERSION%;%MAVEN_REPO%\org\openjfx\javafx-graphics\%JAVAFX_VERSION%;%MAVEN_REPO%\org\openjfx\javafx-base\%JAVAFX_VERSION%" --add-modules javafx.controls,javafx.fxml,javafx.graphics com.filex.Main

if %errorlevel% neq 0 (
    echo.
    echo Error: Failed to run the application
    pause
    exit /b 1
)

echo.
echo Application finished.
pause