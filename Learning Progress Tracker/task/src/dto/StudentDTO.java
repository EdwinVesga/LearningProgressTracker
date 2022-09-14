package dto;

import java.util.List;

public class StudentDTO {

    private static Long ID_COUNTER = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<Course> courses;

    public StudentDTO() {
        this.id = ID_COUNTER;
        ID_COUNTER++;
    }

    public String getFullName() {
        return String.join(" ", firstName, lastName);
    }
    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
