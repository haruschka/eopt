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
package com.erichsteiger.eopt.app;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.bo.SystemInfo;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX Presentation Application
 */
public class PresentationApp extends Application {
  private static final Logger LOGGER = LoggerFactory.getLogger(PresentationApp.class);
  private static LocalDateTime startTime;
  private SystemInfo systemInfo = new SystemInfo();

  @Override
  public void start(Stage stage) {
    LOGGER.info("PresentationApp, JavaFX {} , running on Java {}, os {}, os version {}, os arch {}",
        systemInfo.javafxVersion(),
        systemInfo.javaVersion(),
        systemInfo.os(),
        systemInfo.osVersion(),
        systemInfo.osArch());
    try {
      stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon128.png")));
    } catch (Exception e) {
      LOGGER.error("cannot load icon", e);
    }

    showBasicStartupScreen(stage);
    LOGGER.info("fully started in {}ms", ChronoUnit.MILLIS.between(startTime, LocalDateTime.now()));
  }

  private void showBasicStartupScreen(Stage stage) {
    BasicStartupScreen main = new BasicStartupScreen();
    main.show(stage);
//    main.getBtnNew().setOnMouseClicked(e -> createNewSlideShow(stage));
//    main.getBtnOpen().setOnMouseClicked(e -> openSlideShow(stage));

  }

  public Optional<String> getExtensionByStringHandling(String filename) {
    return Optional.ofNullable(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1));
  }

  /**
   * to run the application without module-arguments use Main.main instead of this
   * main method.
   * 
   * @param args array of program arguments
   */
  public static void main(String[] args) {
    startTime = LocalDateTime.now();
    launch(args);
  }

}