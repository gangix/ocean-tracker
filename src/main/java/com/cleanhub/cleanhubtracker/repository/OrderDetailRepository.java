package com.cleanhub.cleanhubtracker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cleanhub.cleanhubtracker.dto.TopCustomerDto;
import com.cleanhub.cleanhubtracker.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	
	@Query("""
		SELECT new com.cleanhub.cleanhubtracker.dto.TopCustomerDto(order.companyName, MAX(quantity))
		FROM OrderDetail order
		WHERE order.timestamp >= :startTime
		AND order.timestamp <= :endTime
		GROUP BY companyName
		ORDER BY MAX(quantity) desc
	""")
	List<TopCustomerDto> findByTimestampBetweenGroupByCompanyName(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}
