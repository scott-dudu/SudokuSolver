
import java.util.HashSet;
import java.util.Iterator;

public class Solver {
    private Square[][] grid;
    private static boolean firstCheck;

    public Solver(Square[][] grid){
        this.grid = grid;
        firstCheck = false;
    }

    public boolean isSolved(){
        for (Square[] row : grid){
            for (Square s : row){
                if (s.getNumber() == Square.BLANK) return false;
            }
        }

        return true;
    }

    public boolean isBlank(){
        for (Square[] row : grid){
            for (Square s : row){
                if (s.getNumber() != Square.BLANK) return false;
            }
        }

        return true;
    }

    public void solve(){
        if (isSolved()) return;

        scan();
        firstCheck = true;
        solve();
    }

    private void scan(){
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
                    
                    //if there is only one possible num, insert the num
                    if (s.getPossibleNums().size() == 1){
                        Iterator<Integer> it = s.getPossibleNums().iterator();
                        int num = it.next();
                        s.setNumber(num);
                        s.removePossibleNum(num);
                        removeNum(num, i, j);
                    }
                }

            }
        }
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

        //remove after done: to check code
        System.out.println(row + " " + col + " : " + s.getPossibleNums());

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
                s.clearPossibleNums();
                removeNum(i, row, col);
                return true;

            }
        }

        return false;
    }

    private void removeNum(int n, int row, int col){
        int i, j;

        for (i = 0; i < SudokuSolver.SQUARES_IN_COLUMN; i++){
            if (grid[row][i].getNumber() == Square.BLANK) grid[row][i].removePossibleNum(n);
            if (grid[i][col].getNumber() == Square.BLANK) grid[i][col].removePossibleNum(n);
        }

        int squareRow = (row / 3) * 3;
        int squareCol = (col / 3) * 3;
        for (i = squareRow; i < squareRow + 3; i++){
            for (j = squareCol; j < squareCol + 3; j++){

                if (grid[i][j].getNumber() == Square.BLANK) grid[i][j].removePossibleNum(n);

            }
        }
    }

    //TODO: implement guessing feature for when there are no clear choices.

    public static void reset(){firstCheck = false;}
}
