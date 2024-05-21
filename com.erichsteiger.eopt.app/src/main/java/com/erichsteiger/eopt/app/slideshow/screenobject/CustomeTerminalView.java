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

import com.erichsteiger.eopt.app.slideshow.ISlideNavigationListener;
import com.erichsteiger.eopt.app.slideshow.SlidePane;
import com.erichsteiger.eopt.bo.screenobject.TerminalViewBO;
import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CustomeTerminalView extends Pane implements IRepositionable {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomeTerminalView.class);

  private TerminalViewBO bo;
  private TabPane tabPane;
  private boolean maximized;
  private TerminalTab terminal;
  private TerminalConfig darkConfig;

  public CustomeTerminalView(TerminalViewBO bo, ISlideNavigationListener slideNavigationListener) {
    this.bo = bo;

    darkConfig = new TerminalConfig();
    darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
    darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
    darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));
    darkConfig.setFontSize(24);
    tabPane = new TabPane();

    tabPane.setFocusTraversable(true);
    this.getChildren().add(tabPane);

    TerminalBuilder terminalBuilder = new TerminalBuilder(darkConfig);
//    terminalBuilder.setTerminalPath(Path.of(URI.create("file:/home/erich/")));
    terminal = terminalBuilder.newTerminal();
    terminal.setText(bo.getTitle());
    terminal.setClosable(false);
    terminal.getTerminalConfig().setFontSize(20);

    terminal.onTerminalFxReady(() -> {
      terminal.getTerminal().command(bo.getCmd());
    });

    terminal.getTerminal().setOnKeyPressed(e -> {
      LOGGER.trace("setOnKeyPressed src: {} text: {} eventtype: {} code: {} ctrl {}", e.getSource(), e.getCharacter(),
          e.getEventType(),
          e.getCode(), e.isControlDown());
      e.consume();
    });

    terminal.getTerminal().setOnKeyTyped(e -> {
      LOGGER.trace("setOnKeyTyped src: {} text: {} eventtype: {} code: {}", e.getSource(), e.getCharacter(),
          e.getEventType(),
          e.getCode());
    });
    terminal.getTerminal().setOnKeyReleased(e -> {
      LOGGER.trace("setOnKeyReleased src: {} text: {} eventtype: {} code: {} ctrl {} alt {}", e.getSource(),
          e.getCharacter(),
          e.getEventType(),
          e.getCode(), e.isControlDown(), e.isAltDown());
      if (e.isAltDown() && e.getCode() == KeyCode.LEFT) {
        if (slideNavigationListener != null) {
          e.consume();
          slideNavigationListener.previousSlide();
        }
      } else if (e.isAltDown() && e.getCode() == KeyCode.RIGHT) {
        if (slideNavigationListener != null) {
          e.consume();
          slideNavigationListener.nextSlide();
        }
      }
    });

    tabPane.getTabs().add(terminal);
  }

  @Override
  public void reposition() {
    if (getParent() instanceof SlidePane slidePane) {
      Rectangle2D rect = slidePane.getImageRect();
      double slidePaneWidth = rect.getWidth();

      if (maximized) {
        tabPane.setTranslateX(0);
        tabPane.setTranslateY(0);
        tabPane.setPrefWidth(rect.getWidth());
        tabPane.setPrefHeight(rect.getHeight());
        tabPane.setOnKeyPressed(e -> {
          e.consume();
        });

      } else {
        setTranslateX(rect.getWidth() * bo.getPosition().getX());
        setTranslateY(rect.getHeight() * bo.getPosition().getY());

        tabPane.setPrefWidth(slidePaneWidth * bo.getWidth());
        tabPane.setPrefHeight(rect.getHeight() * bo.getHeight());
        tabPane.setTranslateX(0);
        tabPane.setTranslateY(0);
        tabPane.setOnKeyPressed(e -> {
        });
      }
    }
  }

}
