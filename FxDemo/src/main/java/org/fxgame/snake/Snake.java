package org.fxgame.snake;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * 蛇类
 */
public class Snake extends BaseObject {
    private final List<Entity> entityList;
    private Entity entity;
    private int direction;

    public static final int directionLeft = -1;
    public static final int directionRight = 1;
    public static final int directionUp = -2;
    public static final int directionDown = 2;

    public Snake() {
        entityList = new ArrayList<>();
        this.setPositionX(GameObserver.initSnakeX);
        this.setPositionY(GameObserver.initSnakeY);
        this.direction = GameObserver.initDirection;
    }

    /**
     * 创建一条蛇
     */
    @Override
    public void create() {
        this.entity = FXGL.entityBuilder()
                .type(ObjectType.SNAKE)
                .at(this.getPositionX(), this.getPositionY())
                .bbox(new HitBox(BoundingShape.box(GameObserver.simpleWH - 10, GameObserver.simpleWH - 10)))
                .view(new Rectangle(GameObserver.simpleWH, GameObserver.simpleWH, Color.BLUE))
                .collidable()
                .buildAndAttach();
    }

    /**
     * 蛇自动移动
     */
    public void autoMove() {
        this.move(this.direction);
    }

    /**
     * 手动更改移动方向
     */
    public void inputMove(int dir) {
        //判断玩家输入的方向是否与当前相反
        if (dir != -(this.direction)) {
            this.direction = dir;
        }
    }

    /**
     * 蛇移动
     *
     * @param direction 方向：-1左，-2上，1右，2下
     */
    private void move(int direction) {
        this.direction = direction;
        //身体坐标
        double bx = this.getPositionX();
        double by = this.getPositionY();
        switch (direction) {
            case directionLeft:
                this.setPositionX(this.getPositionX() - GameObserver.simpleWH);
                break;
            case directionUp:
                this.setPositionY(this.getPositionY() - GameObserver.simpleWH);
                break;
            case directionRight:
                this.setPositionX(this.getPositionX() + GameObserver.simpleWH);
                break;
            case directionDown:
                this.setPositionY(this.getPositionY() + GameObserver.simpleWH);
                break;
        }
        this.entity.setPosition(new Vec2(this.getPositionX(), this.getPositionY()));
        if (this.entityList.size() > 0) {
            double px = 0;
            double py = 0;
            //身体跟着头部
            for (int i = 0; i < this.entityList.size(); i++) {
                Entity en = this.entityList.get(i);
                if (i > 0) {
                    //先保留当前位置
                    double cx = en.getX();
                    double cy = en.getY();
                    //更改当前对象位置
                    en.setPosition(new Vec2(px, py));
                    //记录当前对象旧的位置
                    px = cx;
                    py = cy;
                }
                //首个身体跟着前一个头部坐标即可
                if (i == 0) {
                    //设置当前位置前先记录当前位置
                    px = en.getX();
                    py = en.getY();
                    en.setPosition(new Vec2(bx, by));
                }
            }
        }
    }

    /**
     * 蛇长身体
     */
    public Snake growUp() {
        double x = 0;
        double y = 0;
        Entity entity;
        if (this.entityList.size() <= 0) {
            if (this.direction == directionLeft) {
                x = this.getPositionX() + GameObserver.simpleWH;
                y = this.getPositionY();
            } else if (this.direction == directionUp) {
                x = this.getPositionX();
                y = this.getPositionY() + GameObserver.simpleWH;
            } else if (this.direction == directionRight) {
                x = this.getPositionX() - GameObserver.simpleWH;
                y = this.getPositionY();
            } else if (this.direction == directionDown) {
                x = this.getPositionX();
                y = this.getPositionY() - GameObserver.simpleWH;
            }
            //首个身体不参与碰撞
            entity = FXGL.entityBuilder()
                    .at(x, y)
                    .view(new Rectangle(GameObserver.simpleWH, GameObserver.simpleWH, Color.GREEN))
                    .buildAndAttach();
        } else {
            Entity e = this.entityList.get(this.entityList.size() - 1);
            if (this.direction == directionLeft) {
                x = e.getX() + GameObserver.simpleWH;
                y = e.getY();
            } else if (this.direction == directionUp) {
                x = e.getX();
                y = e.getY() + GameObserver.simpleWH;
            } else if (this.direction == directionRight) {
                x = e.getX() - GameObserver.simpleWH;
                y = e.getY();
            } else if (this.direction == directionDown) {
                x = e.getX();
                y = e.getY() - GameObserver.simpleWH;
            }
            entity = FXGL.entityBuilder()
                    .type(ObjectType.BODY)
                    .at(x, y)
                    .bbox(new HitBox(BoundingShape.box(GameObserver.simpleWH, GameObserver.simpleWH)))
                    .view(new Rectangle(GameObserver.simpleWH, GameObserver.simpleWH, Color.GREEN))
                    .collidable()
                    .buildAndAttach();
        }
        this.entityList.add(entity);
        return this;
    }

    /**
     * 吃食物
     *
     * @param food 食物
     */
    public void eatFood(Food food) {
        if (food.getPositionX() == this.getPositionX() && food.getPositionY() == this.getPositionY()) {
            this.growUp();//成长
            food.randomPositionBySnake(this);//随机位置
        }
    }

    /**
     * 撞自己的身体
     */
    public boolean hitBody() {
        boolean flag = false;
        for (int i = 0; i < this.entityList.size(); i++) {
            Entity en = this.entityList.get(i);
            if (en.getX() == this.getPositionX() && en.getY() == this.getPositionX()) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
