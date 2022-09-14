package tracker;

import manager.NotificationManager;
import manager.StatisticsManager;
import repository.StudentRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        StudentRepository studentRepository = new StudentRepository();
        StatisticsManager statisticsManager = new StatisticsManager(studentRepository);
        NotificationManager notificationManager = new NotificationManager();

        TrackerCLI trackerCLI = new TrackerCLI(scanner, studentRepository, statisticsManager, notificationManager);

        trackerCLI.init();
    }
}
