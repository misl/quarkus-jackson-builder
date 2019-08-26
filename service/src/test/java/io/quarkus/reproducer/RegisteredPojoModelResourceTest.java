package io.quarkus.reproducer;

import io.quarkus.reproducer.jacksonbuilder.model.RegisteredPojoModel;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class RegisteredPojoModelResourceTest {

  @Test
  public void testSimplePojoModel() throws IOException {
    RegisteredPojoModel model = new RegisteredPojoModel();
    model.setId( "123" );
    model.setVersion( 3 );
    model.setValue( "some" );

    given()
        .contentType( "application/json" )
        .body( model.toJson() )
        .when().post( "/registeredpojomodel" )
        .then()
        .statusCode( 201 )
        .body( "id", equalTo( "123" ) )
        .body( "version", equalTo( 3 ) )
        .body( "value", equalTo( "some" ) );
  }
}