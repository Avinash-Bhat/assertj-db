/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.db.api;

import org.assertj.core.internal.Objects;
import org.assertj.db.error.ShouldBeValueTypeOfAny;
import org.assertj.db.exception.AssertJDBException;
import org.assertj.db.type.DateTimeValue;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.TimeValue;
import org.assertj.db.type.ValueType;
import org.assertj.db.util.Values;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;

import static org.assertj.db.error.ShouldBeBefore.shouldBeBefore;
import static org.assertj.db.error.ShouldBeEqual.shouldBeEqual;
import static org.assertj.db.error.ShouldBeValueType.shouldBeValueType;
import static org.assertj.db.error.ShouldNotBeEqual.shouldNotBeEqual;
import static org.assertj.db.util.Values.areEqual;

/**
 * Assertion methods about a value of a {@code Row} of a {@code Change}.
 *
 * @author Régis Pouiller
 *
 */
public class ChangeValueAssert extends AbstractAssertWithValues<ChangeValueAssert, ChangeRowAssert> {

  /**
   * The actual value on which the assertion is.
   */
  private final Object value;

  /**
   * Assertions for {@code Object}s provided by assertj-core.
   */
  private Objects objects = Objects.instance();

  /**
   * Constructor.
   *
   * @param originalAssert The original assert.
   * @param value The value on which are the assertions.
   */
  ChangeValueAssert(ChangeRowAssert originalAssert, Object value) {
    super(ChangeValueAssert.class, originalAssert);
    this.value = value;
  }

  /**
   * Returns the type of the value (text, boolean, number, date, ...).
   *
   * @return The type of the value.
   */
  protected ValueType getType() {
    return ValueType.getType(value);
  }

  /**
   * Verifies that the type of the value is equal to the type in parameter.
   * <p>
   * Example where the assertion verifies that the value in the {@code Column} called "title" of the {@code Row} at end point
   * of the first {@code Change} is of type {@code TEXT} :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value(&quot;title&quot;).isOfType(ValueType.TEXT);
   * </code>
   * </pre>
   *
   * @param expected The expected type to compare to.
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is different to the type in parameter.
   */
  public ChangeValueAssert isOfType(ValueType expected) {
    ValueType type = getType();
    if (type != expected) {
      throw failures.failure(info, shouldBeValueType(value, expected, type));
    }
    return myself;
  }

  /**
   * Verifies that the type of the value is equal to one of the types in parameters.
   * <p>
   * Example where the assertion verifies that the value in the {@code Column} called "title" of the {@code Row} at end point
   * of the first {@code Change} is of type {@code TEXT} or of type {@code NUMBER} :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value(&quot;title&quot;).isOfType(ValueType.TEXT, ValueType.NUMBER);
   * </code>
   * </pre>
   *
   * @param expected The expected types to compare to.
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is different to all the types in parameters.
   */
  public ChangeValueAssert isOfAnyOfTypes(ValueType... expected) {
    ValueType type = getType();
    for (ValueType valueType : expected) {
      if (type == valueType) {
        return myself;
      }
    }
    throw failures.failure(info, ShouldBeValueTypeOfAny.shouldBeValueTypeOfAny(value, type, expected));
  }

  /**
   * Verifies that the value is a number.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is a number :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNumber();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not a number.
   */
  public ChangeValueAssert isNumber() {
    return isOfType(ValueType.NUMBER);
  }

  /**
   * Verifies that the value is a boolean.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is a boolean :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isBoolean();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not a number.
   */
  public ChangeValueAssert isBoolean() {
    return isOfType(ValueType.BOOLEAN);
  }

  /**
   * Verifies that the value is a date.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is a date :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isDate();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not a number.
   */
  public ChangeValueAssert isDate() {
    return isOfType(ValueType.DATE);
  }

  /**
   * Verifies that the value is a time.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is a time :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isTime();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not a number.
   */
  public ChangeValueAssert isTime() {
    return isOfType(ValueType.TIME);
  }

  /**
   * Verifies that the value is a date/time.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is a date/time :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isDateTime();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not a number.
   */
  public ChangeValueAssert isDateTime() {
    return isOfType(ValueType.DATE_TIME);
  }

  /**
   * Verifies that the value is a array of bytes.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is a array of bytes :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isBytes();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not a number.
   */
  public ChangeValueAssert isBytes() {
    return isOfType(ValueType.BYTES);
  }

