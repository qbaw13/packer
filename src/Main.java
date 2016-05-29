import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MainModel;

/**
 * Created by Kuba on 01.05.2016.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/main-ui.fxml"));
        loader.setControllerFactory(t -> new MainController(new MainModel()));

        stage.setTitle("Packer");
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }

}
