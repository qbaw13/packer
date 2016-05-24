package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.MainModel;
import service.ItemsDistribution;
import stub.PackResultStub;
import sun.plugin.javascript.navig.Anchor;
import utility.ShapeCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainController {

    MainModel mainModel;
    @FXML
    TabPane binTabPane;
    @FXML
    TextArea shapesTextArea;
    @FXML
    TextField widthBinTextField;
    @FXML
    TextField heightBinTextField;
    @FXML
    Button stopButton;
    @FXML
    Button packButton;
    private Rectangle bin;



    public MainController(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void onPack() {
        packButton.setDisable(true);
        stopButton.setDisable(false);
        AnchorPane anchorPane = (AnchorPane) binTabPane.getSelectionModel().getSelectedItem().getContent();
        initBin();
        initBinScale(anchorPane.getWidth(), anchorPane.getHeight());
        anchorPane.getChildren().add(bin);

        String shapesData = shapesTextArea.getText();
//        ItemsDistribution bestItemsDistribution = mainModel.packShapes(new Dimension2D(bin.getWidth(), bin.getHeight()), ShapeCreator.createShapes(shapesData));
        mainModel.packShapes(new Dimension2D(bin.getWidth(), bin.getHeight()), ShapeCreator.createBlocks(shapesData));

        addColors(PackResultStub.itemsDistribution.getShapes());
        anchorPane.getChildren().addAll(PackResultStub.itemsDistribution.getShapes());
        scaleShapes(PackResultStub.itemsDistribution.getShapes(), anchorPane.getWidth(), anchorPane.getHeight());
        addListeners(anchorPane, PackResultStub.itemsDistribution);
    }

    @FXML void onStop() {
        packButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private void addColors(List<Shape> shapes) {
        Random rand = new Random();
        for(Shape shape : shapes) {
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            shape.setFill(new Color(r, g, b, 0.6));
            shape.setStroke(Color.BLACK);
        }
    }

    private void initBin() {
        double width = Double.parseDouble(widthBinTextField.getCharacters().toString());
        double height = Double.parseDouble(heightBinTextField.getCharacters().toString());
        bin = new Rectangle(width, height);
        bin.setFill(Color.WHITE);
        bin.setStroke(Color.DARKGREY);
    }

    private void initBinScale(double paneWidth, double paneHeight) {
        double scaleX = paneWidth / bin.getWidth();
        double scaleY = paneHeight / bin.getHeight();
        scaleRectangleSides(Math.min(scaleX, scaleY), bin);
        centerBinInPane(paneWidth, paneHeight);
    }

    private void addListeners(Pane pane, ItemsDistribution itemsDistribution) {
        ChangeListener<Number> listenerX = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                transformX(itemsDistribution.getShapes(), pane.getWidth(), pane.getHeight());
            }
        };

        ChangeListener<Number> listenerY = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                transformY(itemsDistribution.getShapes(), pane.getWidth(), pane.getHeight());
            }
        };

        pane.getScene().widthProperty().addListener(listenerX);
        pane.getScene().heightProperty().addListener(listenerY);
    }

    private void scaleShapes(List<Shape> shapes, double paneWidth, double paneHeight) {
        Double width = Double.parseDouble(widthBinTextField.getCharacters().toString());
        Double height = Double.parseDouble(heightBinTextField.getCharacters().toString());

        double scaleX = paneWidth / width;
        double scaleY = paneHeight / height;

        for(Shape shape : shapes) {
            if(shape instanceof Rectangle) {
                scaleRectangleSides(Math.min(scaleX, scaleY), (Rectangle) shape);
                scaleRectangleCoordinates(Math.min(scaleX, scaleY), (Rectangle) shape);
                ((Rectangle) shape).setX(((Rectangle) shape).getX() + bin.getX());
                ((Rectangle) shape).setY(((Rectangle) shape).getY() + bin.getY());
            }
        }
    }

    private void transformX(List<Shape> shapes, double paneWidth, double paneHeight) {
        double scaleX = paneWidth / bin.getWidth();
        double scaleY = paneHeight / bin.getHeight();

        if(scaleX < scaleY) {
            for(Shape shape : shapes) {
                if(shape instanceof Rectangle) {
                    scaleRectangleSides(scaleX, (Rectangle) shape);
                    scaleRectangleCoordinates(scaleX, (Rectangle) shape);
                }
            }
            scaleRectangleSides(scaleX, bin);
            scaleRectangleCoordinates(scaleX, bin);
        }

        centerShapesInBin(shapes, paneWidth, paneHeight);
        centerBinInPane(paneWidth, paneHeight);
    }

    private void transformY(List<Shape> shapes, double paneWidth, double paneHeight) {
        double scaleX = paneWidth / bin.getWidth();
        double scaleY = paneHeight / bin.getHeight();

        if(scaleY < scaleX) {
            for(Shape shape : shapes) {
                if(shape instanceof Rectangle) {
                    scaleRectangleSides(scaleY, (Rectangle) shape);
                    scaleRectangleCoordinates(scaleY, (Rectangle) shape);
                }
            }
            scaleRectangleSides(scaleY, bin);
            scaleRectangleCoordinates(scaleY, bin);
        }

        centerShapesInBin(shapes, paneWidth, paneHeight);
        centerBinInPane(paneWidth, paneHeight);
    }

    private void centerBinInPane(double paneWidth, double paneHeight) {
        bin.setX(paneWidth/2 - bin.getWidth()/2);
        bin.setY(paneHeight/2 - bin.getHeight()/2);
    }

    private void centerShapesInBin(List<Shape> shapes, double paneWidth, double paneHeight) {
        double binShiftX = (paneWidth/2 - bin.getWidth()/2) - bin.getX();
        double binShiftY = (paneHeight/2 - bin.getHeight()/2) - bin.getY();
        for(Shape shape : shapes) {
            ((Rectangle) shape).setX(((Rectangle) shape).getX() + binShiftX);
            ((Rectangle) shape).setY(((Rectangle) shape).getY() + binShiftY);
        }
    }

    private void scaleRectangleSides(double scale, Rectangle shape) {
        shape.setWidth(shape.getWidth() * scale);
        shape.setHeight(shape.getHeight() * scale);
    }

    private void scaleRectangleCoordinates(double scale, Rectangle shape) {
        shape.setX(shape.getX() * scale);
        shape.setY(shape.getY() * scale);
    }

}
