/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.rest;


import com.mycompany.methotels.data.SobaDao;
import com.mycompany.methotels.entities.Soba;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Zeljko
 */
public class SobeWebServisImpl implements SobeWebServis{
    @Inject
    private SobaDao sobaDao;

    @Override
    public List<Soba> getAll() {
        return (sobaDao.getListaSoba());
    }

    @Override
    public Soba getSoba(Integer id) {
        return sobaDao.getSobaById(id);
    }

    @Override
    public Response post(Soba soba) {
        sobaDao.dodajIliIzmeniSobu(soba);
        return Response.ok().entity(soba).build();
    }
    
}
