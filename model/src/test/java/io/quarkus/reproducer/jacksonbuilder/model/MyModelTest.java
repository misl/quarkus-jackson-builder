package io.quarkus.reproducer.jacksonbuilder.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for SensorData.Builder
 *
 * @author Minto van der Sluis
 */
public class MyModelTest {

  // -------------------------------------------------------------------------
  // Test cases
  // -------------------------------------------------------------------------

  @Test
  public void testBuilderMinimal() {
    // prepare
    MyModel.Builder builder = new MyModel.Builder( "id1" );

    // execute
    MyModel data = builder.build();

    // verify
    assertThat( data.getVersion() ).isEqualTo( 1 );
    assertThat( data.getId() ).isEqualTo( "id1" );
    assertThat( data.getValue() ).isEqualTo( "" );
  }

  @Test
  public void testBuilder() {
    // prepare
    MyModel.Builder builder = new MyModel.Builder( "id2" )
        .withVersion( 2 )
        .withValue( "value" );

    // execute
    MyModel data = builder.build();

    // verify
    assertThat( data.getVersion() ).isEqualTo( 2 );
    assertThat( data.getId() ).isEqualTo( "id2" );
    assertThat( data.getValue() ).isEqualTo( "value" );
  }

  @Test
  public void testBuilderCloneConstructor() {
    // prepare
    MyModel original = new MyModel.Builder( "id1" )
        .withVersion( 3 )
        .withValue( "val" )
        .build();

    // execute
    MyModel clone = new MyModel.Builder( original ).build();

    // verify
    assertThat( clone.getVersion() ).isEqualTo( 3 );
    assertThat( clone.getId() ).isEqualTo( "id1" );
    assertThat( clone.getValue() ).isEqualTo( "val" );
  }
}
