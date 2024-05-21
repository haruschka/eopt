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
package com.erichsteiger.eopt.app.slideshow.screenobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.app.slideshow.SlidePane;
import com.erichsteiger.eopt.bo.screenobject.LabelBO;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class CustomLabel extends Pane implements IRepositionable {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomLabel.class);

  private LabelBO labelInfo;
  private Label label1;
  private Label label2;

  public CustomLabel(String text) {
    label2 = new Label(text);
    getChildren().add(label2);
    label1 = new Label(text);
    getChildren().add(label1);

    labelInfo = new LabelBO();
    labelInfo.setText(text);
    label1.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue,
        Number newValue) -> {
      setMinHeight(newValue.doubleValue() * 1.2);
      setMaxHeight(newValue.doubleValue() * 1.2);
    });
  }

  public CustomLabel(LabelBO li) {
    this(li == null ? "" : li.getText());
    if (li != null) {
      setColor(li);
      this.labelInfo = li;
    }
  }

  private void createDropShadow(Color shadowColor) {
    label2.setVisible(false);
    DropShadow dropShadow = new DropShadow();
    dropShadow.setRadius(5.0);
    dropShadow.setOffsetX(2.0);
    dropShadow.setOffsetY(2.0);
    dropShadow.setColor(shadowColor);
    label1.setEffect(dropShadow);
  }

  private void createInnerShadow(Color shadowColor) {
    label2.setVisible(false);
    InnerShadow dropShadow = new InnerShadow();
    dropShadow.setRadius(5.0);
    dropShadow.setOffsetX(2.0);
    dropShadow.setOffsetY(2.0);
    dropShadow.setColor(shadowColor);
    label1.setEffect(dropShadow);
  }

  @Override
  public void reposition() {
    if (getParent() instanceof SlidePane slidePane) {
      Rectangle2D slideImageRect = slidePane.getImageRect();

      double heightRatio = slideImageRect.getHeight() / Screen.getPrimary().getBounds().getHeight();
      double widthRatio = slideImageRect.getWidth() / Screen.getPrimary().getBounds().getWidth();

      double fontRatio = heightRatio;
      if (heightRatio < widthRatio) {
        fontRatio = widthRatio;
      }
      LOGGER.debug("font-ration {}", fontRatio);
      LOGGER.debug("parent is {}, slidePaneWidth: {} slidePaneHeight: {}", getParent(), slideImageRect.getWidth(),
          slideImageRect.getHeight());
      setTranslateX(slideImageRect.getWidth() * labelInfo.getPosition().getX());
      setTranslateY(slideImageRect.getHeight() * labelInfo.getPosition().getY());

      label1.setFont(Font.font(labelInfo.getFontName(), labelInfo.getFontWeight(),
          labelInfo.getFontSize() * fontRatio));
      label2.setFont(Font.font(labelInfo.getFontName(), labelInfo.getFontWeight(),
          labelInfo.getFontSize() * fontRatio));

      label1.setTranslateX(9 * fontRatio);
      label1.setTranslateY(9 * fontRatio);
      label2.setTranslateX(10 * fontRatio);
      label2.setTranslateY(10 * fontRatio);

      label1.setTranslateZ(100);
      label2.setTranslateZ(90);
      setPrefHeight(label1.getHeight());
      setMaxHeight(label1.getHeight());
    }
  }

  public void setText(String text) {
    label1.setText(text);
    label2.setText(text);
    setMinHeight(label1.getHeight() * 1.2);
  }

  @Override
  public String toString() {
    return "CustomLabel: " + hashCode() + " - " + label1.getText();
  }

  public void setColor(LabelBO bo) {
    label1.setTextFill(Color.valueOf(bo.getFgColor()));
    label2.setTextFill(Color.valueOf(bo.getShadowColor()));
    switch (bo.getShadowEffect()) {
    case NONE:
      label2.setVisible(false);
      break;
    case BASIC:
      label2.setVisible(true);
      break;
    case DROP_SHADOW:
      createDropShadow(Color.valueOf(bo.getShadowColor()));
      break;
    case INNER_SHADOW:
      createInnerShadow(Color.valueOf(bo.getShadowColor()));
      break;
    default:
      label2.setVisible(false);
      break;
    }
  }

}
