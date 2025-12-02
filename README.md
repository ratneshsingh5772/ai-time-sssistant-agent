# AI Demo - Gemini Time Agent

A Spring Boot application that uses Google's Gemini API with function calling to create an AI agent that tells the current time in various cities around the world.

## Prerequisites

- Java 17+
- Maven 3.6+
- Google API Key (for Gemini access)

## Quick Start

The easiest way to run the application:

```bash
# 1. Set your API key
export GOOGLE_API_KEY=your-api-key-here

# 2. Run the application
./run.sh
```

In a separate terminal, test the API:
```bash
./test-api.sh
```

## Setup Instructions

### 1. Set Google API Key

You need a Google API key with access to Gemini models. Get one from [Google AI Studio](https://makersuite.google.com/app/apikey).

Set it as an environment variable:

```bash
export GOOGLE_API_KEY=your-api-key-here
```

Alternatively, you can set it in `src/main/resources/application.properties`:

```properties
google.api.key=your-api-key-here
```

**Note:** Environment variable takes precedence over application.properties.

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Helper Scripts

Three convenience scripts are provided:

1. **run.sh** - Validates environment and starts the application
   ```bash
   ./run.sh
   ```

2. **test-api.sh** - Tests all API endpoints
   ```bash
   ./test-api.sh
   ```

3. **EXAMPLES.md** - Contains detailed curl examples for all use cases

## API Endpoints

### 1. Chat with the Agent

**Endpoint:** `POST /api/chat`

**Request Body:**
```json
{
  "message": "What is the time in Delhi?"
}
```

**Response:**
```json
{
  "reply": "The current time in Delhi is 2025-12-02 15:30:45 IST (Asia/Kolkata)."
}
```

**Example cURL:**
```bash
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}'
```

### 2. Health Check

**Endpoint:** `GET /api/chat/health`

**Example:**
```bash
curl http://localhost:8080/api/chat/health
```

## Example Queries

Try these example queries:

```bash
# Single city
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in New York?"}'

# Multiple cities
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Tell me the current time in London, Tokyo, and Mumbai"}'

# Natural language
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "I need to know what time it is in Paris right now"}'
```

## Supported Cities

The agent supports major cities worldwide, including:

- **India:** Delhi, Mumbai, Bangalore, Chennai, Kolkata
- **USA:** New York, Los Angeles, Chicago, San Francisco, Seattle, Boston, Washington, Dallas
- **Europe:** London, Paris, Berlin
- **Asia:** Tokyo, Beijing, Shanghai, Singapore, Dubai
- **Australia:** Sydney, Melbourne

For cities not explicitly mapped, the agent will use UTC.

## Architecture

### Components

1. **HelloTimeAgent** (`com.ai.aidemo.agent.HelloTimeAgent`)
   - Utility class for time-related operations
   - Maps cities to their time zones
   - Provides getCurrentTime function

2. **GeminiService** (`com.ai.aidemo.service.GeminiService`)
   - Handles communication with Gemini API
   - Implements function calling for tool use
   - Processes API responses and executes functions

3. **AgentService** (`com.ai.aidemo.service.AgentService`)
   - Service layer for agent interactions
   - Delegates to GeminiService

4. **AgentController** (`com.ai.aidemo.controller.AgentController`)
   - REST API endpoints for chat interactions
   - Request/response handling
   - Error management

### DTOs

- **ChatRequest:** Input message from user
- **ChatResponse:** Agent's reply

## Dependencies

- **Spring Boot 3.4.0:** Web framework
- **Spring WebFlux:** For WebClient to call Gemini API
- **Lombok:** Reduces boilerplate code

## Development

### Running in IDE

1. Import the Maven project into your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Set the `GOOGLE_API_KEY` environment variable in your run configuration
3. Run the `AidemoApplication` main class

### Debugging

Set logging level in `application.properties`:

```properties
logging.level.com.ai.aidemo=DEBUG
logging.level.com.google.adk=DEBUG
```

## Troubleshooting

### "GOOGLE_API_KEY is not set" error

Make sure you've set the environment variable before running:
```bash
export GOOGLE_API_KEY=your-api-key-here
mvn spring-boot:run
```

### Agent not responding

1. Check that the API key is valid and has Gemini API access
2. Verify internet connectivity
3. Check logs for errors in the console

### API errors

If you see "429 Too Many Requests":
- You've exceeded your API quota
- Wait a few minutes and try again
- Check your usage at [Google AI Studio](https://makersuite.google.com/)

If you see "400 Bad Request":
- Check the request format in the logs
- Verify the model name is correct (gemini-2.0-flash-exp)

## License

This is a demo project for educational purposes.

## Support

For issues related to:
- **Spring Boot:** https://spring.io/projects/spring-boot
- **Gemini API:** https://ai.google.dev/docs
- **This project:** Check the code comments and logs

## How It Works

1. User sends a message via POST /api/chat
2. AgentService receives the message and forwards to GeminiService
3. GeminiService builds a request with function declarations (tools) for Gemini API
4. Gemini API analyzes the message and decides if it needs to call the getCurrentTime function
5. If a function call is needed, GeminiService extracts the parameters and executes HelloTimeAgent.getCurrentTime()
6. The result is formatted and returned to the user

This demonstrates Gemini's function calling capability, allowing the LLM to use external tools when needed.

