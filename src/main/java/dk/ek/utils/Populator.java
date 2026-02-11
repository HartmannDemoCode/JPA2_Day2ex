package dk.ek.utils;

import dk.ek.persistence.*;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Map;

public class Populator {
    private final EntityManagerFactory emf;
    private final IDAO<Student> studentDAO;
    private final IDAO<Teacher> teacherDAO;
    private final IDAO<Course> courseDAO;
    public Populator(EntityManagerFactory _emf){
        this.emf = _emf;
        this.studentDAO = new StudentDAO(emf);
        this.teacherDAO = new TeatherDAO(emf);
        this.courseDAO = new CourseDAO(emf);
    }
    public Map<String, IEntity> populate(){
        Teacher teacher1 = new Teacher("Abraham", "abe@college.com", "zoom.com/abe");
        Teacher teacher2 = new Teacher("Benedict", "bene@college.com", "zoom.com/bene");
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = start.plusMonths(6);
        Course course1 = new Course(CourseName.MATH, "Mathematics course", start, end, teacher1);
        Course course2 = new Course(CourseName.ART, "Art course", start, end, teacher2);
        Student student1 = new Student("Charlie", "char@college.com", course1);
        Student student2 = new Student("Diana", "dian@college.com", course1);
        Student student3 = new Student("Edward", "edw@college.com", course2);
        Student student4 = new Student("Fiona", "fion@college.com", course2);
        Student student5 = new Student("George", "geo@college.com", course2);
        Student student6 = new Student("Hansi", "han@college.com", course2);
        teacherDAO.create(teacher1);
        teacherDAO.create(teacher2);
        courseDAO.create(course1);
        courseDAO.create(course2);
        studentDAO.create(student1);
        studentDAO.create(student2);
        studentDAO.create(student3);
        studentDAO.create(student4);
        studentDAO.create(student5);
        studentDAO.create(student6);
        return Map.of(
                "teacher1", (IEntity) teacher1
                , "teacher2", (IEntity) teacher2
                , "course1", (IEntity) course1
                , "course2", (IEntity) course2
                , "student1", (IEntity) student1
                , "student2", (IEntity) student2
                , "student3", (IEntity) student3
                , "student4", (IEntity) student4
                , "student5", (IEntity) student5
                , "student6", (IEntity) student6
        );
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Populator populator = new Populator(emf);
        populator.populate().forEach((k,v)-> System.out.println(k+": "+v));
    }
}
