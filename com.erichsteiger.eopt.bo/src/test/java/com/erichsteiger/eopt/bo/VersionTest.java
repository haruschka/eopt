package com.erichsteiger.eopt.bo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VersionTest {
  @Test
  void testCompare1() {
    Version v1 = new Version("1.0");
    Version v2 = new Version("2.0");
    Assertions.assertTrue(v1.compareTo(v2) < 0);
  }

  @Test
  void testCompare2() {
    Version v1 = new Version("1.0");
    Version v2 = new Version("1.1");
    Assertions.assertTrue(v1.compareTo(v2) < 0);
  }

  @Test
  void testCompare3() {
    Version v1 = new Version("1.9");
    Version v2 = new Version("1.11");
    Assertions.assertTrue(v1.compareTo(v2) < 0);
  }

  @Test
  void testCompare4() {
    Version v1 = new Version("9.0");
    Version v2 = new Version("11.0");
    Assertions.assertTrue(v1.compareTo(v2) < 0);
  }

  @Test
  void testCompare5() {
    Version v1 = new Version("9.0.2");
    Version v2 = new Version("11.0");
    Assertions.assertTrue(v1.compareTo(v2) < 0);
  }

  @Test
  void testCompare6() {
    Version v1 = new Version("11.0.2");
    Version v2 = new Version("11.0");
    Assertions.assertTrue(v1.compareTo(v2) < 0);
  }

  @Test
  void testCompare7() {
    Version v1 = new Version("11.0.2");
    Version v2 = new Version("11.0.2");
    Assertions.assertTrue(v1.compareTo(v2) == 0);
  }

  @Test
  void testCompare8() {
    Version v1 = new Version("19.0.1");
    Version v2 = new Version("11.0.2");
    Assertions.assertTrue(v1.compareTo(v2) > 0);
  }

  @Test
  void testToString1() {
    Version v1 = new Version("11.0.2");
    Assertions.assertEquals("11.0.2", v1.toString());
  }

  @Test
  void testToString2() {
    Version v1 = new Version();
    Assertions.assertTrue(v1.toString().equals(""));
  }

  void testHashCode() {
    Version v1 = new Version("19.0.1");
    Version v2 = new Version("19.0.1");
    Assertions.assertEquals(v1.hashCode(), v2.hashCode());
  }

  void testEquals() {
    Version v1 = new Version("19.0.1");
    Version v2 = new Version("19.0.1");
    Assertions.assertEquals(v1, v2);
  }

}
