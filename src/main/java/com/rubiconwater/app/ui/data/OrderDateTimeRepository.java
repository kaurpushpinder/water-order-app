package com.rubiconwater.app.ui.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author pushpinder
 *
 */
public interface OrderDateTimeRepository extends JpaRepository<OrderDateTime, Long> {
	
	@Modifying
	@Transactional
	@Query("delete from OrderDateTime odt where odt.orderId = :orderId")
	void removeEntity(String orderId);

	@Query("SELECT odt FROM OrderDateTime odt where odt.startDate < :maxDateToCheck and odt.startDate >= :startDate and "
			+ "((odt.startDateTime <= :startDateTime  and odt.endDateTime >= :startDateTime ) or "
			+ "(odt.startDateTime <= :endDateTime  and odt.endDateTime >= :endDateTime ) )")
	List<OrderDateTime> findOverlappingSlots(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime startDate, LocalDateTime maxDateToCheck);
	
	
}
