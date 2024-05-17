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
package com.erichsteiger.eopt.bo.slideshow;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlideFooter {
  private static final Logger LOGGER = LoggerFactory.getLogger(SlideFooter.class);
  public static final String SYSDATE = "$sysdate";
  public static final String AUTHOR = "$author";
  public static final String PAGE_NR = "$page";

  private String footerLeft = SYSDATE;
  private String footerMiddle = AUTHOR;
  private String footerRight = PAGE_NR;

  private String textColor = "BLACK";
  private int fontSize = 20;
  private Boolean footerVisible;

  public String getFooterLeft() {
    return footerLeft;
  }

  public void setFooterLeft(String footerLeft) {
    this.footerLeft = footerLeft;
  }

  public String getFooterMiddle() {
    return footerMiddle;
  }

  public void setFooterMiddle(String footerMiddle) {
    this.footerMiddle = footerMiddle;
  }

  public String replacePlaceholders(String s, int pageNo, String author) {
    LOGGER.trace("replacePlaceholders {} {} {}", s, pageNo, author);
    return s.replace(SlideFooter.SYSDATE, LocalDate.now().toString())
        .replace(SlideFooter.AUTHOR, author == null ? "Erich Steiger" : author)
        .replace(SlideFooter.PAGE_NR, "" + pageNo);
  }

  public String getFooterRight() {
    return footerRight;
  }

  public void setFooterRight(String footerRight) {
    this.footerRight = footerRight;
  }

  public int getFontSize() {
    return fontSize;
  }

  public String getTextColor() {
    return textColor;
  }

  public void setTextColor(String textColor) {
    this.textColor = textColor;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public Boolean getFooterVisible() {
    return footerVisible;
  }

  public void setFooterVisible(Boolean footerVisible) {
    this.footerVisible = footerVisible;
  }
}