  /**
   * Verifies that the value is a text.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is a text :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isText();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not a number.
   */
  public ChangeValueAssert isText() {
    return isOfType(ValueType.TEXT);
  }

  /**
   * Verifies that the value is {@code null}.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is {@code null} :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNull();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is not {@code null}.
   */
  public ChangeValueAssert isNull() {
    objects.assertNull(info, value);
    return myself;
  }

  /**
   * Verifies that the value is not {@code null}.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not {@code null} :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNotNull();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is {@code null}.
   */
  public ChangeValueAssert isNotNull() {
    objects.assertNotNull(info, value);
    return myself;
  }

  /**
   * Verifies that the value is equal to a boolean.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is equal to true boolean :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isEqualTo(true);
   * </code>
   * </pre>
   *
   * @param expected The expected boolean value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the boolean in parameter.
   */
  public ChangeValueAssert isEqualTo(Boolean expected) {
    isBoolean();
    if (areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldBeEqual((Boolean) value, expected));
  }

  /**
   * Verifies that the value is equal to true boolean.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isTrue();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to true boolean.
   */
  public ChangeValueAssert isTrue() {
    return isEqualTo(true);
  }

  /**
   * Verifies that the value is equal to false boolean.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isFalse();
   * </code>
   * </pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to false boolean.
   */
  public ChangeValueAssert isFalse() {
    return isEqualTo(false);
  }

  /**
   * Verifies that the value is equal to a number.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is equal to number 3 :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isEqualTo(3);
   * </code>
   * </pre>
   *
   * @param expected The expected number value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the number in parameter.
   */
  public ChangeValueAssert isEqualTo(Number expected) {
    isNumber();
    if (areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldBeEqual((Number) value, expected));
  }

  /**
   * Verifies that the value is equal to a array of bytes.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is equal to a array of bytes loaded from a file in the classpath :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * byte[] bytes = bytesContentFromClassPathOf(&quot;file.png&quot;);
   * assertThat(changes).change().rowAtEndPoint().value().isEqualTo(bytes);
   * </code>
   * </pre>
   *
   * @param expected The expected array of bytes value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the array of bytes in parameter.
   */
  public ChangeValueAssert isEqualTo(byte[] expected) {
    isBytes();
    if (areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldBeEqual());
  }

  /**
   * Verifies that the value is equal to a text.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is equal to a text :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isEqualTo(&quot;text&quot;);
   * </code>
   * </pre>
   *
   * @param expected The expected text value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the text in parameter.
   */
  public ChangeValueAssert isEqualTo(String expected) {
    isOfAnyOfTypes(ValueType.TEXT, ValueType.NUMBER, ValueType.DATE, ValueType.TIME, ValueType.DATE_TIME);
    if (areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info,
                           shouldBeEqual(Values.getRepresentationFromValueInFrontOfExpected(value, expected), expected));
  }

  /**
   * Verifies that the value is equal to a date value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is equal to a date value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isEqualTo(DateValue.of(2014, 7, 7));
   * </code>
   * </pre>
   *
   * @param expected The expected date value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the date value in parameter.
   */
  public ChangeValueAssert isEqualTo(DateValue expected) {
    isOfAnyOfTypes(ValueType.DATE, ValueType.DATE_TIME);
    if (areEqual(value, expected)) {
      return myself;
    }
    if (getType() == ValueType.DATE) {
      throw failures.failure(info, shouldBeEqual(DateValue.from((Date) value), expected));
    }
    throw failures.failure(info, shouldBeEqual(DateTimeValue.from((Timestamp) value), DateTimeValue.of(expected)));
  }

  /**
   * Verifies that the value is equal to a time value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is equal to a time value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isEqualTo(TimeValue.of(21, 29, 30));
   * </code>
   * </pre>
   *
   * @param expected The expected time value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the time value in parameter.
   */
  public ChangeValueAssert isEqualTo(TimeValue expected) {
    isTime();
    if (areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldBeEqual(TimeValue.from((Time) value), expected));
  }

  /**
   * Verifies that the value is equal to a date/time value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is equal to a date/time value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isEqualTo(DateTimeValue.of(DateValue.of(2014, 7, 7), TimeValue.of(21, 29)));
   * </code>
   * </pre>
   *
   * @param expected The expected date/time value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the date/time value in parameter.
   */
  public ChangeValueAssert isEqualTo(DateTimeValue expected) {
    isOfAnyOfTypes(ValueType.DATE, ValueType.DATE_TIME);
    if (areEqual(value, expected)) {
      return myself;
    }
    if (getType() == ValueType.DATE) {
      throw failures.failure(info, shouldBeEqual(DateTimeValue.of(DateValue.from((Date) value)), expected));
    }
    throw failures.failure(info, shouldBeEqual(DateTimeValue.from((Timestamp) value), expected));
  }

