package com.customer_service.customer_service.dto;

import com.customer_service.customer_service.entity.Address;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @NotBlank(message = "First name field can't be empty")
    private String firstName;

    //@NotBlank(message = "Last name field can't be empty")
    private String lastName;

    @Email(message = "Invalid email id")
    @NotBlank(message = "Email field can't be empty")
    private String email;

    @Pattern(regexp="[1-9][0-9]{9}",message = "Invalid phone number")
    @NotBlank(message = "Phone number field can't be empty")
    private String phoneNumber;
}
