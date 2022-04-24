import java.util.Scanner;

/**
 * If the owner owns only one train station, the rent is 500
 * If the owner owns two of them, the rent is 1000
 * If the owner owns three of them, the rent is 2000
 * If the owner owns four of them, the rent is 4000
 */
public class TrainStationPropertyCell extends PropertyCell {
    public static final int TRAIN_STATION_COST = 500;
    public static final TrainStationPropertyCell[] TRAIN_STATION_ARRAY = {
            new TrainStationPropertyCell("Kowloon"),
            new TrainStationPropertyCell("Mongkok"),
            new TrainStationPropertyCell("Central"),
            new TrainStationPropertyCell("Shatin")
    };
    public TrainStationPropertyCell(String name){
        super(name,TRAIN_STATION_COST);
    }
    public void event(Player p , Cell[] cells) {
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
                System.out.println(owner.getName() + " is in the Park. Free parking.");
            } else {
                p.charge(getRent(p));
                owner.charge(-getRent(p));
                System.out.println(p.getName() + " have paid " + owner.getName() + " $" + getRent(p));
            }
        }
    }
    public int getRent(Player p){
        int stationCnt=-1;
        int rent;
        for (int i = 0; i < TRAIN_STATION_ARRAY.length; i++) {
            if(TRAIN_STATION_ARRAY[i].owner==owner){
                stationCnt++;
            }
        }
        return rent= (int) (TRAIN_STATION_COST*Math.pow(2,stationCnt));


    }

    //TODO add some other methods if necessary
 
}
