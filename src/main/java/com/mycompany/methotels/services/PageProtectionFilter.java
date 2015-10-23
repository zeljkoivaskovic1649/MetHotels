/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.services;


import com.mycompany.methotels.entities.Korisnik;
import com.mycompany.methotels.pages.Index;
import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.internal.EmptyEventContext;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

/**
 *
 * @author Zeljko
 */
public class PageProtectionFilter implements ComponentRequestFilter {
    private final String autoLoginStr = System.getProperty("jumpstart.auto-login");
    private final PageRenderLinkSource pageRenderLinkSource;
    private final ComponentSource componentSource;
    private final Request request;
    private final Response response;
    private ApplicationStateManager sessionStateManager;
    private final Logger logger;
    
    public PageProtectionFilter(PageRenderLinkSource pageRenderLinkSource, ComponentSource
    componentSource, Request request, Response response, ApplicationStateManager asm, Logger logger) {
        this.pageRenderLinkSource = pageRenderLinkSource;
        this.request = request;
        this.response = response;
        this.componentSource = componentSource;
        this.sessionStateManager = asm;
        this.logger = logger;
    }
    
    @Override
    public void handlePageRender(PageRenderRequestParameters parameters,
    ComponentRequestHandler handler) throws IOException {
        if (isAuthorisedToPage(parameters.getLogicalPageName(), parameters.getActivationContext())) {
            handler.handlePageRender(parameters);
    } else {
// The method will have redirected us to the Login page or the PageDenied page
            return;
        }
    }
    
    @Override
    public void handleComponentEvent(ComponentEventRequestParameters parameters,
    ComponentRequestHandler handler) throws IOException {
        if (isAuthorisedToPage(parameters.getActivePageName(), parameters.getEventContext())) {
            handler.handleComponentEvent(parameters);
        } else {
// The method will have redirected us to the Login page or the PageDenied page
            return;
        }
    }
    
    @Override
    public boolean isAuthorisedToPage(String requestedPageName, EventContext eventContext) throws
    IOException {
// Does the page have security annotations @ProtectedPage or @RolesAllowed?
        org.apache.tapestry5.runtime.Component page = componentSource.getPage(requestedPageName);
        boolean protectedPage = page.getClass().getAnnotation(ProtectedPage.class) != null;
        RolesAllowed rolesAllowed = page.getClass().getAnnotation(RolesAllowed.class);
        // If the security annotations on the page conflict in meaning, then error
        if (rolesAllowed != null && !protectedPage) {
            throw new IllegalStateException("Page \"" + requestedPageName
            + "\" is annotated with @RolesAllowed but not @ProtectedPage.");
        }
// If page is public (ie. not protected), then everyone is authorised to it so allow access
        if (!protectedPage) {
            return true;
} // Else if request is AJAX with no session, return an AJAX response that forces reload of the page
        else if (request.isXHR() && request.getSession(false) == null) {
            OutputStream os = response.getOutputStream("application/json;charset=UTF-8");
            os.write("{\"script\":\"window.location.reload();\"}".getBytes());
            os.flush();
            return false;
        }
        else if (isAuthenticated()) {
        // If user is authorised to the page, then all is well so allow access
            if (isAuthorised(rolesAllowed)) {
                return true;
            } // Else, redirect to the PageDenied page
            else {
                Link pageProtectedLink = pageRenderLinkSource.createPageRenderLinkWithContext(Index.class, requestedPageName);
                response.sendRedirect(pageProtectedLink);
                return false;
            }
        } // Else... go to the Login page
            else {
                Link loginPageLink = pageRenderLinkSource.createPageRenderLink(Index.class);
                response.sendRedirect(loginPageLink);
                return false;
            }
    }
    
    private Link makeLinkToRequestedPage(String requestedPageName, EventContext eventContext) {
        Link linkToRequestedPage;
        if (eventContext instanceof EmptyEventContext) {
            linkToRequestedPage = pageRenderLinkSource.createPageRenderLink(requestedPageName);
        } else {
            Object[] args = new String[eventContext.getCount()];
            for (int i = 0; i < eventContext.getCount(); i++) {
                args[i] = eventContext.get(String.class, i);
            }
            linkToRequestedPage = pageRenderLinkSource.createPageRenderLinkWithContext(requestedPageName, args);
        }
        return linkToRequestedPage;
    }
    
    private boolean isAuthenticated() throws IOException {
        Korisnik korisnik = sessionStateManager.getIfExists(Korisnik.class);
        if (korisnik != null) {
            if (korisnik.getRola() == "Admin" || korisnik.getRola() == "User") {
                return true;
            }
        }
        return false;
    }
    
    private boolean isAuthorised(RolesAllowed rolesAllowed) throws IOException {
        boolean authorised = false;
        if (rolesAllowed == null) {
            authorised = true;
        } else {
        // Here we could check whether the user's role, or perhaps roles, include one of the rolesAllowed.
        // Typically we'd cache the user's roles in the Visit.
            Korisnik korisnik = sessionStateManager.getIfExists(Korisnik.class);
            String rola = korisnik.getRola();
            for (String i : rolesAllowed.value()) {
                if (i.equalsIgnoreCase(rola)) {
                    authorised = true;
                }
            }
        }
        return authorised;
    }
}
