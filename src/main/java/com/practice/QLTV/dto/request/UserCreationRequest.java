package com.practice.QLTV.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain alphanumeric characters.")
    String username;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long.")
    String password;

    @NotBlank(message = "Full name cannot be blank.")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters.")
    String fullName;

    @NotBlank(message = "Phone number cannot be blank.")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be numeric and between 10 and 15 digits.")
    String phoneNumber;

    @NotBlank(message = "Identity number cannot be blank.")
    @Pattern(regexp = "^[0-9]{9,12}$", message = "Identity number must be numeric and between 9 and 12 digits.")
    String identityNumber;

    @NotNull(message = "Date of birth cannot be null.")
    @Past
    Date dob;

    @NotBlank(message = "Address cannot be blank.")
    String address;

    @NotBlank(message = "Email cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format.")
    String email;

}
