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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.app.slideshow.SlidePane;
import com.erichsteiger.eopt.bo.screenobject.MediaViewBO;

import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.util.Duration;

public class CustomMediaView extends MediaView implements IRepositionable {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomMediaView.class);

  private MediaViewBO mv;
  private boolean videoPlayed;
  private boolean maximized;
  private boolean editable;

  public CustomMediaView(String mediaSource, MediaViewBO mv) {
    super(new MediaPlayer(new Media(mediaSource)));
    this.mv = mv;

    setOnMousePressed(e -> {
      if (editable && e.getButton() == MouseButton.PRIMARY) {
        setCursor(Cursor.MOVE);
      }
    });
    setOnMouseDragged(e -> {
      if (editable && e.getButton() == MouseButton.PRIMARY) {
        LOGGER.debug("move {} {}", e.getSceneX(), e.getSceneY());
        setTranslateX(e.getSceneX());
        setTranslateY(e.getSceneY());
      }
    });
    setOnMouseReleased(e -> {
      if (editable && e.getButton() == MouseButton.PRIMARY) {
        setCursor(Cursor.DEFAULT);
        mv.getPosition().setX(e.getSceneX() / Screen.getPrimary().getBounds().getWidth());
        mv.getPosition().setY(e.getSceneY() / Screen.getPrimary().getBounds().getHeight());
        LOGGER.debug("position p {}", mv.getPosition());
      }
    });

    getMediaPlayer().setOnEndOfMedia(() -> {
      setVideoPlayed(true);
      if (mv.getAutoRepeat().booleanValue()) {
        LOGGER.info("repeate");
        getMediaPlayer().seek(Duration.ZERO);
        play();
        return;
      }
      if (mv.getMinimizeAfterPlay().booleanValue()) {
        setMaximized(false);
        reposition();
      }
    });
  }

  @Override
  public void reposition() {
    if (!maximized && getParent() instanceof SlidePane slidePane) {
      Rectangle2D slidePaneRect = slidePane.getImageRect();

      LOGGER.debug("parent is {}, slidePaneWidth: {} slidePaneHeight: {}", getParent(), slidePaneRect.getWidth(),
          slidePaneRect.getHeight());

      positionMediaView(slidePaneRect.getWidth() * mv.getPosition().getX(),
          slidePaneRect.getHeight() * mv.getPosition().getY(),
          slidePaneRect.getWidth() * mv.getWidth());
    } else {
      maximize();
    }
  }

  private void positionMediaView(double x, double y, double width) {
    LOGGER.info("x: {} y: {} width: {}", x, y, width);
    setTranslateX(x);
    setTranslateY(y);
    setFitWidth(width);
  }

  public void maximize() {
    LOGGER.info("maximize");
    if (getParent() instanceof SlidePane slidePane) {
      Rectangle2D rect = slidePane.getImageRect();
      positionMediaView(rect.getWidth() * mv.getMaximizedPosition().getX(),
          rect.getHeight() * mv.getMaximizedPosition().getY(),
          rect.getWidth() * mv.getMazimizedWidth());
    }

    maximized = true;
  }

  public boolean hasVideoPlayed() {
    return videoPlayed;
  }

  public void setVideoPlayed(boolean videoPlayed) {
    this.videoPlayed = videoPlayed;
  }

  public void play() {
    if (mv.getMaximizeToPlay().booleanValue()) {
      maximize();
    }
    getMediaPlayer().setRate(mv.getReplaySpeed());
    getMediaPlayer().play();
    setTranslateZ(10);
  }

  public void showEditoContextMenu(double x, double y) {
    LOGGER.info("show context menu");

    getContextMenu().show(this, x, y);
  }

  private ContextMenu getContextMenu() {
    ContextMenu contextMenu = new ContextMenu();
    MenuItem selectMovieFile = new MenuItem("Select File");
    selectMovieFile.setOnAction(e -> selectFile());

    CheckMenuItem maximizeToPlay = new CheckMenuItem("Maximized while playing");
    maximizeToPlay.setSelected(mv.getMaximizeToPlay());
    maximizeToPlay.setOnAction(e -> {
      LOGGER.debug("mv maximized {}", mv.getMaximizeToPlay());
      maximizeToPlay.setSelected(mv.getMaximizeToPlay());
      mv.setMaximizeToPlay(!maximizeToPlay.isSelected());
      LOGGER.debug("mv maximized {}", mv.getMaximizeToPlay());
    });

    contextMenu.getItems().addAll(selectMovieFile, maximizeToPlay);
    return contextMenu;
  }

  private void selectFile() {
    FileChooser fc = new FileChooser();
    File file = fc.showOpenDialog(this.getScene().getWindow());
    if (file != null) {
      mv.setVideoPath(file.getAbsolutePath());
    }
  }

  public boolean isMaximized() {
    return maximized;
  }

  public void setMaximized(boolean maximized) {
    this.maximized = maximized;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;

    if (editable) {

    }
  }
}
