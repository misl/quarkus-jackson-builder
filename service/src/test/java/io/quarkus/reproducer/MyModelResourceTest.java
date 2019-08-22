package io.quarkus.reproducer;

import io.quarkus.reproducer.jacksonbuilder.model.MyModel;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class MyModelResourceTest {

  @Test
  public void testHelloEndpoint() throws IOException {
    MyModel model = new MyModel.Builder( "123" )
        .withVersion( 3 )
        .withValue( "some" )
        .build();

    given()
        .contentType( "application/json" )
        .body( model.toJson() )
        .when().post( "/model" )
        .then()
        .statusCode( 201 )
        .body( "id", equalTo( "123" ) )
        .body( "version", equalTo( 3 ) )
        .body( "value", equalTo( "some" ) );
  }

}