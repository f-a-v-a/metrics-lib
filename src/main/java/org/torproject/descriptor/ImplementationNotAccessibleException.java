/* Copyright 2014--2017 The Tor Project
 * See LICENSE for licensing information */

package org.torproject.descriptor;

/**
 * Thrown if a descriptor source implementation class cannot be found,
 * instantiated, or accessed.
 *
 * @see DescriptorSourceFactory
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class ImplementationNotAccessibleException
    extends RuntimeException {

  public ImplementationNotAccessibleException(String message,
      Throwable ex) {
    super(message, ex);
  }

  public ImplementationNotAccessibleException(String message) {
    super(message);
  }

}

