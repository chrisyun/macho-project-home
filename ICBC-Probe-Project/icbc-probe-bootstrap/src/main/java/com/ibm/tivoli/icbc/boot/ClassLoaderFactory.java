package com.ibm.tivoli.icbc.boot;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * 
 * *
 * <p>
 * Utility class for building class loaders for Catalina. The factory method
 * requires the following parameters in order to build a new class loader (with
 * suitable defaults in all cases):
 * </p>
 * <ul>
 * <li>A set of directories containing unpacked classes (and resources) that
 * should be included in the class loader's repositories.</li>
 * <li>A set of directories containing classes and resources in JAR files. Each
 * readable JAR file discovered in these directories will be added to the class
 * loader's repositories.</li>
 * <li><code>ClassLoader</code> instance that should become the parent of the
 * new class loader.</li>
 * </ul>
 * 
 * @author Zhao Dong Lu
 * 
 */
public class ClassLoaderFactory {

  // ------------------------------------------------------- Static Variables

  /**
   * Debugging detail level for processing the startup.
   */
  private static int debug = 0;

  // ------------------------------------------------------ Static Properties

  /**
   * Return the debugging detail level.
   */
  public static int getDebug() {

    return (debug);

  }

  /**
   * Set the debugging detail level.
   * 
   * @param newDebug
   *          The new debugging detail level
   */
  public static void setDebug(int newDebug) {

    debug = newDebug;

  }

  /**
   * Create and return a new class loader, based on the configuration defaults
   * and the specified directory paths:
   * 
   * @param unpacked
   *          Array of pathnames to unpacked directories that should be added to
   *          the repositories of the class loader, or <code>null</code> for no
   *          unpacked directories to be considered
   * @param packed
   *          Array of pathnames to directories containing JAR files that should
   *          be added to the repositories of the class loader, or
   *          <code>null</code> for no directories of JAR files to be considered
   * @param parent
   *          Parent class loader for the new class loader, or <code>null</code>
   *          for the system class loader.
   * 
   * @exception Exception
   *              if an error occurs constructing the class loader
   */
  public static ClassLoader createClassLoader(File unpacked[], File packed[], ClassLoader parent) throws Exception {

    if (debug >= 1) {
      log("Creating new class loader");
    }

    // Construct the "class path" for this class loader
    ArrayList list = new ArrayList();

    // Add unpacked directories
    if (unpacked != null) {
      for (int i = 0; i < unpacked.length; i++) {
        File file = unpacked[i];
        if (!file.isDirectory() || !file.exists() || !file.canRead()) {
          continue;
        }
        if (debug >= 1) {
          log("  Including directory " + file.getAbsolutePath());
        }
        URL url = new URL("file", null, file.getCanonicalPath() + File.separator);
        list.add(url.toString());
      }
    }

    // Add packed directory JAR files
    if (packed != null) {
      for (int i = 0; i < packed.length; i++) {
        File directory = packed[i];
        if (!directory.isDirectory() || !directory.exists() || !directory.canRead()) {
          continue;
        }
        String filenames[] = directory.list();
        for (int j = 0; j < filenames.length; j++) {
          String filename = filenames[j].toLowerCase();
          if (!filename.endsWith(".jar")) {
            continue;
          }
          File file = new File(directory, filenames[j]);
          if (debug >= 1) {
            log("  Including jar file " + file.getAbsolutePath());
          }
          URL url = new URL("file", null, file.getCanonicalPath());
          list.add(url.toString());
        }
      }
    }

    // Construct the class loader itself
    String array[] = (String[]) list.toArray(new String[list.size()]);
    StandardClassLoader classLoader = null;
    if (parent == null) {
      classLoader = new StandardClassLoader(array);
    } else {
      classLoader = new StandardClassLoader(array, parent);
    }
    classLoader.setDelegate(true);
    return (classLoader);

  }

  // -------------------------------------------------------- Private Methods

  /**
   * Log a message for this class.
   * 
   * @param message
   *          Message to be logged
   */
  private static void log(String message) {

    System.out.print("ClassLoaderFactory:  ");
    System.out.println(message);

  }

  /**
   * Log a message and exception for this class.
   * 
   * @param message
   *          Message to be logged
   * @param exception
   *          Exception to be logged
   */
  private static void log(String message, Throwable exception) {

    log(message);
    exception.printStackTrace(System.out);

  }

}
