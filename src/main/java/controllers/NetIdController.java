package controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/netid")
@Produces(MediaType.APPLICATION_JSON)
public class NetIdController  {


    @GET
    public String getNetID(){
        return "la393";
    }
}
