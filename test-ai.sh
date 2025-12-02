#!/bin/bash

echo "========================================"
echo "Testing AI Agent Application"
echo "========================================"
echo ""

# Test 1: Check if server is running
echo "Test 1: Checking if server is running on port 8081..."
if curl -s http://localhost:8081 > /dev/null 2>&1; then
    echo "✓ Server is running on port 8081"
else
    echo "✗ Server is NOT running on port 8081"
    echo "  Please start the application first with: ./mvnw spring-boot:run"
    exit 1
fi
echo ""

# Test 2: Ask for time in Delhi
echo "Test 2: Asking 'What is the time in Delhi?'"
echo "Request:"
echo '{"message": "What is the time in Delhi?"}'
echo ""
echo "Response:"
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}' \
     2>/dev/null | jq . 2>/dev/null || curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}' 2>/dev/null
echo ""
echo ""

# Test 3: Ask for time in New York
echo "Test 3: Asking 'What is the time in New York?'"
echo "Request:"
echo '{"message": "What is the time in New York?"}'
echo ""
echo "Response:"
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in New York?"}' \
     2>/dev/null | jq . 2>/dev/null || curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in New York?"}' 2>/dev/null
echo ""
echo ""

# Test 4: Ask for time in Tokyo
echo "Test 4: Asking 'What is the time in Tokyo?'"
echo "Request:"
echo '{"message": "What is the time in Tokyo?"}'
echo ""
echo "Response:"
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Tokyo?"}' \
     2>/dev/null | jq . 2>/dev/null || curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Tokyo?"}' 2>/dev/null
echo ""
echo ""

# Test 5: General question
echo "Test 5: Asking a general question 'Hello, how are you?'"
echo "Request:"
echo '{"message": "Hello, how are you?"}'
echo ""
echo "Response:"
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello, how are you?"}' \
     2>/dev/null | jq . 2>/dev/null || curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello, how are you?"}' 2>/dev/null
echo ""
echo ""

echo "========================================"
echo "Tests completed!"
echo "========================================"

