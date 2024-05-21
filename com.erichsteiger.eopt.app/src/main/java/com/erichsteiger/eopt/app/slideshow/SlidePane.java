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
package com.erichsteiger.eopt.app.slideshow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.app.slideshow.screenobject.CustomImageView;
import com.erichsteiger.eopt.app.slideshow.screenobject.CustomLabel;
import com.erichsteiger.eopt.app.slideshow.screenobject.CustomMediaView;
import com.erichsteiger.eopt.app.slideshow.screenobject.CustomeTerminalView;
import com.erichsteiger.eopt.app.slideshow.screenobject.IRepositionable;
import com.erichsteiger.eopt.bo.screenobject.AbstractScreenObject;
import com.erichsteiger.eopt.bo.screenobject.ImageBO;
import com.erichsteiger.eopt.bo.screenobject.LabelBO;
import com.erichsteiger.eopt.bo.screenobject.MediaViewBO;
import com.erichsteiger.eopt.bo.screenobject.TerminalViewBO;
import com.erichsteiger.eopt.bo.slide.SlideBO;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SlidePane extends Pane {
  private static final Logger LOGGER = LoggerFactory.getLogger(SlidePane.class);
  private List<Node> temporaryObjects = new ArrayList<>();
  private File slideShowPath;
  private boolean editModeOn;
  private BackgroundImage backgroundImage;

  public SlidePane(String currentSlideURL, File slideShowPath) {
    this();
    setBackground(new Background(createImage(currentSlideURL)));
    this.slideShowPath = slideShowPath;
  }

  public SlidePane() {
    heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue,
        Number newValue) -> resize(getWidth(), newValue.doubleValue()));
    widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue,
        Number newValue) -> resize(newValue.doubleValue(), getHeight()));
  }

  @Override
  public void resize(double width, double height) {
    super.resize(width, height);
    LOGGER.debug("resize SlidePane width: {} height: {}", width, height);

    for (Node item : temporaryObjects) {
      LOGGER.debug("resize temporary objects {}", item);
      if (item instanceof IRepositionable repositionableObj) {
        repositionableObj.reposition();
      }
    }
  }

  protected BackgroundImage createImage(String url) {
    try {
      backgroundImage = new BackgroundImage(new Image(url), BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT,
          new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true),
          new BackgroundSize(100, 100, true, true, true, false));
      return backgroundImage;
    } catch (Exception e) {
      LOGGER.error("cannot load image from url {}", url, e);
    }
    return null;
  }

  public void add(Node node) {
    temporaryObjects.add(node);
    getChildren().add(node);
  }

  public void createCustomObjects(SlideBO info, ISlideNavigationListener slideNavigationListener) {
    if (info == null) {
      return;
    }
    for (AbstractScreenObject obj : info.getScreenObjects()) {
      LOGGER.info("createCustomObjects: {}", obj);
      if (obj instanceof LabelBO li) {
        createLabel(li);
      } else if (obj instanceof MediaViewBO mv) {
        createMediaViewObj(mv);
      } else if (obj instanceof ImageBO img) {
        creatImageViewObj(img);
      } else if (obj instanceof TerminalViewBO terminal) {
        createTerminalViewObj(terminal, slideNavigationListener);
      }

    }
  }

  private void createTerminalViewObj(TerminalViewBO terminal, ISlideNavigationListener slideNavigationListener) {
    CustomeTerminalView terminalView = new CustomeTerminalView(terminal, slideNavigationListener);
    add(terminalView);
    terminalView.reposition();
  }

  private void createLabel(LabelBO li) {
    CustomLabel l = new CustomLabel(li);
    add(l);
    l.reposition();
  }

  private void creatImageViewObj(ImageBO img) {
    File f = new File(img.getImageFile());
    if (!f.exists() && slideShowPath != null) {
      f = new File("./" + File.separator + img.getImageFile());
      if (!f.exists()) {
        f = new File(slideShowPath.getAbsolutePath() + File.separator + img.getImageFile());
      }
    }

    CustomImageView imgView = new CustomImageView(
        new Image(f.toURI().toString()), img);

    add(imgView);

  }

  private void createMediaViewObj(MediaViewBO mv) {
    String mediaSource = new File(slideShowPath.getAbsolutePath() + File.separator
        + mv.getVideoPath()).toURI().toString();
    if (mv.getVideoPath().startsWith("https://")) {
      mediaSource = mv.getVideoPath();
      LOGGER.info(mediaSource);
    } else if (mv.getVideoPath().startsWith(File.separator)) {
      mediaSource = new File(mv.getVideoPath()).toURI().toString();
    }

    CustomMediaView mediaView = new CustomMediaView(mediaSource, mv);
    add(mediaView);

    mediaView.reposition();
    mediaView.setFocusTraversable(false);

    if (mv.getAutoPlay().booleanValue()) {
      if (mv.getMaximizeToPlay().booleanValue()) {
        mediaView.maximize();
      }
      mediaView.getMediaPlayer().setRate(mv.getReplaySpeed());
      mediaView.getMediaPlayer().play();
      mediaView.setTranslateZ(10);
    }

    mediaView.setOnMouseClicked(e -> {
      if (editModeOn) {
        if (e.getButton() == MouseButton.SECONDARY) {
          LOGGER.debug("edit mode");
          mediaView.showEditoContextMenu(e.getSceneX(), e.getSceneY());
        }
      } else {
        LOGGER.info("mediaview clicked");
        if (mediaView.isMaximized()) {
          mediaView.setMaximized(false);
          mediaView.reposition();
        } else {
          if (e.getButton() != MouseButton.PRIMARY) {
            LOGGER.info("seek start position");
            mediaView.getMediaPlayer().seek(Duration.ZERO);
          }
          if (mv.getMaximizeToPlay().booleanValue()) {
            mediaView.maximize();
          }
          LOGGER.info("replay rate {}", mv.getReplaySpeed());
          mediaView.getMediaPlayer().play();
          mediaView.getMediaPlayer().setRate(mv.getReplaySpeed());
        }
      }
      e.consume();
    });

  }

  public void toggleEditMode(boolean editModeOn) {
    this.editModeOn = editModeOn;
    for (Node node : temporaryObjects) {
      if (node instanceof CustomMediaView mv) {
        mv.setEditable(editModeOn);
      }
    }
  }

  public Rectangle2D getImageRect() {
    double slidePaneHeight = getHeight();
    double slidePaneWidth = getWidth();
    Double bgImageHeight = backgroundImage == null ? null : backgroundImage.getImage().getHeight();
    Double bgImageWidth = backgroundImage == null ? null : backgroundImage.getImage().getWidth();
    if (bgImageHeight == null) {
      return new Rectangle2D(0, 0, 100, 100);
    }
    double imageRation = bgImageWidth / bgImageHeight;
    LOGGER.debug("imageRation {} paneRation {}", imageRation, slidePaneWidth / slidePaneHeight);
    double y = 0;
    double x = 0;
    double height = 0;
    double width = 0;
    if (imageRation > slidePaneWidth / slidePaneHeight) {
      x = 0;
      width = slidePaneWidth;
      height = slidePaneWidth / bgImageWidth * bgImageHeight;
      y = (slidePaneHeight - height) / 2;
    } else {
      y = 0;
      width = slidePaneHeight / bgImageHeight * bgImageWidth;
      height = slidePaneHeight;
      x = (slidePaneWidth - width) / 2;
    }
    Rectangle2D rect = new Rectangle2D(x, y, width, height);
    LOGGER.debug("rect {}", rect);
    return rect;
  }
}
