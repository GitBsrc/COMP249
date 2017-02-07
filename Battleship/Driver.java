/**
 * The Driver class executes the Battleship game!
 * @author Berfin Saricam 40017210
 * @version 1
 * COMP_249_ASSIGMENT_1
 * Winter 2017
 */

public class Driver
{
	/**
	 * Purpose is to start the game.
	 * @param gameObj creates a type Battleship
	 * @return all the strings from Battleship and final winner.
	 */
	public static String gameMustGoOn(Battleship gameObj)
	{
		Boolean win = false;
		String playerWinning;
		
		//do while loop goes on until a player no longer has ships left
		do 
		{
			gameObj.getUserTarget();
			gameObj.computerTurn();

			if( (gameObj.getCompShips() == 0) || (gameObj.getHumanShips() == 0) )
				win = true;

		} while(win == false);
		playerWinning = (gameObj.getCompShips() == 0) ? "Human" : "Computer";
		//if computer has no more ships, human wins, else computer wins
		return playerWinning;

	}

	public static void main(String[] args)
	{
		Battleship gameObj = new Battleship();
		
		String gameWinner = gameMustGoOn(gameObj);
		System.out.println("After a fair but difficult game, the winner of this battle was: "+ gameWinner+ ".");
		System.out.println("Thank you for playing, see you next time!");
	}
}
