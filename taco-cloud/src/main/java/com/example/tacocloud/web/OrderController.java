package com.example.tacocloud.web;
import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
//end::baseClass[]
import org.springframework.web.bind.annotation.PostMapping;
//tag::baseClass[]
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.extern.slf4j.Slf4j;
import com.example.tacocloud.Order;
import com.example.tacocloud.User;
import com.example.tacocloud.data.OrderRepository;

//@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
  private OrderRepository orderRepo;

  private OrderProps props;
  public OrderController(OrderRepository orderRepo, OrderProps props) {
    this.orderRepo = orderRepo;
    this.props = props;
  }
//end::baseClass[]
//tag::orderForm[]
/*
  @GetMapping("/current")
  public String orderForm(Model model) {
    //model.addAttribute("order", new Order());
    return "orderForm";
  }
  */
  @GetMapping("/current")
  public String orderForm(@AuthenticationPrincipal User user, 
      @ModelAttribute Order order) {
    if (order.getDeliveryName() == null) {
      order.setDeliveryName(user.getFullname());
    }
    if (order.getDeliveryStreet() == null) {
      order.setDeliveryStreet(user.getStreet());
    }
    if (order.getDeliveryCity() == null) {
      order.setDeliveryCity(user.getCity());
    }
    if (order.getDeliveryState() == null) {
      order.setDeliveryState(user.getState());
    }
    if (order.getDeliveryZip() == null) {
      order.setDeliveryZip(user.getZip());
    }
    
    return "orderForm";
  }
//end::orderForm[]

/*
//tag::handlePost[]
  @PostMapping
  public String processOrder(Order order) {
    log.info("Order submitted: " + order);
    return "redirect:/";
  }
//end::handlePost[]
*/
  
//tag::handlePostWithValidation[]
  /*@PostMapping
  public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {// @Valid validation
    if (errors.hasErrors()) {
      return "orderForm";
    }
    orderRepo.save(order);
    sessionStatus.setComplete();// 重置session，清理掉旧订单

    //log.info("Order submitted: " + order);
    return "redirect:/";
  }*/
  @PostMapping
  public String processOrder(@Valid Order order, Errors errors, 
      SessionStatus sessionStatus, 
      @AuthenticationPrincipal User user) {// 接受 Authentication对象
    
    if (errors.hasErrors()) {
      return "orderForm";
    }
    
    order.setUser(user);
    
    orderRepo.save(order);
    sessionStatus.setComplete();
    
    return "redirect:/";
  }
//end::handlePostWithValidation[]
  @GetMapping
  public String ordersForUser(
      @AuthenticationPrincipal User user, Model model) {

    Pageable pageable = PageRequest.of(0, props.getPageSize());
    model.addAttribute("orders",
        orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

    return "orderList";
  }
//tag::baseClass[]
  
}
//end::baseClass[]
