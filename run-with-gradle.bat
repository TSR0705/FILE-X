@echo off
echo Running FileX with Gradle...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17+ and make sure it's in your PATH
    pause
    exit /b 1
)

echo Java is installed. Checking for Gradle...
gradle --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Gradle not found in PATH. Trying to use Gradle wrapper...
    if exist "gradlew.bat" (
        echo Using Gradle wrapper...
        call gradlew.bat run
    ) else (
        echo Gradle wrapper not found. Please install Gradle or download the Gradle wrapper.
        pause
        exit /b 1
    )
) else (
    echo Using system Gradle...
    gradle run
)

if %errorlevel% neq 0 (
    echo.
    echo Error: Failed to run the application
    pause
    exit /b 1
)

echo.
echo Application finished.
pause