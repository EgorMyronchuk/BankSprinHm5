    package app.dto;

    import app.model.Account;
    import app.model.Employer;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.ArrayList;
    import java.util.List;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CustomerRequest {
        @NotNull
        @Size(min = 2, message = "user name should have at least 2 characters")
        private String name;

        @NotBlank(message = "Email is required")
        @Pattern(
                regexp = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
                message = "Invalid email format"
        )
        private String email;

        @NotNull
        @Min(18)
        private Integer age;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        private String password;

        @Pattern(regexp = "(\\+38|0)[0-9]{9}",message = "Invalid phone format")
        @NotBlank
        private String phone;

    }
