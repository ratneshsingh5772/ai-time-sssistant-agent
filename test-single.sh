#!/bin/bash

echo "========================================"
echo "Single AI Test (Rate-Limit Friendly)"
echo "========================================"
echo ""

# Check if server is running
echo "Checking if server is running on port 8081..."
if ! curl -s http://localhost:8081 > /dev/null 2>&1; then
    echo "✗ Server is NOT running on port 8081"
    echo "  Please start the application first with: ./mvnw spring-boot:run"
    exit 1
fi
echo "✓ Server is running on port 8081"
echo ""

# Get the message from command line argument or use default
MESSAGE="${1:-What is the time in Delhi?}"

echo "Sending message: \"$MESSAGE\""
echo ""
echo "Response:"
echo "---"

curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d "{\"message\": \"$MESSAGE\"}" \
     2>/dev/null | jq . 2>/dev/null || curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d "{\"message\": \"$MESSAGE\"}" 2>/dev/null

echo ""
echo "---"
echo ""
echo "✓ Test completed!"
echo ""
echo "💡 Tips:"
echo "   - Wait 2-3 minutes between tests to avoid rate limits"
echo "   - Run with custom message: ./test-single.sh \"Your message here\""
echo "   - Check Google AI Studio for your API quota status"
echo ""

