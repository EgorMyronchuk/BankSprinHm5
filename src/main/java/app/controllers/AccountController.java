package app.controllers;

import app.service.AccountService;
import lombok.RequiredArgsConstructor;
import app.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("account")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final SimpMessagingTemplate messagingTemplate; // Для отправки сообщений через WebSocket

    @PostMapping("deposit")
    public ResponseEntity<?> deposit (@RequestParam String cardNumber, @RequestParam Double amount) {
        log.info("deposit card request ");
        Optional<Account> account = accountService.deposit(cardNumber, amount);
        if (account.isPresent()) {
            log.info("deposit account successful");
            messagingTemplate.convertAndSend("/topic/account-changes", "Deposit successful: " + account.get().getBalance());
            return ResponseEntity.status(HttpStatus.OK).body(account.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with this number not found");
        }
    }

    @PostMapping("withdraw")
    public ResponseEntity<?> withdraw (@RequestParam String cardNumber, @RequestParam Double amount) {
        log.info("withdraw card request ");
        Optional<Account> account = accountService.withdraw(cardNumber, amount);
        if (account.isPresent()) {
            log.info("withdraw account successful");
            messagingTemplate.convertAndSend("/topic/account-changes", "Withdraw successful: " + account.get().getBalance());
            return ResponseEntity.status(HttpStatus.OK).body(account.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with this number not found");
        }
    }

    @PostMapping("transfer")
    public ResponseEntity<?> transfer (@RequestParam String cardNumberFrom, @RequestParam String cardNumberTo, @RequestParam Double amount) {
        log.info("transfer card request ");
        Optional<List<Account>> account = accountService.transfer(cardNumberFrom, cardNumberTo, amount);
        if (account.isPresent()) {
            log.info("transfer account successful");
            messagingTemplate.convertAndSend("/topic/account-changes", "Transfer successful");
            return ResponseEntity.status(HttpStatus.OK).body(account.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card with this number not found or Not enough money to transfer");
        }
    }
}
