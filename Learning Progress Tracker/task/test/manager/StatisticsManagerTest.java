package manager;

import constant.CourseType;
import domain.StatisticResult;
import dto.Course;
import dto.StudentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class StatisticsManagerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StatisticsManager statisticsManager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetPopularityWithEmptyCourses() {
//        when(studentRepository.findAll()).thenReturn(List.of());
//
//        StatisticResult<CourseType> result = statisticsManager.getPopularity();
//
//        Assertions.assertEquals("n/a", result.getMost().getName());
//        Assertions.assertEquals("n/a", result.getLeast().getName());
//    }
//
//    @Test
//    void testGetPopularityWithNotEnrolledCourses() {
//        when(studentRepository.findAll()).thenReturn(getStudentsWithNotEnrollment());
//
//        StatisticResult<CourseType> result = statisticsManager.getPopularity();
//
//        Assertions.assertEquals("n/a", result.getMost().getName());
//        Assertions.assertEquals("n/a", result.getLeast().getName());
//    }
//
//    private List<StudentDTO> getStudentsWithNotEnrollment() {
//        List<Course> courses = List.of(
//                new Course("Spring"),
//                new Course("Databases"),
//                new Course("DSA"),
//                new Course("Java")
//        );
//
//        StudentDTO student1 = new StudentDTO();
//        student1.setCourses(courses);
//        StudentDTO student2 = new StudentDTO();
//        student2.setCourses(courses);
//
//        return List.of(student1, student2);
//    }
}

