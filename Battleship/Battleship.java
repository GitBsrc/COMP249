/**
 * The Battleship class contains all important information regarding the battleship gameboard and the players information. 
 * @author Berfin Saricam 40017210
 * COMP_249_ASSIGNMENT_1
 * Winter 2017
 */
import java.util.Scanner;
import java.util.Random;

public class Battleship{
	
	//final constants
	final static int TOTAL_NUMBER_SHIPS = 6;
	final static int TOTAL_NUMBER_GRENADES = 4;
	final static int GMB_ROWS = 8;
	final static int GMB_COLS = 8;
	final static String COL_CHARACTERS = "ABCDEFGH";

	static Random rand = new Random();	
	static Scanner kb= new Scanner(System.in);
	
	//skip turn if grenade
	public static Boolean didHumanHitGrenade = false;
	public static Boolean didComputerHitGrenade = false;


	static Cells[][] gameBoard = new Cells[GMB_ROWS][GMB_COLS];



	/**
	 * @param initGameBoard initializes the gameboard coordinates 
	 * @param getPositions gets the positions of all the ships and grenades 
	 * @param populateComputerCells uses the random generator to place its ships and grenades
	 */
	public Battleship(){
		initGameBoard();
		getPositions();
		populateComputerCells();
	}

	
	//initialize the game board
	private void initGameBoard(){
		for(int i = 0; i < gameBoard.length  ; i++)
			for(int j = 0; j < gameBoard[i].length; j++)
				gameBoard[i][j] = new Cells();
	}

	private static String shootRocket(int row, int col, String player){
		String weapon = "";

		if(gameBoard[row][col].isFull() == true){
			weapon = gameBoard[row][col].getshipOrGrenade();
			System.out.println(weapon+" was hit by "+player+" !!");
		}
		else
			System.out.println(player+" missed!!");
			
		if(gameBoard[row][col].isPositionShot() == true)
			System.out.println("Position already called");
		
		gameBoard[row][col].setPositionShot();
		return weapon;
	}

	//random coordinates
	private static int[] randomCoord(){ 
		int row = rand.nextInt(GMB_ROWS);
		int col = rand.nextInt(GMB_ROWS);
		int[] returnCoordinates = {row, col};
		return returnCoordinates;
	}

	/** 
	 * Purpose is to make sure the ships and grenades are placed in bound.
	 * @param whatPosition stores player info on positions
	 * @return true if in bound, else will return error message later
	 */
	private static Boolean isValidInput(String whatPosition){
		String pattern = "[a-hA-H][1-8]";
		
		if(whatPosition.matches(pattern)){ 
			int[] coordinates = positionToRowAndCol(whatPosition);
			int row = coordinates[0];
			int col = coordinates[1];
			return true;
		}
		else return false;
	}

	/**
	 * Purpose is to set a position for each row and column cell.
	 * @param pos is to turn the position into a string
	 * @return array of row and column respectively
	 */
	private static int[] positionToRowAndCol(String pos){
		char firstCharacter = Character.toUpperCase(pos.charAt(0));
		int colNumber = COL_CHARACTERS.indexOf(firstCharacter);

		int rowNumber = Integer.valueOf(pos.substring(1)).intValue() - 1;
		int[] returnArray = { rowNumber, colNumber };

		return returnArray;
	}

	/**
	 * Purpose is to take the positions of each row and column cell and return as string. 
	 * @param coordinates
	 * @return capital letter row and column number as string
	 * resetting row index to 1
	 */
	private static String RowAndColString(int[] coordinates){
		int row = coordinates[0];
		int col = coordinates[1];
		char colLetter = COL_CHARACTERS.charAt(col); 
		row = row + 1; 
		
		return String.format("%c%d",colLetter,row);
	}

	/**
	 * Purpose is to get random coordinates for the computer ships and grenades.
	 */
	private static void populateComputerCells(){
		int computer_shipsLeft = TOTAL_NUMBER_SHIPS;
		int computer_grenadesLeft = TOTAL_NUMBER_GRENADES;

		while(computer_shipsLeft > 0){
			int[] coordinates = randomCoord();
			int x = coordinates[0];
			int y = coordinates[1];
			if(gameBoard[x][y].getshipOrGrenade() == "_"){
				gameBoard[x][y].occupyCoord("Ship", "Computer");
				computer_shipsLeft--;
			}
		}

		while(computer_grenadesLeft > 0){
			int[] coordinates = randomCoord();
			int x = coordinates[0];
			int y = coordinates[1];
			if(gameBoard[x][y].getshipOrGrenade() == "_"){
				gameBoard[x][y].occupyCoord("Grenade", "Computer");
				computer_grenadesLeft--;
			}
		}
	}

