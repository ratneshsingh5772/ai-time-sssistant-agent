# 🎨 Web UI Guide - AI Time Assistant

## How to Access the UI

### Option 1: Direct Browser Access (Easiest)

1. **Make sure your application is running:**
   ```bash
   cd /home/ratnesh/Documents/aidemo
   ./mvnw spring-boot:run
   ```

2. **Open your web browser** and go to:
   ```
   http://localhost:8081
   ```

3. **Start chatting!** The UI will automatically connect to your AI backend.

### Option 2: Open from Command Line

```bash
# For default browser
xdg-open http://localhost:8081

# Or for specific browsers
google-chrome http://localhost:8081
firefox http://localhost:8081
```

---

## UI Features

### 🎯 What You Can Do

- **Ask about time in any city** - "What is the time in Delhi?"
- **Quick suggestions** - Click pre-made question buttons
- **Natural conversation** - Chat naturally with the AI
- **Real-time responses** - See typing indicators and smooth animations
- **Rate limit warnings** - Get notified if you hit API limits

### 🎨 UI Elements

1. **Header** - Shows AI status and branding
2. **Chat Area** - Displays conversation history
3. **Suggestion Buttons** - Quick access to common queries
4. **Input Box** - Type your questions
5. **Send Button** - Submit your message (or press Enter)

---

## Quick Start Examples

Once the UI is open, try these:

1. **Click a suggestion button:**
   - "🇮🇳 Time in Delhi"
   - "🇺🇸 Time in New York"
   - "🇯🇵 Time in Tokyo"

2. **Type custom questions:**
   - "What's the time in Paris?"
   - "Tell me the current time in Sydney"
   - "Hello! How are you?"

---

## Screenshots Description

The UI features:
- **Beautiful gradient theme** (Purple/Blue)
- **Chat bubbles** (Your messages on right, AI on left)
- **Loading animation** (Bouncing dots while AI thinks)
- **Smooth animations** (Messages fade in)
- **Responsive design** (Works on desktop and mobile)

---

## Troubleshooting

### UI doesn't load (blank page)
**Solution:**
```bash
# Check if server is running
curl http://localhost:8081

# If not, start it
./mvnw spring-boot:run
```

### "Failed to connect to AI service"
**Solution:**
- Verify the application is running
- Check the console for errors
- Make sure port 8081 is not blocked

### Rate Limit Error in UI
**Solution:**
- The UI will show a warning message
- Wait 2-3 minutes between requests
- See the on-screen instructions

### UI shows but doesn't respond
**Solution:**
- Check browser console (F12) for errors
- Verify `/api/chat` endpoint: `curl -X POST http://localhost:8081/api/chat -H "Content-Type: application/json" -d '{"message":"test"}'`
- Restart the application

---

## Testing the UI

### Step 1: Start the Application
```bash
cd /home/ratnesh/Documents/aidemo
./mvnw spring-boot:run
```

Wait for:
```
Started AidemoApplication in X.XXX seconds
```

### Step 2: Open Browser
```bash
xdg-open http://localhost:8081
```

### Step 3: Test
1. Click "🇮🇳 Time in Delhi" button
2. Wait for AI response
3. Try typing your own question

---

## Technical Details

**File Location:** `src/main/resources/static/index.html`

**How it works:**
1. Spring Boot automatically serves files from `static/` folder
2. `index.html` is the default page at `http://localhost:8081/`
3. UI makes AJAX calls to `/api/chat` endpoint
4. Responses are displayed in real-time

**Technologies Used:**
- Pure HTML/CSS/JavaScript (no framework needed)
- Fetch API for HTTP requests
- CSS animations and gradients
- Responsive design

---

## Customization

### Change Colors
Edit the CSS in `index.html`:
```css
/* Find this line and change colors */
background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
```

### Add More Suggestions
Edit the HTML in `index.html`:
```html
<button class="suggestion-btn" onclick="sendSuggestion('Your question here')">
    🌍 Your Label
</button>
```

### Change Port
Edit `application.properties`:
```properties
server.port=8082
```
Then access at: `http://localhost:8082`

---

## Alternative: Mobile Access

If you want to access from another device on the same network:

1. **Find your IP address:**
   ```bash
   hostname -I | awk '{print $1}'
   ```

2. **Allow external access** (edit `application.properties`):
   ```properties
   server.address=0.0.0.0
   ```

3. **Access from mobile:**
   ```
   http://YOUR_IP:8081
   ```
   Example: `http://192.168.1.100:8081`

---

## Demo Workflow

```
1. Start Application
   └─> ./mvnw spring-boot:run

2. Open Browser
   └─> http://localhost:8081

3. Click Suggestion
   └─> "🇮🇳 Time in Delhi"

4. See AI Response
   └─> "The current time in Delhi is..."

5. Type Custom Question
   └─> "What time is it in Paris?"

6. Chat Naturally!
   └─> Continue conversation
```

---

## Production Tips

For production deployment:

1. **Build the JAR:**
   ```bash
   ./mvnw clean package
   ```

2. **Run the JAR:**
   ```bash
   java -jar target/aidemo-0.0.1-SNAPSHOT.jar
   ```

3. **Use environment variables for API key:**
   ```bash
   export GOOGLE_API_KEY=your_key_here
   java -jar target/aidemo-0.0.1-SNAPSHOT.jar
   ```

4. **Deploy to cloud** (Heroku, AWS, Google Cloud, etc.)

---

## Support

**UI not working?** Check:
- [ ] Application is running (`ps aux | grep aidemo`)
- [ ] Port 8081 is accessible (`curl http://localhost:8081`)
- [ ] Browser console for JavaScript errors (Press F12)
- [ ] API endpoint works (`curl -X POST http://localhost:8081/api/chat ...`)

**Need help?** Check the logs:
```bash
# If running in terminal, check the output
# Or check application logs for errors
```

---

## Enjoy Your AI Assistant! 🚀

You now have a beautiful web interface to interact with your AI agent. No command line needed - just open your browser and start chatting!

