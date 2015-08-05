/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;

import com.mycompany.methotels.data.Soba;
import java.util.ArrayList;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

/**
 *
 * @author Zeljko
 */
public class DodavanjeSobe {
    @Persist
    @Property
    private ArrayList<Soba> sobe;
    @Property
    private Soba soba;
    
    void onActivate(){
        if(sobe == null){
            sobe = new ArrayList<Soba>();
        }
    }
    
    Object onSuccess(){
        sobe.add(soba);
        return this;
    }
}