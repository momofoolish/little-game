package sample.obj.impl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.obser.Device;
import sample.obj.ObjectBase;

/**
 * 墙
 */
public class Wall implements ObjectBase {
    private final int left = 20;
    private final int up = 20;
    private final int right = Device.WIDTH - 20;
    private final int down = Device.HEIGHT - 20;

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BROWN);
        gc.fillRect(0, 0, 20, Device.HEIGHT);
        gc.fillRect(0, 0, Device.WIDTH, 20);
        gc.fillRect(Device.WIDTH - 20, 20, 20, Device.HEIGHT);
        gc.fillRect(20, Device.HEIGHT - 20, Device.WIDTH, 20);
    }

    public int getLeft() {
        return left;
    }

    public int getUp() {
        return up;
    }

    public int getRight() {
        return right;
    }

    public int getDown() {
        return down;
    }

}