  /**
   * Verifies that the value is not equal to a boolean.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not equal to true boolean :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNotEqualTo(true);
   * </code>
   * </pre>
   *
   * @param expected The expected boolean value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is equal to the boolean in parameter.
   */
  public ChangeValueAssert isNotEqualTo(Boolean expected) {
    isBoolean();
    if (!areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldNotBeEqual((Boolean) value, expected));
  }

  /**
   * Verifies that the value is not equal to a array of bytes.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not equal to a array of bytes loaded from a file in the classpath :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * byte[] bytes = bytesContentFromClassPathOf(&quot;file.png&quot;);
   * assertThat(changes).change().rowAtEndPoint().value().isNotEqualTo(bytes);
   * </code>
   * </pre>
   *
   * @param expected The expected array of bytes value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is equal to the array of bytes in parameter.
   */
  public ChangeValueAssert isNotEqualTo(byte[] expected) {
    isBytes();
    if (!areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldNotBeEqual());
  }

  /**
   * Verifies that the value is not equal to a date/time value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not equal to a date/time value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNotEqualTo(DateTimeValue.of(DateValue.of(2014, 7, 7), TimeValue.of(21, 29)));
   * </code>
   * </pre>
   *
   * @param expected The expected date/time value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is equal to the date/time value in parameter.
   */
  public ChangeValueAssert isNotEqualTo(DateTimeValue expected) {
    isOfAnyOfTypes(ValueType.DATE, ValueType.DATE_TIME);
    if (!areEqual(value, expected)) {
      return myself;
    }
    if (getType() == ValueType.DATE) {
      throw failures.failure(info, shouldNotBeEqual(DateTimeValue.of(DateValue.from((Date) value)), expected));
    }
    throw failures.failure(info, shouldNotBeEqual(DateTimeValue.from((Timestamp) value), expected));
  }

  /**
   * Verifies that the value is not equal to a date value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not equal to a date value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNotEqualTo(DateValue.of(2014, 7, 7));
   * </code>
   * </pre>
   *
   * @param expected The expected date value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is equal to the date value in parameter.
   */
  public ChangeValueAssert isNotEqualTo(DateValue expected) {
    isOfAnyOfTypes(ValueType.DATE, ValueType.DATE_TIME);
    if (!areEqual(value, expected)) {
      return myself;
    }
    if (getType() == ValueType.DATE) {
      throw failures.failure(info, shouldNotBeEqual(DateValue.from((Date) value), expected));
    }
    throw failures.failure(info, shouldNotBeEqual(DateTimeValue.from((Timestamp) value), DateTimeValue.of(expected)));
  }

  /**
   * Verifies that the value is not equal to a number.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not equal to number 3 :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNotEqualTo(3);
   * </code>
   * </pre>
   *
   * @param expected The expected number value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is equal to the number in parameter.
   */
  public ChangeValueAssert isNotEqualTo(Number expected) {
    isNumber();
    if (!areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldNotBeEqual((Number) value, expected));
  }

  /**
   * Verifies that the value is not equal to a text.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not equal to a text :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNotEqualTo(&quot;text&quot;);
   * </code>
   * </pre>
   *
   * @param expected The expected text value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is equal to the text in parameter.
   */
  public ChangeValueAssert isNotEqualTo(String expected) {
    isOfAnyOfTypes(ValueType.TEXT, ValueType.NUMBER, ValueType.DATE, ValueType.TIME, ValueType.DATE_TIME);
    if (!areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info,
                           shouldNotBeEqual(Values.getRepresentationFromValueInFrontOfExpected(value, expected), expected));
  }

  /**
   * Verifies that the value is not equal to a time value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is not equal to a time value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isNotEqualTo(TimeValue.of(21, 29, 30));
   * </code>
   * </pre>
   *
   * @param expected The expected time value.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is equal to the time value in parameter.
   */
  public ChangeValueAssert isNotEqualTo(TimeValue expected) {
    isTime();
    if (!areEqual(value, expected)) {
      return myself;
    }
    throw failures.failure(info, shouldNotBeEqual(TimeValue.from((Time) value), expected));
  }

