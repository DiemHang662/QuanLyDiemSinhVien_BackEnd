package com.btl.repository.impl;

import com.btl.pojo.Diem;
import com.btl.pojo.HocKy;
import com.btl.pojo.LoaiDiem;
import com.btl.pojo.LopHoc;
import com.btl.pojo.MonHoc;
import com.btl.pojo.SinhVien;
import com.btl.repository.DiemRepository;
import java.util.ArrayList;
import java.util.HashMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Join;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
@Transactional
public class DiemRepositoryImpl implements DiemRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    private static final int PAGE_SIZE = 10;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Diem> getAllDiems() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Diem> query = builder.createQuery(Diem.class);
        Root<Diem> root = query.from(Diem.class);
        query.select(root);
        Query<Diem> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<SinhVien> getAllSinhViens() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<SinhVien> query = builder.createQuery(SinhVien.class);
        Root<SinhVien> root = query.from(SinhVien.class);
        query.select(root);
        Query<SinhVien> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<MonHoc> getAllMonHocs() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MonHoc> query = builder.createQuery(MonHoc.class);
        Root<MonHoc> root = query.from(MonHoc.class);
        query.select(root);
        Query<MonHoc> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<LopHoc> getAllLopHocs() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<LopHoc> query = builder.createQuery(LopHoc.class);
        Root<LopHoc> root = query.from(LopHoc.class);
        query.select(root);
        Query<LopHoc> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<HocKy> getAllHocKys() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<HocKy> query = builder.createQuery(HocKy.class);
        Root<HocKy> root = query.from(HocKy.class);
        query.select(root);
        Query<HocKy> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<LoaiDiem> getAllLoaiDiems() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<LoaiDiem> query = builder.createQuery(LoaiDiem.class);
        Root<LoaiDiem> root = query.from(LoaiDiem.class);
        query.select(root);
        Query<LoaiDiem> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Diem> getDiemBySinhVienIdAndMonHocIdAndLopHocIdAndHocKyId(int sinhVienId, int monHocId, int lopHocId, int hocKyId) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Diem> query = builder.createQuery(Diem.class);
        Root<Diem> root = query.from(Diem.class);
        query.select(root);
        Predicate predicateSinhVien = builder.equal(root.get("sinhVien").get("id"), sinhVienId);
        Predicate predicateMonHoc = builder.equal(root.get("monHoc").get("id"), monHocId);
        Predicate predicateHocKy = builder.equal(root.get("lopHoc").get("id"), lopHocId);
        Predicate predicateLopHoc = builder.equal(root.get("hocKy").get("id"), hocKyId);
        query.where(builder.and(predicateSinhVien, predicateMonHoc, predicateLopHoc));
        Query<Diem> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Diem> getDiemByMonHocIdAndLopHocIdAndHocKyId(int monHocId, int lopHocId, int hocKyId) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Diem> query = builder.createQuery(Diem.class);
        Root<Diem> root = query.from(Diem.class);
        query.select(root);
        Predicate predicateMonHoc = builder.equal(root.get("monHoc").get("id"), monHocId);
        Predicate predicateLopHoc = builder.equal(root.get("lopHoc").get("id"), lopHocId);
        Predicate predicateHocKy = builder.equal(root.get("hocKy").get("id"), hocKyId);
        query.where(builder.and(predicateMonHoc, predicateLopHoc, predicateHocKy));
        Query<Diem> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public void saveDiem(Diem diem) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(diem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateDiem(Diem diem) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.update(diem);
        transaction.commit();
    }

    @Override
    public void deleteDiem(int id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Diem diem = session.get(Diem.class, id);
        if (diem != null) {
            session.delete(diem);
        }
        transaction.commit();
    }

    @Override
    public List<Diem> getDiemByLoaiDiemId(int loaiDiemId) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Diem> query = builder.createQuery(Diem.class);
        Root<Diem> root = query.from(Diem.class);
        query.select(root);
        Predicate predicateLoaiDiem = builder.equal(root.get("loaiDiem").get("id"), loaiDiemId);
        query.where(predicateLoaiDiem);
        Query<Diem> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Diem> getDiemBySinhVienId(int sinhVienId) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Diem> query = builder.createQuery(Diem.class);
        Root<Diem> root = query.from(Diem.class);
        query.select(root);
        Predicate predicateSinhVien = builder.equal(root.get("sinhVien").get("id"), sinhVienId);
        query.where(predicateSinhVien);
        Query<Diem> q = session.createQuery(query);
        return q.getResultList();
    }

   @Override
