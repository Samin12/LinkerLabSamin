import java.util.ArrayList;

/**
 * Created by Samin on 2/9/18.
 */
public class Mod {
    public ArrayList<String> definitionList = new ArrayList<String>();
    public ArrayList<String> useList = new ArrayList<String>();
    public ArrayList<String> useListOP = new ArrayList<String>();
    public ArrayList<String> text = new ArrayList<String>();
    public ArrayList<String> usedSymbols = new ArrayList<String>();



    public ArrayList<String> programText = new ArrayList<String>();

    public ArrayList<Integer> textAddresses = new ArrayList<Integer>();

    public int moduleNumber;
    public int moduleLength;
    public int baseAddress;
    public int modNumber;

    public Mod(int moduleLength, ArrayList<String> text, ArrayList<String> useList, ArrayList<String> defList,int baseAddress,int modNumber){
        this.moduleLength = moduleLength;
        this.moduleNumber = moduleNumber;
        this.text = text;
        this.useList = useList;
        this.definitionList = defList;
        this.baseAddress=baseAddress;
        this.modNumber=modNumber;

        for (int i = 0; i < useList.size(); i++) {
            if (i>0){
                useListOP.add(useList.get(i));
            }
        }



    }






}
