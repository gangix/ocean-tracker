package com.cleanhub.cleanhubtracker.service;

import java.time.LocalDateTime;
import java.util.List;

import com.cleanhub.cleanhubtracker.dto.TopCustomerDto;

public interface OrderService {
	List<TopCustomerDto> getTop10Companies(LocalDateTime staDateTime, LocalDateTime endDateTime);
}
