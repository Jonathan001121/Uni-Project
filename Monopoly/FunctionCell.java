public class FunctionCell extends Cell {
    //TODO add some other methods if necessary
    public FunctionCell(String name){
        super(name);
    }

    @Override
    public void event(Player p, Cell[] cells) {
        System.out.println("You have arrived: "+name);
    }
}


