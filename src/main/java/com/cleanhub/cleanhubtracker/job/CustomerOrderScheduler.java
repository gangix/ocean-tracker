package com.cleanhub.cleanhubtracker.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.cleanhub.cleanhubtracker.client.CustomerInformationClient;
import com.cleanhub.cleanhubtracker.client.OrderInformationClient;
import com.cleanhub.cleanhubtracker.repository.OrderDetailRepository;

import feign.FeignException.FeignClientException;
import jakarta.transaction.Transactional;

@Component
@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class CustomerOrderScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderScheduler.class);

	private final OrderDetailRepository orderDetailRepository;
	private final CustomerInformationClient customerInformationClient;
	private final OrderInformationClient orderInformationClient;

	public CustomerOrderScheduler(OrderDetailRepository orderDetailRepository,
			CustomerInformationClient customerInformationClient, OrderInformationClient orderInformationClient) {
		this.orderDetailRepository = orderDetailRepository;
		this.customerInformationClient = customerInformationClient;
		this.orderInformationClient = orderInformationClient;
	}

	@Scheduled(cron = "${cleanhub.api.feed.cron}")
	@Transactional
	public void emitAndPersistOrderDetail() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("emitAndPersistOrderDetail");
		try {
			customerInformationClient.getCustomers().forEach(customerDetail -> {
				var orderDetail = orderInformationClient.getOrderDetail(customerDetail.landingPageRoute());
				orderDetail.setCompanyName(customerDetail.companyName());
				orderDetailRepository.save(orderDetail);
			});
		} catch (FeignClientException feignClientException) {
			LOGGER.error("Feign client exception occured", feignClientException);
		}

		stopWatch.stop();
		LOGGER.info("Time taken to complete {} : {}", stopWatch.lastTaskInfo(), stopWatch.getTotalTimeSeconds());

	}
}
