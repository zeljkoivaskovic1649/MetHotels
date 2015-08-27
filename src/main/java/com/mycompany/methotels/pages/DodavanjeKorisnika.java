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
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Zeljko
 */
public class DodavanjeKorisnika {
    @Property
    private Korisnik korisnik;
    @Property
    private Korisnik onekorisnik;
    @Property
    private List<Korisnik> korisnici;
    @Inject
    private KorisnikDao korisnikDao;
    @Inject
    private SobaDao sobaDao;
    @Property
    private Soba sobaId;
    @Property
    @Persist
    private List<Soba> sobe;
    public ValueEncoder getEncoder(){
        return new ValueEncoder<Soba>(){

            @Override
            public String toClient(Soba v) {
                return String.valueOf(v.getId());
            }

            @Override
            public Soba toValue(String string) {
                Soba s = sobaDao.getSobaById(Integer.parseInt(string));
                return s;
            }
            
        };
    }

    
    void onActivate(){
        if(korisnici == null){
            korisnici = new ArrayList<Korisnik>();
        }
        korisnici = korisnikDao.getListaKorisnika();
        sobe = sobaDao.getListaSoba();
    }
    
    @CommitAfter
    Object onSuccess(){
        korisnik.setSobaId(sobaId);
        korisnikDao.dodajKorisnika(korisnik);
        return this;
    }
    
    Object onActionFromDelete(int id){
        korisnikDao.obrisiKorisnika(id);
        return this;
    }
}