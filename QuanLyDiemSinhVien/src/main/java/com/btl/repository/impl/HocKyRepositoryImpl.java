package com.btl.repository.impl;

import com.btl.pojo.HocKy;
import com.btl.repository.HocKyRepository;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@Repository
public class HocKyRepositoryImpl implements HocKyRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<HocKy> getNameHocKy() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HocKy> criteriaQuery = criteriaBuilder.createQuery(HocKy.class);
        Root<HocKy> root = criteriaQuery.from(HocKy.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    
    @Override
public Integer getIdByName(String hocKyName) {
    Session session = sessionFactory.openSession();
    try {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root<HocKy> root = query.from(HocKy.class);

        query.select(root.get("id"))
             .where(builder.equal(root.get("name"), hocKyName));

        Query<Integer> q = session.createQuery(query);
        Integer hocKyId = q.getSingleResult();
        return hocKyId;
    } catch (NoResultException e) {
        return null; // or handle it as per your logic
    } finally {
        session.close();
    }
}

}
