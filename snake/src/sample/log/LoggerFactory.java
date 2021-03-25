package sample.log;

public class LoggerFactory implements Logger{
    private LoggerFactory(){}

    public static Logger getLogger(){
        return new LoggerFactory();
    }

    @Override
    public void info(String s) {
        logger("信息：" + s);
    }

    @Override
    public void warn(String s) {
        logger("警告：" + s);
    }

    @Override
    public void error(String s) {
        logger("错误：" + s);
    }

    private void logger(String s){
        System.out.println(s);
    }
}
