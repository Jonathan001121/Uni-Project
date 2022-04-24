class GotoJailCell extends FunctionCell {
    //TODO add some other methods if necessary

    public GotoJailCell(){
        super("Go to Jail");
    }

    @Override
    public void event(Player p, Cell[] cells) {
        System.out.println(p.getName()+" go to Jail");
        p.putToJail();
    }
}
