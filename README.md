## Introdution

This is a program that is able to solve already-made sudoku puzzles made by Scott Du.

## What is Sudoku

Sudoku is a game with a 9x9 grid. The grid is also split into nine 3x3 grids. The goal of the game is to fill in the grid with numbers 1-9 without any numbers repeating itself in a horizontal line, vertical line, and its respective 3x3 grid. There are usually some numbers already placed on the grid to help deduce which number goes where using logic and reasoning.

## How the Solver works

The program is coded to have the user input the problem into the board either by using the mouse or keyboard. Once the user is done, they can hit solve for the program to begin.

The program is coded to solve the problem like how a person would solve it. The program:

 1. scans the horizontal and vertical lines the box is in and reduce the possible numbers that can fit in the box

 2. scans the 3x3 grid the current box is in to further reduce the amount of possible numbers that can fit

 3. uses the process of elimination to confirm that a possible number can only be in the box to complete the horizonatl line, vertical line, or box

 4. inputs a number in when there is only one possible number that can go into the box

 5. begins guessing when there is no clear choice, in any box, from step 3 or 4.

Due to the human-like process the program takes, when given a illegitinate sudoku puzzle, the program starts guessing every possible outcome the program can take, increasing the time it takes to solve the given puzzle.

## Further improvement

I may revisit this code again to better improve the solver to solve legitimate and illegitimate sudoku puzzles.
