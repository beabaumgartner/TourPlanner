package at.fhtw.tourplanner;

import at.fhtw.tourplanner.utils.ApplicationFileParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TourPlannerApplication {
	public static final String API_KEY = ApplicationFileParser.parseApiKey();
	public static void main(String[] args) {
		SpringApplication.run(TourPlannerApplication.class, args);
	}
}
