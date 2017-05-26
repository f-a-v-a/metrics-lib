/* Copyright 2015--2017 The Tor Project
 * See LICENSE for licensing information */

package org.torproject.descriptor.impl;

import org.torproject.descriptor.DescriptorParseException;
import org.torproject.descriptor.ExtraInfoDescriptor;
import org.torproject.descriptor.RelayExtraInfoDescriptor;

import java.util.ArrayList;
import java.util.List;

public class RelayExtraInfoDescriptorImpl
    extends ExtraInfoDescriptorImpl implements RelayExtraInfoDescriptor {

  protected static List<ExtraInfoDescriptor> parseDescriptors(
      byte[] descriptorsBytes, boolean failUnrecognizedDescriptorLines)
      throws DescriptorParseException {
    List<ExtraInfoDescriptor> parsedDescriptors = new ArrayList<>();
    List<byte[]> splitDescriptorsBytes =
        DescriptorImpl.splitRawDescriptorBytes(descriptorsBytes,
        Key.EXTRA_INFO.keyword + SP);
    for (byte[] descriptorBytes : splitDescriptorsBytes) {
      ExtraInfoDescriptor parsedDescriptor =
          new RelayExtraInfoDescriptorImpl(descriptorBytes,
          failUnrecognizedDescriptorLines);
      parsedDescriptors.add(parsedDescriptor);
    }
    return parsedDescriptors;
  }

  protected RelayExtraInfoDescriptorImpl(byte[] descriptorBytes,
      boolean failUnrecognizedDescriptorLines)
      throws DescriptorParseException {
    super(descriptorBytes, failUnrecognizedDescriptorLines);
  }
}

