package dk.ek.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class CourseDAO implements IDAO<Course> {
    EntityManagerFactory emf;
    public CourseDAO(EntityManagerFactory _emf){
        this.emf = _emf;
    }
    @Override
    public Course create(Course e) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        }
    }

    @Override
    public Set<Course> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT e FROM Course e").getResultList());
        }
    }

    @Override
    public Course getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            Course employee = em.find(Course.class, id);
            if(employee == null)
                throw new EntityNotFoundException("No entity found with id: "+id);
            return employee;
        }
    }

    @Override
    public Course update(Course e) {
        try(EntityManager em = emf.createEntityManager()){
            Course foundEmployee = em.find(Course.class, e.getId());
            if(foundEmployee == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            Course employee = em.merge(e);
            em.getTransaction().commit();
            return employee;
        }
    }

    @Override
    public Long delete(Course e) {
        try(EntityManager em = emf.createEntityManager()){
            Course employee = em.find(Course.class, e.getId());
            if(employee == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            em.remove(employee);
            em.getTransaction().commit();
            return employee.getId();
        }
    }
}
