import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Samin on 2/9/18.
 */
public class Linker2 {

    public static void main(String[] args) throws Exception {

        File file = new File(args[0]);
        Scanner input = new Scanner(file);
        ArrayList<String> allText = new ArrayList<>();
        ArrayList<String> errorArray = new ArrayList<>();

        ArrayList<Mod> modArray = new ArrayList<>();
        int numberOfModules = input.nextInt();


        while (input.hasNext()){
            allText.add(input.next());
        }


        //definitions 0 1 or 2

        //make multipley defined error catcher
        int baseAddress =0;

        for (int i = 0; i < numberOfModules; i++) {

            int moduleLength=0;

            //int moduleNumber=i;
            ArrayList<String> text= new ArrayList<>();
            ArrayList<String> useList = new ArrayList<>();
            ArrayList<String> defList = new ArrayList<>();


            int numDefs = Integer.parseInt(allText.get(0));
           // allText.remove(0);
            //edit here
            if (numDefs>0){


                defList.add(allText.get(0));
                allText.remove(0);
                for (int j = 0; j < numDefs*2; j++) {
                    defList.add(allText.get(0));
                    allText.remove(0);

                }


             //   System.out.println(defList + "def");
            }else {
                defList.add("0");
                allText.remove(0);
            }

        //    System.out.println(defList);

          //  System.out.println(allText.get(0)+"-<");

            int numUses = Integer.parseInt(allText.get(0));

            useList.add(allText.get(0));
            allText.remove(0);
            for (int j = 0; j < numUses; j++) {
                useList.add(allText.get(0));
                allText.remove(0);
            }



       //     System.out.println(useList);


            int programTextNum =  Integer.parseInt(allText.get(0));

            moduleLength=programTextNum;

           // System.out.println(moduleLength);
            text.add(allText.get(0));
            allText.remove(0);
            for (int j = 0; j < programTextNum*2; j++) {

                if (allText.get(0).length()==4){
                    Integer chekSize = Integer.parseInt(allText.get(0).substring(1));
                  //  Integer chekSizeR = Integer.parseInt(allText.get(0).substring(1));
                    if (chekSize>200 && text.get(text.size()-1).equals("A")){
                        errorArray.add("Error: Absolute address exceeds machine size; zero used.");
                        text.add(allText.get(0).charAt(0)+"000");
                        allText.remove(0);
                        continue;
                    }else if (text.get(text.size()-1).equals("R") && chekSize>moduleLength){
                        errorArray.add("Error: Relative address exceeds module size; zero used.");
                        text.add(allText.get(0).charAt(0)+"000");
                        allText.remove(0);
                        continue;
                    }else if (text.get(text.size()-1).equals("E") && chekSize>programTextNum){
                        errorArray.add("Error: External address exceeds length of use list; treated as immediate.");
                        text.add(allText.get(0).charAt(0)+"000");
                     //   text.get(text.size()-1))
                        //text.set(text.size()-1,"A");
                        //System.out.println(text);
                        allText.remove(0);

                        continue;
                    }
                  //  System.out.println(chekSize+" this the ");
                }
                text.add(allText.get(0));
                //System.out.println(allText.get(0)+"pgrm txt");
                allText.remove(0);

            }

         //   System.out.println(text);

               // System.out.println(i);
            modArray.add(new Mod(moduleLength,text,useList,defList,baseAddress,i));


            //System.out.println(moduleLength+" mod len");
            baseAddress+=moduleLength;


        }



        HashMap<String,Integer> symbolTable = new HashMap<>();




        //symbol table resulution

        for (int i = 0; i < numberOfModules; i++) {

            if (modArray.get(i).definitionList.size()>2){
                int moduleAbsolute =modArray.get(i).baseAddress;
                int moduleRelative =Integer.parseInt(modArray.get(i).definitionList.get(2));
                //System.out.println(moduleAbsolute);
                if (!symbolTable.containsKey(modArray.get(i).definitionList.get(1))){
                    symbolTable.put(modArray.get(i).definitionList.get(1),moduleRelative+moduleAbsolute);
                }else {

                    errorArray.add("\"Error: This variable is multiply defined; first value used.");
                   // System.out.println("Error: This variable is multiply defined; first value used.");
                }
            }
        }


        System.out.println("Symbol Table ");
        System.out.println(symbolTable);


        //symbol uses
        ArrayList<String> usedSymbols = new ArrayList<>();


        for (int i = 0; i < modArray.size(); i++) {
            for (int j = 0; j < modArray.get(i).useListOP.size(); j++) {
                usedSymbols.add(modArray.get(i).useListOP.get(0));
            }
        }

    //    System.out.println(usedSymbols);




        for (String s:usedSymbols) {
            if (!symbolTable.containsKey(s)){
                symbolTable.put(s,0);
                errorArray.add(s+"is not defined; zero used.");
            }else {

            }
        }

        //check if symbol used or not
      //  System.out.println(usedSymbols+ "used symbols");

        ArrayList<String> symbolsNotUsed = new ArrayList<>();
//        for (int i = 0; i < usedSymbols.size(); i++) {
//            if (!symbolTable.containsKey(usedSymbols.get(i))){
//                symbolsNotUsed.add(usedSymbols.get(i));
//            }
//        }

        //working here

        for (String s:symbolTable.keySet()) {
            if (!usedSymbols.contains(s)){
                symbolsNotUsed.add(s);
                errorArray.add("Warning " +s+ " was defined in module but never used.");
            }
        }




        //I just some extra warnings added
        for (Mod m:modArray) {
            for (int i = 0; i < m.definitionList.size(); i++) {
                if (i%2!=0){
                    if (!m.useListOP.contains(m.definitionList.get(i))){
                   //     System.out.println("Warning: "+m.definitionList.get(i)+" was defined in module "+m.modNumber+" but never used.");
                        errorArray.add("Warning: "+m.definitionList.get(i)+" was defined in module "+m.modNumber+" but not used in the module");
                    }if (Integer.parseInt(m.definitionList.get(i+1))>m.moduleLength){
                        errorArray.add("Error: In module "+m.modNumber+" the def of "+m.definitionList.get(i)+" exceeds the module size; zero (relative) used.");

                    }
                }
            }

            //need symbos from deflist

        }


//        for (Mod m:modArray) {
//            int def = Integer.parseInt(m.definitionList.get(2));
//            if (def>m.moduleLength){
//                errorArray.add("Error: In module "+m.modNumber+" the def of "++" exceeds the module size; zero (relative) used.");
//            }
//        }



        //pass 1 starts from here
        HashMap<Integer,String> memoryIndex = new HashMap<>();
        HashMap<Integer,Integer> memoryMap = new HashMap<>();


        ArrayList<String> programText = new ArrayList<>();

        for (int i = 0; i < modArray.size(); i++) {
            for (int j = 0; j < modArray.get(i).text.size(); j++) {
                if (j>0){
                    programText.add(modArray.get(i).text.get(j));
                }

            }
        }





        for (int i = 0; i < programText.size(); i++) {
            if (i%2==0){
                //memoryIndex.put(programText.get(i),0);
            }else {
                memoryIndex.put(Integer.parseInt(programText.get(i)),programText.get(i-1));
            }
        }

       // System.out.println(memoryIndex);

        if (!errorArray.isEmpty()){
            System.out.println("Errors occured \n");
            System.out.println(errorArray);
        }



        for (int i = 0; i < modArray.size(); i++) {
            for (int j = 0; j < modArray.get(i).text.size(); j++) {
                if (j>0 && j%2==0){
                    System.out.print(modArray.get(i).text.get(j)+" : ");
                    if (memoryIndex.get(Integer.parseInt(modArray.get(i).text.get(j))).equals("R")){
                        System.out.println(modArray.get(i).baseAddress+Integer.parseInt(modArray.get(i).text.get(j))+"\n");
                    }else if (memoryIndex.get(Integer.parseInt(modArray.get(i).text.get(j))).equals("E")){
                        String cat = modArray.get(i).text.get(j).substring(1);
                        Integer removedOpCpde = Integer.parseInt(cat);


                        System.out.println(symbolTable.get(modArray.get(i).useListOP.get(removedOpCpde))+ Integer.parseInt((modArray.get(i).text.get(j)))-removedOpCpde+"\n");

                    } else {
                        System.out.println((modArray.get(i).text.get(j))+"\n");
                    }
                }
            }
        }

        System.out.println("Errors occured \n");
        System.out.println(errorArray);
    }
}
//3,6