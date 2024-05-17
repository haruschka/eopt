/*
 * Copyright 2024 Erich Steiger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.erichsteiger.eopt.bo.light;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

class PojoTest {

  @Test
  void testPojoStructureAndBehavior() {
    Validator validator = ValidatorBuilder.create()
        // Add Rules to validate structure for POJO_PACKAGE
        // See com.openpojo.validation.rule.impl for more ...
        .with(new GetterMustExistRule()).with(new SetterMustExistRule())
        // Add Testers to validate behaviour for POJO_PACKAGE
        // See com.openpojo.validation.test.impl for more ...
        .with(new SetterTester()).with(new GetterTester()).build();

    List<PojoClass> listOfPojos = new ArrayList<>();
    listOfPojos.add(PojoClassFactory.getPojoClass(FlashLightBO.class));
    validator.validate(listOfPojos);
  }
}
