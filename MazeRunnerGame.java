import java.util.Scanner;

public class MazeRunnerGame {
    private char[][] maze = {
        {'#', '#', '#', '#', '#', '#', '#'}, //Build a starter maze 
        {'#', 'P', '.', '.', '.', '.', '#'},
        {'#', '#', '#', '#', '.', '#', '#'},
        {'.', '.', '.', '.', '.', '.', '#'},
        {'#', '#', '#', '.', '#', '#', '#'},
        {'.', '.', '.', '.', 'E', '.', '#'},
        {'#', '#', '#', '#', '#', '#', '#'}
    };

    private int playerRow = 1; // These two lines declare and initialize a 2D character array called 'maze which represents the gamers maze
    private int playerCol = 1; 
    private int numberOfSteps = 0; //These lines declare and initialize playerRow and playerCol, which are used to keep track of the player's position in the maze.
    private int score = 0;
    private int highScore = 0;
    private boolean gameOver = false; // flag to control the game loop

    private Scanner scanner = new Scanner(System.in); // Used to read input from user

    public static void main(String[] args) {
        MazeRunnerGame game = new MazeRunnerGame();
        game.runMainMenu();
    }
 // The main method of the program. It creates an instance of the MazeRunnerGame class and calls the runMainMenu() method to start the game.
    
