package app.controller;

import app.controllers.AccountController;
import app.controllers.CustomerController;
import app.dto.AccountRequest;
import app.dto.CustomerRequest;
import app.dto.CustomerResponse;
import app.facade.AccountFacade;
import app.facade.CustomerFacade;
import app.model.Account;
import app.model.Customer;
import app.service.AccountService;
import app.service.CustomerService;
import app.utils.CustomCurrency;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;
    @MockBean
    private AccountService accountService;
    @Autowired
    private AccountFacade accountFacade;
    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCustomerById() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Egor");
        customer1.setAge(25);
        customer1.setEmail("egor@gmail.com");
        customer1.setPassword("egor");
        customer1.setPhone("+38054321207");

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("Egor");
        customerResponse.setAge(25);
        customerResponse.setEmail("egor@gmail.com");
        customerResponse.setPhone("+38054321207");
        customerResponse.setEmployers(customer1.getEmployers());
        customerResponse.setAccounts(customer1.getAccounts());

        when(customerService.getCustomerById(1L)).thenReturn(customer1);

        mockMvc.perform(get("/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Egor"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.email").value("egor@gmail.com"))
                .andExpect(jsonPath("$.phone").value("+38054321207"));

        // Проверяем, что метод deposit() был вызван 1 раз
        verify(customerService, times(1)).getCustomerById(1L);

    }

    @Test
    void getAllCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Egor");
        customer1.setAge(25);
        customer1.setEmail("egor@gmail.com");
        customer1.setPassword("egor");
        customer1.setPhone("+38054321207");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Alex");
        customer2.setAge(30);
        customer2.setEmail("alex@gmail.com");
        customer2.setPassword("alex");
        customer2.setPhone("+38034321207");

        CustomerResponse customerResponse1 = new CustomerResponse();
        customerResponse1.setId(1L);
        customerResponse1.setName("Egor");
        customerResponse1.setAge(25);
        customerResponse1.setEmail("egor@gmail.com");
        customerResponse1.setPhone("+38054321207");

        CustomerResponse customerResponse2 = new CustomerResponse();
        customerResponse2.setId(2L);
        customerResponse2.setName("Alex");
        customerResponse2.setAge(30);
        customerResponse2.setEmail("alex@gmail.com");
        customerResponse2.setPhone("+38034321207");

        List<CustomerResponse> customerResponseList = new ArrayList<>();
        customerResponseList.add(customerResponse1);
        customerResponseList.add(customerResponse2);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        Pageable pageable = PageRequest.of(1, 2);
        Page<Customer> customerPage = new PageImpl<>(customerList, pageable, customerList.size());

        // Возвращаем Page<Customer> из сервиса
        when(customerService.getAllCustomers(1, 2)).thenReturn(customerPage);

        mockMvc.perform(get("/customers")
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Egor"))
                .andExpect(jsonPath("$.content[0].age").value(25))
                .andExpect(jsonPath("$.content[0].email").value("egor@gmail.com"))
                .andExpect(jsonPath("$.content[0].phone").value("+38054321207"))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].name").value("Alex"))
                .andExpect(jsonPath("$.content[1].age").value(30))
                .andExpect(jsonPath("$.content[1].email").value("alex@gmail.com"))
                .andExpect(jsonPath("$.content[1].phone").value("+38034321207"));

        verify(customerService, times(1)).getAllCustomers(1, 2);
    }


    @Test
    public void saveCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setName("Egor");
        request.setAge(25);
        request.setPassword("egor2121");
        request.setEmail("egor@gmail.com");
        request.setPhone("+38054321207");

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Egor");
        customer1.setAge(25);
        customer1.setEmail("egor@gmail.com");
        customer1.setPassword("egor2121");
        customer1.setPhone("+38054321207");

        String jasonRequest = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(post("/customers")
                        .content(jasonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService , times(1)).saveCustomer(customer1);

    }

    @Test
    void changeCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setName("Egor");
        request.setAge(25);
        request.setPassword("egor2121");
        request.setEmail("egor@gmail.com");
        request.setPhone("+38054321207");

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Egor");
        customer1.setAge(25);
        customer1.setEmail("egor@gmail.com");
        customer1.setPassword("egor2121");
        customer1.setPhone("+38054321207");

        mockMvc.perform(put("/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(customerService , times(1)).changeCustomer(1L,customer1);
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/{id}", 1L))
                .andExpect(status().isOk());

        verify(customerService , times(1)).deleteCustomer(1L);
    }

    @Test
    public void createAccountForCustomer() throws Exception {
        mockMvc.perform(post("/customers/{id}/accounts", 1L)
                        .param("currency","USD"))
                .andExpect(status().isOk());

        verify(customerService , times(1)).createAccountForCustomer(1L,CustomCurrency.USD);
    }


    @Test
    public void deleteAccountForCustomer() throws Exception {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCurrency(CustomCurrency.USD);
        accountRequest.setNumber("41410000000001");

        Account account = new Account();
        account.setId(1L);
        account.setCurrency(CustomCurrency.USD);
        account.setNumber(accountRequest.getNumber());

        mockMvc.perform(delete("/customers/{id}/accounts", 1L)
                        .content(objectMapper.writeValueAsString(accountRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService , times(1)).deleteAccountForCustomer(1L,account);
    }


}