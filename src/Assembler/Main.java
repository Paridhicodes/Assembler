package Assembler;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        //---------Creating HashMap for storing pneumonic and opcode pair----------

        HashMap<String,String> hm=new HashMap<>();
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


        //-----------Initializing the HashMaps-----------

        HashMap<String,Integer> labels=new HashMap<>();
        HashMap<String,Integer> operands=new HashMap<>();
        HashMap<String,Integer> opcodes=new HashMap<>();

        //----------------Reading a file-----------------
        String inputLine = null;
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("input_instructions.txt"));
            while ((inputLine = reader.readLine()) != null) {
                String outputLine;
//                if(inputLine.equals(patternToMatch)){
//                    System.out.println(inputLine);
//                    for(int i=0;i<=1;i++){
//                        outputLine=reader.readLine();
//                        System.out.println(outputLine);
//                    }
//                    break;
//
//                }
            }
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
}
