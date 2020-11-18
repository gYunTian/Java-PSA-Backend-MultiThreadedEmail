package com.vsta.dto;

/**
 * Data Transfer Object Projection helper to support the conversion of result
 * from native query object to a temporary User entity Reference:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
 */

public interface UserDTO {

  /**
   * Gets the name of this User object.
   * @return This User's name.
   */
  String getName();

  /**
   * Gets the email of this User object.
   * @return This User's email.
   */
  String getEmail();

}
