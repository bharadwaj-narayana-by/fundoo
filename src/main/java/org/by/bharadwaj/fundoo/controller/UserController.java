package org.by.bharadwaj.fundoo.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.by.bharadwaj.fundoo.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.by.bharadwaj.fundoo.dto.UserDTO;
import org.by.bharadwaj.fundoo.entity.User;
import org.by.bharadwaj.fundoo.exception.FundooException;
import org.by.bharadwaj.fundoo.response.Response;
import org.by.bharadwaj.fundoo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(description = "user role controller")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	@ApiOperation("Api to register user")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO,BindingResult result){

		if(result.hasErrors()) {
			throw new FundooException(HttpStatus.UNPROCESSABLE_ENTITY.value(),result.getAllErrors().get(0).getDefaultMessage());
		}
		User user = userService.register(userDTO);
		return new ResponseEntity<Response>(new Response(HttpStatus.CREATED.value(), "User Registered Successfully", userDTO), HttpStatus.CREATED);
	}


	@PostMapping("login")
	@ApiOperation("Api to login user")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
		String token = userService.login(loginDto);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "user logged in",token), HttpStatus.OK);
	}

	@GetMapping("/verify-email/{token}")
	@ApiOperation("Api verify the user email")
	public ResponseEntity<Response> verifyEmail(@PathVariable String token) {
		User user = userService.verifyEmail(token);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "user logged in",token),
				HttpStatus.OK);
	}


	@GetMapping("")
	@ApiOperation("Api to get all users")
	public ResponseEntity<Response> getAllUser(){
		List<User> users =  userService.getAllUser();
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "User Retrived Successfully", users), HttpStatus.OK);
	}


	@GetMapping("/forgot-password")
	@ApiOperation("Api to send password reset link to mail")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email){
		userService.forgotPassword(email);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Reset link has sent to mail Successfully", ""), HttpStatus.OK);
	}

	@GetMapping("/reset-password")
	@ApiOperation("Api to rest password of a user")
	public ResponseEntity<Response> resetPassword(@RequestHeader String token,  @RequestParam String password){
		User user =  userService.resetPassword(token, password);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Password has been reset Successfully", user), HttpStatus.OK);
	}
}
