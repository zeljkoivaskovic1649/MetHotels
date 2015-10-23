/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.components;

import java.text.DecimalFormat;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 *
 * @author Zeljko
 */
public class Mnozenje {
    @Parameter(required = true)
    @Property
    private Double prviBroj;
    @Parameter(required = true)
    @Property
    private Double drugiBroj;
    
    public String getProizvod(){
        DecimalFormat df = new DecimalFormat("#.##");
        double rezultat = prviBroj * drugiBroj;
        return df.format(rezultat);
    }
}
