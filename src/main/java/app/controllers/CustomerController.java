package app.controllers;

import app.dto.AccountRequest;
import app.dto.CustomerRequest;
import app.dto.CustomerResponse;
import app.facade.AccountFacade;
import app.facade.CustomerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import app.model.Account;
import app.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import app.service.CustomerService;
import app.utils.CustomCurrency;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerFacade customerFacade;
    private final AccountFacade accountFacade;

    @GetMapping("{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        log.info("Received request to get customer with ID: {}", id);
        CustomerResponse customerResponse = customerFacade.convertToResponse(customerService.getCustomerById(id));
        log.info("Successfully retrieved customer with ID: {}", id);
        return customerResponse;

    }

    @GetMapping
    public Page<CustomerResponse> getAllCustomers(
            @RequestParam(defaultValue = "0" ) int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Received request to get all customer");
        Page<Customer> pagedCustomers = customerService.getAllCustomers(page , size);
        log.info("Successfully retrieved all customer");
        return pagedCustomers.map(customerFacade::convertToResponse);
    }

    @PostMapping
    public void saveCustomer(@Valid @RequestBody CustomerRequest customer) {
        log.info("Received request to save customer");
        customerService.saveCustomer(customerFacade.convertToEntity(customer));
    }

    @PutMapping("{id}")
    public void changeCustomer(@PathVariable Long id, @RequestBody CustomerRequest customer) {
        log.info("Received request to change customer");
        customerService.changeCustomer(id, customerFacade.convertToEntity(customer));
        log.info("Successfully changed the customer with ID: {}", id);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable Long id) {
        log.info("Received request to delete customer");
        customerService.deleteCustomer(id);
        log.info("Successfully deleted customer with ID: {}", id);
    }

    @PostMapping("{id}/accounts")
    public void createAccountForCustomer(@PathVariable Long id, @RequestParam CustomCurrency currency) {
        log.info("Received request to create account for customer");
        customerService.createAccountForCustomer(id, currency);
        log.info("Successfully created account for customer with ID: {}", id);
    }

    @DeleteMapping("{id}/accounts")
    public void deleteAccountForCustomer(@PathVariable Long id, @RequestBody AccountRequest account) {
        log.info("Received request to delete account for customer");
        customerService.deleteAccountForCustomer(id,accountFacade.convertToEntity(account));
        log.info("Successfully deleted account for customer with ID: {}", id);
    }
}
