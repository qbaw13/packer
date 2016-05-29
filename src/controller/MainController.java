package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.MainModel;
import entity.Block;
import entity.BlocksDistribution;
import service.Packer;
import utility.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainController {

    @FXML
    TabPane binTabPane;
    @FXML
    TextArea shapesTextArea;
    @FXML
    Button stopButton;
    @FXML
    Button packButton;
    @FXML
    TextArea console;
    private MainModel mainModel;
    private List<Rectangle> bins;
    private Rectangle currentBin;

    public MainController(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @FXML
    public void initialize() {
        console.setEditable(false);
    }

    @FXML
    public void onPack() {

        if(mainModel.checkEnteredBlocks(shapesTextArea.getText())) {
            setButtonsOnPack();
            List<Block> blocks = mainModel.getBlocks(shapesTextArea.getText());
            mainModel.sortBlocksDescending(blocks);
            Packer packer = mainModel.packShapes(blocks);

            packer.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    removeAllTabs();
                    List<BlocksDistribution> blocksDistributions = mainModel.fetchPackedShapes(blocks);
                    displayDistributions(blocksDistributions);
                    displayDecsions(mainModel.fetchDecisions(blocks));
                    setButtonsOnStopPack();
                }
            });
        }
        else {
            displayInformationAlert(Text.WRONG_ENTERED_DATA_TITLE, Text.WRONG_ENTERED_DATA_CONTENT);
        }
    }

    @FXML
    public void onStop() {
        mainModel.stopPacker();
        setButtonsOnStopPack();
    }

    @FXML
    public void onClear() {
        removeAllTabs();
    }

    @FXML
    public void onClose() {
        Platform.exit();
        System.exit(0);
    }

    private void displayDistributions(List<BlocksDistribution> blocksDistributions) {
        bins = new ArrayList<>();

        for(int i = 0; i < blocksDistributions.size(); i++) {
            Tab tab = createTab(i);
            AnchorPane currentPane = (AnchorPane) tab.getContent();
            Rectangle bin = createBin(blocksDistributions.get(i).getWidth(), blocksDistributions.get(i).getHeight());
            scaleBin(bin, tab.getTabPane().getWidth(), tab.getTabPane().getHeight()-20);
            currentPane.getChildren().add(bin);
            bins.add(bin);
            currentBin = bin;
            prepareBlocks(blocksDistributions.get(i), tab);
            currentPane.getChildren().addAll(blocksDistributions.get(i).getShapes());
        }
    }

    private void displayInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private void displayDecsions(String decisions) {
        console.clear();
        console.appendText(decisions);
    }

    private Tab createTab(int tabNumber) {
        AnchorPane anchorPane = new AnchorPane();
        Tab tab = new Tab(Text.SOLUTION + " " + tabNumber, anchorPane);
        tab.setId(tabNumber + "");
        binTabPane.getTabs().add(tab);

        return tab;
    }

    private void prepareBlocks(BlocksDistribution blocksDistribution, Tab tab) {
        addColors(blocksDistribution.getShapes());
        scaleShapes(blocksDistribution, tab.getTabPane().getWidth(),  tab.getTabPane().getHeight()-20);
    }

    private void setButtonsOnPack() {
        packButton.setDisable(true);
        stopButton.setDisable(false);
    }

    private void setButtonsOnStopPack() {
        packButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private Rectangle createBin(double width, double height) {
        Rectangle bin = new Rectangle(width, height);
        bin.setFill(Color.WHITE);
        bin.setStroke(Color.DARKGREY);
        return bin;
    }

    private void scaleBin(Rectangle bin, double width, double height) {
        double scaleX = width / bin.getWidth();
        double scaleY = height / bin.getHeight();
        scaleRectangleSides(bin, Math.min(scaleX, scaleY));
        centerBinInPane(bin, width, height);
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

    private void scaleShapes(BlocksDistribution blocksDistribution, double paneWidth, double paneHeight) {
        double scaleX = paneWidth / blocksDistribution.getWidth();
        double scaleY = paneHeight / blocksDistribution.getHeight();

        for(Shape shape : blocksDistribution.getShapes()) {
            if(shape instanceof Rectangle) {
                scaleRectangleSides((Rectangle) shape, Math.min(scaleX, scaleY));
                scaleRectangleCoordinates(Math.min(scaleX, scaleY), (Rectangle) shape);
                ((Rectangle) shape).setX(((Rectangle) shape).getX() + currentBin.getX());
                ((Rectangle) shape).setY(((Rectangle) shape).getY() + currentBin.getY());
            }
        }
    }

    private void centerBinInPane(Rectangle bin, double paneWidth, double paneHeight) {
        bin.setX(paneWidth/2 - bin.getWidth()/2);
        bin.setY(paneHeight/2 - bin.getHeight()/2);
    }

    private void scaleRectangleSides(Rectangle shape, double scale) {
        shape.setWidth(shape.getWidth() * scale);
        shape.setHeight(shape.getHeight() * scale);
    }

    private void scaleRectangleCoordinates(double scale, Rectangle shape) {
        shape.setX(shape.getX() * scale);
        shape.setY(shape.getY() * scale);
    }

    private void removeAllTabs() {
        binTabPane.getTabs().clear();
    }
}
