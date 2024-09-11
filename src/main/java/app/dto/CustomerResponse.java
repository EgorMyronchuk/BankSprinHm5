package app.dto;

import app.model.Account;
import app.model.Employer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;

    private String name;

    private String email;

    private Integer age;

    private String phone;

    private List<Account> accounts = new ArrayList<>();

    private List<Employer> employers = new ArrayList<>();

}
