package org.by.bharadwaj.fundoo.service;

import org.by.bharadwaj.fundoo.dto.LoginDto;
import org.by.bharadwaj.fundoo.dto.UserDTO;
import org.by.bharadwaj.fundoo.entity.User;

import java.util.List;

public interface UserService {

	public User register(UserDTO userDTO);

	public String login(LoginDto loginDto) ;

	public User verifyEmail(String token);

	public List<User> getAllUser();

	public void forgotPassword(String emailId);

	public User resetPassword(String token, String password);

}
