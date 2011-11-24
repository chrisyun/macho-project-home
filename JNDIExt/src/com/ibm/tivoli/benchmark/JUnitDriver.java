package com.ibm.tivoli.benchmark;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import junit.framework.Test;
import junit.textui.TestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class JUnitDriver extends JapexDriverBase {
  String _methodName;
  Test _testSuite;
  Object _object;
  Method _method;
  Class<?> _testClass;
  Method _beforeClassMethod;
  Method _afterClassMethod;
  Method _beforeMethod;

  Method _afterMethod;

  public void initializeDriver() {
    super.initializeDriver();
    try {
      String testName = getParam("testName");
      if (testName != null) {
        this._testClass = getClass().getClassLoader().loadClass(testName);

        findJUnit3Methods(this._testClass);

        if (this._beforeClassMethod != null)
          this._beforeClassMethod.invoke(null, new Object[0]);
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void prepare(TestCase testCase) {
    try {
      String testName = testCase.getParam("testName");

      if (testName != null) {
        this._testClass = getClass().getClassLoader().loadClass(testName);
      }

      this._methodName = testCase.getParam("methodName");

      if (this._methodName == null) {
        Method suiteMethod = this._testClass.getMethod("suite", (Class[]) null);
        this._testSuite = ((Test) suiteMethod.invoke((Object) null, (Object[]) null));
      } else {
        this._method = this._testClass.getMethod(this._methodName, (Class[]) null);

        Constructor con = null;
        try {
          con = this._testClass.getConstructor(new Class[] { new String().getClass() });
        } catch (NoSuchMethodException _) {
        }
        if (con == null) {
          try {
            con = this._testClass.getConstructor(new Class[0]);
          } catch (NoSuchMethodException _) {
          }
          if (con == null) {
            throw new RuntimeException("Unable to find suitable constructor in class '" + this._testClass.getName() + "'");
          }

          this._object = con.newInstance(new Object[0]);
        } else {
          this._object = con.newInstance(new Object[] { testName });
        }

      }

      if (this._beforeMethod != null)
        this._beforeMethod.invoke(this._object, new Object[0]);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void run(TestCase testCase) {
    try {
      if (this._methodName == null) {
        TestRunner.run(this._testSuite);
      } else {
        this._method.invoke(this._object, (Object[]) null);
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void finish(TestCase testCase) {
    super.finish(testCase);
    try {
      if (this._afterMethod != null)
        this._afterMethod.invoke(this._object, new Object[0]);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void terminateDriver() {
    super.terminateDriver();
    try {
      if (this._afterClassMethod != null)
        this._afterClassMethod.invoke(null, new Object[0]);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void findJUnit3Methods(Class<?> testClass) {
    try {
      this._beforeMethod = testClass.getMethod("setUp");
      System.out.println(this._beforeMethod);
      this._afterMethod = testClass.getMethod("tearDown");
      System.out.println(this._afterMethod);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void findJUnit4Methods(Class<?> testClass) {
    try {
      int notFound = 4;
      do {
        Method[] mm = testClass.getDeclaredMethods();
        for (int i = 0; i < mm.length; ++i) {
          if (this._afterMethod == null) {
            After after = (After) mm[i].getAnnotation(After.class);
            if (after != null) {
              this._afterMethod = mm[i];
              --notFound;
            }
          }
          if (this._beforeMethod == null) {
            Before before = (Before) mm[i].getAnnotation(Before.class);
            if (before != null) {
              this._beforeMethod = mm[i];
              --notFound;
            }
          }
          if (this._afterClassMethod == null) {
            AfterClass afterClass = (AfterClass) mm[i].getAnnotation(AfterClass.class);
            if (afterClass != null) {
              this._afterClassMethod = mm[i];
              --notFound;
            }
          }
          if (this._beforeClassMethod == null) {
            BeforeClass beforeClass = (BeforeClass) mm[i].getAnnotation(BeforeClass.class);
            if (beforeClass != null) {
              this._beforeClassMethod = mm[i];
              --notFound;
            }
          }
        }

        testClass = testClass.getSuperclass();
        if (notFound <= 0)
          break;
      } while (testClass != Object.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}