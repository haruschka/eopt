package com.erichsteiger.eopt.app;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.app.slideshow.SlidePane;
import com.erichsteiger.eopt.bo.slide.SlideBO;
import com.erichsteiger.eopt.bo.slideshow.SlideShowBO;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SlideShowEditorPanel extends Pane {
  private static final Logger LOGGER = LoggerFactory.getLogger(SlideShowEditorPanel.class);
  private VBox thumbNails;
  private SlideShowBO bo;
  private File tempDir;
  private PresentingFileHandler presentingFileHandler = new PresentingFileHandler();
  private ScrollPane scrollPane;
  private ImageView imgView;
  private BorderPane bp = new BorderPane();
  private Pane thumbNailsPane;

  SlideShowEditorPanel(SlideShowBO bo, File tempDir, Menu menuSlide) {
    this.bo = bo;
    this.tempDir = tempDir;

    createThumbNailView();
    createSlideMenu(menuSlide);
    createSlideView();
    refreshThumbnails();
    setVisible(false);
    getChildren().add(bp);
  }

  private void createSlideView() {
    imgView = new ImageView(
        new Image(new File(tempDir.getAbsolutePath() + File.separator + bo.getSlides().get(0).getBackgroundImagePath())
            .toURI().toString()));
    imgView.setFitWidth(600);
    imgView.setPreserveRatio(true);
    bp.setCenter(imgView);
    BorderPane.setAlignment(imgView, Pos.CENTER);
  }

  @Override
  public void resize(double width, double height) {
    super.resize(width, height);
    LOGGER.debug("width {} height {}", width, height);
    thumbNailsPane.setMaxSize(230, height);
    thumbNailsPane.setPrefSize(230, height);
    scrollPane.setPrefSize(200, height);
    scrollPane.setMaxSize(200, height);
    imgView.setFitWidth(width - 250);
  }

  public void saveFile(String fileName) {
    presentingFileHandler.saveBO(tempDir, bo);
    presentingFileHandler.createPresentingFile(tempDir, fileName);
    bo.setChanged(false);
  }

  private void refreshThumbnails() {
    ContextMenu contextMenu = new ContextMenu();
    MenuItem deleteSlide = new MenuItem("Delete Slide");
    deleteSlide.setOnAction(e -> deleteSlide(e));
    contextMenu.getItems().add(deleteSlide);

    thumbNails.getChildren().removeAll(thumbNails.getChildren());
    for (SlideBO slide : bo.getSlides()) {
      File bgImageFile = new File(tempDir.getAbsolutePath() + File.separator + slide.getBackgroundImagePath());
      LOGGER.debug("bgImageFile {}", bgImageFile);
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

    MenuItem startSlideShow = new MenuItem("Start Slideshow");
    startSlideShow.setOnAction(e -> startSlideShow());
    startSlideShow.setAccelerator(KeyCombination.keyCombination("F5"));
    menuSlide.getItems().removeAll(menuSlide.getItems());
    menuSlide.getItems().addAll(createNewSlide, startSlideShow);

  }

  private void startSlideShow() {
    Stage stage = new Stage();
    stage.setTitle(bo.getTitle() == null ? "eOPT" : bo.getTitle().getText());
    stage.setFullScreenExitHint("");

    Pane slideShow = new SlidePane(
        new File(tempDir.getAbsolutePath() + File.separator + bo.getSlides().get(0).getBackgroundImagePath()).toURI()
            .toString(),
        tempDir);
    Scene scene = new Scene(slideShow, 1024, 575);
    if (bo.getStartFullscreen().booleanValue()) {
      stage.setMaximized(true);
      stage.setFullScreen(true);
    }
    scene.setFill(null);
    stage.setScene(scene);

    stage.show();
  }

  private void createNewSlide() {
    LOGGER.debug("create new slide");
    FileChooser fileChooser = new FileChooser();
    File newFile = fileChooser.showOpenDialog(this.getScene().getWindow());
    LOGGER.debug("selected new filename {}", newFile);
    if (newFile == null) {
      return;
    }
    bo.addSlide(new SlideBO(presentingFileHandler.copySlideToTempDir(newFile, tempDir)));
    refreshThumbnails();
  }

  private void createThumbNailView() {
    double rectHight = 200d * bo.getSlides().size();
    if (rectHight < getHeight() - 80) {
      rectHight = getHeight() - 80;
    }
    thumbNailsPane = new Pane();
    thumbNails = new VBox();
    thumbNails.setSpacing(20);
    thumbNails.setBorder(
        new Border(new BorderStroke(new Color(0, 0, 0, 0), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
            new BorderWidths(10))));
    scrollPane = new ScrollPane();
    scrollPane.setContent(thumbNails);
    scrollPane.setTranslateX(0);
    scrollPane.setTranslateY(0);
    scrollPane.setStyle("-fx-background: rgb(0,0,0,0.2);\n -fx-background-color: rgb(0,0,0,0.2)");

    scrollPane.setOnKeyPressed(e -> {
      if (e.isControlDown() && e.getCode() == KeyCode.DOWN) {
        moveSlideDown();
      } else if (e.isControlDown() && e.getCode() == KeyCode.UP) {
        moveSlideUp();
      }
    });

    thumbNailsPane.getChildren().add(scrollPane);
    bp.setLeft(thumbNailsPane);
  }

  private void moveSlideUp() {
    ImageView selectedImg = getSelectedSlide();
    if (selectedImg.getUserData() instanceof SlideBO slideBO) {
      int idx = thumbNails.getChildren().indexOf(selectedImg);
      if (idx != -1 && idx > 0) {
        thumbNails.getChildren().remove(idx);
        thumbNails.getChildren().add(idx - 1, selectedImg);
        SlideBO slideBo = bo.getSlides().get(idx);
        bo.getSlides().remove(idx);
        bo.getSlides().add(idx - 1, slideBo);
        bo.setChanged(true);
      }
    }
  }

  private void moveSlideDown() {
    ImageView selectedImg = getSelectedSlide();
    if (selectedImg.getUserData() instanceof SlideBO slideBO) {
      int idx = thumbNails.getChildren().indexOf(selectedImg);
      if (idx != -1 && idx < thumbNails.getChildren().size() - 1) {
        thumbNails.getChildren().remove(idx);
        thumbNails.getChildren().add(idx + 1, selectedImg);
        SlideBO slideBo = bo.getSlides().get(idx);
        bo.getSlides().remove(idx);
        bo.getSlides().add(idx + 1, slideBo);
        bo.setChanged(true);
      }
    }
  }

  private ImageView getSelectedSlide() {
    for (Node node : thumbNails.getChildren()) {
      if (node.getEffect() != null) {
        if (node instanceof ImageView imgView) {
          LOGGER.info("source {}", imgView);
          return imgView;
        }
      }
    }
    return null;
  }

  private void deleteSlide(ActionEvent e) {
    LOGGER.debug("delete slide {}", e);
    if (e.getSource() instanceof MenuItem item) {
      LOGGER.info("delete user-data {}", item.getUserData());
      if (item.getUserData() instanceof ImageView thumbImg) {
        LOGGER.info("thumbImg.getUserDate  {}", thumbImg.getUserData());
        if (thumbImg.getUserData() instanceof SlideBO slideBO) {
          bo.getSlides().remove(slideBO);
          new File(tempDir + File.separator + slideBO.getBackgroundImagePath()).delete();
        }
        thumbNails.getChildren().remove(thumbImg);
        bo.setChanged(true);
      }
    }
  }

  private void handleMouseClickOnThumbImage(MouseEvent e, ImageView img, ContextMenu contextMenu) {

    LOGGER.info("e {}", e);
    if (img.getEffect() == null) {
      for (Node node : thumbNails.getChildren()) {
        node.setEffect(null);
      }
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
    if (img.getUserData() instanceof SlideBO slideBO) {
      imgView.setImage(
          new Image(new File(tempDir.getAbsolutePath() + File.separator + slideBO.getBackgroundImagePath())
              .toURI().toString()));

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

  public boolean isChanged() {
    return bo.isChanged();
  }
}