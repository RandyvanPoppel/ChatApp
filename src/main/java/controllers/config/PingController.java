package controllers.config;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("ping")
public class PingController {
    @GET
    public String ping() {
        System.out.println("Pinged successfully");
        return "Pinged successfully";
    }
}
