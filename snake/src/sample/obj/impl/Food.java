package sample.obj.impl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.obj.ObjectBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 食物
 */
public class Food implements ObjectBase {
    public Food() {
        int start = 20;
        while (start >= wall.getLeft() && start < wall.getRight() - 20) {
            this.positionXList.add(start);
            start = start + 20;
        }

        start = 20;
        while (start >= wall.getUp() && start < wall.getDown() - 20) {
            this.positionYList.add(start);
            start = start + 20;
        }
    }

    private final Wall wall = new Wall();
    private GraphicsContext gc;

    private int positionX = 0;
    private int positionY = 0;

    private List<Integer> positionXList = new ArrayList<>();
    private List<Integer> positionYList = new ArrayList<>();

    @Override
    public void draw(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * 创建食物
     */
    public void createFood() {
        gc.setFill(Color.GREEN);
        gc.fillRect(this.positionX, this.positionY, 20, 20);
    }

    /**
     * 随机位置
     */
    public void randomPosition() {
        //随机位置
        Random random = new Random();
        random.setSeed(new Date().getTime());
        int x = this.positionXList.get(random.nextInt(this.positionXList.size()));
        int y = this.positionYList.get(random.nextInt(this.positionYList.size()));
        this.positionX = x;
        this.positionY = y;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
