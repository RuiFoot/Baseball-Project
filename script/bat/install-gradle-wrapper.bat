@echo off
setlocal enabledelayedexpansion

echo This script downloads and installs the Gradle wrapper

:: Ensure we're in the project root directory
cd /d "%~dp0\..\..\"

:: Create directories if they don't exist
if not exist "gradle\wrapper" mkdir "gradle\wrapper"

:: Download the Gradle wrapper JAR
echo Downloading Gradle wrapper JAR...
powershell -Command "& {Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.13.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'}"

:: Verify the download
for %%F in (gradle\wrapper\gradle-wrapper.jar) do (
    if %%~zF EQU 0 (
        echo Failed to download gradle-wrapper.jar or file is empty
        exit /b 1
    )
)

echo Gradle wrapper JAR downloaded successfully

echo Gradle wrapper installation complete