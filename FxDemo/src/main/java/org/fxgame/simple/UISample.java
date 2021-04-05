package org.fxgame.simple;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.text.Text;

public class UISample extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initUI() {
        Text text = new Text("Hello World!");

        FXGL.addUINode(text, 100, 100);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
