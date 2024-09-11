package app.facade;

import app.dto.EmployerRequest;
import app.dto.EmployerResponse;
import app.model.Employer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployerFacade {

    private final ModelMapper modelMapper = new ModelMapper();

    public Employer convertToEntity(EmployerRequest employerRequest) {
        return modelMapper.map(employerRequest, Employer.class);
    }

    public EmployerResponse convertToResponse(Employer employer) {
        return modelMapper.map(employer, EmployerResponse.class);
    }
}
