package sample.controller;

import javafx.scene.canvas.Canvas;
import sample.log.Logger;
import sample.log.LoggerFactory;
import sample.obj.impl.Snake;
import sample.obser.Device;
import sample.obser.GameObserver;

public class Controller {
    private final static Logger logger = LoggerFactory.getLogger();

    public static void register(Canvas canvas, Snake snake, GameObserver observer) {
        canvas.setOnKeyPressed(event -> {
            //logger.info(event.getCode().getName());
            switch (event.getCode()) {
                case A:
                    //判断方向是否向左移动和向右
                    if(snake.getDirection() != -1 && snake.getDirection() != 1){
                        snake.moveToLeft();
                    }
                    break;
                case W:
                    if(snake.getDirection() != 0 && snake.getDirection() != 2){
                        snake.moveToUp();
                    }
                    break;
                case D:
                    if(snake.getDirection() != -1 && snake.getDirection() != 1){
                        snake.moveToRight();
                    }
                    break;
                case S:
                    if(snake.getDirection() != 0 && snake.getDirection() != 2){
                        snake.moveToDown();
                    }
                    break;
                case ENTER:
                    observer.restartGame();
                    break;
                case P:
                    observer.pauseGame();
                    break;
            }
        });
    }
}
