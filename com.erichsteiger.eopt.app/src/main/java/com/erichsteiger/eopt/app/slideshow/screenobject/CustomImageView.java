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

import com.erichsteiger.eopt.app.slideshow.SlidePane;
import com.erichsteiger.eopt.bo.screenobject.ImageBO;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImageView extends ImageView implements IRepositionable {

  private ImageBO bo;

  public CustomImageView(Image image, ImageBO bo) {
    super(image);
    this.bo = bo;
  }

  @Override
  public void reposition() {
    if (getParent() instanceof SlidePane slidePane) {
      Rectangle2D rect = slidePane.getImageRect();
      double slidePaneWidth = rect.getWidth();

      setX(bo.getPosition().getX());
      setY(bo.getPosition().getY());
      setPreserveRatio(true);
      setFitWidth(slidePaneWidth * bo.getWidth());
    }
  }

}
