/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.data;


import com.mycompany.methotels.entities.Soba;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.got5.tapestry5.jquery.components.InPlaceEditor;

/**
 *
 * @author Zeljko
 */
public class DodavanjeSobe {
    @Inject
    private SobaDao sobaDao;
    @Property
    @Persist
    private Soba soba;
    @Property
    private Soba onesoba;;
    @Property
    private List<Soba> sobe;
    @InjectComponent
    private Zone zoneSoba;
    @InjectComponent
    private Zone formZone;
    @Inject
    private Request request;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @Inject
    private ComponentResources _componentResources;
    
    void onActivate(){
        sobe = sobaDao.getListaSoba();
    }
    
    @CommitAfter
    Object onSuccess() {
        sobaDao.dodajIliIzmeniSobu(soba);
        sobe = sobaDao.getListaSoba();
        soba = new Soba();
        if(request.isXHR()){
            ajaxResponseRenderer.addRender(zoneSoba).addRender(formZone);
        }
        return request.isXHR() ? zoneSoba.getBody() : null;
    }
    
    @CommitAfter
    Object onActionFromDelete(int id) {
        sobaDao.obrisiSobu(id);
        sobe = sobaDao.getListaSoba();
        return request.isXHR() ? zoneSoba.getBody() : null;
    }
    
    @CommitAfter
    Object onActionFromEdit(Soba soba) {
        this.soba = soba;
        return request.isXHR() ? formZone.getBody() : null;
    }
    
    @CommitAfter
    @OnEvent(component = "imeSobe", value = InPlaceEditor.SAVE_EVENT)
    void setImeDrzave(Long id, String value) {
        Soba soba = (Soba) sobaDao.getSobaById(id.intValue());
        soba.setImeSobe(value);
        System.out.println("Cuvanje sobe");
        sobaDao.dodajIliIzmeniSobu(soba);
    }
    
    @CommitAfter
    @OnEvent(component = "sprat", value = InPlaceEditor.SAVE_EVENT)
    void setSprat(Long id, String value) {
        Soba soba = (Soba) sobaDao.getSobaById(id.intValue());
        soba.setSprat(value);
        System.out.println("Cuvanje sobe");
        sobaDao.dodajIliIzmeniSobu(soba);
    }
}
