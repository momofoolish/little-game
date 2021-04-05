package org.fxgame.snake;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.fxgame.simple.PhysicsSample;

/**
 * @author jwss
 * FXGL版贪吃蛇
 */
public class SnakeApplication extends GameApplication {
    private TimerAction timerAction;
    private Snake snake;
    private Food food;
    private Wall wall;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("贪吃蛇");
        gameSettings.setWidth(GameObserver.width);
        gameSettings.setHeight(GameObserver.height);
    }

    @Override
    protected void initGame() {
        this.snake = new Snake();
        this.snake.create();
        this.snake.growUp().growUp();
        this.food = new Food();
        this.food.create();
        this.wall = new Wall();
        this.wall.create();
        timerAction = FXGL.getGameTimer().runAtInterval(() -> this.snake.autoMove(), Duration.seconds(GameObserver.timerSpeed));
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(ObjectType.SNAKE, ObjectType.FOOD, (sn, bn) -> {
            //蛇吃到食物
            this.snake.growUp();
            this.food.randomPositionBySnake(this.snake);
        });
        FXGL.onCollision(ObjectType.SNAKE, ObjectType.BODY, (sn, bo) -> {
            //蛇咬到自己的身体
            if(this.snake.hitBody()){
                timerAction.pause();
            }
        });
        FXGL.onCollisionBegin(ObjectType.SNAKE, ObjectType.WALL, (sn, wa) -> {
            //蛇撞墙了
            timerAction.pause();
        });
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        input.addAction(new UserAction("left") {
            @Override
            protected void onActionEnd() {
                snake.inputMove(Snake.directionLeft);
            }
        }, KeyCode.A);
        input.addAction(new UserAction("up") {
            @Override
            protected void onActionEnd() {
                snake.inputMove(Snake.directionUp);
            }
        }, KeyCode.W);
        input.addAction(new UserAction("right") {
            @Override
            protected void onActionEnd() {
                snake.inputMove(Snake.directionRight);
            }
        }, KeyCode.D);
        input.addAction(new UserAction("down") {
            @Override
            protected void onActionEnd() {
                snake.inputMove(Snake.directionDown);
            }
        }, KeyCode.S);
        input.addAction(new UserAction("pause") {
            @Override
            protected void onActionEnd() {
                timerAction.pause();
            }
        }, KeyCode.P);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
