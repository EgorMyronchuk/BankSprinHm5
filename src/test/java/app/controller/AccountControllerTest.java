package app.controller;

import app.controllers.AccountController;

import app.model.Account;
import app.model.Customer;
import app.service.AccountService;
import app.utils.CustomCurrency;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @MockBean
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deposit() throws Exception {
        // Создаем мок данных
        Customer customer = new Customer();
        Account account = new Account("123", CustomCurrency.USD, customer);
        account.setBalance(200.0);

        // Задаем поведение мока
        when(accountService.deposit("123", 100.0)).thenReturn(Optional.of(account));

        // Выполняем POST-запрос к контроллеру
        mockMvc.perform(post("/account/deposit")
                        .param("cardNumber", "123")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("123"))
                .andExpect(jsonPath("$.balance").value(200.0));

        // Проверяем, что метод deposit() был вызван 1 раз
        verify(accountService, times(1)).deposit("123", 100.0);
    }

    @Test
    void withdraw() throws Exception {
        // Создаем мок данных
        Customer customer = new Customer();
        Account account = new Account("123", CustomCurrency.USD, customer);
        account.setBalance(200.0);

        // Задаем поведение мока
        when(accountService.withdraw("123", 100.0)).thenReturn(Optional.of(account));

        // Выполняем POST-запрос к контроллеру
        mockMvc.perform(post("/account/withdraw")
                        .param("cardNumber", "123")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("123"))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.balance").value(200.0));

        // Проверяем, что метод deposit() был вызван 1 раз
        verify(accountService, times(1)).withdraw("123", 100.0);
    }

    @Test
    void transfer() throws Exception {

        Customer customer = new Customer();
        Account account = new Account("123", CustomCurrency.USD, customer);
        account.setBalance(200.0);
        Account account2 = new Account("234", CustomCurrency.EURO, customer);
        List<Account> accounts = new ArrayList<>();{{{
            accounts.add(account);
            accounts.add(account2);
        }}}

        when(accountService.transfer("123", "234", 100d)).thenReturn(Optional.of(accounts));

        mockMvc.perform(post("/account/transfer")
                        .param("cardNumberFrom", "123")
                        .param("cardNumberTo", "234")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("123"))
                .andExpect(jsonPath("$[0].currency").value("USD"))
                .andExpect(jsonPath("$[0].balance").value(200.0))
                .andExpect(jsonPath("$[1].number").value("234"))
                .andExpect(jsonPath("$[1].currency").value("EURO"))
                .andExpect(jsonPath("$[1].balance").value(0.0));


        verify(accountService, times(1)).transfer("123", "234", 100.0);
    }

}





