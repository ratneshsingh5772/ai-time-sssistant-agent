# Project Summary: AI Demo - Gemini Time Agent

## Overview

This Spring Boot application demonstrates how to build an AI-powered agent using Google's Gemini API with function calling capabilities. The agent can tell users the current time in cities around the world.

## Key Features

1. **Function Calling**: Uses Gemini's function calling to execute the `getCurrentTime` tool
2. **Natural Language Understanding**: Handles conversational queries about time
3. **Multiple Cities Support**: Can process queries about multiple cities in one request
4. **RESTful API**: Clean REST endpoints for integration
5. **Production Ready**: Includes error handling, logging, and validation

## Project Structure

```
aidemo/
├── src/
│   ├── main/
│   │   ├── java/com/ai/aidemo/
│   │   │   ├── AidemoApplication.java          # Main Spring Boot application
│   │   │   ├── agent/
│   │   │   │   └── HelloTimeAgent.java         # Time utility functions
│   │   │   ├── controller/
│   │   │   │   └── AgentController.java        # REST API endpoints
│   │   │   ├── dto/
│   │   │   │   ├── ChatRequest.java            # Request DTO
│   │   │   │   └── ChatResponse.java           # Response DTO
│   │   │   └── service/
│   │   │       ├── AgentService.java           # Business logic layer
│   │   │       └── GeminiService.java          # Gemini API integration
│   │   └── resources/
│   │       └── application.properties           # Configuration
│   └── test/
│       └── java/com/ai/aidemo/
│           └── AidemoApplicationTests.java      # Unit tests
├── pom.xml                                      # Maven dependencies
├── README.md                                    # Main documentation
├── EXAMPLES.md                                  # API examples
├── run.sh                                       # Startup script
├── test-api.sh                                  # Test script
└── .gitignore                                   # Git ignore rules
```

## Technical Stack

- **Java 17**: Modern Java LTS version
- **Spring Boot 3.4.0**: Application framework
  - Spring Web: REST API
  - Spring WebFlux: WebClient for HTTP calls
- **Lombok**: Reduces boilerplate code
- **Maven**: Build and dependency management
- **Gemini 2.0 Flash**: Google's AI model

## API Architecture

### Flow

1. **User Request** → REST Controller (`/api/chat`)
2. **Controller** → AgentService (business logic)
3. **AgentService** → GeminiService (API integration)
4. **GeminiService** → Gemini API (with function declarations)
5. **Gemini API** → Returns function call request
6. **GeminiService** → Executes `HelloTimeAgent.getCurrentTime()`
7. **Response** → Formatted and returned to user

### Function Calling

The application declares a `getCurrentTime` function to Gemini:

```json
{
  "name": "getCurrentTime",
  "description": "Get the current time in a specified city",
  "parameters": {
    "type": "object",
    "properties": {
      "city": {
        "type": "string",
        "description": "The name of the city"
      }
    },
    "required": ["city"]
  }
}
```

When Gemini determines it needs time information, it calls this function, and the application executes the Java method and returns the result.

## Configuration

### Required Environment Variables

- `GOOGLE_API_KEY`: Your Google API key with Gemini access

### Optional Configuration (application.properties)

```properties
# Server
server.port=8080

# Google API
google.api.key=${GOOGLE_API_KEY}

# Logging
logging.level.com.ai.aidemo=INFO
```

## Running the Application

### Option 1: Quick Start Script
```bash
export GOOGLE_API_KEY=your-api-key-here
./run.sh
```

### Option 2: Maven
```bash
export GOOGLE_API_KEY=your-api-key-here
mvn spring-boot:run
```

### Option 3: JAR
```bash
export GOOGLE_API_KEY=your-api-key-here
mvn clean package
java -jar target/aidemo-0.0.1-SNAPSHOT.jar
```

## Testing

### Automated Testing
```bash
./test-api.sh
```

### Manual Testing
```bash
# Health check
curl http://localhost:8080/api/chat/health

# Get time
curl -X POST http://localhost:8080/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}'
```

## Supported Cities

The application has built-in timezone mappings for:
- **India**: Delhi, Mumbai, Bangalore, Chennai, Kolkata
- **USA**: New York, Los Angeles, Chicago, San Francisco, Seattle
- **Europe**: London, Paris, Berlin
- **Asia**: Tokyo, Beijing, Shanghai, Singapore, Dubai
- **Australia**: Sydney, Melbourne

Unknown cities default to UTC.

## Error Handling

The application handles:
- Invalid API keys (startup validation)
- Empty messages (HTTP 400)
- Network errors (graceful error messages)
- API rate limits (error responses)
- Unknown cities (UTC fallback)

## Security Considerations

1. **API Key**: Never commit API keys to version control
2. **Environment Variables**: Use env vars for secrets
3. **Input Validation**: All user inputs are validated
4. **Error Messages**: Don't expose sensitive information in errors

## Extending the Application

### Adding More Functions

1. Create a new method in an agent class
2. Add function declaration in `GeminiService.buildRequest()`
3. Handle the function call in `GeminiService.handleFunctionCall()`

### Adding More Cities

1. Update `HelloTimeAgent.getTimeZoneForCity()` with new mappings

### Adding Session Management

1. Store user sessions in a Map or Redis
2. Include conversation history in Gemini requests
3. Implement session timeout logic

## Performance

- **Cold Start**: ~2-5 seconds (Spring Boot initialization)
- **API Response Time**: ~1-3 seconds (Gemini API latency)
- **Concurrent Requests**: Handles multiple simultaneous requests

## Deployment

### Local Development
```bash
./run.sh
```

### Docker (create Dockerfile)
```dockerfile
FROM openjdk:17-slim
COPY target/aidemo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Cloud Deployment
- AWS Elastic Beanstalk
- Google Cloud Run
- Azure App Service
- Heroku

Set `GOOGLE_API_KEY` as an environment variable in your cloud platform.

## Monitoring

Check application health:
```bash
curl http://localhost:8080/api/chat/health
```

View logs:
```bash
tail -f logs/spring.log  # if configured
```

## Troubleshooting

See README.md for detailed troubleshooting steps.

## Future Enhancements

1. **More Tools**: Weather, currency conversion, etc.
2. **Database**: Store conversation history
3. **Authentication**: User authentication and authorization
4. **Rate Limiting**: Prevent API abuse
5. **Caching**: Cache timezone lookups
6. **WebSocket**: Real-time streaming responses
7. **Multi-language**: Support multiple languages

## Resources

- [Google AI Documentation](https://ai.google.dev/docs)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Gemini API Quickstart](https://ai.google.dev/tutorials/rest_quickstart)

## License

This is a demo project for educational purposes.

## Author

Created as a demonstration of Gemini API function calling with Spring Boot.

