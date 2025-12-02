# AI Agent Testing Guide

This guide explains how to test if your AI agent is working correctly.

## Prerequisites

1. **Ensure the application is running:**
   ```bash
   cd /home/ratnesh/Documents/aidemo
   ./mvnw spring-boot:run
   ```
   
   Wait until you see a message like:
   ```
   Started AidemoApplication in X.XXX seconds
   ```

2. **Check the port:**
   The application runs on **port 8081** (configured in `application.properties`)

## Testing Methods

### Method 1: Using the Test Script (Easiest)

Run the automated test script:
```bash
./test-ai.sh
```

This will run 5 different tests and show you the results.

### Method 2: Manual Testing with curl

#### Test 1: Ask for time in Delhi
```bash
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}'
```

**Expected Response:**
```json
{
  "reply": "The current time in Delhi is 2025-12-02 09:56:18 IST"
}
```

#### Test 2: Ask for time in New York
```bash
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in New York?"}'
```

#### Test 3: Ask for time in Tokyo
```bash
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Tell me the current time in Tokyo"}'
```

#### Test 4: General conversation
```bash
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello! How are you?"}'
```

### Method 3: Using Postman or Browser Extensions

1. **Open Postman** (or any REST client)
2. **Create a new POST request** to: `http://localhost:8081/api/chat`
3. **Set Headers:**
   - `Content-Type: application/json`
4. **Set Body (raw JSON):**
   ```json
   {
     "message": "What is the time in Delhi?"
   }
   ```
5. **Click Send**

### Method 4: Using a Simple HTML Page

Create a file `test.html` with this content:

```html
<!DOCTYPE html>
<html>
<head>
    <title>AI Agent Test</title>
</head>
<body>
    <h1>AI Agent Test</h1>
    <input type="text" id="message" placeholder="Ask something..." style="width: 300px">
    <button onclick="sendMessage()">Send</button>
    <div id="response" style="margin-top: 20px; padding: 10px; background: #f0f0f0;"></div>

    <script>
        async function sendMessage() {
            const message = document.getElementById('message').value;
            const response = await fetch('http://localhost:8081/api/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message: message })
            });
            const data = await response.json();
            document.getElementById('response').innerHTML = '<strong>AI Response:</strong><br>' + data.reply;
        }
    </script>
</body>
</html>
```

Open this file in your browser and test!

## Troubleshooting

### Issue 1: Connection Refused
**Problem:** `curl: (7) Failed to connect to localhost port 8081: Connection refused`

**Solution:**
- Check if the application is running: `ps aux | grep aidemo`
- Check if port 8081 is in use: `netstat -tlnp | grep 8081`
- Start the application if not running: `./mvnw spring-boot:run`

### Issue 2: Port Already in Use
**Problem:** `Web server failed to start. Port 8081 was already in use.`

**Solution:**
- Kill the process using port 8081: `kill -9 $(lsof -t -i:8081)`
- Or change the port in `application.properties`: `server.port=8082`

### Issue 3: API Key Error
**Problem:** `GOOGLE_API_KEY is not set!`

**Solution:**
- Check `application.properties` has the correct API key:
  ```
  google.api.key=GOOGLE API KEY
  ```

### Issue 4: Empty Response
**Problem:** The curl command returns nothing

**Solution:**
- Check the application logs for errors
- Verify the endpoint exists: `curl http://localhost:8081/actuator/health`
- Check the GeminiService logs for API errors

### Issue 5: 429 Too Many Requests ⚠️
**Problem:** `429 Too Many Requests from POST https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp:generateContent`

**What it means:**
- Your API key has hit the rate limit (too many requests in a short time)
- Google Gemini API free tier has limits:
  - **15 requests per minute (RPM)**
  - **1 million tokens per minute (TPM)**
  - **1,500 requests per day (RPD)**

**Solutions:**
1. **Wait a few minutes** and try again
2. **Reduce request frequency** - Don't send multiple requests rapidly
3. **Use a different model** (change in GeminiService.java):
   - Try `gemini-1.5-flash` or `gemini-1.5-pro` instead of `gemini-2.0-flash-exp`
4. **Upgrade your API key** to a paid tier for higher limits
5. **Check your quota**: Visit [Google AI Studio](https://aistudio.google.com/) → API Keys

**For testing:**
- Wait 2-3 minutes between test runs
- Test one query at a time instead of running the full test script
- The application is working correctly - this is just a rate limit issue!

## What Success Looks Like

✅ **Working AI Agent:**
- The application starts without errors
- curl commands return JSON responses with a "reply" field
- The AI responds appropriately to time queries
- The AI can call the `getCurrentTime` function tool
- Responses are relevant and helpful

⚠️ **Working but Rate Limited:**
- Server responds on port 8081 ✓
- Returns JSON with error about "429 Too Many Requests"
- This means your application is working, but you've hit Google's API rate limit
- Wait a few minutes and try again

❌ **Not Working:**
- Connection refused errors
- Empty responses or null responses
- Application won't start
- Compilation errors

## Quick Health Check

Run this command to quickly verify everything is working:
```bash
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "ping"}' && echo ""
```

If you get a JSON response with a "reply" field, your AI is working! 🎉

## Advanced Testing

### Check Application Logs
```bash
tail -f /tmp/aidemo.log
```

### Test API Response Time
```bash
time curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}'
```

### Continuous Testing
```bash
watch -n 5 'curl -s -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d "{\"message\": \"What is the time in Delhi?\"}"'
```

This will call the API every 5 seconds and show the response.

## Support

If you encounter issues:
1. Check the application logs
2. Verify your API key is valid
3. Ensure port 8081 is not blocked by firewall
4. Test with simple messages first before complex queries