    public void runMainMenu() {
        boolean exitGame = false; // Declaration of the runMainMenu() method, which displays the main menu and handles the user's choice.
        while (!exitGame) {
            displayMainMenu();    // Start of a while loop that runs as long as "exitGame" is false.
            int option = getUserChoice();
            switch (option) {           // Use a switch statement to execute different actions based on the user's menu choice.
                case 1:
                    playGame();
                    break;
                case 2:
                    showInstructions();
                    break;
                case 3:
                    showCredits();
                    break;
                case 4:
                    showHighScore();
                    break;
                case 5:
                    exitGame();
                    exitGame = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Thanks for playing my game");
    }
 // Declaration of the displayMainMenu() method, responsible for showing the main menu options to the player.
    
    public void displayMainMenu() {
        System.out.println("===== Maze Runner Main Menu =====");
        System.out.println("1. Play Game");
        System.out.println("2. Instructions");
        System.out.println("3. Credits");
        System.out.println("4. High Score");
        System.out.println("5. Exit");
        System.out.println("===============================");
    }
 // Display the main menu options to the player.
    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in); //responsible for getting the user's choice from the menu.
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            System.out.print("Enter your choice: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Use a conditional statement to validate that the user enters a valid integer choice
        return choice;
    }
 // Read and return the user's choice as an integer, and consume the newline character to prevent issues when reading input later.
    
    public void playGame() {
        System.out.println("Welcome to Maze Runner! Find your way to the exit (E). Good luck!");
        long startTime = System.currentTimeMillis();

        while (true) { // Update the loop condition
            printMaze(); // Displays a welcome message to the player and start a timer to measure the elapsed time
            handlePlayerMovement(); 
            
            // Call the printMaze() method to display the current state of the maze and the handlePlayerMovement() method to handle the player's movement.
            if (hasPlayerWon()) {
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                score = calculateScore(elapsedTime);
                System.out.println("Congratulations! You reached the exit. You win!");
                displayResult();  // Check if the player has reached the exit (E) and won the game.
                if (startNewGame()) { // Calculate the player's score based on the elapsed time and the number of steps taken, congratulate the player, and display the final result.
                    maze = createNewMaze();
                    playerRow = 1;
                    playerCol = 1;
                    numberOfSteps = 0;
                    score = 0;
                    startTime = System.currentTimeMillis();
                } else {
                    gameOver = false; // Ask the player if they want to play again. If yes, reset the maze, player position, steps, score, and start a new game.
                    break; //exit game loop
                }
                return; // Exit the playGame() method after the game is won or exited.
            }

            numberOfSteps++; // Return from the playGame() method after the game is won or the player chooses not to play again.
        

            
        }
    }
 // Increment the number of steps taken by the player after each move.
    public void printMaze() { //responsible for displaying the current state of the maze
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
// nested loops to iterate through the 2D maze array and print each cell to the console, row by row.
    public void handlePlayerMovement() {
        System.out.print("Enter your move (W/A/S/D): ");
        char move = scanner.next().charAt(0);

        // Starts the timer to measure the elapsed time for the player's move.
        long startTime = System.currentTimeMillis();

        switch (move) {
            case 'W':
            case 'w':
                movePlayer(-1, 0); // Move up (row - 1)
                break;
            case 'A':
            case 'a':
                movePlayer(0, -1); // Move left (column - 1)
                break;
            case 'S':
            case 's':
                movePlayer(1, 0); // Move down (row + 1)
                break;
            case 'D':
            case 'd':
                movePlayer(0, 1); // Move right (column + 1)
                break;
            default:
                System.out.println("Invalid move. Please try again.");
                handlePlayerMovement();
             
                // Uses a switch statement to determine the direction of the player's move and call the movePlayer() method with appropriate row and column offsets.
        }

        // Calculate the elapsed time after the move
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        // Check if the elapsed time exceeds 10 seconds
        if (elapsedTime > 10000) {
            System.out.println("Time's up! You took more than 10 seconds for this move.");
            displayResult();
            if (startNewGame()) {
                maze = createNewMaze();
                playerRow = 1;
                playerCol = 1;
                numberOfSteps = 0;
                score = 0;
            } else {
                exitGame();
            }
        }
    }
// Moves player according to the given rows and columns
    public void movePlayer(int rowOffset, int colOffset) {
        int newRow = playerRow + rowOffset;
        int newCol = playerCol + colOffset;

        // Check if the new position is the exit ('E')
        if (maze[newRow][newCol] == 'E') {
            System.out.println("Congratulations! You reached the exit. You win!");
            displayResult();
            if (startNewGame()) {
                maze = createNewMaze();
                playerRow = 1;
                playerCol = 1;
                numberOfSteps = 0;
                score = 0;
            } else {
                gameOver = true; // Exit game loop
            }
            return;
        }
     // Calculate the new position of the player based on the row and column offsets.
        if (isValidMove(newRow, newCol)) {
            maze[playerRow][playerCol] = '.';
            playerRow = newRow;
            playerCol = newCol;
            maze[playerRow][playerCol] = 'P';
         // Check if the new position is a valid move (not out of bounds and not a wall '#').
         // If the move is valid, update the player's position in the maze and print the updated maze.
         // If the move is not valid, inform the player that it's an invalid move and prompt them to try again
            //printMaze();
        } else {
            System.out.println("Invalid move. Please try again.");
        }
    }
 
 // Check if the new position is the exit ('E'). If yes, congratulate the player, display the result, and ask if they want to play again.


    public void exitGame() {
        System.out.println("Thanks for playing Maze Runner! Goodbye!");
        gameOver = true; // Set gameOver flag to true to exit the game loop
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && maze[row][col] != '#';
    } // Declaration of the isValidMove() method, responsible for checking if a move is valid (within the maze boundaries and not a wall).
    	
    // Returns true if the move is within the maze boundaries and the cell at the new position is not a wall ('#'), otherwise, return false.
   
    
    public boolean hasPlayerWon() { 
        return maze[playerRow][playerCol] == 'E'; //responsible for checking if the player has won the game (reached the exit).
    } // Return true if the player's current position in the maze is the exit ('E'), indicating the player has won the game.

    
    public int calculateScore(long elapsedTime) { // Calculate the score using a formula. The player receives points for finishing the maze quickly (time-based) and loses points for the number of steps taken.
        return 1000 - (int) (elapsedTime / 100) - (numberOfSteps * 10);
    } 
    
    // Display the number of steps taken and the player's score. If the current score is higher than the previous high score, update the high score.
    
    public void displayResult() {
        System.out.println("Number of Steps: " + numberOfSteps);
        System.out.println("Score: " + numberOfSteps);
        if (numberOfSteps > highScore) {
            highScore = score; 
        }
    }

 // Displays the game instructions to the player, explaining how to navigate the maze using the specified keys.
    public void showInstructions() {
        System.out.println("=== Instructions ===");
        System.out.println("Navigate the maze using the following keys:");
        System.out.println("W: Move up");
        System.out.println("A: Move left");
        System.out.println("S: Move down");
        System.out.println("D: Move right");
        System.out.println("Reach the exit point (E) to win!");
        System.out.println("====================");
    }

    public void showCredits() {
        System.out.println("=== Credits ===");
        System.out.println("Game developed by Salman Naqvi");
        System.out.println("Version 1.0");
        System.out.println("================");
    }

    // Displays the highest score achieved in the game (highScore) to the player.
    public void showHighScore() {
        System.out.println("=== High Score ===");
        System.out.println("High Score: " + highScore);
        System.out.println("===================");
    }


  
    private char[][] createNewMaze() {
        char[][] newMaze = {
            {'#', '#', '#', '#', '#', '#', '#'},
            {'#', 'P', '.', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '.', '#', '#'},
            {'#', '.', '.', '.', '.', '.', '#'},
            {'#', '#', '#', '.', '#', '#', '#'},
            {'#', '.', '.', '.', 'E', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#'}
        };
        return newMaze;
    }

    public boolean startNewGame() {
        System.out.print("Do you want to play again? (Y/N): "); // Ask the player if they want to play again. If yes ('Y' or 'yes'), return true to start a new game.
        String response = scanner.next().trim().toLowerCase();
        if (response.equals("y") || response.equals("yes")) {
            return true;
        } else if (response.equals("n") || response.equals("no")) {
            System.out.println("Thanks for playing Maze Runner! Goodbye!");
            gameOver = true;
            return false;
        } else {
            System.out.println("Invalid response. Please enter 'Y' or 'N'.");
            return startNewGame(); // Ask again for a valid response
        }
    
    }
}







	        
 
	        


