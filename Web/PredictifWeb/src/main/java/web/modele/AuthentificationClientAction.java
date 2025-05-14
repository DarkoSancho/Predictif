/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author otkito
 */
public class AuthentificationClientAction extends Action {

    public AuthentificationClientAction(Service service) {
        super(service);
    }

    
    @Override
    public void executer(HttpServletRequest request) {
        
        Service s = new Service();
        Client client = new Client();
        client = s.authentifierClient( request.getAttribute("login").toString(), request.getAttribute("password").toString());
        if (client)
        request.setAttribute("ligne-message", );
        System.out.println(request.getAttribute("listeMediums"));
    }         

}