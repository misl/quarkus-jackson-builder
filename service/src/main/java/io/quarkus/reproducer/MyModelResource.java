package io.quarkus.reproducer;

import io.quarkus.reproducer.jacksonbuilder.model.MyModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/model")
public class MyModelResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response newModel( String body ) throws IOException {
    MyModel model = MyModel.fromJson( body );
    return Response.status( 201 ).entity( model.toJson() ).build();
  }
}