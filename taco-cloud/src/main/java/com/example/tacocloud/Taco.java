// tag::all[]
// tag::allButValidation[]
package com.example.tacocloud;
import java.util.List;
import java.util.Date;
// end::allButValidation[]
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
// tag::allButValidation[]
import lombok.Data;

@Data
public class Taco {
  private Long id;
  private Date createdAt;
  // end::allButValidation[]
  @NotNull
  @Size(min=5, message="Name must be at least 5 characters long")
  // tag::allButValidation[]
  private String name;
  // end::allButValidation[]
  @Size(min=1, message="You must choose at least 1 ingredient")
  // tag::allButValidation[]
  private List<Ingredient> ingredients;

}
//end::allButValidation[]
//tag::end[]
