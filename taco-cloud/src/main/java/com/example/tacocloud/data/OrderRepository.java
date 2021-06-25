package com.example.tacocloud.data;
import com.example.tacocloud.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByDeliveryZip(String deliveryZip);// 动词find+（可选主题） + By + 断言
}
