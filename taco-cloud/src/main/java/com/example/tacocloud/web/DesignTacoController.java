package com.example.tacocloud.web;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import com.example.tacocloud.Ingredient;
import com.example.tacocloud.Ingredient.Type;
import com.example.tacocloud.Taco;
import com.example.tacocloud.Order;
import com.example.tacocloud.data.IngredientRepository;
import com.example.tacocloud.data.TacoRepository;

// simple logging facade for java
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")// 跨请求使用该属性
public class DesignTacoController {

  private final IngredientRepository ingredientRepo;
  private TacoRepository designRepo;
  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo){
    this.ingredientRepo = ingredientRepo;
    this.designRepo = designRepo;
  }
//end::head[]
  @ModelAttribute(name="order")
  public Order order() {
    return new Order();
  }
  @ModelAttribute(name="taco")
  public Taco taco() {
    return new Taco();
  }
//@ModelAttribute
public void addIngredientsToModel(Model model) {
	List<Ingredient> ingredients = Arrays.asList(
	  new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
	  new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
	  new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
	  new Ingredient("CARN", "Carnitas", Type.PROTEIN),
	  new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
	  new Ingredient("LETC", "Lettuce", Type.VEGGIES),
	  new Ingredient("CHED", "Cheddar", Type.CHEESE),
	  new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
	  new Ingredient("SLSA", "Salsa", Type.SAUCE),
	  new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
	);
	
	Type[] types = Ingredient.Type.values();
	for (Type type : types) {
	  model.addAttribute(type.toString().toLowerCase(),
	      filterByType(ingredients, type));
	}
}

//tag::showDesignForm[]
  @GetMapping
  public String showDesignForm(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepo.findAll().forEach(i->ingredients.add(i));
    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }
    // model.addAttribute("design", new Taco());
    return "design";
  }

//end::showDesignForm[]

/*
//tag::processDesign[]
  @PostMapping
  public String processDesign(Design design) {
    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesign[]
 */

//tag::processDesignValidated[]
  @PostMapping
  public String processDesign(//@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
    @Valid Taco design, Errors errors, 
      @ModelAttribute Order order) {// ModelAttribute 表明值是来自模型
    if (errors.hasErrors()) {
      log.info(errors.toString());
      return "design";
    }
    log.info("ingredient");
    log.info(design.getIngredients().toString());
    // Save the taco design...
    // We'll do this in chapter 3
    Taco saved = designRepo.save(design);
    order.addDesign(saved);
    // log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesignValidated[]

//tag::filterByType[]
  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

//end::filterByType[]
// tag::foot[]
}
// end::foot[]
