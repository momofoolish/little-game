package org.fxgame.snake;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends BaseObject {
    @Override
    public void create() {
        //创建墙
        FXGL.entityBuilder()
                .type(ObjectType.WALL)
                .at(0, 0)
                .bbox(new HitBox(BoundingShape.box(GameObserver.simpleWH * 40, GameObserver.simpleWH - 1)))
                .view(new Rectangle(GameObserver.simpleWH * 40, GameObserver.simpleWH, Color.BROWN))
                .collidable()
                .buildAndAttach();
        FXGL.entityBuilder()
                .type(ObjectType.WALL)
                .at(-1, 0)
                .bbox(new HitBox(BoundingShape.box(GameObserver.simpleWH, GameObserver.simpleWH * 30)))
                .view(new Rectangle(GameObserver.simpleWH, GameObserver.simpleWH * 30, Color.BROWN))
                .collidable()
                .buildAndAttach();
        FXGL.entityBuilder()
                .type(ObjectType.WALL)
                .at(GameObserver.width - GameObserver.simpleWH + 1, 0)
                .bbox(new HitBox(BoundingShape.box(GameObserver.simpleWH, GameObserver.simpleWH * 30)))
                .view(new Rectangle(GameObserver.simpleWH, GameObserver.simpleWH * 30, Color.BROWN))
                .collidable()
                .buildAndAttach();
        FXGL.entityBuilder()
                .type(ObjectType.WALL)
                .at(0, GameObserver.height - GameObserver.simpleWH + 1)
                .bbox(new HitBox(BoundingShape.box(GameObserver.simpleWH * 40, GameObserver.simpleWH)))
                .view(new Rectangle(GameObserver.simpleWH * 40, GameObserver.simpleWH, Color.BROWN))
                .collidable()
                .buildAndAttach();
    }
}
