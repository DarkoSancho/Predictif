package web.vue;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Serialisation {
    public Serialisation() {
    }
    public abstract void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException;
}