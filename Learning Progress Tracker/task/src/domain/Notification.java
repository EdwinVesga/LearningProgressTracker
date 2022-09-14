package domain;

public class Notification {

    private static final String SUBJECT = "Your Learning Progress";

    private String sender;

    private String email;

    private String course;

    public Notification(String sender, String email, String course) {
        this.sender = sender;
        this.email = email;
        this.course = course;
    }

    public String getSender() {
        return sender;
    }

    public void printNotification() {
        System.out.printf("To: %s%n", email);
        System.out.printf("Re: %s%n", SUBJECT);
        System.out.printf("Hello, %s! You have accomplished our %s course!%n", sender, course);
    }
}
