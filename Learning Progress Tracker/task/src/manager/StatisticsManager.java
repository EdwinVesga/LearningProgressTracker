package manager;

import constant.CourseType;
import domain.CourseResume;
import domain.StatisticAverage;
import domain.StatisticResult;
import repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsManager {

    private final StudentRepository studentRepository;

    public StatisticsManager(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StatisticResult<List<CourseType>> getPopularity() {
        Map<String, Integer> popularity = new HashMap<>();

        this.studentRepository.findAll()
                .stream()
                .flatMap(s -> s.getCourses().stream())
                .forEach(c -> popularity.put(c.getName(),
                        c.getPoints() != 0 ? popularity.getOrDefault(c.getName(), 0) + 1 : popularity.getOrDefault(c.getName(), 0)));

        Deque<Map.Entry<String, Integer>> sortedPopularity = popularity.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(a -> a.getValue()))
                .collect(Collectors.toCollection(ArrayDeque::new));

        List<CourseType> mostPopular = new ArrayList<>();
        List<CourseType> leastPopular = new ArrayList<>();

        if (sortedPopularity.isEmpty()) {
            mostPopular.add(CourseType.NONE);
            leastPopular.add(CourseType.NONE);
        } else if (sortedPopularity.peekFirst().getValue() == sortedPopularity.peekLast().getValue()) {
            mostPopular.addAll(sortedPopularity.stream().map(c -> CourseType.getCourse(c.getKey())).collect(Collectors.toList()));
            leastPopular.add(CourseType.NONE);
        } else {
            Integer max = sortedPopularity.peekLast().getValue();
            while (sortedPopularity.peekLast().getValue() == max) {
                mostPopular.add(CourseType.getCourse(sortedPopularity.pollLast().getKey()));
            }

            Integer min = sortedPopularity.peekFirst().getValue();
            while (!sortedPopularity.isEmpty() && sortedPopularity.peekFirst().getValue() == min) {
                leastPopular.add(CourseType.getCourse(sortedPopularity.pollFirst().getKey()));
            }
        }

        return new StatisticResult<>(mostPopular, leastPopular);
    }

    public StatisticResult<List<CourseType>> getStudentActivityMeasure() {
        Map<String, Integer> activity = new HashMap<>();

        this.studentRepository.findAll()
                .stream()
                .flatMap(s -> s.getCourses().stream())
                .forEach(c -> activity.put(c.getName(), activity.getOrDefault(c.getName(), 0) + c.getSubmissions()));

        Deque<Map.Entry<String, Integer>> sortedActivities = activity.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(a -> a.getValue()))
                .collect(Collectors.toCollection(ArrayDeque::new));

        List<CourseType> mostActivity = new ArrayList<>();
        List<CourseType> leastActivity = new ArrayList<>();

        if (sortedActivities.isEmpty()) {
            mostActivity.add(CourseType.NONE);
            leastActivity.add(CourseType.NONE);
        } else if (sortedActivities.peekFirst().getValue() == sortedActivities.peekLast().getValue()) {
            mostActivity.addAll(sortedActivities.stream().map(c -> CourseType.getCourse(c.getKey())).collect(Collectors.toList()));
            leastActivity.add(CourseType.NONE);
        } else {
            Integer max = sortedActivities.peekLast().getValue();
            while (sortedActivities.peekLast().getValue() == max) {
                mostActivity.add(CourseType.getCourse(sortedActivities.pollLast().getKey()));
            }

            Integer min = sortedActivities.peekFirst().getValue();
            while (!sortedActivities.isEmpty() && sortedActivities.peekFirst().getValue() == min) {
                leastActivity.add(CourseType.getCourse(sortedActivities.pollFirst().getKey()));
            }
        }

        return new StatisticResult<>(mostActivity, leastActivity);
    }


    public StatisticResult<CourseType> getDifficulty() {
        Map<String, StatisticAverage> difficulty = new HashMap<>();

        this.studentRepository.findAll()
                .stream()
                .flatMap(s -> s.getCourses().stream())
                .forEach(c -> {
                    StatisticAverage avg = difficulty.getOrDefault(c.getName(), new StatisticAverage());
                    avg.addAmount(c.getPoints());
                    difficulty.put(c.getName(), avg);
                });

        Deque<String> sortedPopularity = difficulty.entrySet()
                .stream()
                .sorted(Comparator.comparingDouble(a -> a.getValue().getAverage()))
                .map(e -> e.getKey()).collect(Collectors.toCollection(ArrayDeque::new));

        CourseType easiest = CourseType.getCourse(sortedPopularity.pollLast());
        CourseType hardest = CourseType.getCourse(sortedPopularity.pollFirst());

        return new StatisticResult<>(hardest, easiest);
    }

    public CourseResume getCourseResume(String courseName) {
        CourseResume courseResume = new CourseResume(CourseType.getCourse(courseName));
        studentRepository.findAll().forEach(s -> {
           s.getCourses().stream().filter(c -> courseName.equals(c.getName())).forEach(c -> {
               courseResume.addStudentPoints(s.getId(), c.getPoints());
           });
        });
        return courseResume;
    }


}
