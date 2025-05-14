/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Medium;

/**
 *
 * @author otkito
 */
public class ConsulterListeMediumsSerialisation extends Serialisation {
    
     public ConsulterListeMediumsSerialisation() {
    }

    
    
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            out = response.getWriter();
            List<Medium> mediums = (List<Medium>)request.getAttribute("listeMediums");
            // Todo : Fabriquer du JSON et l'écrire dans out
            // en utilisant la librairie Gson :
            // -> Gson (méthodes toJson et fromJson)
            // -> JsonObject, JsonArray
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonContainer = new JsonObject();
            JsonArray jsonListeMediums = new JsonArray();
            for (Medium medium : mediums) {
                JsonObject jsonMedium = new JsonObject();
                jsonMedium.addProperty("id", medium.getId());
                jsonMedium.addProperty("denomination", medium.getDenomination());
                jsonMedium.addProperty("presentation", medium.getPresentation());
                System.out.println(medium.getPresentation());

                jsonMedium.addProperty("genre", medium.getGenre().toString());
                
                jsonListeMediums.add(jsonMedium);
            }   jsonContainer.add("Mediums", jsonListeMediums);
            JsonObject jsonResultat = new JsonObject();
            jsonResultat.add("container", jsonContainer);
            out.println(gson.toJson(jsonContainer));
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ConsulterListeMediumsSerialisation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }

    }   
}


