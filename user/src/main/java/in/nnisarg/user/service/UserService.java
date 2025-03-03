package in.nnisarg.user.service;

import java.util.Optional;
import java.util.UUID;

import in.nnisarg.user.entity.User;

public interface UserService {

	String register(String email, String password, String sendAs, String plan);

	String login(String email, String password);

	Optional<User> getProfile(UUID userId);

	void logout(UUID userId);

}
