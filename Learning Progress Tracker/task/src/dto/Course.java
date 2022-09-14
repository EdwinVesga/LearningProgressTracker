package dto;

public class Course {

    private String name;

    private long points;

    private int submissions;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
        this.points = 0L;
        this.submissions = 0;
    }

    public String getName() {
        return name;
    }

    public long getPoints() {
        return points;
    }

    public Course setPoints(long points) {
        this.points = points;
        return this;
    }

    public Course addPoints(long points) {
        this.points += points;
        this.submissions++;
        return this;
    }

    public int getSubmissions() {
        return submissions;
    }

    @Override
    public String toString() {
        return String.format("%s=%d", name, points);
    }
}
