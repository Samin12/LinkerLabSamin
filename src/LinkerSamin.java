
import java.io.File;
import java.util.*;

public class LinkerSamin {

    public static void main(String[] args) throws Exception {

        File file = new File(args[0]);
        //System.out.println(file.exists());
        ArrayList<String> finalArray = new ArrayList<String>();
        Scanner input = new Scanner(file);
        int relocationConstant = 0;
        ArrayList<String> errorsNotDefined = new ArrayList<String>();
        StringBuilder warnings = new StringBuilder();
        StringBuilder outside = new StringBuilder();
        StringBuilder multiple = new StringBuilder();
        HashMap<String, Integer> myHashMapDefinitions = new HashMap<>();
        HashMap<String, Integer> myHashMapModule = new HashMap<String, Integer>();
        HashMap<String, String> myHashMapUses = new HashMap<>();
        HashMap<String, Integer> myHashMapProgramTexts = new HashMap<String, Integer>();
        ArrayList <String> programText = new ArrayList<String>();
        System.out.print("Symbol Table\n");


        //Scanner input = new Scanner(file);
        ArrayList<String> allText = new ArrayList<>();


        int numberOfModules = input.nextInt();


        while (input.hasNext()){
            allText.add(input.next());
        }
       // System.out.println(allText);

        allText.removeAll(Collections.singleton("A"));
        allText.removeAll(Collections.singleton("I"));
        allText.removeAll(Collections.singleton("R"));
        allText.removeAll(Collections.singleton("E"));


        for (int i = 0; i < numberOfModules; i++) {

            int numDefs = Integer.parseInt(allText.get(0));
            allText.remove(0);
            for (int j = 0; j < numDefs; j++) {
                String symbol = allText.get(0); allText.remove(0);
                //System.out.println(symbol);

                Integer location = Integer.parseInt(allText.get(0)); allText.remove(0);
                //System.out.println(location+"loc");

                location+=relocationConstant;
                if (!myHashMapDefinitions.containsKey(symbol)){
                    myHashMapDefinitions.put(symbol,location);
                    myHashMapModule.put(symbol,i);//i = module number
                }else {
                    multiple.append("err");
                }
                //System.out.println(myHashMapDefinitions+"adfasdfasfsdf");
            }


            Integer numberUses = Integer.parseInt(allText.get(0));
            allText.remove(0);

            for (int j = 0; j <numberUses ; j++) {

                String symbolUse =allText.get(0);
                allText.remove(0);
                //prob here

                Character locationUse = allText.get(0).toCharArray()[0];
                if (Character.isDigit(locationUse) && Character.getNumericValue(locationUse)>2){

                }else {
                    allText.remove(0);

                }
                myHashMapUses.put(symbolUse, locationUse.toString());
            }


            //System.out.println(allText);
            Integer numberProgramText = Integer.parseInt(allText.get(0));
            allText.remove(0);


           // System.out.println(numberProgramText);
            for(String key: myHashMapDefinitions.keySet()) {
                if(myHashMapDefinitions.get(key)>(numberProgramText-1)+relocationConstant) {
                    myHashMapDefinitions.put(key, relocationConstant);
                    outside.append(" Error: The definition of " + key + " is outside module 1; zero (relative) used");
                }
            }


            System.out.println(allText);
            relocationConstant+= numberProgramText;
            for(int l=0; l<numberProgramText; l++) {
                //System.out.println(allText.get(0));
               // System.out.println(myHashMapDef
                // initions);
                //System.out.println(allText.get(0));
                System.out.println(allText +"dasf");
                Integer text = Integer.parseInt(allText.get(0));
                allText.remove(0);
                programText.add(text.toString());
            }



            //System.out.println(programText);
        }




    }




}
