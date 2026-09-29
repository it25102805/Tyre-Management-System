package lk.asityre.tyrerebuild.webapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;      // was: name

    @Column(name = "last_name")
    private String lastName;       // new, NOT NULL in the table

    private String username;       // new, NOT NULL and unique in the table
    private String email;
    private String password;
    private String role;           // ADMIN, MANAGER, EMPLOYEE or CUSTOMER (no VISITOR)

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Convenience so MainController and dashboard.html can keep using "name"
    public String getName() { return firstName + " " + lastName; }
}