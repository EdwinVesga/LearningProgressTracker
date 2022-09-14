package constant;

import java.util.Locale;

public enum CourseType {

    JAVA("Java", 600), DSA("DSA", 400), DATABASES("Databases", 480), SPRING("Spring", 550), NONE("n/a", 0);

    private String name;
    
    private int limitPoints;

    CourseType(String name, int limitPoints) {
        this.name = name;
        this.limitPoints = limitPoints;
    }

    public String getName() {
        return name;
    }

    public int getLimitPoints() {
        return limitPoints;
    }

    public static CourseType getCourse(String name) {
        CourseType course;

        try {
            course = valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            course = CourseType.NONE;
        }

        return course;
    }
}
