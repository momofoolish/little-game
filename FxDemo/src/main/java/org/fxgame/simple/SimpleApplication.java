package org.fxgame.simple;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SimpleApplication extends GameApplication {

    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("第一个FXGL游戏");
    }

    @Override
    protected void initGame() {
        // FXGL.entityBuilder()
        //         .at(150, 150)
        //         .view(new Rectangle(40, 40, Color.BLUE))
        //         .buildAndAttach();

        // FXGL.entityBuilder()
        //         .at(0,0)
        //         .view(new ImageView("./assets/yan_8.jpg"))
        //         .buildAndAttach();

        FXGL.entityBuilder()
                .at(400, 300)
                .view(new Rectangle(40, 40))
                .with(new RotatingComponent())
                .buildAndAttach();
    }

    @Override
    protected void initPhysics() {

    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        input.addAction(new UserAction("Print Line") {
            @Override
            protected void onAction() {
                // System.out.println("onAction");
            }

            @Override
            protected void onActionBegin() {
                System.out.println("onActionBegin");
            }

            @Override
            protected void onActionEnd() {
                System.out.println("onActionEnd");
            }
        }, KeyCode.F);
    }

    private static class RotatingComponent extends Component {
        @Override
        public void onUpdate(double tpf) {
            // 指定此组件强制执行的实体的行为
            entity.rotateBy(tpf * 45);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
