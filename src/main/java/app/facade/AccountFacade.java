package app.facade;

import app.dto.AccountRequest;
import app.dto.AccountResponse;
import app.dto.EmployerRequest;
import app.dto.EmployerResponse;
import app.model.Account;
import app.model.Employer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountFacade {

    private final ModelMapper modelMapper = new ModelMapper();

    public Account convertToEntity(AccountRequest employerRequest) {
        return modelMapper.map(employerRequest, Account.class);
    }
    public AccountResponse convertToResponse(Account employer) {
        return modelMapper.map(employer, AccountResponse.class);
    }


}
