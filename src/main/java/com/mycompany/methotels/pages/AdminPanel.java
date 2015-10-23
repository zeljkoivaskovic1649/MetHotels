/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;


import com.mycompany.methotels.data.KorisnikDao;
import com.mycompany.methotels.data.SobaDao;
import com.mycompany.methotels.entities.Korisnik;
import com.mycompany.methotels.entities.Soba;
import com.mycompany.methotels.services.ProtectedPage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Zeljko
 */
@ProtectedPage
@RolesAllowed(value={"Admin"})
public class AdminPanel {
    @Property
    private List<Soba> sobe;
    @Property
    private Soba soba;
    @Property
    private List<Korisnik> korisnici;
    @Property
    private Korisnik korisnik;
    @Inject
    private KorisnikDao korisnikDao;
    @Inject
    private SobaDao sobaDao;
    
    void onActivate(){
        if(sobe == null){
            sobe = new ArrayList<Soba>();
        }
        if(korisnici == null){
            korisnici = new ArrayList<Korisnik>();
        }
        sobe = sobaDao.getListaSoba();
        korisnici = korisnikDao.getListaKorisnika();
    }
    
    @CommitAfter
    Object onSuccessFromSoba(){
        sobaDao.dodajSobu(soba);
        return this;
    }
    
    @CommitAfter
    Object onSuccessFromKorisnik(){
        korisnikDao.registrujKorisnika(korisnik);
        return this;
    }
}
