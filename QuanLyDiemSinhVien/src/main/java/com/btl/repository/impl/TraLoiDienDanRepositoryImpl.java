package com.btl.repository.impl;

import com.btl.pojo.DienDan;
import com.btl.pojo.SinhVien;
import com.btl.pojo.TraLoiDienDan;
import com.btl.repository.TraLoiDienDanRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import org.hibernate.Transaction;

@Repository
@Transactional
public class TraLoiDienDanRepositoryImpl implements TraLoiDienDanRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<TraLoiDienDan> getTraLoiByDienDan(int dienDanId) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM TraLoiDienDan t WHERE t.dienDan.id = :dienDanId";
        return session.createQuery(hql, TraLoiDienDan.class)
                .setParameter("dienDanId", dienDanId)
                .getResultList();
    }

    @Override
    public void addTraLoi(TraLoiDienDan traLoiDienDan) {
        this.sessionFactory.getCurrentSession().save(traLoiDienDan);
    }

    @Override
    public void updateTraLoi(TraLoiDienDan traLoiDienDan) {
        this.sessionFactory.getCurrentSession().update(traLoiDienDan);
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TraLoiDienDan traLoiDienDan = session.get(TraLoiDienDan.class, id);
        if (traLoiDienDan != null) {
            session.delete(traLoiDienDan);
        }
        transaction.commit();
        session.close();
    }


}
