package com.minibanking.customer.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
	
@NotBlank(message = "Name cannot be empty")
@Pattern(flags = Flag.CASE_INSENSITIVE, regexp = "^[a-z ]*$", message = "Name should contain only alphabets") 
private String name;

@NotBlank(message = "DOB cannot be empty")
private String dob;

@NotBlank(message = "Address cannot be empty")
private String address;

@Pattern(regexp = "^[0-9]*$", message = "Phone number should contain only digits")
@Size(max=10, min=10, message = "Phone number should be 10 digits")
private String mobile;

@Email(message = "Invalid email")
private String email;

@NotBlank(message = "KycType cannot be empty")
private String kycType;

@NotBlank(message = "KycNo cannot be empty")
private String kycNo;

}
