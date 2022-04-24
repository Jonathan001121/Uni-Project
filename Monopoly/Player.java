import java.util.concurrent.ThreadLocalRandom;

public class Player {
    private final String name;
    private int money;
    private int position;
    private int lastMove;
    private boolean inPark;
    private boolean inJail;

    /**
     * Constructor
     * 
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name=name;
        money=10000;
        position=Gameboard.HOME_POSITON;


    }

    /**
     * A player roll a dice and change its position
     */
    public void roll() {
        if(!isInJail()) {
            lastMove = ThreadLocalRandom.current().nextInt(1, 7);
            this.position += lastMove;
            System.out.println(this.name + ", this is your turn. We roll for you.");
            System.out.println("It is..." + lastMove);
            if (this.position >= Gameboard.CELL_SIZE) {
                this.position -= Gameboard.CELL_SIZE;
                money += 2000;
            }
        }else if(isInJail()){
            System.out.println(name+", sorry you are in Jail. Skip one round.");
            inJail=false;
        }
    }
    /**
     * Return true if the player is in Park 
     *
     * @return true if the player is in Park
     */
    public boolean isInPark() { 
        if(this.position==11){
            return true;
        }
        return false;
    }
    /**
     * Set the player in a park. This will only be called when a player
     * move to the cell Car Park. 
     *
     * @param inPark true if the player is set in the park
     */
    public void setInPark(boolean inPark) { 
        if(inPark){
            this.position=11;
        }
     }
    /**
     * Return true if the player is in Jail. It should return false
     * if the player visits Jail (i.e., rolls a dice and moves to the cell Jail)
     *
     * @return true if the player is in Jail
     */
    public boolean isInJail() { 
        if(position==Gameboard.JAIL_POSITION&&inJail){
            return true;
        }
        return false;
     }
    /**
     * Put the player into Jail directly without passing Home (i.e., no 2000)
     */
    public void putToJail() {
        //TODO
        this.position=Gameboard.JAIL_POSITION;
        inJail=true;
    }

    /**
     * Get the value of dice that the player has just rolled.
     *
     * @return the value of dice
     */
    public int getLastMove() { 
        return this.lastMove;
    }
    /**
     * Get the amount of money that the player has
     *
     * @return the amount of money the player has
     */
    public int getMoney() { 
        return this.money;
     }
     /**
      * Return the name of the player
      *
      * @return the name of the player
      */
    public String getName() { 
        return this.name;
     }
     /**
      * return the current position of the player
      * 
      * @return the position of the player
      */
    public int getPosition() { 
        return this.position;
     }
     /**
      * charge certain amount of dollar from the player.
      * 
      * @param money The amount being charged
      */
    public void charge(int money) {
        this.money-=money;
     }


    //TODO add some other private methods if necessary
}
