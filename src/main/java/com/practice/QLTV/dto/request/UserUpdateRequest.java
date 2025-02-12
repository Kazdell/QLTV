package com.practice.QLTV.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain alphanumeric characters.")
    String username;

    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long.")
    String password;

    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters.")
    String fullName;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be numeric and between 10 and 15 digits.")
    String phoneNumber;

    @Pattern(regexp = "^[0-9]{9,12}$", message = "Identity number must be numeric and between 9 and 12 digits.")
    String identityNumber;

    @Past(message = "Date of birth must be in the past.")
    Date dob;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format.")
    String email;

    String address;

    Integer roleGroupId;
}
