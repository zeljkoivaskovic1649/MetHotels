/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;

import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Zeljko
 */
public class Soba {
    private String imeSobe;
    private String sprat;
    private boolean tv;
    private boolean internet;
    private boolean djakuzi;

    @Inject
    public Soba() {
    }

    public Soba(String imeSobe, String sprat, boolean tv, boolean internet, boolean djakuzi) {
        this.imeSobe = imeSobe;
        this.sprat = sprat;
        this.tv = tv;
        this.internet = internet;
        this.djakuzi = djakuzi;
    }
    
    public Soba(Soba s){
        this.imeSobe = s.getImeSobe();
        this.sprat = s.getSprat();
        this.tv = s.isTv();
        this.internet = s.isInternet();
        this.internet = s.isInternet();
    }

    /**
     * @return the imeSobe
     */
    public String getImeSobe() {
        return imeSobe;
    }

    /**
     * @param imeSobe the imeSobe to set
     */
    public void setImeSobe(String imeSobe) {
        this.imeSobe = imeSobe;
    }

    /**
     * @return the sprat
     */
    public String getSprat() {
        return sprat;
    }

    /**
     * @param sprat the sprat to set
     */
    public void setSprat(String sprat) {
        this.sprat = sprat;
    }

    /**
     * @return the tv
     */
    public boolean isTv() {
        return tv;
    }

    /**
     * @param tv the tv to set
     */
    public void setTv(boolean tv) {
        this.tv = tv;
    }

    /**
     * @return the internet
     */
    public boolean isInternet() {
        return internet;
    }

    /**
     * @param internet the internet to set
     */
    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    /**
     * @return the djakuzi
     */
    public boolean isDjakuzi() {
        return djakuzi;
    }

    /**
     * @param djakuzi the djakuzi to set
     */
    public void setDjakuzi(boolean djakuzi) {
        this.djakuzi = djakuzi;
    }
}