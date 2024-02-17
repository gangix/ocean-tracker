package com.cleanhub.cleanhubtracker.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cleanhub.cleanhubtracker.entity.OrderDetail;

@FeignClient(name = "orders", url = "${cleanhub.rest.base.url}")
public interface OrderInformationClient {

	@GetMapping(path = "/orders", produces = "application/json", consumes = "application/json")
	OrderDetail getOrderDetail(@RequestParam("route") String route);

}