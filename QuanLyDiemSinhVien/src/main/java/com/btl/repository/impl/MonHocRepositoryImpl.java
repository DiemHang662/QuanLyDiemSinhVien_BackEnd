package com.btl.repository.impl;

import com.btl.pojo.Diem;
import com.btl.pojo.MonHoc;
import com.btl.repository.MonHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.Join;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@Repository
public class MonHocRepositoryImpl implements MonHocRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<MonHoc> getNameMonHoc() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MonHoc> criteriaQuery = criteriaBuilder.createQuery(MonHoc.class);
        Root<MonHoc> root = criteriaQuery.from(MonHoc.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonHoc> findByGiangVienId(int giangVienId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MonHoc> criteriaQuery = criteriaBuilder.createQuery(MonHoc.class);
        Root<MonHoc> root = criteriaQuery.from(MonHoc.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("giangVienId"), giangVienId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public MonHoc findById(int id) {
        return entityManager.find(MonHoc.class, id);
    }

    @Override
    public List<MonHoc> getMonHocsBySinhVienId(int sinhVienId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<MonHoc> criteriaQuery = criteriaBuilder.createQuery(MonHoc.class);
        Root<MonHoc> monHocRoot = criteriaQuery.from(MonHoc.class);

        // Join MonHoc with Diem
        Join<MonHoc, Diem> diemJoin = monHocRoot.join("diems");

        // Define the criteria with distinct
        criteriaQuery.select(monHocRoot).distinct(true)
                .where(criteriaBuilder.equal(diemJoin.get("sinhVien").get("id"), sinhVienId));

        // Execute the query
        Query<MonHoc> query = session.createQuery(criteriaQuery);
        List<MonHoc> monHocs = query.getResultList();

        return monHocs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonHoc> getAllMonHocs() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MonHoc> criteriaQuery = criteriaBuilder.createQuery(MonHoc.class);
        Root<MonHoc> root = criteriaQuery.from(MonHoc.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public MonHoc getMonHocById(int id) {
        return entityManager.find(MonHoc.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getIdByName(String monHocName) {
        Session session = sessionFactory.openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<MonHoc> root = query.from(MonHoc.class);

            query.select(root.get("id"))
                    .where(builder.equal(root.get("name"), monHocName));

            Query<Integer> q = session.createQuery(query);
            Integer monHocId = q.getSingleResult();
            return monHocId;
        } catch (NoResultException e) {
            return null; // or handle it as per your logic
        } finally {
            session.close();
        }
    }

}
