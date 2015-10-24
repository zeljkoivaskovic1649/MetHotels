/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services;

import com.mycompany.methotels.entities.Korisnik;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Zeljko
 */
public class UserRealm extends AuthorizingRealm{
    private Session session;
    
    public UserRealm(Session session) {
        super(new MemoryConstrainedCacheManager());
        setName("localaccounts");
        this.session = session;
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        if (pc == null) {
            throw new AuthorizationException("Greska");
        }
        if (pc.isEmpty()) {
            return null;
        }
        if (pc.fromRealm(getName()).size() <= 0) {
            return null;
        }
        
        String username = (String) pc.fromRealm(getName()).iterator().next();
        System.out.println("Username je" + username);
        if (username == null) {
            return null;
        }
        
        Korisnik korisnik = getKorisnikByUsername(username);
        if (korisnik == null) {
            return null;
        }
        Set<String> roles = new HashSet<String>(1);
        roles.add(korisnik.getRola());
        return new SimpleAuthorizationInfo(roles);
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        Korisnik korisnik = loginKorisnika(username, upToken.getPassword().toString());
        if (korisnik == null) {
            System.out.println("Korisnik ne postoji");
            throw new IncorrectCredentialsException();
        }
        Set<String> roles = new HashSet<String>(1);
        roles.add(korisnik.getRola());
        return new SimpleAuthenticationInfo(korisnik.getEmail(), new String(korisnik.getPassword()), getName());
    }
    
    private Korisnik loginKorisnika(String username, String password) {
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
    
    private Korisnik getKorisnikByUsername(String username) {
        List<Korisnik> korisnici = session.createCriteria(Korisnik.class).add(Restrictions.eq("username", username)).list();
        if(korisnici.size() > 0){
            return korisnici.get(0);
        }else{
            return null;
        }
    }
}
