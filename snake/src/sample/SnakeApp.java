package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import sample.log.Logger;
import sample.log.LoggerFactory;
import sample.obser.Device;
import sample.obser.GameObserver;

public class SnakeApp extends Application {
    private final Logger logger = LoggerFactory.getLogger();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("贪吃蛇");

        GameObserver gameObserver = GameObserver.getObserver();
        Canvas canvas = gameObserver.initGame();

        root.getChildren().add(canvas);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Device.stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
