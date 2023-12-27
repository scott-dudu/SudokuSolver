
import processing.core.PApplet;

public class SudokuSolver extends PApplet{
    final int SQUARES_IN_ROW = 9;
    final int SQUARES_IN_COLUMN = 9;
    final int SQUARE_WIDTH = 60;
    final int SQUARE_START_POS = 50;
    final int BLANK = -1;

    final int SOLVE_RESET_X = 615;
    final int SOLVE_RESET_LENGTH = 160;
    final int SOLVE_RESET_WIDTH = 50;
    final int SOVLE_BUTTON_Y = 250;
    final int RESET_BUTTON_Y = 350;

    Square[][] grid = new Square[SQUARES_IN_COLUMN][SQUARES_IN_ROW];
    Square[] numbers = new Square[SQUARES_IN_ROW + 1];
    Square selectedSquare;
    int i, j, xPos, yPos;
    
    public void settings(){
        size(800, 800);

        for (i = 0; i < SQUARES_IN_ROW; i++) {

            //create sudoku grid
            for (j = 0; j < SQUARES_IN_COLUMN; j++){
                selectedSquare = new Square(SQUARE_START_POS + (SQUARE_WIDTH * i), SQUARE_START_POS + (SQUARE_WIDTH * j), BLANK);
                grid[i][j] = selectedSquare;
            }

            //create number grid
            selectedSquare = new Square(SQUARE_START_POS + (SQUARE_WIDTH * i), SQUARE_START_POS + ((SQUARES_IN_COLUMN + 1) * SQUARE_WIDTH), i + 1);
            numbers[i] = selectedSquare;
        }

        numbers[numbers.length - 1] = new Square(SQUARE_START_POS + (SQUARE_WIDTH * SQUARES_IN_ROW), SQUARE_START_POS + ((SQUARES_IN_COLUMN + 1) * SQUARE_WIDTH), BLANK);

        selectedSquare = null;
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

        //standard text style
        textAlign(CENTER, CENTER);
        textSize(25);

        //text for buttons
        fill(0);
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

                //create square in grid with correct color
                fill(square.getColor());
                rect(xPos, yPos, SQUARE_WIDTH, SQUARE_WIDTH);
                if (square.getNumber() != BLANK){
                    fill(0);
                    text(square.getNumber(), xPos + (SQUARE_WIDTH / 2), yPos + (SQUARE_WIDTH / 2));
                }
            }

            //create number grid
            Square number = numbers[i];
            fill(number.getColor());
            rect(number.getXPos(), number.getYPos(), SQUARE_WIDTH, SQUARE_WIDTH);
            fill(0);
            text(number.getNumber(), number.getXPos() + (SQUARE_WIDTH / 2), number.getYPos() + (SQUARE_WIDTH / 2));
        }
        
        //delete Button
        Square deleteButton = numbers[numbers.length - 1];
        fill(color(255, 0, 0));
        rect(deleteButton.getXPos(), deleteButton.getYPos(), SQUARE_WIDTH, SQUARE_WIDTH);
        fill(0);
        textSize(16);
        text("Delete", deleteButton.getXPos() + (SQUARE_WIDTH / 2), deleteButton.getYPos() + (SQUARE_WIDTH / 2));
    }

    //mouse input
    public void mouseReleased(){

        //activating solve and reset buttons
        if (mouseX > SOLVE_RESET_X && mouseX < SOLVE_RESET_X + SOLVE_RESET_LENGTH){
            if (mouseY > SOVLE_BUTTON_Y && mouseY < SOVLE_BUTTON_Y + SOLVE_RESET_WIDTH){
                System.out.println("pressed solve");
                //TODO - use solver class
            }

            //clear board
            if (mouseY > RESET_BUTTON_Y && mouseY < RESET_BUTTON_Y + SOLVE_RESET_WIDTH){
                for (Square[] row : grid){
                    for (Square s : row){
                        s.setNumber(BLANK);
                    }
                }
            }
        }

        //select square
        int squareCol = (mouseX - SQUARE_START_POS) / SQUARE_WIDTH;
        if (squareCol >= 0 && squareCol <= SQUARES_IN_ROW){

            //select square on board
            int squareRow = (mouseY - SQUARE_START_POS) / SQUARE_WIDTH;
            if (squareRow >= 0 && squareRow < SQUARES_IN_COLUMN){
                Square square = grid[squareCol][squareRow];

                if (square.isSelected()){
                    square.selectedOff();
                    selectedSquare = null;
                } else {
                    square.selectedOn();

                    //change selectedSquare to new one
                    if (selectedSquare != null) selectedSquare.selectedOff();
                    selectedSquare = square;
                }
            }

            //select square on number grid to input into selectedSquare
            if (squareRow == SQUARES_IN_COLUMN + 1){
                if (selectedSquare != null){
                    Square number = numbers[squareCol];

                    selectedSquare.setNumber(number.getNumber());
                }
            }
        }
    }

    //keyboard input
    public void keyPressed(){

        //keyboard alternative for solve button
        if (key == ENTER) {
            System.out.println("hit enter key");
            //TODO
        }

        //keyboard alternative for numbers
        if (selectedSquare != null){

            if(key == '1' || key == '!') selectedSquare.setNumber(1);
            else if (key == '2' || key == '@') selectedSquare.setNumber(2);
            else if (key == '3' || key == '#') selectedSquare.setNumber(3);
            else if (key == '4' || key == '$') selectedSquare.setNumber(4);
            else if (key == '5' || key == '%') selectedSquare.setNumber(5);
            else if (key == '6' || key == '^') selectedSquare.setNumber(6);
            else if (key == '7' || key == '&') selectedSquare.setNumber(7);
            else if (key == '8' || key == '*') selectedSquare.setNumber(8);
            else if (key == '9' || key == '(') selectedSquare.setNumber(9);
            else if (key == BACKSPACE) selectedSquare.setNumber(BLANK);

        }
    }

    public static void main(String[] args) {
        PApplet.main("SudokuSolver");
    }
}
