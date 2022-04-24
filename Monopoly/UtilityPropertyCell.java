import java.util.Scanner;

/**
* The rent of UtilityPropertyCell is computed by the number of steps that the player rolled multiply by x
* x = 100, if both property are owned by the same player
* x = 10, if the owner of this property cell owns only one property cell.
*/
public class UtilityPropertyCell extends PropertyCell {
    public static final int UTILITY_PROPERTY_COST = 500;
    public static final UtilityPropertyCell LIBRARY_UTILITY_CELL = new UtilityPropertyCell("Library", UTILITY_PROPERTY_COST);
    public static final UtilityPropertyCell CANTEEN_UTILITY_CELL = new UtilityPropertyCell("Canteen", UTILITY_PROPERTY_COST);
    public UtilityPropertyCell(String name,int baseCost){
        super(name,baseCost);

    }

    @Override
    public void event(Player p, Cell[] cells) {
        System.out.println("You have landed on: " + name + "!");
        Scanner in = new Scanner(System.in);
        if (owner == null && p.getMoney() >= baseCost) {
            System.out.print("Do you want to buy this for $" + baseCost + "? (y/n):");
            String input = in.next();
            if (input.equals("y")) {
                this.owner = p;
                p.charge(baseCost);
                System.out.println("You have bought this land!");
            }
        } else if (owner != p && owner!=null) {
            if (owner.isInPark()) {
                System.out.println(owner + " is in the Park. Free parking.");
            } else {
                p.charge(getRent(p));
                owner.charge(-getRent(p));
                System.out.println(p.getName() + " have paid " + owner.getName() + " $" + getRent(p));
            }
        }
    }

    @Override
    public int getRent(Player p) {
        Player libraryOwner=LIBRARY_UTILITY_CELL.owner;
        Player canteenOwner= CANTEEN_UTILITY_CELL.owner;
        if(libraryOwner!=null && libraryOwner.equals(canteenOwner)){
            return p.getLastMove()*100;
        }else{
            return p.getLastMove()*10;
        }
    }
    //TODO add some other methods if necessary

}
