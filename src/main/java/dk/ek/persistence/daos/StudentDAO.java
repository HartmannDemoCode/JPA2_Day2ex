package dk.ek.persistence.daos;

import dk.ek.persistence.interfaces.IDAO;
import dk.ek.persistence.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class StudentDAO implements IDAO<Student> {
    EntityManagerFactory emf;
    public StudentDAO(EntityManagerFactory _emf){
        this.emf = _emf;
    }
    @Override
    public Student create(Student e) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        }
    }

    @Override
    public Set<Student> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT e FROM Student e").getResultList());
        }
    }

    @Override
    public Student getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            Student student = em.find(Student.class, id);
            if(student == null)
                throw new EntityNotFoundException("No entity found with id: "+id);
            return student;
        }
    }

    @Override
    public Student update(Student e) {
        try(EntityManager em = emf.createEntityManager()){
            Student foundstudent = em.find(Student.class, e.getId());
            if(foundstudent == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            Student student = em.merge(e);
            em.getTransaction().commit();
            return student;
        }
    }

    @Override
    public Long delete(Student e) {
        try(EntityManager em = emf.createEntityManager()){
            Student student = em.find(Student.class, e.getId());
            if(student == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            em.remove(student);
            em.getTransaction().commit();
            return student.getId();
        }
    }
}
