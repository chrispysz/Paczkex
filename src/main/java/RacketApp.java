import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class responsible for initializing JavaFX components and setting the scene from fxml.
 */
public class RacketApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("login"), 800, 600);

        stage.setScene(scene);
        stage.setTitle("Paczkex");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RacketApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
