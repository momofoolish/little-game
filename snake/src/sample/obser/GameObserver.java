package sample.obser;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.SnakeApp;
import sample.controller.Controller;
import sample.log.Logger;
import sample.log.LoggerFactory;
import sample.obj.impl.Food;
import sample.obj.impl.Snake;
import sample.obj.impl.Wall;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 游戏观察员
 */
public class GameObserver {
    private GameObserver() {
    }

    private final Logger logger = LoggerFactory.getLogger();

    private Canvas canvas;
    private GraphicsContext gc;
    private Timer timer;

    private Snake snake;
    private Food food;
    private Wall wall;

    /**
     * 获取游戏观察员对象
     *
     * @return GameObserver
     */
    public static GameObserver getObserver() {
        return new GameObserver();
    }

    /**
     * 初始化游戏
     */
    public Canvas initGame() {
        logger.info("【游戏初始化开始】");
        canvas = new Canvas(Device.WIDTH, Device.HEIGHT);
        canvas.setFocusTraversable(true);//设置允许键盘按下
        gc = canvas.getGraphicsContext2D();
        logger.info("生成画布--完成");
        //定时器
        timer = new Timer();
        //墙
        wall = new Wall();
        logger.info("生成墙--完成");
        //食物
        food = new Food();
        food.draw(gc);
        food.randomPosition();
        logger.info("生成食物--完成");
        //画蛇
        snake = new Snake();
        snake.draw(gc);
        snake.growUp();
        logger.info("生成蛇--完成");
        //注册控制器
        Controller.register(canvas, snake, this);
        //执行定时器
        this.timerRun();
        //返回canvas
        logger.info("【游戏初始化完成】");
        return canvas;
    }

    private void timerRun() {
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //判断蛇是否撞墙和撞自己尾巴
                if (snake.hitWall(wall) || snake.hitBody()) {
                    endGame(timer);
                    return;
                }
                //蛇吃了食物
                if (snake.eatFood(food)) {
                    //随机食物位置
                    food.randomPosition();
                }
                //刷新
                Device.flash(gc, wall, food);
                //蛇自动移动
                if (snake.getDirection() == -1) {
                    snake.moveToLeft();
                } else if (snake.getDirection() == 0) {
                    snake.moveToUp();
                } else if (snake.getDirection() == 1) {
                    snake.moveToRight();
                } else if (snake.getDirection() == 2) {
                    snake.moveToDown();
                }
            }
        }, 0, 200);
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        logger.info("开始游戏");
    }

    /**
     * 暂停游戏
     */
    public void pauseGame() {
        Device.GAME_STATE = 1;
    }

    /**
     * 重开游戏
     */
    public void restartGame() {
        Device.GAME_STATE = 0;
        Device.stage.close();

        Stage primaryStage = new Stage();
        Group root = new Group();
        primaryStage.setTitle("贪吃蛇");

        Canvas canvas = this.initGame();

        root.getChildren().add(canvas);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Device.stage = primaryStage;
    }

    /**
     * 结束游戏
     */
    public void endGame(Timer timer) {
        //设置游戏结束状态
        timer.cancel();
        Device.GAME_STATE = 2;

        this.gc.setStroke(Color.BLACK);
        this.gc.fillRect(0, 0, Device.WIDTH, Device.HEIGHT);

        //显示游戏结束字样
        this.gc.setFont(Font.font(48));
        this.gc.setFill(Color.RED);
        double x = (double) Device.WIDTH / 5;
        double y = (double) Device.HEIGHT / 2;
        this.gc.fillText("Game Over!!!", x, y);

        //重新开始提示
        this.gc.setFont(Font.font(16));
        this.gc.setFill(Color.WHITE);
        this.gc.fillText(">>回车重新开始<<", x + 60, y + 60);

        //日志输出
        // logger.info("textX=" + x + ",textY=" + y);
        logger.info("游戏结束");
    }
}
