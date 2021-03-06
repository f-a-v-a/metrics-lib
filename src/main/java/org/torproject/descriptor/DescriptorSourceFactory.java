/* Copyright 2011--2018 The Tor Project
 * See LICENSE for licensing information */

package org.torproject.descriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for descriptor sources which in turn produce descriptors.
 *
 * <p>Descriptor sources are the only producers of classes implementing
 * the {@link Descriptor} superinterface.  There exist descriptor sources
 * for obtaining remote descriptor data ({@link DescriptorCollector}) and
 * descriptor sources for processing local descriptor data
 * ({@link DescriptorReader} and {@link DescriptorParser}).</p>
 *
 * <p>By default, this factory returns implementations from the library's
 * own impl package.  This may be overridden by setting Java properties,
 * though most users will simply use the default implementations.</p>
 *
 * <p>These properties can be used for setting the implementation:</p>
 * <ul>
 *   <li>{@code descriptor.collector}</li>
 *   <li>{@code descriptor.parser}</li>
 *   <li>{@code descriptor.reader}</li>
 * </ul>
 *
 * <p>Assuming the classpath contains the special implementation
 * referenced, your application classes as well as a descriptor API jar
 * the following is an example for using a different implementation of the
 * descriptor downloader:</p>
 *
 * <p>{@code
 *  java -Ddescriptor.downloader=my.special.descriptorimpl.Downloader \
 *      my.app.Mainclass
 * }</p>
 *
 * @since 1.0.0
 */
public final class DescriptorSourceFactory {

  private static Logger log = LoggerFactory.getLogger(
      DescriptorSourceFactory.class);

  /**
   * Default implementation of the {@link DescriptorParser} descriptor
   * source.
   *
   * @since 1.0.0
   */
  public static final String PARSER_DEFAULT =
      "org.torproject.descriptor.impl.DescriptorParserImpl";

  /**
   * Default implementation of the {@link DescriptorReader} descriptor
   * source.
   *
   * @since 1.0.0
   */
  public static final String READER_DEFAULT =
      "org.torproject.descriptor.impl.DescriptorReaderImpl";

  /**
   * Default implementation of the {@link DescriptorCollector} descriptor
   * source.
   *
   * @since 1.0.0
   */
  public static final String COLLECTOR_DEFAULT =
      "org.torproject.descriptor.index.DescriptorIndexCollector";

  /**
   * Property name for overriding the implementation of the
   * {@link DescriptorParser} descriptor source, which is by default set
   * to the class in {@link #PARSER_DEFAULT}.
   *
   * @since 1.0.0
   */
  public static final String PARSER_PROPERTY = "descriptor.parser";

  /**
   * Property name for overriding the implementation of the
   * {@link DescriptorReader} descriptor source, which is by default set
   * to the class in {@link #READER_DEFAULT}.
   *
   * @since 1.0.0
   */
  public static final String READER_PROPERTY = "descriptor.reader";

  /**
   * Property name for overriding the implementation of the
   * {@link DescriptorCollector} descriptor source, which is by default
   * set to the class in {@link #COLLECTOR_DEFAULT}.
   *
   * @since 1.0.0
   */
  public static final String COLLECTOR_PROPERTY = "descriptor.collector";

  /**
   * Create a new {@link DescriptorParser} by instantiating the class in
   * {@link #PARSER_PROPERTY}.
   *
   * @since 1.0.0
   */
  public static final DescriptorParser createDescriptorParser() {
    return (DescriptorParser) retrieve(PARSER_PROPERTY);
  }

  /**
   * Create a new {@link DescriptorReader} by instantiating the class in
   * {@link #READER_PROPERTY}.
   *
   * @since 1.0.0
   */
  public static final DescriptorReader createDescriptorReader() {
    return (DescriptorReader) retrieve(READER_PROPERTY);
  }

  /**
   * Create a new {@link DescriptorCollector} by instantiating the class
   * in {@link #COLLECTOR_PROPERTY}.
   *
   * @since 1.0.0
   */
  public static final DescriptorCollector createDescriptorCollector() {
    return (DescriptorCollector) retrieve(COLLECTOR_PROPERTY);
  }

  private static final <T> Object retrieve(String type) {
    Object object;
    String clazzName = null;
    try {
      switch (type) {
        case PARSER_PROPERTY:
          clazzName = System.getProperty(type, PARSER_DEFAULT);
          break;
        case READER_PROPERTY:
          clazzName = System.getProperty(type, READER_DEFAULT);
          break;
        case COLLECTOR_PROPERTY:
          clazzName = System.getProperty(type, COLLECTOR_DEFAULT);
          break;
        default:
          throw new RuntimeException("Cannot retrieve class for type " + type
              + ".");
      }
      object = ClassLoader.getSystemClassLoader().loadClass(clazzName)
          .newInstance();
      log.info("Serving implementation {} for {}.", clazzName, type);
    } catch (ClassNotFoundException | InstantiationException
             | IllegalAccessException ex) {
      throw new RuntimeException("Cannot load class "
          + clazzName + "for type " + type, ex);
    }
    return object;
  }
}

