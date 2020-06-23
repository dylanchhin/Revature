package bank.model;

/***
 *
 * @author Shawyn Kane
 */
public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String role;

    public User(String email, String firstName, String lastName, String password, String phoneNumber, String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean equals(User otherUser) {
        return this.email.equals(otherUser.getEmail()) && this.getPassword().equals(otherUser.getPassword()) && this.getFirstName().equals(otherUser.getFirstName()) && this.getLastName().equals(otherUser.getLastName()) && this.getPhoneNumber().equals(otherUser.getPhoneNumber()) && this.getRole().equals(otherUser.getRole());
    }

    public boolean missingInformation() {
        return (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty() || firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty() || phoneNumber == null || phoneNumber.trim().isEmpty() || role == null || role.trim().isEmpty());
    }
}
