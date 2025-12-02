#!/bin/bash

# Test script for AI Demo - Gemini Time Agent
# This script tests the REST API endpoints

BASE_URL="http://localhost:8080"

echo "======================================"
echo "AI Demo - Gemini Time Agent Test Script"
echo "======================================"
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if server is running
echo "1. Testing health endpoint..."
HEALTH_RESPONSE=$(curl -s -w "\n%{http_code}" ${BASE_URL}/api/chat/health)
HTTP_CODE=$(echo "$HEALTH_RESPONSE" | tail -n1)
BODY=$(echo "$HEALTH_RESPONSE" | head -n-1)

if [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ Health check passed${NC}"
    echo "  Response: $BODY"
else
    echo -e "${RED}✗ Health check failed (HTTP $HTTP_CODE)${NC}"
    echo "  Make sure the application is running: mvn spring-boot:run"
    exit 1
fi

echo ""
echo "2. Testing chat endpoint with Delhi query..."
DELHI_RESPONSE=$(curl -s -X POST ${BASE_URL}/api/chat \
    -H "Content-Type: application/json" \
    -d '{"message": "What is the time in Delhi?"}')

if echo "$DELHI_RESPONSE" | grep -q "reply"; then
    echo -e "${GREEN}✓ Delhi query successful${NC}"
    echo "  Response: $DELHI_RESPONSE" | jq '.' 2>/dev/null || echo "  Response: $DELHI_RESPONSE"
else
    echo -e "${RED}✗ Delhi query failed${NC}"
    echo "  Response: $DELHI_RESPONSE"
fi

echo ""
echo "3. Testing chat endpoint with New York query..."
NY_RESPONSE=$(curl -s -X POST ${BASE_URL}/api/chat \
    -H "Content-Type: application/json" \
    -d '{"message": "What is the time in New York?"}')

if echo "$NY_RESPONSE" | grep -q "reply"; then
    echo -e "${GREEN}✓ New York query successful${NC}"
    echo "  Response: $NY_RESPONSE" | jq '.' 2>/dev/null || echo "  Response: $NY_RESPONSE"
else
    echo -e "${RED}✗ New York query failed${NC}"
    echo "  Response: $NY_RESPONSE"
fi

echo ""
echo "4. Testing chat endpoint with multiple cities..."
MULTI_RESPONSE=$(curl -s -X POST ${BASE_URL}/api/chat \
    -H "Content-Type: application/json" \
    -d '{"message": "Tell me the time in Tokyo and London"}')

if echo "$MULTI_RESPONSE" | grep -q "reply"; then
    echo -e "${GREEN}✓ Multiple cities query successful${NC}"
    echo "  Response: $MULTI_RESPONSE" | jq '.' 2>/dev/null || echo "  Response: $MULTI_RESPONSE"
else
    echo -e "${RED}✗ Multiple cities query failed${NC}"
    echo "  Response: $MULTI_RESPONSE"
fi

echo ""
echo "5. Testing invalid request (empty message)..."
INVALID_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST ${BASE_URL}/api/chat \
    -H "Content-Type: application/json" \
    -d '{"message": ""}')
HTTP_CODE=$(echo "$INVALID_RESPONSE" | tail -n1)

if [ "$HTTP_CODE" -eq 400 ]; then
    echo -e "${GREEN}✓ Empty message validation works${NC}"
else
    echo -e "${YELLOW}⚠ Expected 400 for empty message, got $HTTP_CODE${NC}"
fi

echo ""
echo "======================================"
echo "Test Summary"
echo "======================================"
echo -e "${GREEN}All tests completed!${NC}"
echo ""
echo "Note: If you see API errors, make sure:"
echo "  1. GOOGLE_API_KEY environment variable is set"
echo "  2. Your API key has access to Gemini API"
echo "  3. You have internet connectivity"

