package domain;

import constant.CourseType;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CourseResume {

    private CourseType courseType;

    private Map<Long, Long> studentPoints;

    public CourseResume(CourseType courseType) {
        this.courseType = courseType;
        studentPoints = new HashMap<>();
    }

    public void addStudentPoints(long studentId, long points) {
        if (points != 0) {
            studentPoints.put(studentId, points);
        }
    }

    private String getCompletedPercentage(long points) {
        NumberFormat format = new DecimalFormat("#0.0'%'");
        format.setRoundingMode(RoundingMode.HALF_UP);
        double percentage = (points / (1.0 * courseType.getLimitPoints())) * 100;
        return format.format(percentage);
    }

    public void getResume() {
        System.out.println(courseType.getName());
        System.out.printf("%-6s %-6s %-6s%n", "id", "points", "completed");

        Function<Map.Entry<Long,Long>, Long> comparingByStudentId = e -> e.getKey();
        Function<Map.Entry<Long,Long>, Long> comparingByPoints = e -> e.getValue();

        studentPoints.entrySet().stream().sorted(Comparator.comparing(comparingByPoints).reversed().thenComparing(comparingByStudentId)).forEach(e -> {
            System.out.printf("%-6d %-6d %-6s%n", e.getKey(), e.getValue(), getCompletedPercentage(e.getValue()));
        });
    }
}
