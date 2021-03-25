package sample;

import sample.log.Logger;
import sample.log.LoggerFactory;
import sample.obj.impl.Wall;

import java.util.ArrayList;
import java.util.List;

public class JTest {
    final static Logger logger = LoggerFactory.getLogger();

    public static void main(String[] args) {
        Wall wall = new Wall();
        List<Integer> integers = new ArrayList<>();

        int start = 20;
        while (start >= wall.getLeft() && start < wall.getRight() - 20) {
            integers.add(start);
            start = start + 20;
        }

        logger.info(integers + "");
    }
}
