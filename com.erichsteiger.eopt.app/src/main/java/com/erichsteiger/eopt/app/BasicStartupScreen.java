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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.bo.slideshow.SlideShowBO;
import com.erichsteiger.eopt.bo.slideshow.SlideShowType;
import com.erichsteiger.eopt.dao.SlideShowInfoIO;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BasicStartupScreen extends VBox {
  private static final String FONT = "Arial";
  private static final Logger LOGGER = LoggerFactory.getLogger(BasicStartupScreen.class);
  private Button btnNew;
  private Button btnOpen;
  private Stage stage;
  private HBox welcomePage;
  private File currentWorkFile;
  private File tempDir;

  BasicStartupScreen() {
    createMenu();

    welcomePage = new HBox();
    getChildren().add(welcomePage);
    welcomePage.setBorder(new Border(new BorderStroke(new Color(0.9, 0.9, 0.9, 0.0),
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(100))));

    welcomePage.setSpacing(50);
    BackgroundImage backgroundImage = new BackgroundImage(
        new Image(getClass().getClassLoader().getResourceAsStream("initBG.jpeg")), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true),
        new BackgroundSize(100, 100, true, true, false, true));

    setBackground(new Background(backgroundImage));

    welcomePage.setPrefHeight(1500);

    Label txtInfo = new Label(
        "Would you like to create a new presentation, based on a Slide-Deck exported from LibreOffice Impress or Powerpoint?");
    txtInfo.setFont(new Font(FONT, 20));
    txtInfo.setTextFill(Color.WHITE);
    txtInfo.setWrapText(true);
    txtInfo.setMaxWidth(523);
    Label txtInfo2 = new Label(
        "Would you like to open an existing presentation?");
    txtInfo2.setFont(new Font(FONT, 22));
    txtInfo2.setTextFill(Color.WHITE);
    txtInfo2.setWrapText(true);

    btnNew = new Button("Create New...");
    btnNew.setOnAction(e -> createNewSlideShow());
    btnOpen = new Button("Open");
    btnOpen.setDisable(true);
    VBox box1 = new VBox(txtInfo, btnNew);
    box1.setSpacing(50);
    box1.setAlignment(Pos.CENTER);

    VBox box2 = new VBox(txtInfo2, btnOpen);
    box2.setSpacing(50);
    box2.setAlignment(Pos.CENTER);

    Label helpLabel = new Label("""
        Press
        \tQ to exit
        \tF for Full-Screen
        \tR to restart slide-show
        \tEscape to leave Full-Screen
        \tright arrow to show next slide
        \tleft arrow to show previous slide""");
    helpLabel.setFont(new Font(FONT, 12));
    helpLabel.setTextFill(Color.WHITE);
    helpLabel.setWrapText(true);
    helpLabel.setMaxWidth(523);
    helpLabel.setMinWidth(220);
    VBox box3 = new VBox(helpLabel);
    box3.setSpacing(50);
    box3.setAlignment(Pos.CENTER);

    Separator separator1 = new Separator();
    separator1.setOrientation(Orientation.VERTICAL);

    Separator separator2 = new Separator();
    separator2.setOrientation(Orientation.VERTICAL);

    welcomePage.getChildren().add(box2);
    welcomePage.getChildren().add(separator1);
    welcomePage.getChildren().add(box1);
    welcomePage.getChildren().add(separator2);
    welcomePage.getChildren().add(box3);

    createVersionInfoLabel();
  }

  private void createNewSlideShow() {
    LOGGER.info("create new slide-show");
    FileChooser fileChooser = new FileChooser();
    File newFile = fileChooser.showSaveDialog(stage);
    LOGGER.info("selected new filename {}", newFile);
    if (newFile != null) {
      if (!newFile.getName().toLowerCase().endsWith("." + ConstValues.EOPT_FILE_SUFFIX)) {
        newFile = new File(newFile.getAbsolutePath() + "." + ConstValues.EOPT_FILE_SUFFIX);
      }
      LOGGER.info("newFile {}", newFile);
      SlideShowInfoIO io = new SlideShowInfoIO();
      if (newFile.getParentFile().list((File a1, String a2) -> a1.isDirectory() && a2.equals("slides")).length == 1) {
        LOGGER.info("found slides in subdir");
        SlideShowBO bo = io.createNewBO();
        bo.setPresentationType(SlideShowType.DEFAULT);
        io.writeJsonFile(newFile, bo);
//        openSlideShow(stage, newFile);
      } else if (newFile.getParentFile()
          .list((File a1, String a2) -> a2.startsWith("img") && a2.endsWith(".jpg")).length > 0) {
        LOGGER.info("found slides in dir, looks like impress");
        SlideShowBO bo = io.createNewBO();
        bo.setSlidesDir(".");
        bo.setPresentationType(SlideShowType.IMPRESS);
        io.writeJsonFile(newFile, bo);
//        openSlideShow(stage, newFile);
      } else if (newFile.getParentFile()
          .list((File a1, String a2) -> a2.startsWith("Slide") && a2.endsWith(".jpeg")).length > 0) {
        LOGGER.info("found slides in dir, looks like powerpoint export");
        SlideShowBO bo = io.createNewBO();
        bo.setSlidesDir(".");
        bo.setPresentationType(SlideShowType.DEFAULT);
        io.writeJsonFile(newFile, bo);
//        openSlideShow(stage, newFile);
      } else {
        Alert a = new Alert(AlertType.NONE);
        a.setAlertType(AlertType.INFORMATION);
        a.setHeaderText(
            "No JPEG Slides found. Please create the new file in the directory where your JPEG Slides are.");
        a.show();

        SlideShowBO bo = io.createNewBO();
        bo.setSlidesDir(".");
        bo.setPresentationType(SlideShowType.EXTENDED);
        io.writeJsonFile(newFile, bo);
        currentWorkFile = newFile;

        try {
          tempDir = Files.createTempDirectory("eopt-").toFile();
          tempDir.deleteOnExit();
        } catch (IOException e) {
          LOGGER.error(e.getMessage(), e);
        }
        io.writeJsonFile(new File(tempDir.getAbsolutePath() + File.separator + "slideshow.bo"), bo);
        welcomePage.setVisible(false);
      }
    }
  }

  private void createMenu() {
    MenuBar menuBar = new MenuBar();

    Menu menuFile = new Menu("_File");
    MenuItem createNew = new MenuItem("Create new");
    createNew.setOnAction(e -> createNewSlideShow());
    createNew.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(e -> System.exit(0));
    exit.setAccelerator(KeyCombination.keyCombination("Q"));
    menuFile.getItems().addAll(createNew, new SeparatorMenuItem(), exit);

    menuBar.getMenus().addAll(menuFile);
    this.getChildren().addAll(menuBar);
  }

  private void createVersionInfoLabel() {
    Label lblVersionInfo = new Label(readInfoFromRessourceFile("version.info"));
    lblVersionInfo.setBackground(null);
    lblVersionInfo.setFont(new Font(FONT, 12));
    lblVersionInfo.setTextFill(Color.WHITE);
    lblVersionInfo.setAlignment(Pos.BOTTOM_RIGHT);

    lblVersionInfo.setPrefHeight(100);
    lblVersionInfo.setPrefWidth(10000);
    getChildren().add(lblVersionInfo);
  }

  private String readInfoFromRessourceFile(String fileName) {
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
      return br.readLine();
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return "";
  }

  public void show(Stage stage) {
    Scene scene = new Scene(this, 1024, 575);
    scene.getStylesheets().add("startup.css");

    stage.setTitle("eOPT");
    scene.setFill(null);
    stage.setScene(scene);
    stage.show();

    this.stage = stage;
  }
}
