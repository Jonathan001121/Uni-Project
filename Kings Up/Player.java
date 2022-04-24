import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Model the player
 */
public class Player {
    /**
     * The length of the list
     */
    private static final int LIST_LENGTH = 6;
    /**
     * A secret list of a player. This list would not be changed during the game
     */
    private Character[] myList;
    /**
     * The number of veto (reject) voting card. Decrement it when the player vote
     * reject.
     */
    private int vetoCard;
    /**
     * The name of the player.
     */
    private String name;

    /**
     * Compute the score of a player. Each player should have a list of character
     *
     * @return the score of a player.
     */
    public int getScore() {
        int scores=0;
        int[] characterPosition=new int[myList.length];
        int outOfGameCount=0;
        //create an array and put the characters position of secret list in the array.
        for(int i=0;i< myList.length;i++){
            characterPosition[i]=myList[i].getPosition();
        }
        //loop through the array. if its position equal to 6. scores plus ten.otherwise add its own position;
        //if position equal to -1(out of game).then outOfGameCount increment  it. At then end if if equal to six it means
        //all of the character on the secret list is dead. and increase 33 point in this situation.
        for(int i=0;i<characterPosition.length;i++){
            if(characterPosition[i]==Gameboard.THRONE){
                scores+=Gameboard.SCORES[Gameboard.SCORES.length-1];
            }else if(characterPosition[i]== Character.OUT_OF_GAME){
                outOfGameCount++;
            }else{
                scores+=characterPosition[i];
            }
        }
        if(outOfGameCount>=6){
            scores+=33;
        }
        return scores;
    }

    /**
     * Return the name of a player.
     * @return the name of the player
     */
    public String getName() { 
        return this.name;
     }

    /**
     * Initialize the number of veto card
     * @param card the number of veto card
     */
    public void initVetoCard(int card) {
        this.vetoCard=card;
    }

    /**
     * Initialize all data attributes.
     * @param name the name of the player
     * @param list a whole list of characters and the player should pick
     *             LIST_LENGTH of <b>unique</b> characters
     */
    public Player(String name, Character[] list) {
        this.name=name;
        myList=new Character[LIST_LENGTH];
        initVetoCard(3);
        boolean isRepeat;
        //randomly assign a secret list and if the list are repeated then loop through this process again.
        do {
            isRepeat=false;
            for (int i = 0; i < myList.length; i++) {
                myList[i] = list[ThreadLocalRandom.current().nextInt(0, list.length)];
            }
            for(int i=0;i< myList.length;i++){
                for(int j=i+1;j< myList.length;j++){
                    if(myList[i]==myList[j]){
                        isRepeat=true;
                    }
                }
            }
        }while(isRepeat);
    }

    /**
     * A method to vote according to the preference of the parameter support.
     * A player is forced to vote yes (support) if he/she has no more veto card.
     *
     * @param support The preference of the player
     * @return true if the player support it. False if the player reject (veto).
     */
    public boolean vote(boolean support) {
        if(vetoCard==0|| support){
            return true;
        }else{
            this.vetoCard--;
            return false;
        }
    }

    /**
     * Vote randomly. The player will be forced to vote support(true) if he is
     * running out of veto card.
     * @return true if the player support it. False if the player reject (veto).
     */
    public boolean voteRandomly() {
        if(vetoCard==0){
            return true;
        }else{
            return ThreadLocalRandom.current().nextBoolean();
        }
    }

