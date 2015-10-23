package com.mycompany.methotels.pages;

import com.mycompany.methotels.data.KorisnikDao;
import com.mycompany.methotels.entities.Korisnik;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class Login
{@Inject
    private KorisnikDao korisnikDao;
    @Property
    private Korisnik korisnik;
    @SessionState
    private Korisnik ulogovaniKorisnik;
    @Component
    private BeanEditForm form;
    
    
    Object onActivate(){
        if(ulogovaniKorisnik.getUsername() != null){
            return Index.class;
        }
        return null;
    }
    
    Object onSucces(){
        String username = korisnik.getUsername();
        String password = korisnik.getPassword();
        Korisnik k = korisnikDao.loginKorisnika(username, password);
        if(k != null){
            ulogovaniKorisnik = k;
            return Index.class;
        }else{
            form.recordError("Uneli ste pogresne podatke");
            return null;
        }
    }

}
