package sample.obser;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import sample.obj.impl.Food;
import sample.obj.impl.Snake;
import sample.obj.impl.Wall;

public class Device {
    public static int WIDTH = 480;
    public static int HEIGHT = 320;
    /**
     * 游戏状态，0为正常游戏，1暂停游戏，2停止游戏
     */
    public static int GAME_STATE = 0;

    /**
     * 场景
     */
    public static Stage stage;

    /**
     * 清屏
     * @param gc GraphicsContext
     */
    public static void flash(GraphicsContext gc, Wall wall, Food food){
        gc.clearRect(0, 0, Device.WIDTH, Device.HEIGHT);
        wall.draw(gc);
        food.createFood();
    }

}
