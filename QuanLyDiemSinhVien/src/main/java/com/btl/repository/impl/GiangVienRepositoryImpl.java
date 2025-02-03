package com.btl.repository.impl;

import com.btl.pojo.GiangVien;
import com.btl.pojo.SinhVien;
import com.btl.repository.GiangVienRepository;
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
public class GiangVienRepositoryImpl implements GiangVienRepository {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public GiangVien findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(GiangVien.class, id);
    }
    
        @Override
    public GiangVien findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(GiangVien.class, username);
    }
    
       @Override
    public GiangVien getGiangVienById(int id) {
        Session session = sessionFactory.openSession();
        GiangVien giangVien = session.get(GiangVien.class, id);
        session.close();
        return giangVien;
    }
    
 

    @Override
    public List<GiangVien> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from GiangVien", GiangVien.class).list();
    }

    @Override
    public void save(GiangVien giangVien) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(giangVien);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(GiangVien giangVien) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(giangVien);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        GiangVien giangVien = session.load(GiangVien.class, id);
        if (giangVien != null) {
            session.delete(giangVien);
        }
    }
}
