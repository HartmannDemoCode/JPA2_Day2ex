package dk.ek.persistence;

import dk.ek.persistence.config.HibernateConfig;
import dk.ek.persistence.daos.RetrieveDAO;
import dk.ek.persistence.interfaces.IEntity;
import dk.ek.persistence.interfaces.IRetrieveDAO;
import dk.ek.persistence.model.Course;
import dk.ek.persistence.model.Student;
import dk.ek.persistence.model.Teacher;
import dk.ek.utils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class IRetrieveDAOTest {

    private static EntityManagerFactory emf;
    private static IRetrieveDAO retrieveDAO;

    private static Populator populator;
    private static Map<String, IEntity> entities;

    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();

        retrieveDAO = new RetrieveDAO(emf); // <-- change to your implementation

        populator = new Populator(emf);
        populator.populate();
    }

    @BeforeEach
    void init() {
        entities = populator.populate();
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.shutdownTestEmf();
    }

    @Test
    @DisplayName("getAllStudentsInCourse(courseId) should return all students enrolled in that course")
    void getAllStudentsInCourse() {
        Course course = (Course) entities.get("course1"); // adjust key names to your Populator
        assertNotNull(course, "Populator must provide course1");

        Set<Student> studentsInCourse = retrieveDAO.getAllStudentsInCourse(course.getId());

        assertNotNull(studentsInCourse);
        assertFalse(studentsInCourse.isEmpty(), "Course should have students (based on Populator)");

    }

    @Test
    @DisplayName("getAllCoursesForTeacher(teacherId) should return all courses taught by that teacher")
    void getAllCoursesForTeacher() {
        Teacher teacher = (Teacher) entities.get("teacher1"); // adjust key names
        assertNotNull(teacher, "Populator must provide teacher1");

        Set<Course> courses = retrieveDAO.getAllCoursesForTeacher(teacher.getId());

        assertNotNull(courses);
        assertFalse(courses.isEmpty(), "Teacher should have courses (based on Populator)");

        // every returned course must have that teacher
        courses.forEach(c -> assertNotNull(c.getTeacher()));
        assertTrue(courses.stream().allMatch(c -> c.getTeacher().getId().equals(teacher.getId())));
    }

    @Test
    @DisplayName("getAllStudentsForTeacher(teacherId) should return all students across that teacher's courses")
    void getAllStudentsForTeacher() {
        Teacher teacher = (Teacher) entities.get("teacher1"); // adjust key names
        assertNotNull(teacher, "Populator must provide teacher1");

        Set<Student> students = retrieveDAO.getAllStudentsForTeacher(teacher.getId());

        assertNotNull(students);
        assertFalse(students.isEmpty(), "Teacher should have students via courses (based on Populator)");
    }

    @Test
    @DisplayName("getAllTeachersForCourse(courseName) should return all teachers connected to that course name")
    void getAllTeachersForCourse() {
        Course course = (Course) entities.get("course1");
        assertNotNull(course, "Populator must provide course1");

        String courseName = course.getCourseName().toString();
        assertNotNull(courseName);

        Set<Teacher> teachers = retrieveDAO.getAllTeachersForCourse(courseName);

        assertNotNull(teachers);
        assertFalse(teachers.isEmpty(), "Course name should yield teacher(s)");

        assertTrue(
                teachers.stream().anyMatch(t -> t.getId().equals(course.getTeacher().getId())),
                "Result should include the teacher for the course"
        );
    }

}