package dk.ek.persistence;

import dk.ek.persistence.config.HibernateConfig;
import dk.ek.persistence.daos.CourseDAO;
import dk.ek.persistence.daos.StudentDAO;
import dk.ek.persistence.daos.TeatherDAO;
import dk.ek.persistence.interfaces.IDAO;
import dk.ek.persistence.interfaces.IEntity;
import dk.ek.persistence.model.Course;
import dk.ek.persistence.model.Student;
import dk.ek.persistence.model.Teacher;
import dk.ek.utils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IDAOTest {
    private static EntityManagerFactory emf;
    private static IDAO<Student> studentDAO;
    private static IDAO<Teacher> teacherDAO;
    private static IDAO<Course> courseDAO;
    private static Populator populator;
    private static Map<String, IEntity> entities;

    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        studentDAO = new StudentDAO(emf);
        courseDAO = new CourseDAO(emf);
        teacherDAO = new TeatherDAO(emf);
        populator = new Populator(emf);
        entities = populator.populate();
    }

    @BeforeEach
    void init() {
        // Clean up the database before each test
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.shutdownTestEmf();
    }

    @Test
    void create() {
    }

    @Test
    @DisplayName("StudentDAO.get() should return all students with their associated course and teacher")
    void getStudentsWithCoursesAndTeachers() {
        Set<Student> students = studentDAO.get();
        students.forEach(s -> {
            System.out.println(s.getName() + " is enrolled in course: " + s.getEnrollments().stream().map(enroll->enroll.getCourse().getCourseName().name()).reduce((c1,c2)->c1+", "+c2).orElse("No courses"));
        });
        int actual = students.size();
        int expected = 6; // Based on the Populator data
        assertEquals(expected, actual);
    }
    @Test
    void get() {
        Set<Student> students = studentDAO.get();
        int actual = students.size();
        int expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    void getByID() {
    }

    @Test
    void update() {
        Student employeeToUpdate =(Student) entities.get("student1") ;
        employeeToUpdate.setName("Updated Student Name");
        Student updated = studentDAO.update(employeeToUpdate);
        assertEquals("Updated Student Name", updated.getName());
    }

    @Test
    void delete() {
    }

}