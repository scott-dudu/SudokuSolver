
import processing.core.PApplet;

public class SudokuSolver extends PApplet{
    static final int SQUARES_IN_ROW = 9;
    static final int SQUARES_IN_COLUMN = 9;
    static final int SQUARE_WIDTH = 60;
    static final int SQUARE_START_POS = 50;

    final int SOLVE_RESET_X = 615;
    final int SOLVE_RESET_LENGTH = 160;
    final int SOLVE_RESET_WIDTH = 50;
    final int SOVLE_BUTTON_Y = 250;
    final int RESET_BUTTON_Y = 350;

    Square[][] grid = new Square[SQUARES_IN_COLUMN][SQUARES_IN_ROW];
    Square[] numbers = new Square[SQUARES_IN_ROW + 1];
    Square selectedSquare;
    int i, j;
    Solver solver = new Solver(grid);
    
    public void settings(){
        size(800, 800);

        for (i = 0; i < SQUARES_IN_ROW; i++) {

            //create sudoku grid
            for (j = 0; j < SQUARES_IN_COLUMN; j++){
                selectedSquare = new Square(SQUARE_START_POS + (SQUARE_WIDTH * i), SQUARE_START_POS + (SQUARE_WIDTH * j), Square.BLANK);
                grid[i][j] = selectedSquare;
            }

            //create number grid
            selectedSquare = new Square(SQUARE_START_POS + (SQUARE_WIDTH * i), SQUARE_START_POS + ((SQUARES_IN_COLUMN + 1) * SQUARE_WIDTH), i + 1);
            numbers[i] = selectedSquare;
        }

        numbers[numbers.length - 1] = new Square(SQUARE_START_POS + (SQUARE_WIDTH * SQUARES_IN_ROW), SQUARE_START_POS + ((SQUARES_IN_COLUMN + 1) * SQUARE_WIDTH), Square.BLANK);

        selectedSquare = null;
    }

    public void draw(){
        clear();
        background(0);
        
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
                
                determineSquareColor(square);

                //create square in grid with correct color
                fill(square.getColor());
                rect(square.getXPos(), square.getYPos(), SQUARE_WIDTH, SQUARE_WIDTH);
                if (square.getNumber() != Square.BLANK){
                    fill(0);
                    text(square.getNumber(), square.getXPos() + (SQUARE_WIDTH / 2), square.getYPos() + (SQUARE_WIDTH / 2));
                }
            }

            //create number grid
            Square number = numbers[i];

            determineSquareColor(number);

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

    private void determineSquareColor(Square s){
        int xPos = s.getXPos();
        int yPos = s.getYPos();

        //changing color while hovering over square
        if ((mouseX > xPos && mouseX < xPos + SQUARE_WIDTH) && (mouseY > yPos && mouseY < yPos + SQUARE_WIDTH)) {
            s.inSquareOn();
        } else {
            if (s.isInSquare()) s.inSquareOff();
        }
    }

    //mouse input
    public void mouseReleased(){

        //activating solve and reset buttons
        if (mouseX > SOLVE_RESET_X && mouseX < SOLVE_RESET_X + SOLVE_RESET_LENGTH){
            if (mouseY > SOVLE_BUTTON_Y && mouseY < SOVLE_BUTTON_Y + SOLVE_RESET_WIDTH){
                
                if (selectedSquare != null) {
                    selectedSquare.selectedOff();
                    selectedSquare = null;
                }

                solver.solve();
            }

            //clear board
            if (mouseY > RESET_BUTTON_Y && mouseY < RESET_BUTTON_Y + SOLVE_RESET_WIDTH){
                for (Square[] row : grid){
                    for (Square s : row){
                        s.reset();
                    }
                }

                Solver.reset();
                selectedSquare = null;
            }
        }

        Square s = numbers[numbers.length - 1];
        if (mouseX > s.getXPos() && mouseX < s.getXPos() + SQUARE_WIDTH){
            if (mouseY > s.getYPos() && mouseY < s.getYPos() + SQUARE_WIDTH){
                selectedSquare.setNumber(s.getNumber());
            }
        }

        //select square
        int squareCol = (mouseX - SQUARE_START_POS) / SQUARE_WIDTH;
        if (mouseX > SQUARE_START_POS && squareCol >= 0 && squareCol < SQUARES_IN_ROW){

            //select square on board
            int squareRow = (mouseY - SQUARE_START_POS) / SQUARE_WIDTH;
            if (mouseY > SQUARE_START_POS && squareRow >= 0 && squareRow < SQUARES_IN_COLUMN){
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

        //keyboard alternative for reset button
        if (key == 'r' || key == 'R'){
            for (Square[] row : grid){
                for (Square s : row){
                    s.reset();
                }
            }

            Solver.reset();
            selectedSquare = null;
        }

        //keyboard alternative to mouse input
        if ((key == 'w' || key == 'W')
         || (key == 'a' || key == 'A')
         || (key == 's' || key == 'S')
         || (key == 'd' || key == 'D')
         || key == CODED){

            //initialize
            if (selectedSquare == null) {
                selectedSquare = grid[0][0];
                selectedSquare.selectedOn();
                return;
            }

            //calculate location on grid
            int row = (selectedSquare.getXPos() - SQUARE_START_POS) / SQUARE_WIDTH;
            int col = (selectedSquare.getYPos() - SQUARE_START_POS) / SQUARE_WIDTH;

            selectedSquare.selectedOff();

            if (key == 'w' || key == 'W' || keyCode == UP) col -= 1;
            if (key == 'a' || key == 'A' || keyCode == LEFT) row -= 1;
            if (key == 's' || key == 'S' || keyCode == DOWN) col += 1;
            if (key == 'd' || key == 'D' || keyCode == RIGHT) row += 1;

            //prevents out of bounds
            if (col > 8) col = 0;
            if (col < 0) col = 8;
            if (row > 8) row = 0;
            if (row < 0) row = 8;

            selectedSquare = grid[row][col];
            selectedSquare.selectedOn();

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

            //keyboard alternative for delete button
            else if (key == BACKSPACE) selectedSquare.setNumber(Square.BLANK);

        }
    }

    public static void main(String[] args) {
        PApplet.main("SudokuSolver");
    }
}
