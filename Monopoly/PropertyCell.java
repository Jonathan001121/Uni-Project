import java.util.Scanner;

public class PropertyCell extends Cell {
    protected int baseCost;
    protected Player owner = null;
    int house = 0;

    /**
     * Return the owner of the property
     * @return owner
     */
    public Player getOwner() { 
        return this.owner;
     }
     /**
      * Constructor
      */
    public PropertyCell(String name, int baseCost) {
        super(name);
        this.baseCost=baseCost;
    }
    /**
     * Return the cost of the property cell
     * @return the base cost of the property cell.
     */
    public int getCost() { 
        return this.baseCost;
    }
    /**
     * Return number of houses built on this property cell.
     * @return the number of houses.
     */
    public int getHouse() { 
        return house;
    }


    /**
     * Return a specific format of string. See the assignment webpage
     * for more details
     * @return the specific string of the property cell/
     */
    @Override
    public String toString() {
        return this.name+" owned by "+(owner==null?"-":owner.getName())+" : "+baseCost +(house>0?" House: "+house:"");
    }

    /**
     * Return the rent charged against this player. The formula for an ordinary cell is
     * baseCost * (1 + house * 0.5) rounding the nearest integer.
     *
     * @param p - The player who is charged. p is irrelevant in this case.
     * @return The rent.
     */
    public int getRent(Player p) {
        return (int) Math.round(baseCost*(1+house*0.5));
    }

    //TODO add some other methods if necessary
    public void event(Player p , Cell[] cells){
        System.out.println("You have landed on "+name+"!");
        Scanner in=new Scanner(System.in);
        if(owner==null && p.getMoney()>=baseCost){
            System.out.print("Do you want to buy this for $"+ baseCost+"? (y/n):");
            String input=in.next();
            if(input.equals("y")){
                this.owner=p;
                p.charge(baseCost);
                System.out.println("You have bought this land!");
            }

        }else if(owner==p && p.getMoney()>=baseCost/5){
            System.out.println("Do you want to build a house for this land for $"+baseCost/5+"? (y/n)");
            String input=in.next();
            if(input.equals("y")){
                p.charge(baseCost/5);
                System.out.println("You have bought this land!");
                this.house++;
            }
        }else if(owner!=p && owner!=null){
            if(owner.isInPark()) {
                System.out.println(owner.getName()+" is in the Park. Free parking.");
            }else{
                p.charge(getRent(p));
                owner.charge(-getRent(p));
                System.out.println(p.getName()+" have paid "+owner.getName()+" $"+getRent(p));
            }
        }
    }
}
