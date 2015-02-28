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
package org.assertj.db.api.assertions;

/**
 * Interface that represents a assert on a row.
 *
 * @param <T> The "self" type of this assertion class. Please read &quot;<a href="http://bit.ly/anMa4g"
 *            target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>&quot;
 *            for more details.
 * @author Régis Pouiller
 */
public interface AssertOnRow <T extends AssertOnRow<T>> {

  /**
   * Verifies that the size of a {@link org.assertj.db.type.Row} is equal to the number in parameter.
   * <p>
   * Example where the assertion verifies that the first row of the table has 8 columns :
   * </p>
   *
   * <pre><code class='java'>
   * assertThat(table).row().hasNumberOfColumns(8);
   * </code></pre>
   *
   * @param expected The number to compare to the size.
   * @return {@code this} assertion object.
   * @throws AssertionError If the size is different to the number in parameter.
   */
  public T hasNumberOfColumns(int expected);

  /**
   * Verifies that the values of a column are equal to values in parameter.
   * <p>
   * Example where the assertion verifies that the values in the first {@code Row} of the {@code Table} are equal to the
   * values in parameter :
   * </p>
   *
   * <pre><code class='java'>
   * assertThat(table).row().hasValuesEqualTo(1, &quot;Text&quot;, TimeValue.of(9, 1));
   * </code></pre>
   *
   * @param expected The expected values.
   * @return {@code this} assertion object.
   * @throws AssertionError If the value is not equal to the values in parameter.
   */
  public T hasValuesEqualTo(Object... expected);
}