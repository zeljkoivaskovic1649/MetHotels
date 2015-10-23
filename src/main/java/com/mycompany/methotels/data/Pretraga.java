/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.Korisnik;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 *
 * @author Zeljko
 */
public class Pretraga {
    @Inject
    private Request request;
    @Inject
    private KorisnikDao korisnikDao;
    @Property
    private List<Korisnik> korisnici;
    @Property
    private Korisnik korisnik;
    
    Object onActivate(@RequestParameter("ime") String ime){
        if(korisnici == null){
            korisnici = new ArrayList<Korisnik>();
        }
        String response = "<table class=\"navigation\" > <th>\n"
        + " Ime korisnika\n"
        + " </th>\n"
        + " ";
        korisnici = korisnikDao.getKorisnikByIme(ime);
        for (Korisnik k : korisnici) {
            response += (" <tr>\n"
            + " <td> " + k.getIme() + "</td>\n"
            + " </tr>");
        }
        response += "</table>";
        return new TextStreamResponse("text/plain", response);
    }
}
