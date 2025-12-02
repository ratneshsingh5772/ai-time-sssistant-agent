#!/bin/bash

# Startup script for AI Demo - Gemini Time Agent

echo "======================================"
echo "AI Demo - Gemini Time Agent"
echo "======================================"
echo ""

# Check if GOOGLE_API_KEY is set
if [ -z "$GOOGLE_API_KEY" ]; then
    echo "❌ ERROR: GOOGLE_API_KEY is not set!"
    echo ""
    echo "Please set your Google API key:"
    echo "  export GOOGLE_API_KEY=your-api-key-here"
    echo ""
    echo "You can get an API key from:"
    echo "  https://makersuite.google.com/app/apikey"
    echo ""
    exit 1
fi

echo "✓ GOOGLE_API_KEY is set"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ ERROR: Maven is not installed!"
    echo "Please install Maven to continue."
    exit 1
fi

echo "✓ Maven is installed"
echo ""

# Build the project
echo "Building the project..."
mvn clean package -DskipTests > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo "✓ Build successful"
else
    echo "❌ Build failed!"
    echo "Run 'mvn clean package' to see the error details."
    exit 1
fi

echo ""
echo "======================================"
echo "Starting the application..."
echo "======================================"
echo ""
echo "The application will be available at:"
echo "  http://localhost:8080"
echo ""
echo "API endpoints:"
echo "  POST http://localhost:8080/api/chat"
echo "  GET  http://localhost:8080/api/chat/health"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""
echo "--------------------------------------"
echo ""

# Run the application
mvn spring-boot:run

