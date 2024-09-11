package app.dto;

import app.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployerResponse {

    private Long id;

    private String name;

    private String address;

    private List<Customer> customers;


}
