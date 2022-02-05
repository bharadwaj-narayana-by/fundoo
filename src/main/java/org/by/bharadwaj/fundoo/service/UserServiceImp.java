package org.by.bharadwaj.fundoo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.by.bharadwaj.fundoo.dto.LoginDto;
import org.by.bharadwaj.fundoo.util.EmailService;
import org.by.bharadwaj.fundoo.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.by.bharadwaj.fundoo.dto.UserDTO;
import org.by.bharadwaj.fundoo.entity.User;
import org.by.bharadwaj.fundoo.exception.FundooException;
import org.by.bharadwaj.fundoo.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	EmailService emailService;

	@Value("${spring.mail.username}")
	private String senderEmailId;


	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	public String login(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() ->  new FundooException(HttpStatus.NOT_FOUND.value(),"Email id does not exist"));
		if(!user.getIsEmailVerified()) {
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(), "Email not verified");
		}
		if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(),"password is incorrect");
		}
		return tokenUtil.createToken(user.getId());
	}

	@Override
	public User register(UserDTO userDTO) {
		Optional<User> isUserPresent = userRepository.findByEmail(userDTO.getEmail());
		if (isUserPresent.isPresent()) {
			throw new FundooException(HttpStatus.CONFLICT.value(), "Email is already registered");
		}
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setIsEmailVerified(false);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User savedUser = userRepository.save(user);
		String token = tokenUtil.createToken(savedUser.getId(), new Date(System.currentTimeMillis() + (180 * 1000)));
		emailService.sendMail("Email Verification For Fundoo", "http://localhost:8080/user/verify-email/" + token,
				senderEmailId, savedUser.getEmail());
		return savedUser;
	}



	@Override
	public User verifyEmail(String token) {
		Long userId = tokenUtil.decodeToken(token);
		User user = userRepository.findById(userId).
				orElseThrow(()-> new FundooException(HttpStatus.NOT_FOUND.value(), "User not found"));
		user.setIsEmailVerified(true);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void forgotPassword(String emailId) {
		User user = userRepository.findByEmail(emailId)
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		String token = tokenUtil.createToken(user.getId());
		emailService.sendMail("Reset Password Link", "http://localhost:8080/user/reset-password/" + token,
				 senderEmailId,user.getEmail());
	}

	@Override
	public User resetPassword(String token, String password) {
		Long userId = tokenUtil.decodeToken(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		user.setPassword(passwordEncoder.encode(password));
		return userRepository.save(user);
	}


}
