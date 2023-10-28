package at.fhtw.tourplanner;

import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourListEntry;
import at.fhtw.tourplanner.models.TourLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TourPlannerApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	@Test
	@Order(1)
	void testSearchEmptyTourList() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/search/vienna"))
				.andReturn();

		assertEquals(204, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(2)
	void testGetEmptyTourList() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours"))
				.andReturn();

		assertEquals(204, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(3)
	void testGetTourByInvalidId() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/1"))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(4)
	void testCreateNewTour() throws Exception {
		Tour tour = new Tour();
		tour.setName("testCreateNewTourName");
		tour.setTourDescription("testCreateNewTourDescription");
		tour.setStart("Vienna");
		tour.setDestination("Bratislava");
		tour.setTransportType("car");

		MvcResult mvcResult = mockMvc.perform(post("/tours")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tour)))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		Tour actualTour = objectMapper.readValue(responseBody, Tour.class);

		assertEquals(201, mvcResult.getResponse().getStatus());
		assertEquals("testCreateNewTourName", actualTour.getName());
		assertEquals("testCreateNewTourDescription", actualTour.getTourDescription());
		assertEquals("Vienna", actualTour.getStart());
		assertEquals("Bratislava", actualTour.getDestination());
		assertEquals("car", actualTour.getTransportType());
	}
	@Test
	@Order(5)
	void testCreateInvalidTour() throws Exception {
		Tour tour = new Tour();
		tour.setName("testCreateInvalidTourName");
		tour.setTourDescription("testCreateInvalidTourDescription");
		tour.setStart("Vienna");
		tour.setDestination("Australia");
		tour.setTransportType("car");

		MvcResult mvcResult = mockMvc.perform(post("/tours")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tour)))
				.andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(6)
	void testGetTourList() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours"))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		List<TourListEntry> tourList = List.of(objectMapper.readValue(responseBody, TourListEntry[].class));

		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals(1, tourList.size());
		assertEquals("testCreateNewTourName", tourList.get(0).getName());
	}
	@Test
	@Order(7)
	void testGetTourById() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/1"))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		Tour actualTour = objectMapper.readValue(responseBody, Tour.class);

		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("testCreateNewTourName", actualTour.getName());
		assertEquals("testCreateNewTourDescription", actualTour.getTourDescription());
		assertEquals("Vienna", actualTour.getStart());
		assertEquals("Bratislava", actualTour.getDestination());
		assertEquals("car", actualTour.getTransportType());
	}
	@Test
	@Order(8)
	void testUpdateTourByInvalidId() throws Exception {
		Tour tour = new Tour();
		tour.setName("testUpdateTourByInvalidIdName");
		tour.setTourDescription("testUpdateTourByInvalidIdDescription");
		tour.setStart("Vienna");
		tour.setDestination("Bratislava");
		tour.setTransportType("car");

		MvcResult mvcResult = mockMvc.perform(put("/tours/5")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tour)))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(9)
	void testUpdateInvalidTourById() throws Exception {
		Tour tour = new Tour();
		tour.setName("testUpdateTourByInvalidIdName");
		tour.setTourDescription("testUpdateTourByInvalidIdDescription");
		tour.setStart("Vienna");
		tour.setDestination("Australia");
		tour.setTransportType("car");

		MvcResult mvcResult = mockMvc.perform(put("/tours/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tour)))
				.andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(10)
	void testUpdateTourById() throws Exception {
		Tour tour = new Tour();
		tour.setName("testUpdateTourByIdName");
		tour.setTourDescription("testUpdateTourByIdDescription");
		tour.setStart("Salzburg");
		tour.setDestination("Rome");
		tour.setTransportType("pedestrian");

		MvcResult mvcResult = mockMvc.perform(put("/tours/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tour)))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		Tour actualTour = objectMapper.readValue(responseBody, Tour.class);

		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("testUpdateTourByIdName", actualTour.getName());
		assertEquals("testUpdateTourByIdDescription", actualTour.getTourDescription());
		assertEquals("Salzburg", actualTour.getStart());
		assertEquals("Rome", actualTour.getDestination());
		assertEquals("pedestrian", actualTour.getTransportType());
	}
	@Test
	@Order(11)
	void testSearchTourListNonExistingKeyword() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/search/something"))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(12)
	void testSearchTourList() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/search/rome"))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		List<TourListEntry> tourList = List.of(objectMapper.readValue(responseBody, TourListEntry[].class));

		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals(1, tourList.size());
		assertEquals("testUpdateTourByIdName", tourList.get(0).getName());
	}
	@Test
	@Order(13)
	void testDeleteTourByInvalidId() throws Exception {
		MvcResult mvcResult = mockMvc.perform(delete("/tours/5"))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(14)
	void testDeleteTourById() throws Exception {
		MvcResult mvcResult = mockMvc.perform(delete("/tours/1"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(15)
	void testCreateNewTourAfterDeleting() throws Exception {
		Tour tour = new Tour();
		tour.setName("testCreateNewTourAfterDeletingName");
		tour.setTourDescription("testCreateNewTourAfterDeletingDescription");
		tour.setStart("Vienna");
		tour.setDestination("Bratislava");
		tour.setTransportType("car");

		MvcResult mvcResult = mockMvc.perform(post("/tours")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tour)))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		Tour actualTour = objectMapper.readValue(responseBody, Tour.class);

		assertEquals(201, mvcResult.getResponse().getStatus());
		assertEquals("testCreateNewTourAfterDeletingName", actualTour.getName());
		assertEquals("testCreateNewTourAfterDeletingDescription", actualTour.getTourDescription());
		assertEquals("Vienna", actualTour.getStart());
		assertEquals("Bratislava", actualTour.getDestination());
		assertEquals("car", actualTour.getTransportType());
	}
	@Test
	@Order(16)
	void testGetTourLogsByInvalidTourId() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/5/tourlogs"))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(17)
	void testGetTourLogsButNoRoutLogsExist() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/2/tourlogs"))
				.andReturn();

		assertEquals(204, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(18)
	void testCreateNewTourLogInvalidId() throws Exception {
		TourLog tourLog = new TourLog();
		tourLog.setComment("testCreateNewTourLogInvalidIdComment");
		tourLog.setDifficulty(3);
		tourLog.setTotalTime(5555);
		tourLog.setRating(5);

		MvcResult mvcResult = mockMvc.perform(post("/tours/5/tourlogs")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tourLog)))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(19)
	void testCreateNewTourLog() throws Exception {
		TourLog tourLog = new TourLog();
		tourLog.setComment("testCreateNewTourLogComment");
		tourLog.setDifficulty(3);
		tourLog.setTotalTime(5555);
		tourLog.setRating(5);

		MvcResult mvcResult = mockMvc.perform(post("/tours/2/tourlogs")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tourLog)))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		TourLog actualTourLog = objectMapper.readValue(responseBody, TourLog.class);

		assertEquals(201, mvcResult.getResponse().getStatus());
		assertEquals("testCreateNewTourLogComment", actualTourLog.getComment());
		assertEquals(3, actualTourLog.getDifficulty());
		assertEquals(5555, actualTourLog.getTotalTime());
		assertEquals(5, actualTourLog.getRating());
	}
	@Test
	@Order(20)
	void testGetTourLogs() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/tours/2/tourlogs"))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		List<TourLog> tourLogs = List.of(objectMapper.readValue(responseBody, TourLog[].class));

		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals(1, tourLogs.size());
		assertEquals("testCreateNewTourLogComment", tourLogs.get(0).getComment());
		assertEquals(3, tourLogs.get(0).getDifficulty());
		assertEquals(5555, tourLogs.get(0).getTotalTime());
		assertEquals(5, tourLogs.get(0).getRating());
	}
	@Test
	@Order(21)
	void testUpdateTourLogInvalidId() throws Exception {
		TourLog tourLog = new TourLog();
		tourLog.setComment("testUpdateTourLogComment");
		tourLog.setDifficulty(1);
		tourLog.setTotalTime(1111);
		tourLog.setRating(1);

		MvcResult mvcResult = mockMvc.perform(put("/tourlogs/5")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tourLog)))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(22)
	void testUpdateTourLog() throws Exception {
		TourLog tourLog = new TourLog();
		tourLog.setComment("testUpdateTourLogComment");
		tourLog.setDifficulty(1);
		tourLog.setTotalTime(1111);
		tourLog.setRating(1);

		MvcResult mvcResult = mockMvc.perform(put("/tourlogs/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tourLog)))
				.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		TourLog actualTourLog = objectMapper.readValue(responseBody, TourLog.class);

		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("testUpdateTourLogComment", actualTourLog.getComment());
		assertEquals(1, actualTourLog.getDifficulty());
		assertEquals(1111, actualTourLog.getTotalTime());
		assertEquals(1, actualTourLog.getRating());
	}
	@Test
	@Order(23)
	void testDeleteTourLogInvalidId() throws Exception {
		MvcResult mvcResult = mockMvc.perform(delete("/tourlogs/5"))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	@Test
	@Order(24)
	void testDeleteTourLog() throws Exception {
		MvcResult mvcResult = mockMvc.perform(delete("/tourlogs/1"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
}
