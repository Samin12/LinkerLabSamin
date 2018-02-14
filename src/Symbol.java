

/**
 * Created by Samin on 2/9/18.
 */
public class Symbol
{
    // Globally accessible object attributes
    public String name;
    public int definition;
    public boolean isUsed;


    public Symbol(String name, int definition)
    {
        this.isUsed=false;
        this.name=name;
        this.definition=definition;

    } // End of the constructor



} // End of the symbol class
