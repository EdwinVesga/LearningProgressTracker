package tracker;

import constant.CourseType;
import domain.CourseResume;
import dto.Course;
import dto.StudentDTO;
import manager.NotificationManager;
import manager.StatisticsManager;
import repository.StudentRepository;
import utils.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TrackerCLI {
    private static final String TITLE = "Learning Progress Tracker";

    private final Scanner scanner;

    private final StudentRepository studentRepository;

    private final StatisticsManager statisticsManager;

    private final NotificationManager notificationManager;

    private boolean running;

    public TrackerCLI(Scanner scanner,
                      StudentRepository studentRepository,
                      StatisticsManager statisticsManager,
                      NotificationManager notificationManager) {
        this.scanner = scanner;
        this.studentRepository = studentRepository;
        this.statisticsManager = statisticsManager;
        this.notificationManager = notificationManager;
        this.running = true;
    }

    public void init() {

        System.out.println(TITLE);

        while (running) {
            String command = scanner.nextLine();

            if (command.isEmpty() || command.isBlank()) {
                System.out.println("No input");
            } else {
                processCommand(command);
            }
        }
    }

    private void processCommand(String command) {

        switch (command) {
            case "add students":
                System.out.println("Enter student credentials or 'back' to return");
                addStudents();
                break;
            case "exit":
                System.out.println("Bye!");
                this.running = false;
                break;
            case "back":
                System.out.println("Enter 'exit' to exit the program");
                break;
            case "list":
                listStudents();
                break;
            case "add points":
                addStudentPoints();
                break;
            case "find":
                findStudent();
                break;
            case "statistics":
                System.out.println("Type the name of a course to see details or 'back' to quit");
                printResumeStatistics();
                printCourseResume();
                break;
            case "notify":
                notificationManager.notifyStudents();
                break;
            default:
                System.out.println("Error: unknown command!");
                break;
        }
    }

    private void printCourseResume() {

        while (true) {
            String input = scanner.nextLine();

            if ("back".equals(input)) {
                break;
            }

            CourseType course = CourseType.getCourse(input);
            if (CourseType.NONE.equals(course)) {
                System.out.println("Unknown course.");
            } else {
                CourseResume resume = statisticsManager.getCourseResume(course.getName());
                resume.getResume();
            }
        }

    }

    private void printResumeStatistics() {
        String mostPopular = String.join(", ", statisticsManager.getPopularity().getMost().stream().map(a -> a.getName()).collect(Collectors.toList()));
        String leastPopular = String.join(", ", statisticsManager.getPopularity().getLeast().stream().map(a -> a.getName()).collect(Collectors.toList()));
        String highestActivity = String.join(", ", statisticsManager.getStudentActivityMeasure().getMost().stream().map(a -> a.getName()).collect(Collectors.toList()));
        String lowestActivity = String.join(", ", statisticsManager.getStudentActivityMeasure().getLeast().stream().map(a -> a.getName()).collect(Collectors.toList()));
        String easiestCourse = statisticsManager.getDifficulty().getLeast().getName();
        String hardestCourse = statisticsManager.getDifficulty().getMost().getName();

        System.out.printf("Most popular: %s%n", mostPopular);
        System.out.printf("Least popular: %s%n", leastPopular);
        System.out.printf("Highest activity: %s%n", highestActivity);
        System.out.printf("Lowest activity: %s%n", lowestActivity);
        System.out.printf("Easiest course: %s%n", easiestCourse);
        System.out.printf("Hardest course: %s%n", hardestCourse);
    }

    private void findStudent() {
        System.out.println("Enter and id or 'back' to return.");

        while (true) {
            String input = scanner.nextLine();

            if ("back".equals(input)) {
                break;
            }

            long id = Long.parseLong(input);

            StudentDTO student = this.studentRepository.findById(id);

            if (student == null) {
                System.out.printf("No student is found for id=%d.%n");
            } else {
                List<String> courseToString = new ArrayList<>();
                student.getCourses().stream().forEach(c -> courseToString.add(c.toString()));
                System.out.printf("%d points: %s%n", student.getId(), String.join("; ", courseToString));
            }
        }
    }

    private void addStudentPoints() {
        System.out.println("Enter an id and points or 'back' to return.");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("back")) {
                break;
            }

            String[] inputs = input.split(" ");
            long[] studentPoints = new long[inputs.length];
            boolean validInput = true;

            if (inputs.length != 5) {
                System.out.println("Incorrect points format.");
                validInput = false;
            }

            for (int i = 0; i < inputs.length; i++) {
                try {
                    long number = Long.valueOf(inputs[i]);
                    if (number < 0) {
                        System.out.println("Incorrect points format.");
                        validInput = false;
                        break;
                    }
                    studentPoints[i] = number;
                } catch (NumberFormatException e) {
                    validInput = false;
                    if (i == 0) {
                        System.out.printf("No student is found for id=%s.%n", inputs[i]);
                    } else {
                        System.out.println("Incorrect points format.");
                    }
                    break;
                }
            }

            if (!validInput) {
                continue;
            }

            Long id = studentPoints[0];
            StudentDTO student = this.studentRepository.findById(id);

            if (student == null) {
                System.out.printf("No student is found for id=%d.%n", id);
            } else {
                List<Course> courses = student.getCourses();

                if (courses == null || courses.isEmpty()) {
                    courses = List.of(
                            new Course(CourseType.JAVA.getName()).addPoints(studentPoints[1]),
                            new Course(CourseType.DSA.getName()).addPoints(studentPoints[2]),
                            new Course(CourseType.DATABASES.getName()).addPoints(studentPoints[3]),
                            new Course(CourseType.SPRING.getName()).addPoints(studentPoints[4])
                    );

                    student.setCourses(courses);
                } else {
                    for (int i = 0; i < courses.size(); i++) {
                        courses.get(i).addPoints(studentPoints[i + 1]);
                    }
                }

                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getPoints() >= CourseType.getCourse(courses.get(i).getName()).getLimitPoints()) {
                        notificationManager.addNotification(student.getFullName(), student.getEmail(), courses.get(i).getName());
                    }
                }

                this.studentRepository.addStudent(student);
                System.out.println("Points updated.");
            }
        }
    }

    private void listStudents() {
        List<StudentDTO> students = this.studentRepository.findAll();

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            students.stream().forEach(s -> System.out.println(s.getId()));
        }
    }

    private void addStudents() {
        int counter = 0;

        while(true) {
            String input = scanner.nextLine();

            if ("back".equals(input)) {
                System.out.printf("Total %d students have been added.%n", counter);
                break;
            }

            String[] credentials = input.split(" ");

            if (credentials.length < 3) {
                System.out.println("Incorrect credentials.");
            } else {
                String firstName = credentials[0];
                String[] lastNames = Arrays.copyOfRange(credentials, 1, credentials.length - 1);
                String email = credentials[credentials.length - 1];

                boolean validCredentials = Validator.validateCredentials(firstName, lastNames, email);

                if (validCredentials) {
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setFirstName(firstName);
                    studentDTO.setLastName(String.join(" ",lastNames));
                    studentDTO.setEmail(email);

                    boolean success = studentRepository.addStudent(studentDTO);

                    if (success) {
                        System.out.println("The student has been added.");
                        counter++;
                    } else {
                        System.out.println("This email is already taken.");
                    }
                }
            }
        }
    }
}
