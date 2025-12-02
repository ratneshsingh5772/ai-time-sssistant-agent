#!/bin/bash

echo "================================================"
echo "  🚀 Launching AI Time Assistant"
echo "================================================"
echo ""

# Check if application is already running
if lsof -Pi :8081 -sTCP:LISTEN -t >/dev/null 2>&1 ; then
    echo "✓ Application is already running on port 8081"
else
    echo "⚠️  Application is not running. Starting it now..."
    echo ""
    cd /home/ratnesh/Documents/aidemo
    ./mvnw spring-boot:run > /tmp/aidemo.log 2>&1 &
    APP_PID=$!
    echo "   Started application (PID: $APP_PID)"
    echo "   Waiting for application to start..."

    # Wait for application to be ready
    for i in {1..30}; do
        if curl -s http://localhost:8081 > /dev/null 2>&1; then
            echo "   ✓ Application is ready!"
            break
        fi
        echo -n "."
        sleep 1
    done
    echo ""
fi

echo ""
echo "================================================"
echo "  📱 Opening Web UI in your browser..."
echo "================================================"
echo ""
echo "  URL: http://localhost:8081"
echo ""

# Try to open browser
if command -v xdg-open > /dev/null; then
    xdg-open http://localhost:8081 2>/dev/null
elif command -v google-chrome > /dev/null; then
    google-chrome http://localhost:8081 2>/dev/null &
elif command -v firefox > /dev/null; then
    firefox http://localhost:8081 2>/dev/null &
else
    echo "  Please open your browser manually and go to:"
    echo "  http://localhost:8081"
fi

echo ""
echo "================================================"
echo "  ✨ Ready to chat with your AI Assistant!"
echo "================================================"
echo ""
echo "  💡 Tips:"
echo "     - Click suggestion buttons for quick queries"
echo "     - Ask about time in any city"
echo "     - Press Enter to send messages"
echo "     - Wait 2-3 minutes between requests to avoid rate limits"
echo ""
echo "  📖 For help, see: UI_GUIDE.md"
echo ""
echo "  To stop the application: pkill -f aidemo"
echo ""

