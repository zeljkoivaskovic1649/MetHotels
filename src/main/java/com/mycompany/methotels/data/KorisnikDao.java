/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.Korisnik;
import java.util.List;

/**
 *
 * @author Zeljko
 */
public interface KorisnikDao {
    public List<Korisnik> getListaKorisnika();
    public Korisnik getKorisnikById(Integer id);
    public void obrisiKorisnika(Integer id);
    public Korisnik registrujKorisnika(Korisnik korisnik);
    public Korisnik loginKorisnika(String username, String password);;
    public boolean validanEmail(String email);
    public List<Korisnik> getKorisnikByIme(String ime);
    public abstract int allActiveSizekorisnici();
    public abstract List<Korisnik> loadActiveFromTo(int from);
}
