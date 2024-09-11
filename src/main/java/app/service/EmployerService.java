package app.service;

import app.customException.EntityNotFoundException;
import app.model.Customer;
import app.model.Employer;
import app.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployerService {
    private final EmployerRepository employerRepository;

    public Employer getEmployerById(Long id) {
        Optional<Employer> employerOpt = employerRepository.findById(id);
        if (employerOpt.isEmpty()) {
            throw new EntityNotFoundException("No Employer by id : " + id);
        }
        return employerOpt.get();
    }

    public List<Employer> getAllEmployers() {
        List<Employer> mylist = employerRepository.findAll();;
        if (mylist.isEmpty()) {
            throw new EntityNotFoundException("No Employer found");
        }
        System.out.println(employerRepository.findAll());
        return mylist;
    }

    public void saveEmployer(Employer employer) {
        employerRepository.save(employer);
    }
}
