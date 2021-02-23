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
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
	@Query("select o from OrderEntity o where (o.farmerId = :farmerId or :farmerId is null) "
			+ "and ((o.startDateTime between :startDate and :endDate) or :startDate is null)")
    List<OrderEntity> findOrders(String farmerId, LocalDateTime startDate, LocalDateTime endDate);
	
	@Query("select coalesce(max(ord.id), 0) from OrderEntity ord")
	long getMaxRowId();

	@Modifying
	@Transactional
	@Query("update OrderEntity orders set orders.status = :status where orders.orderId = :orderId")
	void updateStatus(String orderId, OrderStatus status);

	@Query("select o from OrderEntity o where o.orderId = :orderId")
	OrderEntity findOrderById(String orderId);
}
