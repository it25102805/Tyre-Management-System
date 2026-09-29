package lk.asityre.tyrerebuild.webapp.model;
import jakarta.persistence.*;

@Entity
@Table(name ="staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role", nullable = false)
    private String role;

    public Integer getStaffID() {
        return staffId;
    }

    public void setStaffID(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getUserID() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}