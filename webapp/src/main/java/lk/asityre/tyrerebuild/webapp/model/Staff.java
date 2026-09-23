package lk.asityre.tyrerebuild.webapp.model;
import jakarta.persistence.*;

@Entity
@Table(name ="staff")
public class Staff{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "role" , nullable = false)
    private String role;

    @Column(name = "contact_number")
    private String contactNumber;

    public Integer getStaffID() {
        return staffId;
    }

    public void setStaffID(Integer StaffId){
        this.staffId = staffId;
    }

    public Integer getUserID() {
        return userId;
    }

    public void setUserId(Integer userID){
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber){
        this.contactNumber= contactNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
