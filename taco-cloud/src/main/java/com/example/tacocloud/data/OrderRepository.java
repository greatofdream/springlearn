package com.example.tacocloud.data;
import com.example.tacocloud.Order;
import com.example.tacocloud.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByDeliveryZip(String deliveryZip);// 动词find+（可选主题） + By + 断言
    List<Order> findByUserOrderByPlacedAtDesc(
          User user, Pageable pageable);
}
