package io.quarkus.reproducer.jacksonbuilder.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.IOException;

/**
 * Simple POJO model class.
 *
 * @author Minto van der Sluis
 */
@RegisterForReflection
public class SimplePojoModel {

  // -------------------------------------------------------------------------
  // Class attributes
  // -------------------------------------------------------------------------

  private static ObjectMapper objectMapper;

  // -------------------------------------------------------------------------
  // Object attributes
  // -------------------------------------------------------------------------

  private int version = 1;
  private String id = null;
  private String value = null;

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  public SimplePojoModel() {
  }

  // -------------------------------------------------------------------------
  // Interface
  // -------------------------------------------------------------------------

  public String toJson() throws IOException {
    String json = getObjectMapper().writeValueAsString( this );
    System.out.println( "-------> SimplePojoModel.toJson(); " + json );
    return json;
  }

  public static String toJson( final SimplePojoModel model ) throws IOException {
    return model.toJson();
  }

  public static SimplePojoModel fromJson( final String json ) throws IOException {
    return getObjectMapper().readerFor( SimplePojoModel.class ).readValue( json );
  }

  // -------------------------------------------------------------------------
  // Getters / Setters
  // -------------------------------------------------------------------------

  public int getVersion() {
    return version;
  }

  public void setVersion( int version ) {
    this.version = version;
  }

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue( String value ) {
    this.value = value;
  }

  // -------------------------------------------------------------------------
  // Private methods
  // -------------------------------------------------------------------------

  private static ObjectMapper getObjectMapper() {
    if ( null == objectMapper ) {
      objectMapper = new ObjectMapper()
          .configure( DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false )
          .configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false )
          .configure( SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false )
          .setSerializationInclusion( JsonInclude.Include.NON_NULL )
          .setSerializationInclusion( JsonInclude.Include.NON_ABSENT )
          .registerModule( new ParameterNamesModule() )
          .registerModule( new Jdk8Module() )
          .registerModule( new JavaTimeModule() );
    }
    return objectMapper;
  }

}