  /**
   * Verifies that the value is before a date value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is before a date value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isBefore(DateValue.of(2007, 12, 23));
   * </code>
   * </pre>
   *
   * @param date The date value to compare to.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not before to the time value in parameter.
   */
  public ChangeValueAssert isBefore(DateValue date) {
    isOfAnyOfTypes(ValueType.DATE, ValueType.DATE_TIME);
    if (value instanceof Date) {
      if (DateValue.from((Date) value).isBefore(date)) {
        return myself;
      }
      throw failures.failure(info, shouldBeBefore(DateValue.from((Date) value), date));
    } else {
      DateTimeValue dateTimeValue = DateTimeValue.of(date);
      if (DateTimeValue.from((Timestamp) value).isBefore(dateTimeValue)) {
        return myself;
      }
      throw failures.failure(info, shouldBeBefore(DateTimeValue.from((Timestamp) value), dateTimeValue));
    }
  }

  /**
   * Verifies that the value is before a time value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is before a time value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isBefore(TimeValue.of(2007, 12, 23));
   * </code>
   * </pre>
   *
   * @param time The time value to compare to.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not before to the time value in parameter.
   */
  public ChangeValueAssert isBefore(TimeValue time) {
    isTime();
    if (TimeValue.from((Time) value).isBefore(time)) {
      return myself;
    }
    throw failures.failure(info, shouldBeBefore(TimeValue.from((Time) value), time));
  }

  /**
   * Verifies that the value is before a date/time value.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is before a date/time value :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isBefore(DateTimeValue.of(DateValue.of(2007, 12, 23), TimeValue.of(21, 29)));
   * </code>
   * </pre>
   *
   * @param dateTime The date/time value to compare to.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not before to the date/time value in parameter.
   */
  public ChangeValueAssert isBefore(DateTimeValue dateTime) {
    isOfAnyOfTypes(ValueType.DATE, ValueType.DATE_TIME);
    DateTimeValue dateTimeValue;
    if (value instanceof Date) {
      dateTimeValue = DateTimeValue.of(DateValue.from((Date) value));
    } else {
      dateTimeValue = DateTimeValue.from((Timestamp) value);
    }
    if (dateTimeValue.isBefore(dateTime)) {
      return myself;
    }
    throw failures.failure(info, shouldBeBefore(DateTimeValue.from((Timestamp) value), dateTime));
  }

  /**
   * Verifies that the value is before a date, time or date/time represented by a {@code String}.
   * <p>
   * Example where the assertion verifies that the value in the first {@code Column} of the {@code Row} at end point
   * of the first {@code Change} is before a date represented by a {@code String} :
   * </p>
   *
   * <pre>
   * <code class='java'>
   * assertThat(changes).change().rowAtEndPoint().value().isBefore(&quot;2007-12-23&quot;);
   * </code>
   * </pre>
   *
   * @param expected The {@code String} representing a date, time or date/time to compare to.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not before the date, time or date/time represented in parameter.
   */
  public ChangeValueAssert isBefore(String expected) {
    isOfAnyOfTypes(ValueType.DATE, ValueType.TIME, ValueType.DATE_TIME);

    // By considering the possible types, the class of the value is
    // java.sql.Date, java.sql.Time or java.sql.Timestamp

    // If the class is java.sql.Time then comparison by using TimeValue
    if (value instanceof Time) {
      TimeValue timeValue = TimeValue.from((Time) value);
      try {
        TimeValue expectedTimeValue = TimeValue.parse(expected);
        if (timeValue.isBefore(expectedTimeValue)) {
          return myself;
        }
        throw failures.failure(info, shouldBeBefore(timeValue, expectedTimeValue));
      } catch (ParseException e) {
        throw new AssertJDBException("Expected <%s> is not correct to compare to <%s>", expected, timeValue);
      }
    }

    // In the other case then comparison by using DateTimeValue
    DateTimeValue dateTimeValue;
    if (value instanceof Date) {
      dateTimeValue = DateTimeValue.of(DateValue.from((Date) value));
    } else {
      dateTimeValue = DateTimeValue.from((Timestamp) value);
    }

    try {
      DateTimeValue expectedDateTimeValue = DateTimeValue.parse(expected);
      if (dateTimeValue.isBefore(expectedDateTimeValue)) {
        return myself;
      }
      throw failures.failure(info, shouldBeBefore(dateTimeValue, expectedDateTimeValue));
    } catch (ParseException e) {
      throw new AssertJDBException("Expected <%s> is not correct to compare to <%s>", expected, dateTimeValue);
    }
  }
}
