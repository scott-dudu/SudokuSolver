
import processing.core.PApplet;

public class Square extends PApplet{
    private int xPos, yPos, number;
    private boolean mouseInSquare, selected;
    
    public Square(int xPos, int yPos){
        mouseInSquare = false;
        selected = false;

        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos(){return xPos;}

    public int getYPos(){return yPos;}

    public boolean isInSquare(){return mouseInSquare;}

    public boolean isSelected(){return selected;}

    public int getColor(){
        //return gray
        if (mouseInSquare) return 150;

        //return blue
        if (selected) return color(135,206,235);

        //return white
        return 255;
    }

    public void selectedOn(){selected = true;}

    public void selectedOff(){selected = false;}

    public void inSquareOn(){mouseInSquare = true;}

    public void inSquareOff(){mouseInSquare = false;}
}
