package controller;

import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.MainModel;
import service.ItemsDistribution;
import utility.ShapeCreator;

import java.util.ArrayList;
import java.util.List;

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



    public MainController(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @FXML
    public void initialize() {
        List<Shape> tempShapes = new ArrayList<>();
        Rectangle r1 = new Rectangle(100, 100, 100, 100);
        Rectangle r2 = new Rectangle(200, 200, 200, 200);
        tempShapes.add(r1);
        tempShapes.add(r2);
        AnchorPane anchorPane = (AnchorPane) binTabPane.getSelectionModel().getSelectedItem().getContent();
        anchorPane.getChildren().addAll(tempShapes);
    }

    @FXML
    public void onPack() {
        Double width = Double.parseDouble(widthBinTextField.getCharacters().toString());
        Double height = Double.parseDouble(heightBinTextField.getCharacters().toString());
        String shapesData = shapesTextArea.getText();
        ItemsDistribution bestItemsDistribution = mainModel.packShapes(new Dimension2D(width, height), ShapeCreator.createShapes(shapesData));
    }

}
