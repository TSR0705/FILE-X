@echo off
echo Running FileX with local JavaFX installation...
echo.

REM Set up directories
set SCRIPT_DIR=%~dp0
set JAVAFX_LIB_DIR=%SCRIPT_DIR%javafx\lib

REM Check if JavaFX is available
if not exist "%JAVAFX_LIB_DIR%\javafx.controls.jar" (
    if not exist "%JAVAFX_LIB_DIR%\javafx-controls-21.0.2-win.jar" (
        echo Error: JavaFX libraries not found in %JAVAFX_LIB_DIR%
        echo Please download JavaFX 21 SDK from https://gluonhq.com/products/javafx/
        echo And extract it to the 'javafx' folder in this directory
        pause
        exit /b 1
    )
)

REM Compile the application if target/classes doesn't exist
if not exist "target\classes" (
    echo Compiling the application...
    mkdir target\classes 2>nul
    javac --module-path "%JAVAFX_LIB_DIR%" --add-modules javafx.controls,javafx.fxml,javafx.graphics -d "target/classes" "src/main/java/module-info.java" "src/main/java/com/filex/*.java" "src/main/java/com/filex/theme/*.java" "src/main/java/com/filex/model/*.java" "src/main/java/com/filex/util/*.java" "src/main/java/com/filex/service/*.java" "src/main/java/com/filex/controller/*.java"
    if %errorlevel% neq 0 (
        echo Error: Failed to compile the application
        pause
        exit /b 1
    )
    echo Compilation successful!
    echo.
)

REM Run the application
echo Running FileX application...
echo ==================================
java --module-path "%JAVAFX_LIB_DIR%;target/classes;src/main/resources" --add-modules javafx.controls,javafx.fxml,javafx.graphics com.filex.Main

if %errorlevel% neq 0 (
    echo.
    echo Error: Failed to run the application
    pause
    exit /b 1
)

echo.
echo Application finished.
pause