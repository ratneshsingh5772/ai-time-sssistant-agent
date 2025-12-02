# 🤖 AI Time Assistant - Intelligent Agent Demo

**An AI-powered chatbot that tells you the current time in any city using natural language and agent-based architecture.**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Google Gemini](https://img.shields.io/badge/Google-Gemini%20AI-blue.svg)](https://ai.google.dev/)

---

## 🚀 Quick Start

### One-Line Launch
```bash
./launch-ui.sh
```

This will automatically:
1. Start the Spring Boot application
2. Open your browser to the chat interface
3. You're ready to chat with the AI!

### Or Start Manually
```bash
# Start the backend
./mvnw spring-boot:run

# Open browser
xdg-open http://localhost:8081
```

---

## ✨ Features

- 🗣️ **Natural Language Interface** - Ask questions naturally
- 🌍 **30+ Cities Supported** - Major cities worldwide
- ⚡ **Real-time Responses** - Instant timezone information
- 🎨 **Beautiful Web UI** - Modern, responsive chat interface
- 🤖 **AI-Powered** - Google Gemini with function calling
- 🛠️ **Agent Architecture** - Extensible tool-based system

---

## 📖 What Is This?

This project demonstrates **AI Agent architecture** - where an AI model (Google Gemini) can intelligently call functions/tools to accomplish tasks.

**Example:**
```
You: "What is the time in Delhi?"
AI: *Understands intent → Calls getCurrentTime("Delhi") → Formats result*
AI: "The current time in Delhi is 2025-12-02 14:30:45 IST (Asia/Kolkata)."
```

---

## 🏗️ Architecture

```
Web UI → REST API → Agent Service → Gemini AI
                                        ↓
                                   Function Call
                                        ↓
                                 HelloTimeAgent
                                        ↓
                                  Time Result
```

**Key Components:**
- **AgentController** - REST endpoints
- **GeminiService** - AI integration with function calling
- **HelloTimeAgent** - Time zone tool/function
- **Web UI** - Beautiful chat interface

---

## 🧪 Testing

### Test via Web UI (Recommended)
```bash
./launch-ui.sh
```

### Test via Command Line
```bash
# Single test
./test-single.sh

# Full test suite (wait 5 minutes between runs to avoid rate limits)
./test-ai.sh

# Manual cURL test
curl -X POST http://localhost:8081/api/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "What is the time in Delhi?"}'
```

---

## 📚 Documentation

- **[PROJECT_PRESENTATION.md](PROJECT_PRESENTATION.md)** - Complete project presentation
  - Problem statement
  - Why agents?
  - Architecture details
  - Demo scenarios
  - Future roadmap
  
- **[STATUS.md](STATUS.md)** - Current project status and setup verification

- **[TESTING_GUIDE.md](TESTING_GUIDE.md)** - Comprehensive testing guide with troubleshooting

- **[UI_GUIDE.md](UI_GUIDE.md)** - Web UI usage and customization guide

---

## 🛠️ Technology Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.4.0** - Application framework
- **Maven** - Build tool
- **Lombok** - Code generation

### AI/ML
- **Google Gemini API** - Large Language Model
- **Function Calling** - Tool integration

### Frontend
- **HTML5/CSS3** - UI structure and styling
- **Vanilla JavaScript** - No frameworks needed
- **Responsive Design** - Works on all devices

---

## 📁 Project Structure

```
aidemo/
├── src/
│   ├── main/
│   │   ├── java/com/ai/aidemo/
│   │   │   ├── agent/HelloTimeAgent.java       # Time tool
│   │   │   ├── controller/AgentController.java # REST API
│   │   │   ├── service/GeminiService.java      # AI integration
│   │   │   └── dto/                            # Request/Response DTOs
│   │   └── resources/
│   │       ├── application.properties          # Config
│   │       └── static/index.html               # Web UI
├── launch-ui.sh                                # Quick launcher
├── test-ai.sh                                  # Test suite
└── PROJECT_PRESENTATION.md                     # Full documentation
```

---

## 🎯 Use Cases

- ✅ **International Meeting Scheduling** - Coordinate across time zones
- ✅ **Travel Planning** - Check local times before booking
- ✅ **Remote Team Coordination** - Manage distributed teams
- ✅ **Family Connections** - Stay in touch with relatives abroad

---

## 🔧 Configuration

API key is configured in `src/main/resources/application.properties`:
```properties
google.api.key=YOUR_API_KEY_HERE
server.port=8081
```

---

## ⚠️ Known Limitations

- **Rate Limits**: Google's free tier allows 15 requests/minute
  - Wait 2-3 minutes between test runs
  - Use `./test-single.sh` instead of full test suite
  
- **City Coverage**: 30+ major cities supported
  - Unknown cities return graceful error messages

---

## 🚀 Future Enhancements

### Coming Soon
- [ ] Session management with conversation history
- [ ] Multiple tools (weather, currency, flights)
- [ ] User authentication
- [ ] Database integration
- [ ] Voice input support

### Long-Term Vision
- [ ] Mobile app (iOS/Android)
- [ ] Multi-agent orchestration
- [ ] Calendar integration
- [ ] Enterprise features
- [ ] Custom AI model fine-tuning

See [PROJECT_PRESENTATION.md](PROJECT_PRESENTATION.md) for full roadmap.

---

## 🎬 Demo

### Try These Queries:

```
"What is the time in Delhi?"
"Tell me the current time in Tokyo"
"What's the time in New York right now?"
"Time in London please"
"Hello! How are you?"
```

### Quick Demo:
```bash
./launch-ui.sh
```
Then click any suggestion button in the UI!

---

## 🐛 Troubleshooting

### Application won't start
```bash
# Check if port is in use
lsof -i :8081

# Kill existing process
pkill -f aidemo

# Restart
./mvnw spring-boot:run
```

### 429 Rate Limit Error
- **This is normal!** Your app is working correctly
- Wait 2-3 minutes between requests
- Google's free tier: 15 requests/minute

### UI doesn't load
```bash
# Rebuild with static files
./mvnw clean package
./mvnw spring-boot:run
```

See [TESTING_GUIDE.md](TESTING_GUIDE.md) for detailed troubleshooting.

---

## 📊 Project Status

✅ **Fully Functional**
- REST API working
- AI integration complete
- Web UI deployed
- Testing scripts ready
- Documentation complete

---

## 🙏 Acknowledgments

- **Google Gemini** - AI model and function calling
- **Spring Boot** - Excellent Java framework
- **Open Source Community** - Amazing tools and libraries

---

## 📞 Quick Links

- **Start Application**: `./launch-ui.sh`
- **Test API**: `./test-single.sh`
- **View Presentation**: [PROJECT_PRESENTATION.md](PROJECT_PRESENTATION.md)
- **Troubleshoot**: [TESTING_GUIDE.md](TESTING_GUIDE.md)

---

**Built with ❤️ using AI Agents, Spring Boot, and Google Gemini**

*December 2025*

