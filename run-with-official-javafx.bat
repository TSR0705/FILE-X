@echo off
title FileX - Insider File Leak Detector (Official JavaFX)

echo ======================================================
echo      FileX - Insider File Leak Detector (Official JavaFX)
echo ======================================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17+ and make sure it's in your PATH
    pause
    exit /b 1
)

REM Set JAVAFX_HOME to local javafx directory
set JAVAFX_HOME=%~dp0javafx
echo Using local JavaFX at: %JAVAFX_HOME%
echo.

REM Check if JavaFX lib directory exists
if not exist "%JAVAFX_HOME%\lib" (
    echo Error: JavaFX lib directory not found at %JAVAFX_HOME%\lib
    echo Please verify that JavaFX libraries are in the javafx/lib directory
    pause
    exit /b 1
)

REM Check if required JavaFX JARs exist
if not exist "%JAVAFX_HOME%\lib\javafx.base-21.0.2-win.jar" (
    echo Error: Required JavaFX base library not found
    pause
    exit /b 1
)

if not exist "%JAVAFX_HOME%\lib\javafx.controls-21.0.2-win.jar" (
    echo Error: Required JavaFX controls library not found
    pause
    exit /b 1
)

if not exist "%JAVAFX_HOME%\lib\javafx.fxml-21.0.2-win.jar" (
    echo Error: Required JavaFX FXML library not found
    pause
    exit /b 1
)

if not exist "%JAVAFX_HOME%\lib\javafx.graphics-21.0.2-win.jar" (
    echo Error: Required JavaFX graphics library not found
    pause
    exit /b 1
)

echo JavaFX SDK found at: %JAVAFX_HOME%
echo.

REM Compile the application if target/classes doesn't exist or is missing Main.class
if not exist "target\classes\com\filex\Main.class" (
    echo Compiling the application...
    if not exist "target\classes" mkdir target\classes
    javac --module-path "%JAVAFX_HOME%/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -d "target/classes" "src/main/java/module-info.java" "src/main/java/com/filex/*.java" "src/main/java/com/filex/theme/*.java" "src/main/java/com/filex/model/*.java" "src/main/java/com/filex/util/*.java" "src/main/java/com/filex/service/*.java" "src/main/java/com/filex/controller/*.java"
    if %errorlevel% neq 0 (
        echo Error: Failed to compile the application
        pause
        exit /b 1
    )
    echo Compilation successful!
    echo.
)

REM Run the application
echo Starting FileX application with official JavaFX SDK...
echo ==================================
echo The application window should appear shortly.
echo If it doesn't appear, check if your system supports JavaFX.
echo.

java --module-path "%JAVAFX_HOME%/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp "target/classes;src/main/resources" com.filex.Main

if %errorlevel% neq 0 (
    echo.
    echo Error: Failed to run the application
    echo Make sure JavaFX is properly installed and configured
    pause
    exit /b 1
)

echo.
echo Application finished.
pause