package com.ibm.tivoli.bsm.service;

import java.io.IOException;
import java.io.Reader;

public class CountLineReader extends Reader

{
  Reader in = null;
  int read = 0, charCount = 0, wordCount = 0, lineCount = 0;
  boolean whiteSpace = true;

  public CountLineReader(Reader r) {
    in = r;
  }

  /**
   * Implementation for parent's read method. Counts chars, words, and lines.
   */

  public int read(char[] array, int off, int len) throws IOException {
    if (array == null)
      throw new IOException("Null array");

    // Do actual read
    read = in.read(array, off, len);

    // Now count
    charCount += read;

    // Increment character count
    char c;

    for (int i = 0; i < read; i++) {
      c = array[i];

      // Line count
      if (c == '\n')
        lineCount++;

      // Word count
      if (Character.isWhitespace(c))
        whiteSpace = true;
      else if (Character.isLetterOrDigit(c) && whiteSpace) {
        wordCount++;
        whiteSpace = false;
      }
    }

    return read;
  }

  public void close() throws IOException {
    in.close();
  }

  public int getCharCount() {
    return charCount;
  }

  public int getWordCount() {
    return wordCount;
  }

  public int getLineCount() {
    return lineCount;
  }

}
