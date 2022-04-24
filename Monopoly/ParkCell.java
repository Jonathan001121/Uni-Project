public class ParkCell extends FunctionCell {

    //TODO add some other methods if necessary
    public ParkCell(){
        super("Park");
    }

    @Override
    public void event(Player p, Cell[] cells) {
        System.out.println(p.getName()+" is in the park");
        p.setInPark(true);
    }
}

