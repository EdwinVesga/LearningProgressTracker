package manager;

import domain.Notification;

import java.util.*;

public class NotificationManager {

    private Set<String> studentRecord;

    private Deque<Notification> notifications;

    public NotificationManager() {
        this.studentRecord = new HashSet<>();
        this.notifications = new ArrayDeque<>();
    }

    public void addNotification(String fullName, String email, String course) {

        String record = String.join(",", email, course);

        if (!studentRecord.contains(record)) {
            notifications.add(new Notification(fullName, email, course));
        }
    }

    public void notifyStudents() {
        Set<String> senders = new HashSet<>();

        while (!notifications.isEmpty()) {
            Notification notification = notifications.pollFirst();
            notification.printNotification();
            senders.add(notification.getSender());
        }

        System.out.printf("Total %d students have been notified.", senders.size());
    }
}
