/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zeljko
 */
@Embeddable
public class RezervacijaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "soba_id")
    private int sobaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnik_id")
    private int korisnikId;

    public RezervacijaPK() {
    }

    public RezervacijaPK(int sobaId, int korisnikId) {
        this.sobaId = sobaId;
        this.korisnikId = korisnikId;
    }

    public int getSobaId() {
        return sobaId;
    }

    public void setSobaId(int sobaId) {
        this.sobaId = sobaId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) sobaId;
        hash += (int) korisnikId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RezervacijaPK)) {
            return false;
        }
        RezervacijaPK other = (RezervacijaPK) object;
        if (this.sobaId != other.sobaId) {
            return false;
        }
        if (this.korisnikId != other.korisnikId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.methotels.entities.RezervacijaPK[ sobaId=" + sobaId + ", korisnikId=" + korisnikId + " ]";
    }
    
}
