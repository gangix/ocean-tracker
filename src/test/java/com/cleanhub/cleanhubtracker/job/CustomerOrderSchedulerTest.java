package com.cleanhub.cleanhubtracker.job;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleanhub.cleanhubtracker.client.CustomerInformationClient;
import com.cleanhub.cleanhubtracker.client.OrderInformationClient;
import com.cleanhub.cleanhubtracker.dto.CustomerDetail;
import com.cleanhub.cleanhubtracker.entity.OrderDetail;
import com.cleanhub.cleanhubtracker.repository.OrderDetailRepository;
import com.cleanhub.cleanhubtracker.service.OrderService;

@ExtendWith(MockitoExtension.class)
class CustomerOrderSchedulerTest {
	
	@InjectMocks
	private CustomerOrderScheduler customerOrderScheduler;
	
	@Mock
	private CustomerInformationClient customerInformationClient;
	
	@Mock
	private OrderInformationClient orderInformationClient;
	
	@Mock
	private OrderDetailRepository orderDetailRepository;

	@Test
	void publisItemsToMarkAsPastDueTest() {
		var custDetail1 = new CustomerDetail("company1", "route1");
		var custDetail2 = new CustomerDetail("company2", "route2");
		var orderDetail1 = new OrderDetail();
		orderDetail1.setCompanyName("company1");
		orderDetail1.setQuantity((double)2);
		var orderDetail2 = new OrderDetail();
		orderDetail2.setCompanyName("company2");
		orderDetail2.setQuantity((double)3);
		when(customerInformationClient.getCustomers()).thenReturn(List.of(custDetail1, custDetail2));
		when(orderInformationClient.getOrderDetail("route1")).thenReturn(orderDetail1);
		when(orderInformationClient.getOrderDetail("route2")).thenReturn(orderDetail2);
		customerOrderScheduler.emitAndPersistOrderDetail();

		verify(orderDetailRepository, times(2)).save(any());
	}
}