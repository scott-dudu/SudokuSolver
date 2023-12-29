
import java.util.HashSet;
import java.util.Iterator;

public class Solver {
    private Square[][] grid;
    private boolean firstCheck;
    private boolean stuck;

    public Solver(Square[][] grid){
        this.grid = grid;
        firstCheck = false;
        stuck = true;
    }

    private boolean isSolved(){
        for (Square[] row : grid){
            for (Square s : row){
                if (s.getNumber() == Square.BLANK) return false;
            }
        }

        return true;
    }

    private boolean isBlank(){
        for (Square[] row : grid){
            for (Square s : row){
                if (s.getNumber() != Square.BLANK) return false;
            }
        }

        return true;
    }

    private boolean isValid(){
        int row, col;

        for (row = 0; row < SudokuSolver.SQUARES_IN_COLUMN; row++){
            for (col = 0; col < SudokuSolver.SQUARES_IN_ROW; col++){
                Square s = grid[row][col];
                
                if (s.getNumber() != Square.BLANK){
                    if (invalidByLines(s, row, col) || invalidBySquare(s, row, col)) return false;
                }

            }
        }


        return true;
    }

    private boolean invalidByLines(Square s, int row, int col){
        int num = s.getNumber();

        for (int i = 0; i < SudokuSolver.SQUARES_IN_COLUMN; i++){
            
            if (!grid[row][i].equals(s) && grid[row][i].getNumber() == num) return true;
            if (!grid[i][col].equals(s) && grid[i][col].getNumber() == num) return true;

        }

        return false;
    }

    private boolean invalidBySquare(Square s, int row, int col){
        int squareRow = (row / 3) * 3;
        int squareCol = (col / 3) * 3;
        int num = s.getNumber();

        for (int i = squareRow; i < squareRow + 3; i++){
            for (int j = squareCol; j < squareCol + 3; j++){

                if (!grid[i][j].equals(s) && grid[i][j].getNumber() == num) return true;

            }
        }

        return false;
    }

    public Square[][] solve(){
        while (!isSolved()) {
            if (isBlank() || !isValid()) return null;

            stuck = true;

            if (!scan()) return null;

            if (firstCheck && stuck){
                if (!guess(grid)) return null;
            }

            firstCheck = true;

        }

        return grid;
    }

    private boolean scan(){
        int i, j;

        //loop through the board
        for(i = 0; i < SudokuSolver.SQUARES_IN_COLUMN;  i++){
            for (j = 0; j < SudokuSolver.SQUARES_IN_ROW; j++){

                //only scan for blank squares
                if (grid[i][j].getNumber() == Square.BLANK){
                    Square s = grid[i][j];

                    //get possible nums
                    s.initialize();
                    scanByLines(s, i, j);
                    scanBySquare(s, i, j);

                    //immediately exits when the puzzle is impossible to solve
                    //second check if square is blank due to processOfElimination ability to insert num
                    if (s.getNumber() == Square.BLANK && s.getPossibleNums().isEmpty()) return false;
                    
                    //if there is only one possible num, insert the num
                    if (s.getPossibleNums().size() == 1){
                        Iterator<Integer> it = s.getPossibleNums().iterator();
                        int num = it.next();
                        s.setNumber(num);
                        s.setTextColor(255, 0, 0);
                        removeNum(grid, num, i, j);

                        stuck = false;
                    }
                }

            }
        }

        return true;
    }

    private void scanByLines(Square s, int row, int col){

        //used for process of elimation
        HashSet<Square> blankSquares = new HashSet<>();

        int i;

        for (i = 0; i < SudokuSolver.SQUARES_IN_COLUMN; i++){

            //remove numbers already in the column
            if (grid[i][col].getNumber() != Square.BLANK) s.removePossibleNum(grid[i][col].getNumber());
            else {

                //check to not add the square the program is on
                if (!grid[i][col].equals(s)) blankSquares.add(grid[i][col]);

            }

        }

        //if inserted a number through process of elimination, exit immediately
        if (firstCheck && processOfElimination(s, blankSquares, row, col)) return;
        blankSquares.clear();

        for (i = 0; i < SudokuSolver.SQUARES_IN_ROW; i++){

            //remove numbers already in the row
            if (grid[row][i].getNumber() != Square.BLANK) s.removePossibleNum(grid[row][i].getNumber());
            else {

                //check to not add the square the program is on
                if (!grid[row][i].equals(s)) blankSquares.add(grid[row][i]);

            }

        }

        //if inserted a number through process of elimination, exit immediately
        if (firstCheck && processOfElimination(s, blankSquares, row, col)) return;
        blankSquares.clear();

    }

