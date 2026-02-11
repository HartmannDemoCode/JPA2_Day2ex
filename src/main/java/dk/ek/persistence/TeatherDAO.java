package dk.ek.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class TeatherDAO implements IDAO<Teacher> {
    EntityManagerFactory emf;
    public TeatherDAO(EntityManagerFactory _emf){
        this.emf = _emf;
    }

    @Override
    public Teacher create(Teacher e) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        }
    }

    @Override
    public Set<Teacher> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT e FROM Teacher e").getResultList());
        }
    }

    @Override
    public Teacher getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            Teacher teacher = em.find(Teacher.class, id);
            if(teacher == null)
                throw new EntityNotFoundException("No entity found with id: "+id);
            return teacher;
        }
    }

    @Override
    public Teacher update(Teacher e) {
        try(EntityManager em = emf.createEntityManager()){
            Teacher teacher = em.find(Teacher.class, e.getId());
            if(teacher == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            teacher = em.merge(e);
            em.getTransaction().commit();
            return teacher;
        }
    }

    @Override
    public Long delete(Teacher e) {
        try(EntityManager em = emf.createEntityManager()){
            Teacher teacher = em.find(Teacher.class, e.getId());
            if(teacher == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            em.remove(teacher);
            em.getTransaction().commit();
            return teacher.getId();
        }
    }
}
