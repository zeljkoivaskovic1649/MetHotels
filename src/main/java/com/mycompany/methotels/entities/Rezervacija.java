/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zeljko
 */
@Entity
@Table(name = "rezervacija")
@NamedQueries({
    @NamedQuery(name = "Rezervacija.findAll", query = "SELECT r FROM Rezervacija r"),
    @NamedQuery(name = "Rezervacija.findBySobaId", query = "SELECT r FROM Rezervacija r WHERE r.rezervacijaPK.sobaId = :sobaId"),
    @NamedQuery(name = "Rezervacija.findByKorisnikId", query = "SELECT r FROM Rezervacija r WHERE r.rezervacijaPK.korisnikId = :korisnikId"),
    @NamedQuery(name = "Rezervacija.findByCena", query = "SELECT r FROM Rezervacija r WHERE r.cena = :cena")})
public class Rezervacija implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RezervacijaPK rezervacijaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @JoinColumn(name = "soba_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Soba soba;
    @JoinColumn(name = "korisnik_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korisnik korisnik;

    public Rezervacija() {
    }

    public Rezervacija(RezervacijaPK rezervacijaPK) {
        this.rezervacijaPK = rezervacijaPK;
    }

    public Rezervacija(RezervacijaPK rezervacijaPK, double cena) {
        this.rezervacijaPK = rezervacijaPK;
        this.cena = cena;
    }

    public Rezervacija(int sobaId, int korisnikId) {
        this.rezervacijaPK = new RezervacijaPK(sobaId, korisnikId);
    }

    public RezervacijaPK getRezervacijaPK() {
        return rezervacijaPK;
    }

    public void setRezervacijaPK(RezervacijaPK rezervacijaPK) {
        this.rezervacijaPK = rezervacijaPK;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Soba getSoba() {
        return soba;
    }

    public void setSoba(Soba soba) {
        this.soba = soba;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rezervacijaPK != null ? rezervacijaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rezervacija)) {
            return false;
        }
        Rezervacija other = (Rezervacija) object;
        if ((this.rezervacijaPK == null && other.rezervacijaPK != null) || (this.rezervacijaPK != null && !this.rezervacijaPK.equals(other.rezervacijaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.methotels.entities.Rezervacija[ rezervacijaPK=" + rezervacijaPK + " ]";
    }
    
}
