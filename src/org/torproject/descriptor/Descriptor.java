/* Copyright 2011, 2012 The Tor Project
 * See LICENSE for licensing information */
package org.torproject.descriptor;

import java.util.List;

/* Store meta-data about how a descriptor was downloaded or read from
 * disk. */
public interface Descriptor {

  /* Return the raw descriptor bytes. */
  public byte[] getRawDescriptorBytes();

  /* Return any unrecognized lines when parsing this descriptor, or an
   * empty list if there were no unrecognized lines. */
  public List<String> getUnrecognizedLines();
}

