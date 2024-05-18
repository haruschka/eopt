package com.erichsteiger.eopt.bo.slideshow;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SlideShowBOTest {
  @Test
  void testConstructor() {
    SlideShowBO bo = new SlideShowBO();
    Assertions.assertNotNull(bo);
    Assertions.assertNotNull(bo.getSlides());
  }

  @Test
  void testSlides() {
    SlideShowBO bo = new SlideShowBO();
    Assertions.assertNotNull(bo.getSlides());
    Assertions.assertTrue(bo.getSlides().isEmpty());
    bo.addSlide(new File("."));
    Assertions.assertFalse(bo.getSlides().isEmpty());
  }

  @Test
  void testToolbar() {
    SlideShowBO bo = new SlideShowBO();
    Assertions.assertNotNull(bo.getToolBar());
  }

  @Test
  void testChanged() {
    SlideShowBO bo = new SlideShowBO();
    Assertions.assertFalse(bo.isChanged());
    bo.setChanged(true);
    Assertions.assertTrue(bo.isChanged());
  }

  @Test
  void testHiddenStamp() {
    SlideShowBO bo = new SlideShowBO();
    Assertions.assertNull(bo.getShowHiddenStamps());
    bo.setShowHiddenStamps(true);
    Assertions.assertTrue(bo.getShowHiddenStamps());
  }
}
