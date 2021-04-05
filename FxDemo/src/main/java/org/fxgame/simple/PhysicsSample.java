package org.fxgame.simple;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.fxgame.simple.dev.DeveloperWASDControl;

public class PhysicsSample extends GameApplication {
    private enum Type {PLAYER, ENEMY}

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        FXGL.entityBuilder()
                .type(Type.PLAYER)
                .at(100, 100)
                .bbox(new HitBox(BoundingShape.box(40, 40)))
                .view(new Rectangle(40, 40, Color.BLUE))
                .collidable()
                .with(new DeveloperWASDControl())
                .buildAndAttach();

        FXGL.entityBuilder()
                .type(Type.ENEMY)
                .at(200, 200)
                .viewWithBBox(new Rectangle(40, 40, Color.RED))
                .collidable()
                .buildAndAttach();
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollision(Type.PLAYER, Type.ENEMY, (player, enemy) -> {
            System.out.println("On Collision");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
