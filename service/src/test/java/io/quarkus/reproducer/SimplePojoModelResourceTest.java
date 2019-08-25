package io.quarkus.reproducer;

import io.quarkus.reproducer.jacksonbuilder.model.SimplePojoModel;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class SimplePojoModelResourceTest {

  @Test
  public void testSimplePojoModel() throws IOException {
    SimplePojoModel model = new SimplePojoModel();
    model.setId( "123" );
    model.setVersion( 3 );
    model.setValue( "some" );

    given()
        .contentType( "application/json" )
        .body( model.toJson() )
        .when().post( "/simplepojomodel" )
        .then()
        .statusCode( 201 )
        .body( "id", equalTo( "123" ) )
        .body( "version", equalTo( 3 ) )
        .body( "value", equalTo( "some" ) );
  }
}