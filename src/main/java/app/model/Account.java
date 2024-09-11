package app.model;

import app.utils.CardNumberUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;
import app.utils.CustomCurrency;
import java.util.UUID;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class Account extends AbstractEntity {
    @Column
    private String number;

    @Column
    @Enumerated(EnumType.STRING)
    private CustomCurrency currency;
    @Column
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    public Account(String number ,CustomCurrency currency, Customer customer) {
        this.number = number;
        this.currency = currency;
        this.customer = customer;
        this.balance = 0.0; // Начальный баланс, если требуется
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }
}
