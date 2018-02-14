/**
 * Created by Samin on 2/9/18.
 */
public class Use {

    // Globally accessible object attributes
    public String symbol; // The symbol attached to the use
    public int address; // The relative address in the useLine
    public int line; // The current useLine

    public Use(String symbol, int address, int line)
    {
        this.address=address;
        this.symbol=symbol;
        this.line=line;
    }

} // End of the Use  class
