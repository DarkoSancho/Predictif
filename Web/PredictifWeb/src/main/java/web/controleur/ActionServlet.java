/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controleur;

import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.service.Service;
import web.modele.ConsulterListeMediumsAction;
import web.vue.ConsulterListeMediumsSerialisation;

/**
 *
 * @author otkito
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    
        @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.initialiserFabriqueContextePersistance();
    }

    @Override
    public void destroy() {
        JpaUtil.libererFabriqueContextePersistance();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    
    @Override
    protected void service (HttpServletRequest req, HttpServletResponse res) {
    String todo = req.getParameter("todo");
    System.out.println("Trace : todo = " + todo);
    Service s = new Service();
    switch(todo){
        case "consulter-liste-medium":{
            
            new ConsulterListeMediumsAction(s).executer(req);
            new ConsulterListeMediumsSerialisation().appliquer(req,res) ; 
            System.out.println("Consulter Liste Medium");
            break;
        }
    }
    }
}
