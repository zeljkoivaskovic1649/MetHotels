/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.Korisnik;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 *
 * @author Zeljko
 */
public class PaginationGrid {
    @Inject
    private KorisnikDao korisnikDao;
    private int size = 20;
    
    Object onActivate(@RequestParameter("page") int page){
        Class<?> act = null;
        int sizeOfAll = korisnikDao.allActiveSizekorisnici();
        List<Korisnik> korisnici = new ArrayList<Korisnik>();
        String response = "<table class=\"navigation\" > <th>\n"
        + " Ime korisnika\n"
        + " </th>\n"
        + " ";
        korisnici = korisnikDao.loadActiveFromTo(size);
        for (Korisnik k : korisnici) {
            response += (" <tr>\n"
            + " <td> " + k.getIme() + "</td>\n"
            + " </tr>");
        }
        response += "</table>";
        float ceil = (float) sizeOfAll / (float) 20;
        int totalPageSizes = (int) Math.ceil(ceil);
        response += "<ul class=\"pagination\">";
        for (int i = 1; i <= totalPageSizes; i++) {
            if (page == i) {
                response += ("<li class=\"callservice active\"><a href=\"#\">" + i + "</a></li>\n");
            } else {
                response += ("<li class=\"callservice\"><a href=\"#\">" + i + "</a></li>\n");
            }
        }
        response += "</ul>";
        return new TextStreamResponse("text/plain", response);
    }
}
