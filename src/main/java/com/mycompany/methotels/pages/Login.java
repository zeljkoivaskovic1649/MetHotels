package com.mycompany.methotels.pages;

import com.mycompany.methotels.data.KorisnikDao;
import com.mycompany.methotels.entities.Korisnik;
import com.mycompany.methotels.services.FacebookService;
import com.mycompany.methotels.services.FacebookServiceInformation;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import java.io.IOException;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.tynamo.security.services.SecurityService;

public class Login{
    @Inject
    private SecurityService securityService;
    @Inject
    private KorisnikDao korisnikDao;
    @Property
    private Korisnik korisnik;
    @SessionState
    private Korisnik ulogovaniKorisnik;
    @Component
    private BeanEditForm form;
    @Inject
    private FacebookService facebookService;
    @SessionState
    @Property
    private FacebookServiceInformation facebookServiceInformation;
    @SessionState
    @Property
    private FacebookServiceInformation information;
    @Property
    private com.restfb.types.User userfb;
    @Property
    @ActivationRequestParameter
    private String code;
    
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
            Subject currentUser = securityService.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(k.getUsername(), ulogovaniKorisnik.getPassword());
            try {
                currentUser.login(token);
            } catch (Exception e) {
                form.recordError("Uneli ste pogrešne parametre");
            }
            return Index.class;
        }else{
            form.recordError("Uneli ste pogresne podatke");
            return null;
        }
    }
    
    public String getFacebookAuthentificationLink() throws OAuthSystemException {
        return facebookService.getFacebookAuthentificationLink();
    }
    
    @CommitAfter
    public boolean isLoggedInFb() {
        if (facebookServiceInformation.getAccessToken() != null) {
            Korisnik fbuser = new Korisnik(userfb.getEmail(), " ", "User", userfb.getId());
            Korisnik exist = null;
            System.out.println("proverava");
            exist = korisnikDao.checkIfFbExists(userfb.getId());
            if (exist == null) {
                korisnikDao.registrujKorisnika(fbuser);
                ulogovaniKorisnik = fbuser;
                System.out.println("registruje");
            } else {
                ulogovaniKorisnik = exist;
                System.out.println("postoji");
            }
        }
        return facebookServiceInformation.getAccessToken() != null;
    }
    
    @SetupRender
    public void setup() throws IOException, OAuthSystemException, OAuthProblemException {
        if (code != null) {
            facebookService.getUserAccessToken(code, information.getAccessToken());
        }
        code = null;
        FacebookClient facebookClient = new DefaultFacebookClient(information.getAccessToken());
        if (information.isLoggedIn()) {
            userfb = facebookClient.fetchObject("me", com.restfb.types.User.class);
        }
    }
}
