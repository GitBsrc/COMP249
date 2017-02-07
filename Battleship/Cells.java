/**
 * Creates all getters and setters and an object to be used in Battleship class
 * @author Berfin Saricam 40017210
 * @version 1
 * COMP_249_ASSIGNMENT_1
 * Winter 2017
 */
public class Cells{
	private Boolean PositionShot;
	private Boolean occupied;
	private String shipOrGrenade;
	private String owner;


	public Cells(){
		shipOrGrenade = "_";
		owner = "_";
		PositionShot = false;
		occupied = false;
	}

	public Cells(String shipOrGrenade, String owner){
		this.shipOrGrenade = shipOrGrenade;
		this.owner = owner;
		this.PositionShot = false;
		this.occupied = false;
	}

	
	public String getshipOrGrenade(){
		return this.shipOrGrenade;
	}

	public String getOwner(){
		return this.owner;
	}

	public Boolean isPositionShot(){
		return this.PositionShot;
	}

	public Boolean isFull(){
		return this.occupied;
	}

	public Boolean hasShip(){
		if(this.shipOrGrenade == "Ship")
			return true;
		else return false;
	}

	public Boolean hasGrenade(){
		if(this.shipOrGrenade == "Grenade")
			return true;
		else return false;
	}


	public void setshipOrGrenade(String shipOrGrenade){
		this.shipOrGrenade = shipOrGrenade;
	}

	public void setOwner(String owner){
		this.owner = owner;
	}

	public void setPositionShot(){
		this.PositionShot = true;
	}

	public void setOccupied(){
		this.occupied = true;
	}

	public void occupyCoord(String shipOrGrenade, String owner){
		if(this.isFull() == false){
			this.setshipOrGrenade(shipOrGrenade);
			this.setOwner(owner);
			this.setOccupied();
		} 
		else{
				System.out.println("Sorry, coordinates already used. Try again!");
			}
	}

	//overriding the toString method
	public String toString()
	{
		String returnString = "";

		if( (this.isPositionShot() == true) && (this.isFull() == true) )
		{
			//hit owners
			if( (this.hasShip() == true) && this.getOwner() == "Human" )
				returnString = "s";
			
			if( (this.hasGrenade() == true) && this.getOwner() == "Human" )
				returnString = "g";
			
			if( (this.hasShip() == true) && this.getOwner() == "Computer" )
				returnString = "S";
			
			if( (this.hasGrenade() == true && this.getOwner() == "Computer") )
				returnString = "G";
			
		} else 
			if( (this.isPositionShot() == true)  && (this.isFull() == false) ){
					returnString = "*";
				}
			else returnString = "_";
		return returnString;
	}


}
