
import processing.core.PApplet;
//import processing.core.PImage;

public class SudokuSolver extends PApplet{
    final int SQUARES_IN_ROW = 9;
    final int SQUARES_IN_COLUMN = 9;
    final int SQUARE_WIDTH = 60;

    final int SOLVE_RESET_X = 615;
    final int SOLVE_RESET_LENGTH = 160;
    final int SOLVE_RESET_WIDTH = 50;
    final int SOVLE_BUTTON_Y = 250;
    final int RESET_BUTTON_Y = 350;

    Square[][] grid = new Square[SQUARES_IN_COLUMN][SQUARES_IN_ROW];
    int i, j, xPos, yPos;
    
    public void settings(){
        size(800, 800);

        for (i = 0; i < SQUARES_IN_ROW; i++) {
            for (j = 0; j < SQUARES_IN_COLUMN; j++){
                Square square = new Square(50 + (60 * i), 50 + (60 * j));
                grid[i][j] = square;
            }
        }
    }

    public void draw(){
        clear();
        background(255);
        
        //solve button
        fill(50, 205, 50);
        rect(SOLVE_RESET_X, SOVLE_BUTTON_Y, SOLVE_RESET_LENGTH, SOLVE_RESET_WIDTH);

        //reset button
        fill(200);
        rect(SOLVE_RESET_X, RESET_BUTTON_Y, SOLVE_RESET_LENGTH, SOLVE_RESET_WIDTH);

        //text for buttons
        fill(0);
        textAlign(CENTER, CENTER);
        textSize(25);
        text("Solve", 695, 275);
        text("Reset", 695, 375);

        //create grid
        for (i = 0; i < SQUARES_IN_ROW; i++) {
            for (j = 0; j < SQUARES_IN_COLUMN; j++){
                Square square = grid[i][j];
                xPos = square.getXPos();
                yPos = square.getYPos();

                //changing color while hovering over square
                if ((mouseX > xPos && mouseX < xPos + SQUARE_WIDTH) && (mouseY > yPos && mouseY < yPos + SQUARE_WIDTH)) {
                    square.inSquareOn();
                } else {
                    if (square.isInSquare()) square.inSquareOff();
                }

                //create square with correct color
                fill(square.getColor());
                rect(xPos, yPos, SQUARE_WIDTH, SQUARE_WIDTH);
            }
        }        
    }

    public void mouseReleased(){

        //activating solve and reset buttons
        if (mouseX > SOLVE_RESET_X && mouseX < SOLVE_RESET_X + SOLVE_RESET_LENGTH){
            if (mouseY > SOVLE_BUTTON_Y && mouseY < SOVLE_BUTTON_Y + SOLVE_RESET_WIDTH){
                //TODO
            }

            if (mouseY > RESET_BUTTON_Y && mouseY < RESET_BUTTON_Y + SOLVE_RESET_WIDTH){
                //TODO
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("SudokuSolver");
    }
}
