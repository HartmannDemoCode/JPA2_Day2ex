package dk.ek.persistence;

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
            System.out.println(s.getName() + " is enrolled in course: " + s.getCourse().getCourseName()
                    + " taught by: " + s.getCourse().getTeacher().getName());
        });
        int actual = students.size();
        int expected = 6; // Based on the Populator data
        assertEquals(expected, actual);
    }
//    @Test
//    void get() {
//        Set<Student> eployees = employeeDAO.get();
//        eployees.forEach(e -> System.out.println(e.getName() + " is deployed in department: " + e.getDepartment().getName()));
//        int actual = eployees.size();
//        int expected = 12;
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getByID() {
//    }
//
//    @Test
//    void update() {
//        Student employeeToUpdate = employees.get(0);
//        employeeToUpdate.setName("Updated Student Name");
//        Student updatedEmployee = employeeDAO.update(employeeToUpdate);
//        assertEquals("Updated Student Name", updatedEmployee.getName());
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void generalUpdateWithCheck_updatesScalarsOnly() {
//        // Arrange: create a detached "patch" employee (only ID + new name/email)
//        Student existing = employees.get(0);
//
//        Student patch = new Student();
//        patch.setId(existing.getId());
//        patch.setName("Patched Name");
//        patch.setEmail("patched@mail.com");
//
//        // Act
//        Student updated = employeeDAO.updateWithCheck(patch);
//
//        // Assert
//        assertEquals("Patched Name", updated.getName());
//        assertEquals("patched@mail.com", updated.getEmail());
//
//        // department should remain unchanged
//        assertNotNull(updated.getDepartment());
//        assertEquals(existing.getDepartment().getId(), updated.getDepartment().getId());
//    }
//
//    @Test
//    void generalUpdateWithCheck_movesEmployeeToExistingDepartment_withoutCascade() {
//        // Arrange
//        Student existing = employees.get(0);
//        Long empId = existing.getId();
//
//        // pick a different department than the employee currently has
//        Department currentDept = existing.getDepartment();
//        Department targetDept = departmentDAO.get().stream()
//                .filter(d -> !d.getId().equals(currentDept.getId()))
//                .findFirst()
//                .orElseThrow();
//
//        Student patch = new Student();
//        patch.setId(empId);
//
//        Department deptRef = new Department();
//        deptRef.setId(targetDept.getId());  // only ID is provided (detached reference)
//        patch.setDepartment(deptRef);
//
//        // Act
//        Student updated = employeeDAO.updateWithCheck(patch);
//
//        // Assert
//        assertNotNull(updated.getDepartment());
//        assertEquals(targetDept.getId(), updated.getDepartment().getId());
//    }
//
//
//    @Test
//    void generalUpdateWithCheck_persistsNewDepartment_whenNoId_withoutCascade() {
//        // Arrange
//        Student existing = employees.get(0);
//
//        Student patch = new Student();
//        patch.setId(existing.getId());
//
//        Department newDept = new Department();
//        newDept.setName("Brand New Department"); // no id => should be persisted explicitly
//        patch.setDepartment(newDept);
//
//        int deptCountBefore = departmentDAO.get().size();
//
//        // Act
//        Student updated = employeeDAO.updateWithCheck(patch);
//
//        // Assert: employee now points to a department that has an id (was persisted)
//        assertNotNull(updated.getDepartment());
//        assertNotNull(updated.getDepartment().getId(), "New department should have been persisted explicitly");
//
//        int deptCountAfter = departmentDAO.get().size();
//        assertEquals(deptCountBefore + 1, deptCountAfter);
//
//        // Verify name was stored
//        Department fromDb = departmentDAO.getByID(updated.getDepartment().getId());
//        assertEquals("Brand New Department", fromDb.getName());
//    }
}