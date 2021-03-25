package sample.obj.impl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.obser.Device;
import sample.log.Logger;
import sample.log.LoggerFactory;
import sample.obj.ObjectBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 蛇
 */
public class Snake implements ObjectBase {
    public Snake() {
        this.bodyList = new ArrayList<>();
    }

    private final Logger logger = LoggerFactory.getLogger();

    private final int width = 20;
    private final int height = 20;
    private int positionX = 40;
    private int positionY = 40;
    private int speed = 2;

    /**
     * -1左，0上，1右，2下
     */
    private int direction = 1;

    private List<Body> bodyList;
    private GraphicsContext gc;

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(positionX, positionY, width, height);
        this.gc = gc;
    }

    /**
     * 重画
     *
     * @param gc 画笔
     */
    public void reDraw(List<Body> bodies, GraphicsContext gc) {
        bodyList = bodies;
        draw(gc);
    }

    /**
     * 长身体
     */
    public void growUp() {
        int count = bodyList.size();
        int bodyX;
        int bodyY;
        //大于0表示有身体
        if (count > 0) {
            //拿到最后一个身体
            Body lastBody = bodyList.get(bodyList.size() - 1);
            bodyX = lastBody.getPositionX() - this.width;
            bodyY = lastBody.getPositionY();
        } else {
            bodyX = this.positionX - this.width;
            bodyY = this.positionY;
        }
        bodyList.add(new Body().create(bodyX, bodyY, gc));
    }

    public void moveToUp() {
        this.move(0, this.height * -1);
        this.setDirection(0);
    }

    public void moveToDown() {
        this.move(0, this.height);
        this.setDirection(2);
    }

    public void moveToLeft() {
        this.move(this.width * -1, 0);
        this.setDirection(-1);
    }

    public void moveToRight() {
        this.move(this.width, 0);
        this.setDirection(1);
    }

    /**
     * 吃食物
     *
     * @param food 食物
     */
    public boolean eatFood(Food food) {
        // logger.info("foodX=" + food.getPositionX() + ",foodY=" + food.getPositionY());
        // logger.info("snakeX=" + this.positionX + ",snakeY=" + this.getPositionY());
        if (food.getPositionX() == this.positionX && food.getPositionY() == this.positionY) {
            this.growUp();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 撞墙
     *
     * @param wall 墙对象
     */
    public boolean hitWall(Wall wall) {
        return this.positionX < wall.getLeft() || this.positionX >= wall.getRight()
                || this.positionY < wall.getUp() || this.positionY >= wall.getDown();
    }

    /**
     * 撞身体
     */
    public boolean hitBody() {
        boolean flag = false;
        for (int i = 0; i < this.bodyList.size(); i++) {
            int x = this.bodyList.get(i).getPositionX();
            int y = this.bodyList.get(i).getPositionY();
            if (x == this.positionX && y == this.positionY) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public List<Body> getBodyList() {
        return bodyList;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * 蛇移动
     *
     * @param x X轴
     * @param y Y轴
     */
    private void move(int x, int y) {
        // 1为游戏暂停，2为游戏结束
        if (Device.GAME_STATE == 1 || Device.GAME_STATE == 2) {
            return;
        }
        // logger.info("【蛇开始移动】");
        //获取蛇的旧坐标
        int snakePositionX = this.positionX;
        int snakePositionY = this.positionY;
        //蛇的坐标变换
        this.positionX += x;
        this.positionY += y;
        // logger.info("positionX=" + this.positionX);
        // logger.info("positionY=" + this.positionY);

        List<Body> bodyListNew = new ArrayList<>();

        //蛇的身体个数
        for (int i = 0; i < this.bodyList.size(); i++) {
            Body body = new Body();
            if (i == 0) {
                body.setPositionX(snakePositionX);
                body.setPositionY(snakePositionY);
            } else {
                body.setPositionX(this.bodyList.get(i - 1).getPositionX());
                body.setPositionY(this.bodyList.get(i - 1).getPositionY());
            }
            // logger.info("bodyX=" + body.getPositionX());
            // logger.info("bodyY=" + body.getPositionY());
            body.create(body.getPositionX(), body.positionY, gc);
            bodyListNew.add(body);
        }
        // logger.info("bodyListNew=" + bodyListNew.size());
        //刷新重绘
        this.reDraw(bodyListNew, this.gc);
        // logger.info("【蛇移动结束】");
    }


    /**
     * 蛇的身体
     */
    static class Body implements ObjectBase {
        private final Logger logger = LoggerFactory.getLogger();

        private final int width = 20;
        private final int height = 20;
        private int positionX;
        private int positionY;

        private Body() {
        }

        public Body(int positionX, int positionY) {
            this.positionX = positionX;
            this.positionY = positionY;
        }

        /**
         * 创建身体对象
         *
         * @param gc 画笔
         */
        public Body create(int snakePositionX, int snakePositionY, GraphicsContext gc) {
            this.positionX = snakePositionX;
            this.positionY = snakePositionY;

            gc.setFill(Color.GRAY);
            gc.fillRect(this.positionX, this.positionY, this.width, this.height);
            this.draw(gc);
            return new Body(this.positionX, this.positionY);
        }

        @Override
        public void draw(GraphicsContext gc) {

        }

        public int getPositionX() {
            return positionX;
        }

        public int getPositionY() {
            return positionY;
        }

        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        public void setPositionY(int positionY) {
            this.positionY = positionY;
        }
    }
}