	/**
	* Begin Game! Purpose is to ask the human player to place its ships and grenades. 
	*/
	private static void getPositions(){
		int humShipsLeft = TOTAL_NUMBER_SHIPS;
		int humGrenadesLeft = TOTAL_NUMBER_GRENADES;
		System.out.println("Hi, let’s play Battleship!");
		while(humShipsLeft > 0){
			
			System.out.println(humShipsLeft+ " Ships ready to be placed!");
			System.out.println("Type in coordinates from [A-H][1-8] in the form A1, D3.");
			System.out.print("Answer: ");
			String userChoice = kb.nextLine();
			
			if(isValidInput(userChoice) == false){
				System.out.println("Sorry, coordinates outside the grid. Try again! \n");			
			} 
				else if( (isValidInput(userChoice) == true) ){
					int[] targetCoordinates = positionToRowAndCol(userChoice);
					int row = targetCoordinates[0];
					int col = targetCoordinates[1];		

					if( (gameBoard[row][col].isFull() == true) ){
						System.out.println("This Cell is full! Try again!");
					} 
						else{
							gameBoard[row][col].occupyCoord("Ship", "Human");
							humShipsLeft--;
					}
			}
		}


		while(humGrenadesLeft > 0){
			System.out.println(humGrenadesLeft+ " Grenades ready to be placed! ");
			System.out.println("Type in coordinates from [A-H][1-8] in the form A1, D3. ");
			System.out.print("Answer: ");
			String userChoice = kb.nextLine();
			if(isValidInput(userChoice) == true){
				int[] targetCoordinates = positionToRowAndCol(userChoice);
				int row = targetCoordinates[0];
				int col = targetCoordinates[1];

				gameBoard[row][col].occupyCoord("Grenade","Human");
				humGrenadesLeft--;
				} 
			else {
					System.out.println("Incorrect input or this cell is already full.. why don't you try again! \n");
			}
		}
		
	}


	/**
	 * Purpose is to check how many ships the computer has left.
	 * @return the number of ships the comp has left
	 */
	public int getCompShips(){
		int compShipsLeft = 0;

		for(int i = 0; i < gameBoard.length; i++)
				for(int j = 0; j < gameBoard[i].length; j++){
					if( (gameBoard[i][j].hasShip() == true) && (gameBoard[i][j].getOwner() == "Computer") && !(gameBoard[i][j].isPositionShot() == true) )
						compShipsLeft++;
				}	
		return compShipsLeft;
	}
	/**
	* Calculates how many ships the human player has left on the board.
	* @return how many ships human has left 
	*/
	public int getHumanShips(){
		int humanShipsLeft = 0;
		for(int i = 0; i < gameBoard.length; i++)
				for(int j = 0; j < gameBoard[i].length; j++){
					if( (gameBoard[i][j].hasShip() == true) && (gameBoard[i][j].getOwner() == "Human") && !(gameBoard[i][j].isPositionShot() == true) )
						humanShipsLeft++;
				}	
		return humanShipsLeft;
	}

	/**
	* Purpose is to set everything Computer does. 
	* Checks if computer hit a grenade 
	* also sets computers ships and grenades randomly 
	* (random initialized earlier) 
	* @return
	*/
	public static void computerTurn(){
		if(didComputerHitGrenade == true){
			didComputerHitGrenade = false;
			System.out.println("Computer misses a turn.");
			return;
		}

		int[] coordinates = randomCoord();
		int row = coordinates[0];
		int col = coordinates[1];
		System.out.println("Computer shot coordinate " + RowAndColString(coordinates)); //does not display what comp shot
		String shot = shootRocket(row,col, "Computer");
		if(shot == "Grenade")
			didComputerHitGrenade = true;

	}
	
	/** 
	 * Purpose is to check if player hits grenade. 
	 */
	public static void getUserTarget(){
		if(didHumanHitGrenade == true){
			didHumanHitGrenade = false;
			System.out.println("Human hit a grenade and misses a turn.");
			return;
		}
		
		Boolean validChoice = false;
		String userTarget = "";
		int[] coordinates;
		int row;
		int col;
		do 
		{
			showGameBoard();
			System.out.println("Type in coordinates from [A-H][1-8] in the form A1, D3. \n");
			System.out.print("Answer: ");
			userTarget = kb.nextLine();
			if(isValidInput(userTarget) == true){
				validChoice = true;
				coordinates = positionToRowAndCol(userTarget);
				row = coordinates[0];
				col = coordinates[1];
				String shot = shootRocket(row,col, "Human");
				if(shot == "Grenade"){
					didHumanHitGrenade = true;
				}
			}
				else{
					System.out.println("Sorry, coordinates outside the grid. Try again!");
				}

		} while(validChoice == false);
	}

	/**
	* Purpose is to display the gameboard.
	* the loops go through each row and column to display
	* everything inside
	*/
	public static void rowLetters(){
		// Prints the row letters.

		System.out.printf("   ");
		for(int i = 0; i < gameBoard.length; i++){
			System.out.printf(COL_CHARACTERS.charAt(i) + "  ");
		}
		System.out.printf("\n");

		
		//Loop print all the cell contents as well as the row and column numbers

		for(int i = 0; i < gameBoard.length; i++){
			
			System.out.printf((i+1) + " ");
			for(int j = 0; j < gameBoard[i].length; j++){
				System.out.printf(gameBoard[i][j].getOwner().substring(0,1));
				System.out.printf(gameBoard[i][j].getshipOrGrenade().substring(0,1));
				System.out.printf(" ");
			}
			System.out.println("");
		}
	}

	public static void showGameBoard(){

		System.out.printf("  ");
		for(int i = 0; i < gameBoard.length; i++){
			System.out.printf(COL_CHARACTERS.charAt(i) + " ");
		}
		System.out.printf("\n");

		// Prints the column numbers and the cells contents.

		for(int i = 0; i < gameBoard.length; i++){
			System.out.printf((i+1) + " ");
			
			for(int j = 0; j < gameBoard[i].length; j++){
				System.out.print(gameBoard[i][j]);
				System.out.printf(" ");		
			}	
			System.out.println("");
		}
		
	}

}
