/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.Korisnik;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Zeljko
 */
public class KorisnikDaoImpl implements KorisnikDao{
    @Inject
    private Session session;

    @Override
    public List<Korisnik> getListaKorisnika() {
        return session.createCriteria(Korisnik.class).list();
    }

    @Override
    public Korisnik getKorisnikById(Integer id) {
        return (Korisnik) session.createCriteria(Korisnik.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void obrisiKorisnika(Integer id) {
        Korisnik korisnik = (Korisnik) session.createCriteria(Korisnik.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.delete(korisnik);
    }
    
    @Override
    public Korisnik registrujKorisnika(Korisnik korisnik) {
        return (Korisnik) session.merge(korisnik);
    }

    @Override
    public Korisnik loginKorisnika(String username, String password) {
        try{
            Korisnik k = (Korisnik) session.createCriteria(Korisnik.class).add(Restrictions.eq("username", username)).
                    add(Restrictions.eq("password", password)).uniqueResult();
            if(k != null){
                return k;
            }
            return null;
        }catch(NullPointerException e){
            return null;
        }
    }
    
    @Override
    public boolean validanEmail(String email) {
        Long rezultati = (Long)session.createCriteria(Korisnik.class).add(Restrictions.eq("email", email)).setProjection(Projections.rowCount()).uniqueResult();
        return (rezultati==0) ? false : true;
    }
    
    @Override
    public List<Korisnik> getKorisnikByIme(String ime) {
        return session.createCriteria(Korisnik.class).add(Restrictions.like("ime", ime + "%")).list();
    }
    
    @Override
    public int allActiveSizekorisnici() {
        Long l = (Long) session.createCriteria(Korisnik.class).setProjection(Projections.rowCount()).uniqueResult();
        return l.intValue();
    }

    @Override
    public List<Korisnik> loadActiveFromTo(int from) {
        int page = (from - 1) * 20;
        List<Korisnik> korisnici = session.createCriteria(Korisnik.class).setFirstResult(page).setMaxResults(20)
                .addOrder(Order.asc("id")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return korisnici;
    }
}
