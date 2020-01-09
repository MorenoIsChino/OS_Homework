package ex_02_processScheduling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Run {
	
	/**
	 * @author 梁博泓
	 * 
	 */

	public static void main(String[] args) throws IOException {

   	 	Scheduling Scheduling_01=new Scheduling(readDataFromTxt());
   	 			   Scheduling_01.FCFS();
   	 	System.out.println("----------------- E N D -----------------");
   	 	Scheduling Scheduling_02=new Scheduling(readDataFromTxt());
	 			   Scheduling_02.RR_quantum_5();
	 	System.out.println("----------------- E N D -----------------");

	    Scheduling Scheduling_03=new Scheduling(readDataFromTxt());
	   	 		   Scheduling_03.SJF_NonPreemptive();
	   	System.out.println("----------------- E N D -----------------");
	   	 		   
	   	Scheduling Scheduling_04=new Scheduling(readDataFromTxt());
		 		   Scheduling_04.SRTF_Preemptive();
		System.out.println("----------------- E N D -----------------");	 		   
		Scheduling Scheduling_05=new Scheduling(readDataFromTxt());
		   		   Scheduling_05.PriorityScheduling_NonPreemptive();
		System.out.println("----------------- E N D -----------------");

	}
	
	
	// 从文件读取进程信息
	public static String[] readDataFromTxt() throws IOException {
		
		BufferedReader readTxt=new BufferedReader(new FileReader(new File("input.txt")));

	     String textLine="";

	     String str="";

	     while(( textLine=readTxt.readLine())!=null){

	               str+=" "+ textLine;

	      }
	     String[] numbersArray=str.split(" ");
  	 
    	 readTxt.close();
	     //numbersArray 下保存的即为各个数字
    	 
    	 return numbersArray;
	}
}
