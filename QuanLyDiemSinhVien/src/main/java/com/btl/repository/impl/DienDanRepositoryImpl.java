package com.btl.repository.impl;

import com.btl.pojo.DienDan;
import com.btl.repository.DienDanRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DienDanRepositoryImpl implements DienDanRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<DienDan> getDienDanByMonHoc(int monHocId) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM DienDan d WHERE d.id IN (SELECT m.dienDan.id FROM MonHoc m WHERE m.id = :monHocId)";
        return session.createQuery(hql, DienDan.class)
                .setParameter("monHocId", monHocId)
                .getResultList();
    }

    @Override
    public DienDan getDienDanById(int id) {
        return this.sessionFactory.getCurrentSession().get(DienDan.class, id);
    }

    @Override
    public void addDienDan(DienDan dienDan) {
        this.sessionFactory.getCurrentSession().save(dienDan);
    }

    @Override
    public void updateDienDan(DienDan dienDan) {
        this.sessionFactory.getCurrentSession().update(dienDan);
    }

    @Override
    public void deleteDienDan(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        DienDan dienDan = session.get(DienDan.class, id);
        if (dienDan != null) {
            session.delete(dienDan);
        }
    }
}
