/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;


import com.mycompany.methotels.data.KorisnikDao;
import com.mycompany.methotels.entities.Korisnik;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Zeljko
 */
public class RegistracijaKorisnika {
    @Inject
    private KorisnikDao korisnikDao;
    @Property
    private Korisnik korisnik;
    @SessionState
    private Korisnik ulogovaniKorisnik;
    @Component
    private BeanEditForm form;
    
    @CommitAfter
    Object onSuccess(){
        if(!korisnikDao.validanEmail(korisnik.getEmail())){
            korisnik.setRola("User");
            Korisnik k = korisnikDao.registrujKorisnika(korisnik);
            ulogovaniKorisnik = k;
            return Index.class;
        }else{
            form.recordError("Email koji ste uneli vec postoji");
            return null;
        }
    }
}
