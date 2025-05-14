/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author otkito
 */
public class ConsulterListeMediumsAction extends Action {

    public ConsulterListeMediumsAction(Service service) {
        super(service);
    }

    
    @Override
    public void executer(HttpServletRequest request) {
        
        Service s = new Service();
        List<Medium> listeMediums = s.listerMediums();
        for (Medium medium : listeMediums) {
            System.out.println(medium);
        }
        request.setAttribute("listeMediums", listeMediums);
        System.out.println(request.getAttribute("listeMediums"));
    }         

}