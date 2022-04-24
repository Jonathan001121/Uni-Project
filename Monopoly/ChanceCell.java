import java.util.concurrent.ThreadLocalRandom;

/**
 * Got one of the following chances randomly:
 * 1. Roll again
 * 2. +1000
 * 3. -1000
 * 4. Move to Jail directly without getting the 2000.
 */
public class ChanceCell extends FunctionCell {
    public ChanceCell(String name){
        super(name);
    }

    @Override
    public void event(Player p, Cell[] cells) {
        int rollNumber = ThreadLocalRandom.current().nextInt(1, 5);
        if (rollNumber == 1) {
            System.out.println(name + " result: Roll again!");
            p.roll();
            System.out.println("You have landed on " + cells[p.getPosition()].name);
            cells[p.getPosition()].event(p, cells);
        } else if(rollNumber==2){
            System.out.println(name + " result: Gain $1000!");
            p.charge(-1000);
        }else if(rollNumber==3){
            System.out.println(name+" result: Deduct $1000!");
            p.charge(1000);
        }else if(rollNumber==4){
            System.out.println(name+" result: Go to Jail, now!");
            p.putToJail();
        }

    }
}