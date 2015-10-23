/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services;

import java.io.IOException;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;

/**
 *
 * @author Zeljko
 */
public interface ComponentRequestFilter {

    void handleComponentEvent(ComponentEventRequestParameters parameters, ComponentRequestHandler handler) throws IOException;

    void handlePageRender(PageRenderRequestParameters parameters, ComponentRequestHandler handler) throws IOException;

    boolean isAuthorisedToPage(String requestedPageName, EventContext eventContext) throws IOException;
    
}
