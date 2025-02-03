package com.btl.repository.impl;

import com.btl.pojo.NguoiDung;
import com.btl.repository.NguoiDungRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Repository
@Transactional
public class NguoiDungRepositoryImpl implements NguoiDungRepository {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public NguoiDung getUserByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<NguoiDung> query = session.createQuery(
                "FROM NguoiDung u WHERE u.username = :username", NguoiDung.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }
 @Override
    public boolean authUser(String username, String password) {
        NguoiDung  u = this.getUserByUsername(username);
        
        return this.passEncoder.matches(password, u.getPassword());
    }
}
