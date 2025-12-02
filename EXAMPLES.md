# Example API Requests for AI Demo - Gemini Time Agent

This file contains example curl commands to test the application.

## Prerequisites

Make sure the application is running:
```bash
./run.sh
# or
mvn spring-boot:run
```

---

## Health Check

```bash
curl http://localhost:8080/api/chat/health
```

Expected response:
```json
{
  "reply": "Agent service is running"
}
```

---

## Single City Time Queries

### Delhi (India)
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}'
```

### New York (USA)
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in New York?"}'
```

### Tokyo (Japan)
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Tokyo?"}'
```

### London (UK)
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in London?"}'
```

### Paris (France)
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Paris?"}'
```

### Sydney (Australia)
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Sydney?"}'
```

---

## Multiple Cities

### Two Cities
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Tell me the time in Tokyo and London"}'
```

### Three Cities
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the current time in Delhi, New York, and Sydney?"}'
```

---

## Natural Language Queries

### Conversational Style
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "I need to know what time it is in San Francisco right now"}'
```

### Informal Query
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Hey, can you tell me the time in Mumbai?"}'
```

### Business Context
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "I have a meeting with someone in Berlin. What time is it there?"}'
```

---

## Different US Time Zones

### East Coast
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Boston?"}'
```

### West Coast
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Los Angeles?"}'
```

### Central
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Chicago?"}'
```

---

## Error Cases

### Empty Message
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": ""}'
```

Expected: HTTP 400 Bad Request

### Unknown City
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in RandomCityThatDoesNotExist?"}'
```

Expected: Response indicating the timezone couldn't be determined

---

## Using the Test Script

Run all tests at once:
```bash
./test-api.sh
```

---

## Response Format

All successful responses follow this format:
```json
{
  "reply": "The current time in [City] is [Time] ([Timezone])."
}
```

Example:
```json
{
  "reply": "The current time in Delhi is 2025-12-02 15:30:45 IST (Asia/Kolkata)."
}
```

---

## Tips

1. **Pretty Print JSON**: Add `| jq` to any curl command
   ```bash
   curl http://localhost:8080/api/chat/health | jq
   ```

2. **Save Response**: Redirect output to a file
   ```bash
   curl http://localhost:8080/api/chat/health > response.json
   ```

3. **View HTTP Status**: Add `-w "\nHTTP Status: %{http_code}\n"`
   ```bash
   curl -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/chat/health
   ```

4. **Verbose Mode**: Add `-v` to see full request/response details
   ```bash
   curl -v http://localhost:8080/api/chat/health
   ```

