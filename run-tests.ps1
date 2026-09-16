# =========================================================================
# Powershell Script to Compile and Run the SDE Interview Prep Test Harness
# =========================================================================

# Ensure we are in the script's root directory
$ScriptRoot = Split-Path -Parent -Path $MyInvocation.MyCommand.Definition
if ($ScriptRoot) {
    Set-Location $ScriptRoot
}

# Create a clean binary destination directory
if (-not (Test-Path bin)) {
    New-Item -ItemType Directory -Force -Path bin | Out-Null
} else {
    Remove-Item -Recurse -Force bin\* -ErrorAction SilentlyContinue | Out-Null
}

Write-Host "Step 1: Locating Java source files in src/..." -ForegroundColor Cyan

# Find all Java files recursively
$JavaSources = Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName }

if ($null -eq $JavaSources -or $JavaSources.Count -eq 0) {
    Write-Host "[-] Error: No Java files found in src/ folder!" -ForegroundColor Red
    Exit 1
}

Write-Host "Step 2: Compiling $($JavaSources.Count) source files..." -ForegroundColor Cyan

# Build arguments list for javac
$JavacArgs = @("-d", "bin") + $JavaSources

# Execute compiler
& javac $JavacArgs

if ($LASTEXITCODE -ne 0) {
    Write-Host "[-] Compilation failed! Please correct the compiler errors above." -ForegroundColor Red
    Exit 1
}

Write-Host "[+] Compilation successful. Executing test suite..." -ForegroundColor Green
Write-Host ""

# Run TestRunner
& java -cp bin test.TestRunner
