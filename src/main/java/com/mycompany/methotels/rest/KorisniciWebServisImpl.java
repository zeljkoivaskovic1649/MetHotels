/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.rest;


import com.mycompany.methotels.data.KorisnikDao;
import com.mycompany.methotels.entities.Korisnik;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Zeljko
 */
public class KorisniciWebServisImpl implements KorisniciWebServis{
    @Inject
    private KorisnikDao korisnikDao;

    @Override
    public List<Korisnik> getAll() {
        return (korisnikDao.getListaKorisnika());
    }

    @Override
    public Korisnik getKorisnik(Integer id) {
        return korisnikDao.getKorisnikById(id);
    }

    @Override
    public Response post(Korisnik korisnik) {
        korisnikDao.registrujKorisnika(korisnik);
        return Response.ok().entity(korisnik).build();
    }
    
}
