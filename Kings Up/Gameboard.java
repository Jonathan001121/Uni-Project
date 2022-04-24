import java.util.Scanner;

/**
 * The class Gameboard is to print control the flow of the game and
 * contains a set of players and characters.
 */
public class Gameboard {

    /**
     * The maximum number of characters can be placed in the same position.
     */
    public static final int FULL = 4;
    /**
     * The total number of characters
     */
    public static final int NO_OF_CHARACTER = 13;
    /**
     * The total number of player
     */
    public static final int NO_OF_PLAYERS = 4;
    /**
     * The position of Throne
     */
    public static final int THRONE = 6;
    /**
     * The scores calculation formula
     */
    public static final int[] SCORES = {0, 1, 2, 3, 4, 5, 10};
    /**
     * The name of the characters
     */
    public static final String[] CHARACTER_NAMES = {
            "Aligliero", "Beatrice", "Clemence", "Dario",
            "Ernesto", "Forello", "Gavino", "Irima",
            "Leonardo", "Merlino", "Natale", "Odessa", "Piero"
    };
    /**
     * The name of the players
     */
    public static final String[] PLAYER_NAMES = {
        "You", "Computer 1", "Computer 2", "Computer 3"
    };
    /**
     * Determine if the players are human player or not.
     */
    public static final boolean[] HUMAN_PLAYERS = {
        true, false, false, false
    };
    /**
     * A list of character
     */
    private Character[] characters;
    /**
     * A list of player
     */
    private Player[] players;


    public static void main(String[] argv) {
        new Gameboard().runOnce();
    }

    /**
     * Initialize all data attributes. You will need to initialize and create
     * the array players and characters. You should initialize them using the
     * String array PLAYER_NAMES and CHARACTER_NAMES respectively.
     */
    public Gameboard() {
        characters=new Character[NO_OF_CHARACTER];
        players=new Player[NO_OF_PLAYERS];
        // create all the character and player object.
        for(int i=0;i< CHARACTER_NAMES.length;i++) {
            characters[i]=new Character(CHARACTER_NAMES[i]);
        }
        for(int i=0;i<PLAYER_NAMES.length;i++){
            players[i]=new Player(PLAYER_NAMES[i],characters);
        }
    }

    /**
     * The main logic of the game. This part has been done for you.
     */
    public void runOnce() {

        print();
        System.out.println("======= Placing stage ======= \n"
                + "Each player will take turns to place three characters on the board.\n"
                + "No character can be placed in the position 0 or 5 or 6 (Throne) at this stage.\n"
                + "A position is FULL when there are four characters placed there already.\n"
                + "The remaining character will be placed at the position 0.\n");

        placingStage();

        print();
        System.out.println("======= Playing stage ======= \n"
                + "Each player will take turn to move a character UP the board.\n"
                + "You cannot move a character that is been killed or its immediate upper position is full.\n"
                + "A voting will be trigger immediately when a character is moved to the Throne (position 6).");

        playingStage();

        print();
        System.out.println("======= Scoring stage ======= \n"
                + "This will trigger if and only if the voting result is ALL positive, i.e., no player play the veto (reject) card. \n"
                + "The score of each player is computed by the secret list of characters owned by each player.");

        scoringStage();
    }


    /**
     * Print the scores of all players correctly. This part has been done
     * for you.
     */
    private void scoringStage() {
        for (Player p : players) {
            System.out.println(p);
            System.out.println("Score: " + p.getScore());
        }
    }

    /**
     * Perform the placing stage. You have to be careful that human player will need to chosen on what to place
     * Non-human player will need to place it using the method placeRandomly (see Player.java)
     */
    private void placingStage() {
        Scanner in = new Scanner(System.in);
        // loop 12 time  to place character.
        for (int i = 0; i < characters.length-1; i++) {
            print();
            System.out.println();
            System.out.print(players[i % 4]);
            System.out.println(", this is your turn to place a character");
            String cToPick;
            int floor;
            //if i%4 ==0, then it is player turn.
            if (i % 4 == 0) {
                boolean isNotIn=true;
                do {
                    System.out.println("Please pick a character");
                    cToPick = in.next();
                    // check if it equals to any of the characters name and if it is outOfGame.
                    // if not then loop to pick character that is on the list and is out of game.
                    for (int j = 0; j < characters.length; j++) {
                        if (characters[j].getName().equals(cToPick) && characters[j].getPosition() == Character.OUT_OF_GAME) {
                            isNotIn = false;
                            //step into a loop to pick a floor is able to place;
                            while (true) {
                                int floorCount=0;
                                System.out.println("Please enter the floor you want to place " + cToPick);
                                floor = in.nextInt();
                                // loop through the character list to check all the character's position.
                                for(int k=0;k< characters.length;k++){
                                    if(characters[k].getPosition()==floor){
                                        floorCount++;
                                    }
                                }
                                //if the floor it pick is between 1 to 4 and that floor the player pick has less than 4 people.
                                //then set the character to the chosen floor and break the loop.otherwise repeat to enter a new floor.
                                if ((floor>=1 && floor<5) && floorCount<4 ) {
                                    characters[j].setPosition(floor);
                                    break;
                                }
                            }
                        }
                    }
                }while(isNotIn);

            }else{
                //computer turn to place character.
                players[i%4].placeRandomly(characters);
            }
        }
        //the thirteen character to place. that is place one the floor 0.
        Character thirteen = null;
        for(int i=0;i<characters.length;i++){
            if(characters[i].getPosition()==-1){
                thirteen=characters[i];
            }
        }
        thirteen.setPosition(0);
    }


