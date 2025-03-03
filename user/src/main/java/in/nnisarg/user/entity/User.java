package in.nnisarg.user.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@Email
	@NotBlank
	@Column(unique = true, nullable = false)
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String sendAs;

	@NotBlank
	private String plan;

	@Column(unique = true, nullable = false)
	private String apiToken;

}
