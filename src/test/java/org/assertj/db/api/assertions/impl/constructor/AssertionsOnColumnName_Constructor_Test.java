package org.assertj.db.api.assertions.impl.constructor;

import org.assertj.db.api.assertions.impl.AssertionsOnColumnName;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests on {@link AssertionsOnColumnName} class :
 * {@link AssertionsOnColumnName#AssertionsOnColumnName()} private constructor.
 *
 * @author Régis Pouiller
 *
 */
public class AssertionsOnColumnName_Constructor_Test {

  /**
   * This method tests the private constructor of {@code Assertions} for the tests coverage..
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws java.lang.reflect.InvocationTargetException
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws IllegalArgumentException
   */
  @Test
  public void test_private_constructor_for_the_tests_coverage()
          throws SecurityException, NoSuchMethodException, IllegalArgumentException,
          InstantiationException, IllegalAccessException, InvocationTargetException {

    Constructor<AssertionsOnColumnName> constructor = AssertionsOnColumnName.class.getDeclaredConstructor();
    assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
    constructor.setAccessible(true);
    constructor.newInstance();
    constructor.setAccessible(false);
  }
}
