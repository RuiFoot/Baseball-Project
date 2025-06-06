#!/bin/bash

# This script downloads and installs the Gradle wrapper

# Ensure we're in the project root directory
cd "$(dirname "$0")/../.." || exit

# Create directories if they don't exist
mkdir -p gradle/wrapper

# Download the Gradle wrapper JAR
echo "Downloading Gradle wrapper JAR..."
curl -L -o gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/v8.13.0/gradle/wrapper/gradle-wrapper.jar

# Verify the download
if [ ! -s gradle/wrapper/gradle-wrapper.jar ]; then
    echo "Failed to download gradle-wrapper.jar or file is empty"
    exit 1
fi

echo "Gradle wrapper JAR downloaded successfully"

# Make gradlew executable
chmod +x gradlew

echo "Gradle wrapper installation complete"