package com.cleanhub.cleanhubtracker.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleanhub.cleanhubtracker.dto.TopCustomerDto;
import com.cleanhub.cleanhubtracker.service.OrderService;

@RestController
@RequestMapping("/api/customers")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping(consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TopCustomerDto>> getTop10Customer(@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
		    @RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
		return ResponseEntity.ok(orderService.getTop10Companies(startTime, endTime));
	}

}