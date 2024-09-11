package app.dto;

import app.model.Customer;
import app.utils.CustomCurrency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;

    private String number;

    private CustomCurrency currency;

    private Double balance;

    private Customer customer;
}