    /**
     * Perform playing stage. Be careful that human player will need to pick the character to move.
     * You should detect any invalid input and stop human player from doing something nonsense or illegal.
     * Computer players will need to run the code pickCharToMoveRandomly or pickCharToMoveSmartly to pick which character to move.
     */
    private void playingStage() {
        //loop until a character has been voted for the new King.
        Scanner in = new Scanner(System.in);
        boolean someoneWin = false;
        //loop until someone won;
        for (int i = 0; !someoneWin; i++) {
            print();
            System.out.println();
            if (i % 4 == 0) {
                //player turn
                System.out.println(players[i%4]);
                System.out.println("This is your turn to move a character up\n");
                Character toPick;
                // input a String to move a level above. and if pickCHarToMove return null,
                //it repeats the process until pickCharToMove return a character.
                do{
                    System.out.println("Please type the character that you want to move.");
                    String input = in.next();
                    toPick = players[i%4].pickCharToMove(characters, input);
                }while(toPick==null);
                toPick.setPosition(toPick.getPosition() + 1);
            }else{
                //computer turn;
                System.out.println(players[i%4]);
                System.out.println("This is your turn to move a character up\n");
                //randomly pick a moveable character to move.
                Character toPick=players[i%4].pickCharToMoveRandomly(characters);
                toPick.setPosition(toPick.getPosition()+1);
            }
            print();
            System.out.println();
            //check if any character position is on throne,if yes then jump to voting stage.
            for (int j = 0; j < characters.length; j++) {
                if(characters[j].getPosition()==THRONE){
                    System.out.println("Please vote. Type V for veto. Other for accept");
                    boolean[]checkVeto=new boolean[players.length];
                    char input=in.next().charAt(0);
                    int falseCount=0;
                    //to check if player want to play veto card and add that boolean into a boolean array.
                    if(input=='V'){
                        checkVeto[0]=players[0].vote(false);

                    }else{
                        checkVeto[0]=players[0].vote(true);
                    }
                    // loop through the rest of the computer to randomly vote and put them into a boolean array.
                    for (int k = 1; k < players.length; k++) {
                        checkVeto[k]=players[k].vote(players[k].voteRandomly());
                    }
                    //loop through the boolean array to check if any of the player played a Veto card.
                    for (int k = 0; k < checkVeto.length; k++) {
                        if(!checkVeto[k]){
                            falseCount++;
                        }
                    }
                    //if falseCount equal 0. it means no one play veto card.and someonewin is true.
                    //else falsecount >0, one of the player played veto card. then the game continue.
                    if(falseCount==0){
                        someoneWin=true;
                    }else if(falseCount>0){
                        characters[j].setPosition(Character.OUT_OF_GAME);
                        break;
                    }
                }

            }
        }
        System.out.println();
    }



    /**
     * Print the gameboard. Please see the assignment webpage or the demo program for
     * the format. You should call this method after a character has been moved or placed or killed.
     */
    private void print() {
        for(int i=THRONE;i>=0;i--){
            System.out.print("Level"+i+":");
            for(int j=0;j< characters.length;j++){
                if(characters[j].getPosition()==i){
                    System.out.print(characters[j].getName()+"\t");
                }
            }
            System.out.println();
        }
        System.out.println("Unplaced/Killed Characters");
        for(int i=0;i< characters.length;i++){
            if(characters[i].getPosition()==-1){
                System.out.print(characters[i]+"\t");
            }
        }
        System.out.println();


    }

}
