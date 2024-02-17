package com.cleanhub.cleanhubtracker.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cleanhub.cleanhubtracker.dto.CustomerDetail;

@FeignClient(name = "customers", url = "${cleanhub.rest.base.url}")
public interface CustomerInformationClient {

	@GetMapping(path= "orders/logos", produces = "application/json", consumes = "application/json")
	List<CustomerDetail> getCustomers();

}