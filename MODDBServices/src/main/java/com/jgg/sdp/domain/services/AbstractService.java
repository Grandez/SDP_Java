/**
 * Clase base de los de servicios de acceso a datos 
 * <br>
 * Contiene los metodos de la arquitetura JPA de manera
 * que ningun servicio accede directamente al EntityManager
 * <br>
 * Los metodos definidos en este clase solo son visibles a traves de los
 * correspondientes servicios
 *  
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services;

import java.lang.reflect.*;
import java.util.*;

import javax.persistence.*;

import org.hibernate.Session;

import com.jgg.sdp.core.exceptions.DBException;
import com.jgg.sdp.domain.DBCache;
import com.jgg.sdp.domain.DBManagerFactory;

public abstract class AbstractService <T>  {

    protected static long begin = System.currentTimeMillis();
    protected EntityManager em  = null;
    protected Query         qry = null;
    protected EntityTransaction tx = null;
    
    protected DBCache<T> dbCache = new DBCache<T>();
    
    protected String tableName = "";
    
    @SuppressWarnings("unchecked")
	public AbstractService() {
        Class<T> base; 
        
        base = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        String fullName = base.toString().substring(6);
        tableName = fullName.substring(fullName.lastIndexOf('.') + 1);
        
        em = DBManagerFactory.getInstance().getEntityManager();
    }
    
//    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
       em = entityManager;
    }

    public void beginTrans() {
        begin = System.currentTimeMillis();
    	if (tx == null) {
    		tx = DBManagerFactory.getInstance().getTransaction();
    	}
		if (tx.isActive() == false) tx.begin();
    }
    
    public void commitTrans() {
    	if (tx == null) tx = em.getTransaction();
        
    	if (tx.isActive() == false) return;
		tx.commit();
		Session session = em.unwrap(org.hibernate.Session.class);
		session.clear();
    }

    public void rollbackTrans() {
    	if (tx == null) tx = em.getTransaction();     		
    	if (tx.isActive() == false) return;
		tx.rollback();
    }
    
    public boolean update(T t) {
    	try {
           em.persist(t);
    	}
    	catch (PersistenceException e) {
//    		e.printStackTrace();
    		rollbackTrans();
    		throw new DBException(e);
    	}
    	return false;
    }
    
    public boolean updateEntity(Object o) {
    	em.persist(o);
    	return false;
    }
    
    public void flush() {
        em.flush();
    }
    
    public void remove(T t) {
        em.remove(t);
    }

    public void delete(Object... keys) {
        Query qry = em.createNamedQuery(tableName + ".delete");
        addParameters(qry, keys);
        qry.executeUpdate();
    }
    
    protected void deleteQuery(String sqlStmt, Object... keys) {
        Query qry = em.createQuery(sqlStmt);
        addParameters(qry, keys);
        qry.executeUpdate();        
    }
    
    protected Long getValue(String qryName, Object... keys) {
        Query qry = em.createNamedQuery(tableName + "." + qryName);
        addParameters(qry, keys);
        Object o = qry.getSingleResult();
        return (o == null) ? 0L : ((Number) o).longValue();
    }
    
	protected T find (String qryName, Object... keys) {
        Query query = em.createNamedQuery(tableName + "." + qryName);
        return findQuery(query, keys);
    }

	protected T findQuery (String qryName, Object... keys) {
        Query query = em.createQuery(qryName);
        return findQuery(query, keys);
    }

	protected List<T> lista(String qryName, Object... keys) {
		Query query = em.createQuery(qryName);
		return listQuery(query, keys);

	}
	
	protected List<T> list(String qryName, Object... keys) {
	    return list(null, qryName, keys);
	}

	protected List<T> lista(Object... keys) {
	    return list("list", keys);
	}
	
	protected List<T> list(Integer max, String qryName, Object... keys) {
		Query query = em.createNamedQuery(tableName + "." + qryName);
		if (max != null) query.setMaxResults(max);
		return listQuery(query, keys);
	}

    @SuppressWarnings("unchecked")
    protected List<Object[]> listAbstract(String qryName, Object... keys) {
        Query query = em.createNamedQuery(tableName + "." + qryName);
        for (int idx = 0; idx < keys.length; idx++) {
            query.setParameter(idx+1, keys[idx]);    
        }
        return query.getResultList();
    }
	
	protected int updateQuery(String qryName, Object... keys) {
		Query query = em.createNamedQuery(tableName + "." + qryName);
        for (int idx = 0; idx < keys.length; idx++) {
            query.setParameter(idx+1, keys[idx]);    
        }
		return query.executeUpdate();
	}

    private T findQuery(Query query, Object... keys) {
        List<T> results = listQuery(query, keys);
        T o = null;
        if(!results.isEmpty()) {
            o = results.get(0);
        }
        return o;
    }
    
    protected T getRecord(String qryStmt, Object... keys) {
 	    Query qry = em.createQuery(qryStmt);
    	return findQuery(qry, keys);
    }
    
    protected List<T> getList(String qryStmt, Object... keys) {
    	Query qry = em.createQuery(qryStmt);
    	return listQuery(qry, keys);
    }

    @SuppressWarnings("unchecked")
	protected List<Object[]> getListAbstract(String stmt, Object ...keys) {
    	Query qry = em.createQuery(stmt);
        for (int idx = 0; idx < keys.length; idx++) {
            qry.setParameter(idx+1, keys[idx]);    
        }
        return (List<Object[]>) qry.getResultList();
    }

    protected Object[] getRecordAbstract(String stmt, Object ...keys) {
        Query qry = em.createQuery(stmt);
        for (int idx = 0; idx < keys.length; idx++) {
            qry.setParameter(idx+1, keys[idx]);    
        }
        return (Object[]) qry.getSingleResult();
    }

    @SuppressWarnings("unchecked")
	protected List<Object[]> nativeQuery(String stmt, Object... keys) {
    	Query qry = em.createNativeQuery(stmt);
        for (int idx = 0; idx < keys.length; idx++) {
            qry.setParameter(idx+1, keys[idx]);    
        }
        return (List<Object[]>) qry.getResultList();
    }

    protected void sqlExecute(String stmt, Object... keys) {
        Query qry = em.createNativeQuery(stmt);
        addParameters(qry, keys);
        qry.executeUpdate();
    }

	
    @SuppressWarnings("unchecked")
    protected List<Object> nativeQueryItem(String stmt, Object... keys) {
        Query qry = em.createNativeQuery(stmt);
        for (int idx = 0; idx < keys.length; idx++) {
            qry.setParameter(idx+1, keys[idx]);    
        }
        return qry.getResultList();
    }
	
    @SuppressWarnings("unchecked")
	protected List<T> listQuery(Query query, Object... keys) {
        for (int idx = 0; idx < keys.length; idx++) {
            if (keys[idx] != null) query.setParameter(idx+1, keys[idx]);    
        }
        return (List<T>) query.getResultList();
    }
    
    private void addParameters(Query qry, Object... parms) {
    	if (parms == null) return;
        for (int idx = 0; idx < parms.length; idx++) {
            qry.setParameter(idx+1, parms[idx]);    
        }
    }
    
}
