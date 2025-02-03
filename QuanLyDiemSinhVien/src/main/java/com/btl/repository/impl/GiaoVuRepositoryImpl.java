package com.btl.repository.impl;

import com.btl.pojo.GiangVien;
import com.btl.pojo.GiaoVu;
import com.btl.pojo.SinhVien;
import com.btl.repository.GiangVienRepository;
import com.btl.repository.GiaoVuRepository;
import com.cloudinary.Cloudinary;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Repository
@Transactional
public class GiaoVuRepositoryImpl implements GiaoVuRepository {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public GiaoVu findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(GiaoVu.class, id);
    }
    
        @Override
    public GiaoVu findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(GiaoVu.class, username);
    }
    
       @Override
    public GiaoVu getGiaoVuById(int id) {
        Session session = sessionFactory.openSession();
        GiaoVu giaoVu = session.get(GiaoVu.class, id);
        session.close();
        return giaoVu;
    }
    
 

    @Override
    public List<GiaoVu> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from GiaoVu", GiaoVu.class).list();
    }

    @Override
    public void save(GiaoVu giaoVu) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(giaoVu);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(GiaoVu giaoVu) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(giaoVu);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        GiaoVu giaoVu = session.load(GiaoVu.class, id);
        if (giaoVu != null) {
            session.delete(giaoVu);
        }
    }
}
