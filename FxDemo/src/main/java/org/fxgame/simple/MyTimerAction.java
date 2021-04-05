package org.fxgame.simple;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class MyTimerAction extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initInput() {
        FXGL.onKeyDown(KeyCode.F, "pause timer", () -> {
            if (timerAction.isPaused()) {
                timerAction.resume();
            } else {
                timerAction.pause();
            }
        });
    }

    private TimerAction timerAction;

    @Override
    protected void initGame() {
        timerAction = FXGL.getGameTimer().runAtInterval(() -> System.out.println("now: " + FXGL.getGameTimer().getNow()), Duration.seconds(0.5));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
