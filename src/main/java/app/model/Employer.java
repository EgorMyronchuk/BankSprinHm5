package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class Employer extends AbstractEntity {
    @Column
    private String name;
    @Column
    private String address;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude // it works only for System.out.println == toString
    @JsonIgnore
    @ManyToMany(mappedBy = "employers")
    private List<Customer> customers;
}
