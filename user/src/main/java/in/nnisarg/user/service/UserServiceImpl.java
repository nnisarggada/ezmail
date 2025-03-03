package in.nnisarg.user.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.nnisarg.user.entity.User;
import in.nnisarg.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String register(String email, String password, String sendAs, String plan) {
		Optional<User> existingUser = userRepo.findByEmail(email);
		if (existingUser.isPresent()) {
			throw new RuntimeException("Email already registered");
		}

		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setSendAs(sendAs);
		user.setPlan(plan);
		// TODO: Implement JWT
		user.setApiToken(UUID.randomUUID().toString());
		userRepo.save(user);
		return user.getApiToken();
	}

	@Override
	public String login(String email, String password) {
		Optional<User> user = userRepo.findByEmail(email);
		if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
			// TODO: Implement JWT
			return UUID.randomUUID().toString();
		}
		throw new RuntimeException("Invalid credentials");
	}

	@Override
	public Optional<User> getProfile(UUID userId) {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			return user;
		}
		throw new RuntimeException("User not found");
	}

	@Override
	public void logout(UUID userId) {
		// TODO: Invalidate refresh token
	}

}
