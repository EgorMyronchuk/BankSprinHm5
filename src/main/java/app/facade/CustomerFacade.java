package app.facade;

import app.dto.CustomerRequest;
import app.dto.CustomerResponse;
import app.dto.EmployerRequest;
import app.dto.EmployerResponse;
import app.model.Customer;
import app.model.Employer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerFacade {

    private final ModelMapper modelMapper = new ModelMapper();

    public Customer convertToEntity(CustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }

    public CustomerResponse convertToResponse(Customer customer) {
        return modelMapper.map(customer, CustomerResponse.class);
    }
}
