package app.utils;

import app.model.Account;
import app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardNumberUtils {

    private final AccountRepository accountRepository;

    @Autowired
    public CardNumberUtils(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private static final String PREFIX = "4141"; // Префикс номера карты

    public  String generateCardNumber() {
        // Найти последнюю запись
        Account mostRecentAccount = accountRepository.findMostRecentAccount();
        String newCardNumber;

        if (mostRecentAccount != null) {
            // Получить последний номер карты и увеличить его
            String lastCardNumber = mostRecentAccount.getNumber();
            String numberPart = lastCardNumber.substring(PREFIX.length()); // Удалить префикс
            int newNumber = Integer.parseInt(numberPart) + 1; // Увеличить на 1
            newCardNumber = PREFIX + String.format("%010d", newNumber); // Форматировать с префиксом
        } else {
            // Если нет записей, создать начальный номер
            newCardNumber = PREFIX + String.format("%010d", 1); // Начальный номер
        }

        return newCardNumber;
    }
}
