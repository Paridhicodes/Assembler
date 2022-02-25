package Assembler;

import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class Main {
    static HashMap<String,String> hm;
    public static void main(String[] args) {
        //---------Creating HashMap for storing pneumonic and opcode pair----------

        hm=new HashMap<>();
        hm.put("CLA","0000");
        hm.put("LAC","0001");
        hm.put("SAC","0010");
        hm.put("ADD","0011");
        hm.put("SUB","0100");
        hm.put("BRZ","0101");
        hm.put("BRN","0110");
        hm.put("BRP","0111");
        hm.put("INP","1000");
        hm.put("DSP","1001");
        hm.put("MUL","1010");
        hm.put("DIV","1011");
        hm.put("STP","1100");

        System.out.println("------------------------");
        System.out.println("Pneumonic"+"\t\t"+"Opcode");
        System.out.println("------------------------");
        for(String key:hm.keySet()){
            System.out.println(key+"\t\t  |\t\t "+hm.get(key));
        }
        System.out.println("------------------------\n");

        //-----------Initializing the HashMaps-----------

        HashMap<String,Integer> labels=new HashMap<>();
        HashMap<String,Integer> operands=new HashMap<>();
        HashMap<String,String> pneumonic=new HashMap<>();

        HashSet<String> operandTracker=new HashSet<>();

        //----------------Reading a file-----------------
        String inputLine = null;
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("input_instructions.txt"));
            int loc_counter=0;
            while ((inputLine = reader.readLine()) != null) {
               String []arr=inputLine.split(" ");


               //--------Refer rules.txt for file rules---------

                int len=arr.length;
                if(len<1 || len>3){
                    System.out.println("Instruction format wrong!");
                    return;
                }
                if(len==1){
                    boolean res=operandCheck(arr[0]);
                    if(!res){
                        return;
                    }else{
                        if(operands.containsKey(arr[0])){
                            System.out.println("Each operand can be assigned only a single location!");
                            return;
                        }
                        operands.put(arr[0],loc_counter);
                        operandTracker.remove(arr[0]);
                    }
                }

                if(len==2){
                    String str1=arr[0];
                    String str2=arr[1];

                    boolean res1=labelCheck(str1);
                    if(!res1){
                        return;
                    }else{
                        labels.put(str1,loc_counter);
                    }

                    boolean res2=pneumonicCheck(str2);
                    if(!res2){
                        return;
                    }else{
                        pneumonic.put(str2,hm.get(str2));
                    }
                }

                if(len==3){
                    String str1=arr[0];
                    String str2=arr[1];
                    String str3=arr[2];

                    boolean res1=labelCheck(str1);
                    if(!res1){
                        return;
                    }else{
                        labels.put(str1,loc_counter);
                    }

                    boolean res2=pneumonicCheck(str2);
                    if(!res2){
                        return;
                    }else{
                        pneumonic.put(str2,hm.get(str2));
                    }

                    boolean res3=operandCheck(str3);
                    if(!res3){
                        return;
                    }else{
                        operandTracker.add(str3);
                    }

                }

                loc_counter+=12;
            }

            if(!operandTracker.isEmpty()){
                System.out.println("All operands were not assigned locations!");
                return;
            }

            //-----------------Printing the tables-------------------

            printTables(labels,operands,pneumonic);
        }
        catch (FileNotFoundException ex) {
            System.out.println("file was not found: " + ex);
        }
        catch (IOException ex) {
            System.out.println("io error: " + ex);
        }
        finally {
            try {
                if( reader != null ) reader.close();
            } catch (IOException ex) {
                System.out.println("error closing file " + ex);
            }
        }

    }



    //---------------Operand checker-------------
    public static boolean operandCheck(String op){
        for(int i=0;i<op.length();i++){
            char ch=op.charAt(i);
            if(!Character.isUpperCase(ch)){
                if(Character.isLowerCase(ch)){
                    System.out.println("The operand contains a lower case alphabet!");
                }else if(Character.isDigit(ch)){
                    System.out.println("The operand contains a digit!");
                }else if(Character.isWhitespace(ch)){
                    System.out.println("The operand contains a whitespace!");
                }else{
                    System.out.println("The operand contains a character which is not allowed!");
                }

                return false;
            }
        }

        return true;
    }

    //----------------Pneumonic Checker----------------

    public static boolean pneumonicCheck(String pc){
        if(!hm.containsKey(pc)){
            System.out.println("Not a pneumonic!");
            return false;
        }

        return true;
    }

    //----------------Label Checker----------------

    public static boolean labelCheck(String label){
        int size=label.length();
        if(size<2){
            System.out.println("Label format incorrect!");
            return false;
        }

        char ch1=label.charAt(0);
        if(ch1!='L'){
            System.out.println("Label format incorrect!");
            return false;
        }

        char ch2=label.charAt(1);
        if(ch2=='0'){
            System.out.println("The number following L must be greater than or equal to one!");
            return false;
        }

        for(int i=1;i<label.length();i++){
            char ch=label.charAt(i);
            if(!Character.isDigit(ch)){
                System.out.println("The label must contain digits followed by L. The label format has an error!");
                return false;
            }
        }
        return true;
    }

    //--------------------------------Print Tables-----------------------------------

    public static void printTables(HashMap<String,Integer> labels, HashMap<String,Integer> operands, HashMap<String,String> pneumonic){
        System.out.println("------------------------");
        System.out.println("Labels"+"\t\t"+"    Address");
        System.out.println("------------------------");
        for(String key:labels.keySet()){
            System.out.println(key+"\t\t  |\t\t "+labels.get(key));
        }
        System.out.println("------------------------\n");

        System.out.println("------------------------");
        System.out.println("Operands"+"\t\t"+"Address");
        System.out.println("------------------------");
        for(String key:operands.keySet()){
            System.out.println(key+"\t\t  |\t\t "+operands.get(key));
        }
        System.out.println("------------------------\n");

        System.out.println("------------------------");
        System.out.println("Pneumonic"+"\t\t"+"Opcode");
        System.out.println("------------------------");
        for(String key:pneumonic.keySet()){
            System.out.println(key+"\t\t  |\t\t "+pneumonic.get(key));
        }
        System.out.println("------------------------\n");
    }
}
