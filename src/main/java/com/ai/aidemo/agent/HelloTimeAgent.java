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

    // Timezone constants
    private static final String TZ_ASIA_KOLKATA = "Asia/Kolkata";
    private static final String TZ_AMERICA_NEW_YORK = "America/New_York";
    private static final String TZ_AMERICA_LOS_ANGELES = "America/Los_Angeles";
    private static final String TZ_AMERICA_CHICAGO = "America/Chicago";
    private static final String TZ_EUROPE_LONDON = "Europe/London";
    private static final String TZ_EUROPE_PARIS = "Europe/Paris";
    private static final String TZ_EUROPE_BERLIN = "Europe/Berlin";
    private static final String TZ_ASIA_TOKYO = "Asia/Tokyo";
    private static final String TZ_ASIA_SHANGHAI = "Asia/Shanghai";
    private static final String TZ_ASIA_SINGAPORE = "Asia/Singapore";
    private static final String TZ_ASIA_DUBAI = "Asia/Dubai";
    private static final String TZ_AUSTRALIA_SYDNEY = "Australia/Sydney";

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

        // Define city to timezone mappings
        Map<String, String> cityTimeZoneMap = getCityTimeZoneMap();

        // Find matching timezone
        for (Map.Entry<String, String> entry : cityTimeZoneMap.entrySet()) {
            if (cityLower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // Default to UTC if city not recognized
        return "UTC";
    }

    /**
     * Returns a map of city keywords to their time zones.
     * The order matters - more specific cities should come before general regions.
     */
    private static Map<String, String> getCityTimeZoneMap() {
        Map<String, String> map = new HashMap<>();

        // Indian cities
        map.put("delhi", TZ_ASIA_KOLKATA);
        map.put("mumbai", TZ_ASIA_KOLKATA);
        map.put("bangalore", TZ_ASIA_KOLKATA);
        map.put("chennai", TZ_ASIA_KOLKATA);
        map.put("kolkata", TZ_ASIA_KOLKATA);
        map.put("india", TZ_ASIA_KOLKATA);

        // US cities - East Coast
        map.put("new york", TZ_AMERICA_NEW_YORK);
        map.put("boston", TZ_AMERICA_NEW_YORK);
        map.put("washington", TZ_AMERICA_NEW_YORK);

        // US cities - West Coast
        map.put("los angeles", TZ_AMERICA_LOS_ANGELES);
        map.put("san francisco", TZ_AMERICA_LOS_ANGELES);
        map.put("seattle", TZ_AMERICA_LOS_ANGELES);

        // US cities - Central
        map.put("chicago", TZ_AMERICA_CHICAGO);
        map.put("dallas", TZ_AMERICA_CHICAGO);

        // European cities
        map.put("london", TZ_EUROPE_LONDON);
        map.put("uk", TZ_EUROPE_LONDON);
        map.put("paris", TZ_EUROPE_PARIS);
        map.put("france", TZ_EUROPE_PARIS);
        map.put("berlin", TZ_EUROPE_BERLIN);
        map.put("germany", TZ_EUROPE_BERLIN);

        // Asian cities
        map.put("tokyo", TZ_ASIA_TOKYO);
        map.put("japan", TZ_ASIA_TOKYO);
        map.put("beijing", TZ_ASIA_SHANGHAI);
        map.put("shanghai", TZ_ASIA_SHANGHAI);
        map.put("china", TZ_ASIA_SHANGHAI);
        map.put("singapore", TZ_ASIA_SINGAPORE);
        map.put("dubai", TZ_ASIA_DUBAI);

        // Australian cities
        map.put("sydney", TZ_AUSTRALIA_SYDNEY);
        map.put("melbourne", TZ_AUSTRALIA_SYDNEY);

        return map;
    }
}

