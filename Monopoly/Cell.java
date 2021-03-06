/**
 * This is the base class of Cell. This class is given. You are not supposed to modify this class
 * You are not allowed to modify this class.
 *
 */
public abstract class Cell {
    protected String name;

    /**
     * Return the name of this class. This method is final so all its sub-class should not override this method.
     * @return name of the class
     */
    public final String getName() {
        return name;
    }

    /**
     * Constructor of the class
     * @param name the name of the cell
     */
    public Cell(String name) {
        this.name = name;
    }

    /**
     * Return the string that to be displayed in the gameboard. Depends on different situation, this should be different.
     *
     * @return the string that to be displayed in the gamebaord.
     */
    public String toString() {
        return name;
    }
    /**
     * A event trigger when a player is stepped on it.
     * @param p The player who trigger the event.
     * @param cells All cells on the game board.
     */
    public abstract void event(Player p , Cell[] cells);
}
