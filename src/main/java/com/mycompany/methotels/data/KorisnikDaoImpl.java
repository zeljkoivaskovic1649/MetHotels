/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.Korisnik;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
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
    public void dodajKorisnika(Korisnik korisnik) {
        session.persist(korisnik);
    }

    @Override
    public void obrisiKorisnika(Integer id) {
        Korisnik korisnik = (Korisnik) session.createCriteria(Korisnik.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.delete(korisnik);
    }
    
}
