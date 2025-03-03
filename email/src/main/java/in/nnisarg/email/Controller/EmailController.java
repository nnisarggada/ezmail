package in.nnisarg.email.Controller;

import in.nnisarg.email.Entity.Email;
import in.nnisarg.email.Repository.EmailRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/send")
public class EmailController {

    @Autowired
    private EmailRepository emailRepository;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestParam String sender,
                                            @RequestParam String receiver,
                                            @RequestParam String message) {
        Email email = new Email();
        email.setSender(sender);
        email.setReceiver(receiver);
        email.setEmail(message);
        email.setStatus("sent"); // Assume success for now

        emailRepository.save(email);
        return ResponseEntity.ok("Email sent successfully and stored in DB.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Email> getEmail(@PathVariable Long id) {
        Optional<Email> email = emailRepository.findById(id);
        return email.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable Long id, @RequestParam String message) {
        Optional<Email> emailOpt = emailRepository.findById(id);
        if (emailOpt.isPresent()) {
            Email email = emailOpt.get();
            email.setEmail(message);
            emailRepository.save(email);
            return ResponseEntity.ok("Email updated successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmail(@PathVariable Long id) {
        if (emailRepository.existsById(id)) {
            emailRepository.deleteById(id);
            return ResponseEntity.ok("Email deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}
