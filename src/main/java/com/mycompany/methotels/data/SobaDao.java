/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.Soba;
import java.util.List;

/**
 *
 * @author Zeljko
 */
public interface SobaDao {
    public List<Soba> getListaSoba();
    public Soba getSobaById(Integer id);
    public void dodajIliIzmeniSobu(Soba soba);
    public void obrisiSobu(Integer id);
    public List<Soba> getListaSobaPoImenu(String ime);
}