public Double getAverageScoreForStudent(int sinhVienId, int monHocId, int lopHocId, int hocKyId) {
    Session session = sessionFactory.getCurrentSession();  // Using getCurrentSession() for better integration with transactions

    try {
        // HQL truy vấn
        String hql = "SELECT (SUM(CASE WHEN d.loaiDiem.id = 1 THEN d.score ELSE 0 END) + "
                + "       SUM(CASE WHEN d.loaiDiem.id = 2 THEN d.score ELSE 0 END)) / 2.0 "
                + "FROM Diem d "
                + "WHERE d.sinhVien.id = :sinhVienId "
                + "AND d.monHoc.id = :monHocId "
                + "AND d.lopHoc.id = :lopHocId "
                + "AND d.hocKy.id = :hocKyId";

        // Sử dụng Double.class để phù hợp với kiểu dữ liệu trả về của truy vấn
        Query<Double> query = session.createQuery(hql, Double.class);
        query.setParameter("sinhVienId", sinhVienId);
        query.setParameter("monHocId", monHocId);
        query.setParameter("lopHocId", lopHocId);
        query.setParameter("hocKyId", hocKyId);

        Double result = query.uniqueResult();

        // Chuyển đổi kết quả về Double nếu cần thiết
        return result != null ? result : 0.0;
    } catch (Exception e) {
        e.printStackTrace();  // Log the exception
        return 0.0;
    }
}

@Override
public List<Object[]> getAllAverageScores(int monHocId, int lopHocId, int hocKyId) {
    Session session = sessionFactory.openSession();
    List<Object[]> results = new ArrayList<>();

    try {
        // HQL query to get average scores
        String hql = "SELECT d.sinhVien.id, d.sinhVien.name, "
                   + "(SUM(CASE WHEN d.loaiDiem.id = 1 THEN d.score ELSE 0 END) + "
                   + "SUM(CASE WHEN d.loaiDiem.id = 2 THEN d.score ELSE 0 END)) / 2.0 "
                   + "FROM Diem d "
                   + "WHERE d.monHoc.id = :monHocId "
                   + "AND d.lopHoc.id = :lopHocId "
                   + "AND d.hocKy.id = :hocKyId "
                   + "GROUP BY d.sinhVien.id, d.sinhVien.name";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("monHocId", monHocId);
        query.setParameter("lopHocId", lopHocId);
        query.setParameter("hocKyId", hocKyId);

        results = query.getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        // Handle exception as needed
    } finally {
        session.close();
    }

    return results;
}


@Override
public List<Object[]> getHighestAverageScoresByClass(int monHocId, int hocKyId) {
    Session session = sessionFactory.openSession();
    List<Object[]> results = new ArrayList<>();

    try {
        String hql = "SELECT d.lopHoc.id, d.lopHoc.name, "
                   + "(SUM(CASE WHEN d.loaiDiem.id = 1 THEN d.score ELSE 0 END) + "
                   + "SUM(CASE WHEN d.loaiDiem.id = 2 THEN d.score ELSE 0 END)) / 2.0 AS averageScore "
                   + "FROM Diem d "
                   + "WHERE d.monHoc.id = :monHocId AND d.hocKy.id = :hocKyId "
                   + "GROUP BY d.lopHoc.id, d.lopHoc.name "
                   + "ORDER BY averageScore DESC";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("monHocId", monHocId);
        query.setParameter("hocKyId", hocKyId);

        results = query.getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        // Handle exception as needed
    } finally {
        session.close();
    }

    return results;
}


}
