
import java.util.HashSet;

import processing.core.PApplet;

public class Square extends PApplet{
    private int xPos, yPos, number, textColor;
    private boolean mouseInSquare, selected;
    private HashSet<Integer> possibleNums;
    static final int BLANK = -1;
    
    public Square(int xPos, int yPos, int number, int textColor){
        reset();

        this.number = number;
        this.textColor = textColor;

        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos(){return xPos;}

    public int getYPos(){return yPos;}

    public int getNumber(){return number;}

    public void setNumber(int n){
        number = n;
        possibleNums.clear();
    }

    public boolean isInSquare(){return mouseInSquare;}

    public boolean isSelected(){return selected;}

    public int getSquareColor(){
        //return blue
        if (selected) return color(135,206,235);

        //return gray
        if (mouseInSquare) return 150;

        //return white
        return 255;
    }

    public void selectedOn(){selected = true;}

    public void selectedOff(){selected = false;}

    public void inSquareOn(){mouseInSquare = true;}

    public void inSquareOff(){mouseInSquare = false;}

    public void setTextColor(int r, int g, int b){textColor = color(r, g, b);}

    public int getTextColor(){return textColor;}

    public boolean equals(Square s){
        if (s.xPos == this.xPos){
            if (s.yPos == this.yPos){
                return true;
            }
        }

        return false;
    }

    public void addPossibleNum(int n){possibleNums.add(n);}

    public void removePossibleNum(int n){
        if (possibleNums.contains(n)) possibleNums.remove(n);
    }

    public void clearPossibleNums(){possibleNums.clear();}

    public HashSet<Integer> getPossibleNums(){return possibleNums;}

    public void initialize(){
        for(int i = 1; i <= 9; i++){
            addPossibleNum(i);
        }
    }

    public void reset(){
        selectedOff();
        inSquareOff();
        possibleNums = new HashSet<>();
        number = BLANK;
        textColor = 0;
    }
}
