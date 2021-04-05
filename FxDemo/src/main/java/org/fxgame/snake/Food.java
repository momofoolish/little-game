package org.fxgame.snake;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Date;
import java.util.Random;

/**
 * 食物类
 */
public class Food extends BaseObject {
    private Entity entity;

    public Food() {
        this.setPositionX(160);
        this.setPositionY(60);
    }

    @Override
    public void create() {
        //创建食物
        entity = FXGL.entityBuilder()
                .type(ObjectType.FOOD)
                .at(this.getPositionX(), this.getPositionY())
                .bbox(new HitBox(BoundingShape.box(GameObserver.simpleWH, GameObserver.simpleWH)))
                .view(new Rectangle(GameObserver.simpleWH, GameObserver.simpleWH, Color.ORANGERED))
                .collidable()
                .buildAndAttach();
    }

    /**
     * 根据蛇的位置随机食物的位置
     *
     * @param snake 蛇
     */
    public void randomPositionBySnake(Snake snake) {
        Random random = new Random(new Date().getTime());
        //设置随机范围
        double rx = random.nextInt(GameObserver.width - (GameObserver.simpleWH * 2)) + GameObserver.simpleWH;
        double ry = random.nextInt(GameObserver.height - (GameObserver.simpleWH * 2)) + GameObserver.simpleWH;
        //不能与蛇的位置相同
        if (rx == snake.getPositionX() && ry == snake.getPositionY()) {
            this.randomPositionBySnake(snake);
        }
        //随机位置
        this.setPositionX(rx);
        this.setPositionY(ry);
        entity.setPosition(new Vec2(rx, ry));
    }
}
