package com.cleanhub.cleanhubtracker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cleanhub.cleanhubtracker.client.CustomerInformationClient;
import com.cleanhub.cleanhubtracker.client.OrderInformationClient;
import com.cleanhub.cleanhubtracker.dto.TopCustomerDto;
import com.cleanhub.cleanhubtracker.repository.OrderDetailRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderDetailRepository orderDetailRepository;

	public OrderServiceImpl(OrderDetailRepository orderDetailRepository,
			CustomerInformationClient customerInformationClient, OrderInformationClient orderInformationClient) {
		this.orderDetailRepository = orderDetailRepository;
	}

	@Override
	public List<TopCustomerDto> getTop10Companies(LocalDateTime staDateTime, LocalDateTime endDateTime) {
		var top10Companies = orderDetailRepository.findByTimestampBetweenGroupByCompanyName(staDateTime, endDateTime,
				PageRequest.of(0, 10));
		
		return top10Companies;
	}
}
