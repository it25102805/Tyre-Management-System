package lk.asityre.tyrerebuild.webapp.model;

import jakarta.persistence.*;

@Entity
<<<<<<< HEAD
@Table(name ="staff")
public class Staff {
=======
@Table(name = "staff")
public class Staff {

>>>>>>> origin/main
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "role", nullable = false)
    private String role;

<<<<<<< HEAD
    public Integer getStaffID() {
        return staffId;
    }

    public void setStaffID(Integer staffId) {
=======
    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
>>>>>>> origin/main
        this.staffId = staffId;
    }

    public Integer getUserId() {
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