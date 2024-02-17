package com.cleanhub.cleanhubtracker.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import com.cleanhub.cleanhubtracker.entity.OrderDetail;
import com.cleanhub.cleanhubtracker.repository.OrderDetailRepository;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = "scheduler.enabled=false")
class OrderControllerTest {
	@LocalServerPort
	private Integer port;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		orderDetailRepository.deleteAll();
	}

	@Test
	void getTop10Customer_ShouldReturnTop10CustomerByQuantityValue() {
		createOrderDetails();
		Response response = given().queryParam("start_time", LocalDateTime.now().minusMinutes(10).format(DateTimeFormatter.ISO_DATE_TIME))
				.queryParam("end_time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).when()
				.get("/api/customers");
		
		
        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
        List<Map<String, Object>> listOfObjects = response.jsonPath().getList("");
        assertEquals(listOfObjects.size(), 10, "Expected 10 objects in the list");
        assertTrue(listOfObjects.stream().noneMatch(map -> map.get("companyName").equals("customer1")));
        assertTrue(listOfObjects.stream().noneMatch(map -> map.get("companyName").equals("customer2")));
        assertTrue(listOfObjects.stream().anyMatch(map -> map.get("companyName").equals("customer11")));
  	}

	private void createOrderDetails() {
		createOrderDetail("customer1", (double) 10);
		createOrderDetail("customer2", (double) 11);
		createOrderDetail("customer3", (double) 12);
		createOrderDetail("customer4", (double) 13);
		createOrderDetail("customer5", (double) 14);
		createOrderDetail("customer6", (double) 15);
		createOrderDetail("customer7", (double) 16);
		createOrderDetail("customer8", (double) 16);
		createOrderDetail("customer9", (double) 17);
		createOrderDetail("customer10", (double) 18);
		createOrderDetail("customer11", (double) 19);
		createOrderDetail("customer11", (double) 18);
		createOrderDetail("customer12", (double) 18);
	}

	private void createOrderDetail(String customerName, double quantity) {
		var orderDetail = new OrderDetail();
		orderDetail.setCompanyName(customerName);
		orderDetail.setQuantity(quantity);
		orderDetailRepository.save(orderDetail);
	}
}