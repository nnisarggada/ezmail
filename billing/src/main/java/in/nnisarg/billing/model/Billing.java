package in.nnisarg.billing.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "billing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId; // User associated with the billing record

    @Column(nullable = false)
    private String plan; // Basic, Premium, Enterprise

    @Column(nullable = false)
    private Double amount; // Cost of the plan

    @Column(nullable = false)
    private String status; // Paid, Unpaid, Failed
}
