package io.quarkus.reproducer.jacksonbuilder.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for RegisteredPojoModel
 *
 * @author Minto van der Sluis
 */
public class RegisteredPojoModelTest {

  // -------------------------------------------------------------------------
  // Test cases
  // -------------------------------------------------------------------------

  @Test
  public void testBuilderMinimal() {
    // prepare

    // execute
    RegisteredPojoModel data = new RegisteredPojoModel();

    // verify
    assertThat( data.getVersion() ).isEqualTo( 1 );
    assertThat( data.getId() ).isNull();
    assertThat( data.getValue() ).isNull();
  }

  @Test
  public void testBuilder() {
    // prepare

    // execute
    RegisteredPojoModel data = new RegisteredPojoModel();
    data.setVersion( 2 );
    data.setId( "id1" );
    data.setValue( "value" );

    // verify
    assertThat( data.getVersion() ).isEqualTo( 2 );
    assertThat( data.getId() ).isEqualTo( "id1" );
    assertThat( data.getValue() ).isEqualTo( "value" );
  }
}
