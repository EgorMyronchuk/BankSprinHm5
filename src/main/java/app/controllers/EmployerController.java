package app.controllers;

import app.dto.EmployerRequest;
import app.dto.EmployerResponse;
import app.facade.EmployerFacade;
import app.model.Customer;
import app.model.Employer;
import app.service.CustomerService;
import app.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("employer")
@Slf4j
public class EmployerController {

    private final EmployerService employerService;
    private final EmployerFacade employerFacade;

    @GetMapping("{id}")
    public EmployerResponse getEmployerById(@PathVariable Long id) {
        return employerFacade.convertToResponse(employerService.getEmployerById(id));
    }

    @GetMapping
    public List<EmployerResponse> getAllEmployers() {
        log.info("Received request to get all employers");
        List<Employer> employers = employerService.getAllEmployers();
        return employers.stream()
                .map(employerFacade::convertToResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void saveEmployer(@Valid @RequestBody EmployerRequest employer) {
        log.info("Received request to save employer");
        employerService.saveEmployer(employerFacade.convertToEntity(employer));
    }
}
