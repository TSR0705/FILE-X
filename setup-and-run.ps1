# setup-and-run.ps1
# Automates environment setup and execution for FileX

$PROJECT_ROOT = Get-Location
$JAVAFX_DIR = "$PROJECT_ROOT/javafx"
$LIB_DIR = "$PROJECT_ROOT/lib"
$TARGET_DIR = "$PROJECT_ROOT/target/classes"

Write-Host "Setting up FileX environment..."

# 1. Create directories
if (-not (Test-Path $JAVAFX_DIR)) { New-Item -ItemType Directory -Path $JAVAFX_DIR }
if (-not (Test-Path $LIB_DIR)) { New-Item -ItemType Directory -Path $LIB_DIR }
if (-not (Test-Path $TARGET_DIR)) { New-Item -ItemType Directory -Force -Path $TARGET_DIR }

# 2. Download JavaFX SDK if missing
if (-not (Test-Path "$JAVAFX_DIR/lib/javafx.controls.jar")) {
    Write-Host "Downloading JavaFX SDK 21.0.2..."
    $javafxUrl = "https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_windows-x64_bin-sdk.zip"
    Invoke-WebRequest -Uri $javafxUrl -OutFile "javafx_sdk.zip"
    Expand-Archive -Path "javafx_sdk.zip" -DestinationPath "$PROJECT_ROOT/temp_javafx" -Force
    Move-Item -Path "$PROJECT_ROOT/temp_javafx/javafx-sdk-21.0.2/*" -Destination $JAVAFX_DIR -Force
    Remove-Item -Path "javafx_sdk.zip", "$PROJECT_ROOT/temp_javafx" -Recurse -Force
}

# 3. Download MySQL JDBC if missing
if (-not (Test-Path "$LIB_DIR/mysql-connector.jar")) {
    Write-Host "Downloading MySQL Connector/J..."
    $mysqlUrl = "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar"
    Invoke-WebRequest -Uri $mysqlUrl -OutFile "$LIB_DIR/mysql-connector.jar"
}

# 4. Compile Project
Write-Host "Compiling FileX..."
$modulePath = "$JAVAFX_DIR/lib"
$sourceFiles = Get-ChildItem -Path "src/main/java" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
$classpath = "$LIB_DIR/mysql-connector.jar"

& javac --module-path $modulePath --add-modules javafx.controls,javafx.fxml -cp $classpath -d $TARGET_DIR $sourceFiles

if ($LASTEXITCODE -ne 0) {
    Write-Error "Compilation failed!"
    exit 1
}

# 5. Copy Resources
Write-Host "Copying resources..."
Copy-Item -Path "src/main/resources/*" -Destination $TARGET_DIR -Recurse -Force

# 6. Run Project
Write-Host "Launching FileX..."
& java --module-path $modulePath --add-modules javafx.controls,javafx.fxml -cp "$TARGET_DIR;$LIB_DIR/mysql-connector.jar" com.filex.Main
