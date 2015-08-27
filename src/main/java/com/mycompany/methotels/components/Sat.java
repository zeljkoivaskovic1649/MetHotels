/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.components;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Zeljko
 */
public class Sat {
    
    public String getSat(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date datum = new Date();
        String sat = sdf.format(datum);
        return sat;
    }
}
