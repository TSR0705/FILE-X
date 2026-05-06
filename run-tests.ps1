# run-tests.ps1
# Runs the FileX Backend Test suite

$PROJECT_ROOT = Get-Location
$JAVAFX_DIR = "$PROJECT_ROOT/javafx"
$LIB_DIR = "$PROJECT_ROOT/lib"
$TARGET_DIR = "$PROJECT_ROOT/target/classes"

Write-Host "Running FileX Backend Tests..."

# Check if target directory exists
if (-not (Test-Path $TARGET_DIR)) {
    Write-Error "Project not compiled. Please run setup-and-run.ps1 first."
    exit 1
}

# Run the backend test class
$modulePath = "$JAVAFX_DIR/lib"
$classpath = "$TARGET_DIR;$LIB_DIR/mysql-connector.jar"

Write-Host "Executing TestBackend..."
& java --module-path $modulePath --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp $classpath com.filex.TestBackend

if ($LASTEXITCODE -eq 0) {
    Write-Host "Tests passed successfully!" -ForegroundColor Green
} else {
    Write-Host "Tests failed!" -ForegroundColor Red
}
