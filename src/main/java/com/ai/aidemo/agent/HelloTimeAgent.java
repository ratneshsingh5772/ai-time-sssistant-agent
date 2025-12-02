package com.ai.aidemo.agent;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello Time Agent - A utility class for time-related operations.
 *
 * This class provides time information for cities across the world.
 */
public class HelloTimeAgent {

    /**
     * Private constructor to prevent instantiation of this utility class
     */
    private HelloTimeAgent() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Tool function to get the current time in a specified city.
     *
     * @param city The city name (e.g., "Delhi", "New York", "London")
     * @return A map containing city name and current time
     */
    public static Map<String, String> getCurrentTime(String city) {

        Map<String, String> result = new HashMap<>();
        result.put("city", city);

        // Map common city names to their time zones
        String timeZone = getTimeZoneForCity(city);

        try {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(timeZone));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String formattedTime = now.format(formatter);

            result.put("time", formattedTime);
            result.put("timezone", timeZone);
        } catch (Exception e) {
            // If timezone is not found, return a default message
            result.put("time", "Unknown - could not determine timezone for " + city);
            result.put("timezone", "Unknown");
        }

        return result;
    }

    /**
     * Maps city names to their corresponding time zones.
     * This is a simplified mapping - in production, you'd use a proper geocoding service.
     */
    private static String getTimeZoneForCity(String city) {
        String cityLower = city.toLowerCase().trim();

        // Indian cities
        if (cityLower.contains("delhi") || cityLower.contains("mumbai") ||
            cityLower.contains("bangalore") || cityLower.contains("chennai") ||
            cityLower.contains("kolkata") || cityLower.contains("india")) {
            return "Asia/Kolkata";
        }

        // US cities
        if (cityLower.contains("new york") || cityLower.contains("boston") ||
            cityLower.contains("washington")) {
            return "America/New_York";
        }
        if (cityLower.contains("los angeles") || cityLower.contains("san francisco") ||
            cityLower.contains("seattle")) {
            return "America/Los_Angeles";
        }
        if (cityLower.contains("chicago") || cityLower.contains("dallas")) {
            return "America/Chicago";
        }

        // European cities
        if (cityLower.contains("london") || cityLower.contains("uk")) {
            return "Europe/London";
        }
        if (cityLower.contains("paris") || cityLower.contains("france")) {
            return "Europe/Paris";
        }
        if (cityLower.contains("berlin") || cityLower.contains("germany")) {
            return "Europe/Berlin";
        }

        // Asian cities
        if (cityLower.contains("tokyo") || cityLower.contains("japan")) {
            return "Asia/Tokyo";
        }
        if (cityLower.contains("beijing") || cityLower.contains("shanghai") ||
            cityLower.contains("china")) {
            return "Asia/Shanghai";
        }
        if (cityLower.contains("singapore")) {
            return "Asia/Singapore";
        }
        if (cityLower.contains("dubai")) {
            return "Asia/Dubai";
        }

        // Australian cities
        if (cityLower.contains("sydney") || cityLower.contains("melbourne")) {
            return "Australia/Sydney";
        }

        // Default to UTC if city not recognized
        return "UTC";
    }
}

