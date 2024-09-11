package app.service;

import app.customException.EntityNotFoundException;
import app.model.Account;
import app.model.Customer;
import app.repository.AccountRepository;
import app.repository.CustomerRepository;
import app.utils.CardNumberUtils;
import app.utils.CustomCurrency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    private static final long ID = 1L;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CardNumberUtils cardNumberUtils;

    @InjectMocks
    private CustomerService customerService;


    @Test
    void getCustomerByIdTest( ) {
        Customer customer = mock(Customer.class);
        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        Customer resultCustomer = customerService.getCustomerById(ID);
        assertNotNull(resultCustomer);
        assertEquals(resultCustomer,customer);

        verify(customerRepository, times(1)).findById(ID);
    }
    @Test
    void getAllCustomersTest() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Customer> customerPage = mock(Page.class); // Создание mock-объекта для страницы с клиентами

        when(customerRepository.findAll(pageRequest)).thenReturn(customerPage); // Мокируем вызов findAll

        Page<Customer> result = customerService.getAllCustomers(page, size);

        assertNotNull(result);
        assertEquals(customerPage, result);
        verify(customerRepository, times(1)).findAll(pageRequest);
    }
    @Test
    void saveCustomerTest() {
        Customer customer = mock(Customer.class);
        when(customerRepository.save(customer)).thenReturn(customer);
        customerService.saveCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }
    @Test
    void changeCustomerTest() {
        Customer customer = mock(Customer.class);

        when(customerRepository.existsById(ID)).thenReturn(true);
        when(customerRepository.save(customer)).thenReturn(customer);

        customerService.changeCustomer(ID,customer);

        verify(customerRepository, times(1)).existsById(ID);
        verify(customerRepository, times(1)).save(customer);
    }
    @Test
    void deleteCustomerTest() {
        when(customerRepository.existsById(ID)).thenReturn(true);

        doNothing().when(customerRepository).deleteById(ID);

        customerService.deleteCustomer(ID);

        verify(customerRepository, times(1)).existsById(ID);
        verify(customerRepository, times(1)).deleteById(ID);
    }

    @Test
    void createAccountForCustomerTest() {

        Customer customer = mock(Customer.class);
        Account account = new Account();
        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Действие (Act)
        customerService.createAccountForCustomer(ID, CustomCurrency.USD);

        // Проверка (Assert)
        verify(customerRepository, times(1)).findById(ID);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void deleteAccountForCustomerTest() {
        Account account = new Account();
        account.setNumber("3213");
        account.setCurrency(CustomCurrency.USD);
        account.setId(ID);

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Egor");
        customer1.setAge(25);
        customer1.setEmail("egor@gmail.com");
        customer1.setPassword("egor");
        customer1.setPhone("+38054321207");
        customer1.setAccounts(new ArrayList<>(List.of(account)));

        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        // Выполнение метода удаления аккаунта
        customerService.deleteAccountForCustomer(ID, account);

        // Проверка, что метод findById вызвался один раз
        verify(customerRepository, times(1)).findById(ID);

        // Проверка, что аккаунт был удален из списка
        assertTrue(customer1.getAccounts().isEmpty(), "Account list should be empty after deletion");

        // Проверка, что метод save вызвался один раз с обновленным клиентом
        verify(customerRepository, times(1)).save(customer1);
    }

}