    private void scanBySquare(Square s, int row, int col){

        //used for process of elimination
        HashSet<Square> blankSquares = new HashSet<>();

        int squareRow = (row / 3) * 3;
        int squareCol = (col / 3) * 3;
        int i, j;

        //remove numbers already in the respective 3x3 grid
        for (i = squareRow; i < squareRow + 3; i++){
            for(j = squareCol; j < squareCol + 3; j++){

                if (grid[i][j].getNumber() != Square.BLANK) s.removePossibleNum(grid[i][j].getNumber());
                else {

                //check to not add the square the program is on
                    if (!grid[i][j].equals(s)) blankSquares.add(grid[i][j]);

                }
            }
        }

        if (firstCheck) processOfElimination(s, blankSquares, row, col);
    }

    private boolean processOfElimination(Square s, HashSet<Square> blankSquares, int row, int col){
        HashSet<Integer> nums = new HashSet<>();
        Iterator<Square> squaresIterator = blankSquares.iterator();
        int i;

        while (squaresIterator.hasNext()){
            nums.addAll(squaresIterator.next().getPossibleNums());
        }

        Iterator<Integer> sIterator = s.getPossibleNums().iterator();
        while (sIterator.hasNext()){
            i = sIterator.next();

            if (!nums.contains(i)){

                s.setNumber(i);
                s.setTextColor(255, 0, 0);
                removeNum(grid, i, row, col);

                stuck = false;

                return true;

            }
        }

        return false;
    }

    private void removeNum(Square[][] gridSquares, int n, int row, int col){
        int i, j;

        for (i = 0; i < SudokuSolver.SQUARES_IN_COLUMN; i++){
            if (gridSquares[row][i].getNumber() == Square.BLANK) gridSquares[row][i].removePossibleNum(n);
            if (gridSquares[i][col].getNumber() == Square.BLANK) gridSquares[i][col].removePossibleNum(n);
        }

        int squareRow = (row / 3) * 3;
        int squareCol = (col / 3) * 3;
        for (i = squareRow; i < squareRow + 3; i++){
            for (j = squareCol; j < squareCol + 3; j++){

                if (gridSquares[i][j].getNumber() == Square.BLANK) gridSquares[i][j].removePossibleNum(n);

            }
        }
    }

    private boolean guess(Square[][] gridSquares){
        Square s = findNextEmptySquare(gridSquares);

        HashSet<Integer> possibleNums = s.getPossibleNums();
        Iterator<Integer> it = possibleNums.iterator();

        int row = (s.getXPos() - SudokuSolver.SQUARE_START_POS) / SudokuSolver.SQUARE_WIDTH;
        int col = (s.getYPos() - SudokuSolver.SQUARE_START_POS) / SudokuSolver.SQUARE_WIDTH;
        int num;
        while (it.hasNext()){
            Square[][] temp = copy(grid);

            num = it.next();
            temp[row][col].setNumber(num);
            temp[row][col].setTextColor(255, 0, 0);
            removeNum(temp, num, row, col);

            Solver solver = new Solver(temp);
            
            if (solver.solve() != null) {
                grid = temp;
                return true;
            }
        }

        return false;
    }

    private Square findNextEmptySquare(Square[][] gridSquares){
        for (int i = 0; i < SudokuSolver.SQUARES_IN_COLUMN; i++){
            for (int j = 0; j < SudokuSolver.SQUARES_IN_ROW; j++){

                if (gridSquares[i][j].getNumber() == Square.BLANK) return gridSquares[i][j];

            }
        }

        return null;
    }

    private Square[][] copy(Square[][] gridSquares){
        Square[][] temp =  new Square[gridSquares.length][gridSquares[0].length];

        for (int i = 0; i < temp.length; i++){
            for (int j = 0; j <temp[0].length; j++){
                Square old = gridSquares[i][j];

                Square newSquare = new Square(old.getXPos(), old.getYPos(), old.getNumber(), old.getTextColor());
                temp[i][j] = newSquare;
            }
        }

        return temp;
    }

    public void reset(){firstCheck = false;}
}
