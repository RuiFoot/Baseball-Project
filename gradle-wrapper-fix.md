# Gradle Wrapper Fix

## Issue

The project was experiencing an issue with the Gradle wrapper where the `gradle-wrapper.jar` file was empty (0 bytes), causing the following error when trying to run Gradle commands:

```
Error: Could not find or load main class org.gradle.wrapper.GradleWrapperMain
Caused by: java.lang.ClassNotFoundException: org.gradle.wrapper.GradleWrapperMain
```

This error occurred both in local development and in GitHub Actions CI/CD workflows.

## Solution

We've added scripts to download and install the correct Gradle wrapper JAR file:

### For Linux/macOS

```bash
./script/sh/install-gradle-wrapper.sh
```

### For Windows

```bash
.\script\bat\install-gradle-wrapper.bat
```

These scripts will:
1. Download the correct `gradle-wrapper.jar` file from the official Gradle GitHub repository
2. Place it in the `gradle/wrapper` directory
3. Verify that the download was successful

## GitHub Actions Integration

The GitHub Actions workflow has been updated to automatically run the install script before executing any Gradle commands. This ensures that the CI/CD pipeline will work correctly.

## Manual Fix

If you prefer to fix the issue manually, you can:

1. Download the `gradle-wrapper.jar` file from [Gradle's GitHub repository](https://github.com/gradle/gradle/raw/v8.13.0/gradle/wrapper/gradle-wrapper.jar)
2. Place it in the `gradle/wrapper` directory in your project

## Prevention

To prevent this issue in the future:

1. Always use the Gradle wrapper scripts (`./gradlew` or `gradlew.bat`) instead of a global Gradle installation
2. Include the `gradle-wrapper.jar` file in version control
3. Do not modify the Gradle wrapper files manually