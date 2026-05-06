# PowerShell script to run FileX application using existing compiled classes

Write-Host "Running FileX application..."
Write-Host ""

# Check if Java is installed
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java is installed: $javaVersion"
} catch {
    Write-Host "Error: Java is not installed or not in PATH"
    Write-Host "Please install Java 17+ and make sure it's in your PATH"
    pause
    exit 1
}

# Set Maven repository path
$MAVEN_REPO = "C:\Users\tanma\.m2\repository"
$JAVAFX_VERSION = "21.0.6"

# Set JavaFX JAR paths
$JAVAFX_CONTROLS = "$MAVEN_REPO\org\openjfx\javafx-controls\$JAVAFX_VERSION\javafx-controls-$JAVAFX_VERSION.jar"
$JAVAFX_FXML = "$MAVEN_REPO\org\openjfx\javafx-fxml\$JAVAFX_VERSION\javafx-fxml-$JAVAFX_VERSION.jar"
$JAVAFX_GRAPHICS = "$MAVEN_REPO\org\openjfx\javafx-graphics\$JAVAFX_VERSION\javafx-graphics-$JAVAFX_VERSION.jar"
$JAVAFX_BASE = "$MAVEN_REPO\org\openjfx\javafx-base\$JAVAFX_VERSION\javafx-base-$JAVAFX_VERSION.jar"
$JAVAFX_BASE_WIN = "$MAVEN_REPO\org\openjfx\javafx-base\$JAVAFX_VERSION\javafx-base-$JAVAFX_VERSION-win.jar"
$JAVAFX_CONTROLS_WIN = "$MAVEN_REPO\org\openjfx\javafx-controls\$JAVAFX_VERSION\javafx-controls-$JAVAFX_VERSION-win.jar"
$JAVAFX_FXML_WIN = "$MAVEN_REPO\org\openjfx\javafx-fxml\$JAVAFX_VERSION\javafx-fxml-$JAVAFX_VERSION-win.jar"
$JAVAFX_GRAPHICS_WIN = "$MAVEN_REPO\org\openjfx\javafx-graphics\$JAVAFX_VERSION\javafx-graphics-$JAVAFX_VERSION-win.jar"

# Check if all JavaFX JARs exist
if (!(Test-Path $JAVAFX_CONTROLS)) {
    Write-Host "Error: javafx-controls JAR not found at $JAVAFX_CONTROLS"
    pause
    exit 1
}

if (!(Test-Path $JAVAFX_FXML)) {
    Write-Host "Error: javafx-fxml JAR not found at $JAVAFX_FXML"
    pause
    exit 1
}

if (!(Test-Path $JAVAFX_GRAPHICS)) {
    Write-Host "Error: javafx-graphics JAR not found at $JAVAFX_GRAPHICS"
    pause
    exit 1
}

if (!(Test-Path $JAVAFX_BASE)) {
    Write-Host "Error: javafx-base JAR not found at $JAVAFX_BASE"
    pause
    exit 1
}

Write-Host "All JavaFX dependencies found."
Write-Host ""

# Check if target/classes exists
if (!(Test-Path "target\classes")) {
    Write-Host "Error: target/classes directory not found"
    Write-Host "Please compile the application first"
    pause
    exit 1
}

# Run the application
Write-Host "Running FileX application..."
$modulePath = "$MAVEN_REPO\org\openjfx\javafx-controls\$JAVAFX_VERSION;$MAVEN_REPO\org\openjfx\javafx-fxml\$JAVAFX_VERSION;$MAVEN_REPO\org\openjfx\javafx-graphics\$JAVAFX_VERSION;$MAVEN_REPO\org\openjfx\javafx-base\$JAVAFX_VERSION"
$runClasspath = "target/classes;src/main/resources;$JAVAFX_CONTROLS;$JAVAFX_FXML;$JAVAFX_GRAPHICS;$JAVAFX_BASE;$JAVAFX_BASE_WIN;$JAVAFX_CONTROLS_WIN;$JAVAFX_FXML_WIN;$JAVAFX_GRAPHICS_WIN"

& java --module-path $modulePath --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp $runClasspath com.filex.Main

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "Error: Failed to run the application"
    pause
    exit 1
}

Write-Host ""
Write-Host "Application finished."
pause