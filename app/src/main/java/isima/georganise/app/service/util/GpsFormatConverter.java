package isima.georganise.app.service.util;

import org.springframework.stereotype.Service;

@Service
public class GpsFormatConverter {

    public static String convertToGpx(double latitude, double longitude) {
        return String.format("<gpx><wpt lat=\"%f\" lon=\"%f\"></wpt></gpx>", latitude, longitude);
    }

    public static String convertToGpx(double latitude, double longitude, String name) {
        return String.format("<gpx><wpt lat=\"%f\" lon=\"%f\"><name>%s</name></wpt></gpx>", latitude, longitude, name);
    }

    public static String convertToGpx(double latitude, double longitude, String name, String description) {
        return String.format("<gpx><wpt lat=\"%f\" lon=\"%f\"><name>%s</name><desc>%s</desc></wpt></gpx>", latitude, longitude, name, description);
    }
}
