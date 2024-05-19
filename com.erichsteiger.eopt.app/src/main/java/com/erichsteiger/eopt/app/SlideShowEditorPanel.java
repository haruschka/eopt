package com.erichsteiger.eopt.app;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.bo.slide.SlideBO;
import com.erichsteiger.eopt.bo.slideshow.SlideShowBO;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class SlideShowEditorPanel extends HBox {
  private static final Logger LOGGER = LoggerFactory.getLogger(SlideShowEditorPanel.class);
  private VBox thumbNails;
  private SlideShowBO bo;
  private File tempDir;
  private PresentingFileHandler presentingFileHandler = new PresentingFileHandler();

  SlideShowEditorPanel(SlideShowBO bo, File tempDir, Menu menuSlide) {
    this.bo = bo;
    this.tempDir = tempDir;

    creatThumbNailView();
    createSlideMenu(menuSlide);

    refreshThumbnails();
    setVisible(false);
  }

  private void refreshThumbnails() {
    ContextMenu contextMenu = new ContextMenu();
    MenuItem deleteSlide = new MenuItem("Delete Slide");
    deleteSlide.setOnAction(e -> deleteSlide(e));
    contextMenu.getItems().add(deleteSlide);

    thumbNails.getChildren().removeAll(thumbNails.getChildren());
    for (SlideBO slide : bo.getSlides()) {
      File bgImageFile = new File(tempDir.getAbsolutePath() + File.separator + slide.getBackgroundImagePath());
      LOGGER.info("bgImageFile {}", bgImageFile);
      ImageView img = new ImageView(
          bgImageFile.toURI().toString());
      img.setUserData(slide);
      img.setFitWidth(160);
      img.setPreserveRatio(true);
      img.setOnMouseClicked(e -> handleMouseClickOnThumbImage(e, img, contextMenu));
      thumbNails.getChildren().add(img);
    }
  }

  private void createSlideMenu(Menu menuSlide) {
    MenuItem createNewSlide = new MenuItem("New Slide");
    createNewSlide.setOnAction(e -> createNewSlide());
    createNewSlide.setAccelerator(KeyCombination.keyCombination("CTRL+M"));
    menuSlide.getItems().removeAll(menuSlide.getItems());
    menuSlide.getItems().addAll(createNewSlide);

  }

  private void createNewSlide() {
    LOGGER.info("create new slide");
    FileChooser fileChooser = new FileChooser();
    File newFile = fileChooser.showOpenDialog(this.getScene().getWindow());
    LOGGER.info("selected new filename {}", newFile);
    if (newFile == null) {
      return;
    }
    bo.addSlide(new SlideBO(presentingFileHandler.copySlideToTempDir(newFile, tempDir)));
    refreshThumbnails();
  }

  private void creatThumbNailView() {
    double rectHight = 200d * bo.getSlides().size();
    if (rectHight < getHeight() - 80) {
      rectHight = getHeight() - 80;
    }
    thumbNails = new VBox();
    thumbNails.setSpacing(20);
    thumbNails.setBorder(
        new Border(new BorderStroke(new Color(0, 0, 0, 0), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
            new BorderWidths(10))));
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setPrefSize(200, this.getHeight());
    scrollPane.setContent(thumbNails);
    scrollPane.setTranslateX(0);
    scrollPane.setTranslateY(0);
    scrollPane.setStyle("-fx-background: rgb(0,0,0,0.2);\n -fx-background-color: rgb(0,0,0,0.2)");
    getChildren().add(scrollPane);
  }

  private void deleteSlide(ActionEvent e) {
    LOGGER.debug("delete slide {}", e);
    if (e.getSource() instanceof MenuItem item) {
      LOGGER.debug("delete user-data {}", item.getUserData());
      if (item.getUserData() instanceof ImageView thumbImg) {
        thumbNails.getChildren().remove(thumbImg);
      }
    }
  }

  private void handleMouseClickOnThumbImage(MouseEvent e, ImageView img, ContextMenu contextMenu) {

    LOGGER.info("e {}", e);
    if (img.getEffect() == null) {
      img.setEffect(createLightEffect());
    } else {
      if (e.getButton() == MouseButton.PRIMARY) {
        img.setEffect(null);
      }
    }
    if (e.getButton() == MouseButton.SECONDARY) {
      contextMenu.getItems().get(0).setUserData(img);
      contextMenu.show(img, e.getScreenX(), e.getScreenY());
    }
  }

  private Effect createLightEffect() {
    Light.Distant light = new Light.Distant();
    light.setAzimuth(45.0);
    light.setElevation(130.0);
    Lighting lighting = new Lighting();
    lighting.setLight(light);
    return lighting;
  }
}