package web.modele;

import javax.servlet.http.HttpServletRequest;
import metier.service.Service;

public abstract class Action {
    protected Service service;
    public Action(Service service) {
        this.service = service;
    }

    public abstract void executer(HttpServletRequest request);
}