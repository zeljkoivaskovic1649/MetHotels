/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.AbstractEntity;
import java.util.Collections;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Zeljko
 */
public class GenericDaoImpl<T extends AbstractEntity> implements GenericDao<T>{
    @Inject
    private Session session;

    @Override
    public T merge(T obj) {
        return (T)session.merge(obj);
    }

    @Override
    public T delete(Integer id, Class klasa) {
        AbstractEntity tmpEntity = (AbstractEntity) session.createCriteria(klasa).add(Restrictions.eq("id",
        id)).list().get(0);
        session.delete((T) tmpEntity);
        session.flush();
        return (T) tmpEntity;
    }

    @Override
    public List<T> loadAllActive(Class klasa) {
        List<T> lista = session.createCriteria(klasa).setResultTransformer(
        Criteria.DISTINCT_ROOT_ENTITY).list();
        Collections.sort(lista);
        return lista;
    }

    @Override
    public T getElementById(Integer id, Class klasa) {
        AbstractEntity tmpEntity = (AbstractEntity) session.createCriteria(klasa).add(Restrictions.eq("id",
        id)).list().get(0);
        return (T) tmpEntity;
    }
    
}