    /**
     * Randomly place a character during the placing stage. This method should pick a
     * random unplaced character and place it to a random valid position.
     * @param list The list of all characters. Some of these characters may have been
     *             placed already
     * @return the character that just be placed.
     */
    public Character placeRandomly(Character[] list) {
        boolean check;
        Character c;

        //randomly pick a character in the list and randomly pick a floor between 1 to 4;
        //first check the numbers of character in that floor. if its less than 4 people and that character is outOfGame then place it on.
        //otherwise, if it that floor has 4 people or the character have already placed. it repeats the process.
        do {
            int count = 0;
            c = list[ThreadLocalRandom.current().nextInt(0, list.length)];
            int randomFloor = ThreadLocalRandom.current().nextInt(1, 5);
            check = false;
            for (int i = 0; i < list.length; i++) {
                if (list[i].getPosition() == randomFloor) {
                    count++;
                }
            }
            if (c.getPosition() == Character.OUT_OF_GAME && count <= 3) {
                c.setPosition(randomFloor);
                check = true;
            }
        }while(!check);
        return c;


    }

//    /**
//     * The player shall vote smartly (i.e., its decision will be based on if the player has that
//     * character in his/her list.) If the player is running out of veto card, he/she will be forced
//     * to vote support (true).
//     *
//     * @param character The character that is being vote.
//     * @return true if the player support, false if the player reject(veto).
//     */
//    public boolean voteSmartly(Character character) {
//        //TODO
//    }

    /**
     * The player shall pick a randomly character that is <i>movable</i> during the playing stage.
     * Movable means the character has not yet be killed and the position right above it is not full.
     *
     * Note: this method should not change the position of the character.
     *
     * @param list The entire list of characters
     * @return the character being picked to move. It never returns null.
     */
    public Character pickCharToMoveRandomly(Character[] list) {
        Character c;
        //randomly assign a character on its list count the level above, if one floor above of that chosen character
        // is full(more than 3 people) and the character's position equal to -1(outOfGame),then it repeat it to find a
        //character whose able to move;
        while(true){
            int count=0;
            c=list[ThreadLocalRandom.current().nextInt(0, list.length)];
            for(Character i:list){
                if(i.getPosition()==c.getPosition()+1){
                    count++;
                }
            }
            if(c.getPosition()!=Character.OUT_OF_GAME && count<Gameboard.FULL){
                return c;
            }
        }
    }

    /**
     * This method return the character who's name is the same as the
     * variable name if the character is <i>movable</i>. Movable means
     * the character has not yet be killed and the position right above
     * it is not full.
     *
     * If the name of the character can't be found from the list or the
     * character is not movable, this method returns null.
     *
     * Note: this method should not change the position of the character.
     *
     * @param list The entire list of characters
     * @param name The name of the character being picked.
     * @return the character being picked to move or null if the character
     *          can't be found or the it is not movable.
     */
    public Character pickCharToMove(Character[] list, String name) {
        int targetPosition;
        int count=0;
        //loop through the list to it finds the String that is equal to any of the characters and
        //if its already outOfGame. if it is not out of game then check the floor of its above.
        //and if it above level is not full return the character. otherwise, return null.
        for(int i=0;i<list.length;i++){
            if(name.equals(list[i].getName()) && list[i].getPosition()!=Character.OUT_OF_GAME){
                targetPosition=list[i].getPosition();
                int checkLevel=targetPosition+1;
                for(int j=0;j< list.length;j++){
                    if(list[j].getPosition()==checkLevel){
                        count++;
                    }
                }
                if(count<Gameboard.FULL){
                    return list[i];
                }else{
                    return null;
                }
            }
        }
            return null;
    }

//    /**
//     * Similar to pickCharToMoveRandomly only as the character being picked to move
//     * is related to the secret list of the player. The implementation of this part is
//     * open and you are advised to optimize it to increase the chance of winning.
//     *
//     * Note: this method should not change the position of the character.
//     *
//     * @param list The list of character
//     * @return the character to be move. It never returns null.
//     */
//    public Character pickCharToMoveSmartly(Character[] list) {
//        //TODO
//    }

    /**
     * This returns the name of the player and the secret list of the characters.
     * as a string
     * @return The name of the player followed by the secret list of the characters.
     */
    public String toString() {
        //return the players name, number of veto card and his secret list.
        return this.name+"      Veto Card:"+this.vetoCard+"\n"+Arrays.toString(this.myList);
    }
}
