package com.btl.repository.impl;

import com.btl.pojo.LoaiDiem;
import com.btl.repository.LoaiDiemRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class LoaiDiemRepositoryImpl implements LoaiDiemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<LoaiDiem> getNameLoaiDiem() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LoaiDiem> criteriaQuery = criteriaBuilder.createQuery(LoaiDiem.class);
        Root<LoaiDiem> root = criteriaQuery.from(LoaiDiem.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
