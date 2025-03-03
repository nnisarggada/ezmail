package in.nnisarg.user.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.nnisarg.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String password = request.get("password");
		String sendAs = request.get("sendAs");
		String plan = request.get("plan");

		try {
			String token = userService.register(email, password, sendAs, plan);
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String password = request.get("password");
		try {
			String token = userService.login(email, password);
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// TODO: Implement JWT
	@GetMapping("/profile")
	public ResponseEntity<Object> getProfile(@RequestParam String userId) {
		return ResponseEntity.ok(userService.getProfile(UUID.fromString(userId)));
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam String userId) {
		userService.logout(UUID.fromString(userId));
		return ResponseEntity.ok("Logout successful");
	}
}
