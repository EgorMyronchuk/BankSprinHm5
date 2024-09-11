package app.service;

import app.model.Account;
import app.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountService accountService;

    @Test
    void depositTest() {
        String accountNumber = "123456";
        Double initialBalance = 1000.0;
        Double depositAmount = 500.0;

        Account account = new Account();
        account.setNumber(accountNumber);
        account.setBalance(initialBalance);

        when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Optional<Account> updatedAccount = accountService.deposit(accountNumber, depositAmount);

        assertTrue(updatedAccount.isPresent());
        assertEquals(initialBalance + depositAmount, updatedAccount.get().getBalance());

        // Проверяем, что методы были вызваны
        verify(accountRepository, times(1)).findByNumber(accountNumber);
        verify(accountRepository, times(1)).save(account);
    }
    @Test
    void withdrawTest() {
        String accountNumber = "123456";
        Double initialBalance = 1000.0;
        Double withdrawAmount = 500.0;

        Account account = new Account();
        account.setNumber(accountNumber);
        account.setBalance(initialBalance);

        when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Optional<Account> updatedAccount = accountService.withdraw(accountNumber, withdrawAmount);
        assertTrue(updatedAccount.isPresent());
        assertEquals(initialBalance - withdrawAmount, updatedAccount.get().getBalance());

        verify(accountRepository, times(1)).findByNumber(accountNumber);
        verify(accountRepository, times(1)).save(account);

    }
    @Test
    void transferTest() {
        String fromAccountNumber = "123456";
        Double fromAccountBalance = 1000.0;
        String toAccountNumber = "654321";
        Double toAmount = 500.0;
        Double transferAmount = 500.0;

        Account accountFrom = new Account();
        accountFrom.setNumber(fromAccountNumber);
        accountFrom.setBalance(fromAccountBalance);

        Account accountTo = new Account();
        accountTo.setNumber(toAccountNumber);
        accountTo.setBalance(toAmount);

        when(accountRepository.findByNumber("123456")).thenReturn(Optional.of(accountFrom));
        when(accountRepository.findByNumber("654321")).thenReturn(Optional.of(accountTo));

        when(accountRepository.save(accountFrom)).thenReturn(accountFrom);
        when(accountRepository.save(accountTo)).thenReturn(accountTo);

        Optional<List<Account>> accounts = accountService.transfer(fromAccountNumber,toAccountNumber,transferAmount);

        assertTrue(accounts.isPresent());
        assertEquals(toAmount + transferAmount, accounts.get().get(1).getBalance());
        assertEquals(fromAccountBalance - transferAmount, accounts.get().get(0).getBalance());

        verify(accountRepository, times(1)).save(accountFrom);
        verify(accountRepository, times(1)).save(accountTo);
    }



}
