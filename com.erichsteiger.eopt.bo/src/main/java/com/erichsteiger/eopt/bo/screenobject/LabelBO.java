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
package com.erichsteiger.eopt.bo.screenobject;

import com.erichsteiger.eopt.bo.TextShadowEffect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javafx.scene.text.FontWeight;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class LabelBO extends AbstractScreenObject {
  private String text = "";
  private String fontName = "Arial";
  private int fontSize = 12;
  private FontWeight fontWeight = FontWeight.NORMAL;
  private String bgColor = "BLACK";
  private String fgColor = "WHITE";
  private String shadowColor = "LIGHTGRAY";
  private TextShadowEffect shadowEffect = TextShadowEffect.NONE;
  @Deprecated(since = "1.5", forRemoval = true)
  private Boolean textShadow = Boolean.FALSE;

  public void setShadowColor(String shadowColor) {
    this.shadowColor = shadowColor;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getFontName() {
    return fontName;
  }

  public void setFontName(String fontName) {
    this.fontName = fontName;
  }

  public String getBgColor() {
    return bgColor;
  }

  public void setBgColor(String bgColor) {
    this.bgColor = bgColor;
  }

  public String getFgColor() {
    return fgColor;
  }

  public void setFgColor(String fgColor) {
    this.fgColor = fgColor;
  }

  public String getShadowColor() {
    return shadowColor;
  }

  @Deprecated(since = "1.5", forRemoval = true)
  public Boolean getTextShadow() {
    return textShadow;
  }

  @Deprecated(since = "1.5")
  public void setTextShadow(Boolean textShadow) {
    this.textShadow = textShadow;
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public FontWeight getFontWeight() {
    return fontWeight;
  }

  public void setFontWeight(FontWeight fontWeight) {
    this.fontWeight = fontWeight;
  }

  public TextShadowEffect getShadowEffect() {
    return shadowEffect;
  }

  public void setShadowEffect(TextShadowEffect shadowEffect) {
    this.shadowEffect = shadowEffect;
  }
}
