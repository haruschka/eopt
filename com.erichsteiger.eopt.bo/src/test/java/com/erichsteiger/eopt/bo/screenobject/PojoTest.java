package com.erichsteiger.eopt.bo.screenobject;

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
    listOfPojos.add(PojoClassFactory.getPojoClass(ImageBO.class));
    listOfPojos.add(PojoClassFactory.getPojoClass(MediaViewBO.class));
    listOfPojos.add(PojoClassFactory.getPojoClass(TerminalViewBO.class));
    validator.validate(listOfPojos);
  }
}
