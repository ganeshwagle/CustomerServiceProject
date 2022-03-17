package com.customer_service.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDto {
    private String first_name;
    private String last_name;
    @Pattern(regexp = "[1-9][0-9]{9}", message = "Invalid phone number")
    private String phone_number;
}
