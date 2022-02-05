package org.by.bharadwaj.fundoo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

	@ApiModelProperty(example = "Bharadwaj",required = true)
	@NotBlank(message = "Firstname cannot be blank")
	private String firstName;

	@ApiModelProperty(example = "P N",required = true)
	@NotBlank(message = "Lastname cannot be blank")
	private String lastName;

	@ApiModelProperty(example = "9164877969",required = true)
	@NotBlank(message = "phone number cannot be blank")
	@Length(min = 10,max = 10,message = "Phone number should be 10 digits")
	private String phoneNumber;

	@ApiModelProperty(example = "pnbharadwaj99@gmail.com",required = true)
	@Email(message = "Email is not proper")
	private String email;

	@ApiModelProperty(example = "test@123",required = true)
	@NotBlank(message = "password cannot be blank")
	private String password;
	
}
