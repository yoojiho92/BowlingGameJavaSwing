package Element;

public class Pin {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean state;
    private int pinNum;

    public Pin(){
        this.state = true;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState(){
        return this.state;
    }

    public void setPinNum(int pinNum) {
        this.pinNum = pinNum;
    }

    public int getPinNum() {
        return pinNum;
    }
}
