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
package org.assertj.db.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.db.common.AbstractTest;
import org.junit.Test;

/**
 * Tests on the list of changes.
 * 
 * @author Régis Pouiller.
 * 
 */
public class Changes_GetChangesList_Test extends AbstractTest {

  /**
   * This method test when there is not change.
   */
  @Test
  public void test_when_there_is_no_change() {
    Changes changes = new Changes(source);
    changes.setStartPointNow();
    changes.setEndPointNow();
    assertThat(changes.getChangesList()).hasSize(0);
  }
}