/*
150117007 – Edanur Öztürk
150117044 – Sueda Bilen
150119841 – Zehra Kuru
150517059 – Özge Saltan
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Assembler {

	public static void main(String[] args) throws Exception {

		
		File sourcefile  = new File("input.txt");
		
		if (!sourcefile.exists()) {
			System.out.println("Source file does not exist");
			System.exit(1);
			}
		
		// Create a Scanner for the file 
		Scanner input = new Scanner(sourcefile);
		
		try {  
			
			//Create file to write
            Writer w = new FileWriter("output.hex");  
            w.write("v2.0 raw\n");
       
		//read file line by line
		while(input.hasNext()) {
			String s = input.nextLine();
			
			//for ADDI
			if(s.contains("ADD") && s.charAt(3) == 'I') {
				
				String ADDI[] = s.split(" ");
				
				ADDI[0] = "0101";
				
				String registers[] = ADDI[1].split(",");
				
				String src1 = registerToBinary(registers[0]);
				String dst = registerToBinary(registers[1]);
				int imm6 = Integer.parseInt(registers[2]);
				
				String imm = signedToBinary(6,imm6);
				String binary = ADDI[0] + src1 + dst + imm;
				String hex = BinToHex(binary);

				w.write(hex+" ");
				
	
			}
			
			//for ADD 
			if(s.contains("ADD") && s.charAt(3) != 'I') {

				String ADD[] = s.split(" ");
				
				ADD[0] = "0011";
				
				String registers[] = ADD[1].split(",");
				
				String src1 = registerToBinary(registers[0]);
				String src2 = registerToBinary(registers[1]);
				String dst = registerToBinary(registers[2]);
				
				String binary = ADD[0] + src1 + src2 + dst + "000";
				String hex = BinToHex(binary);
				
				w.write(hex+" ");
				
			}
			
			//for LD
			if(s.contains("LD")) {

				String LD[] = s.split(" ");
				
				LD[0] = "1110";
				
				String registers[] = LD[1].split(",");
				
				String dst = registerToBinary(registers[0]);
				int address = Integer.parseInt(registers[1]);
				
				
				String addr = signedToBinary(9,address);
				
				String binary = LD[0] + dst + addr;
				String hex = BinToHex(binary);
				
				w.write(hex+" ");
			}
			
			//for ST
			if(s.contains("ST")) {
				
				String ST[] = s.split(" ");
				
				ST[0] = "1111";
				
				String registers[] = ST[1].split(",");
				
				String src = registerToBinary(registers[0]);
				int address = Integer.parseInt(registers[1]);
				
				
				String addr = signedToBinary(9,address);
				
				String binary = ST[0] + src + addr;
				String hex = BinToHex(binary);
				
				w.write(hex+" ");
			}
			
			//for ANDI
			if(s.charAt(3) == 'I' && s.contains("AND")) {

				String ANDI[] = s.split(" ");
				
				ANDI[0] = "0110";
				
				String registers[] = ANDI[1].split(",");
				
				String src1 = registerToBinary(registers[0]);
				String dst = registerToBinary(registers[1]);
				int imm6 = Integer.parseInt(registers[2]);
				signedToBinary(6,imm6);
				
				String imm = signedToBinary(6,imm6);
				String binary = ANDI[0] + src1 + dst + imm;	
				String hex = BinToHex(binary);
				
				w.write(hex+" ");
			}
			//for AND
			if(s.contains("AND") && s.charAt(3) != 'I') {

				String AND[] = s.split(" ");
				
				AND[0] = "0010";
					
				String registers[] = AND[1].split(",");
				
				String src1 = registerToBinary(registers[0]);
				String src2 = registerToBinary(registers[1]);
				String dst = registerToBinary(registers[2]);
				
				String binary = AND[0] + src1 + src2 + dst + "000";
				String hex = BinToHex(binary);
		
				w.write(hex+" ");
			}
			
			//for CMP
			if(s.contains("CMP")) {
				
				String CMP[] = s.split(" ");
				
				CMP[0] = "0111";
				
				String registers[] = CMP[1].split(",");
				
				String op1 = registerToBinary(registers[0]);
				String op2 = registerToBinary(registers[1]);
				
				String binary = CMP[0] + op1 + op2 + "000000";
				String hex = BinToHex(binary);
		
				w.write(hex+" ");
			}
			
			//for JUMP
			if(s.contains("JUMP")) {
				
				String JUMP[] = s.split(" ");
				
				JUMP[0] = "1000";
				
				String registers[] = JUMP[1].split(",");
				
				int address = Integer.parseInt(registers[0]);
				
				String addr = signedToBinary(12,address);
				
				String binary = JUMP[0] + addr;
				String hex = BinToHex(binary);
			
				w.write(hex+" ");
				
			}
			
			//for JA
			if(s.contains("JA") && s.charAt(2) != 'E') {
				
				String JA[] = s.split(" ");
				
				JA[0] = "1010";
				
				String registers[] = JA[1].split(",");
				
				int address = Integer.parseInt(registers[0]);
				
				String addr = signedToBinary(12,address);
				
				String binary = JA[0] + addr;
				String hex = BinToHex(binary);
				
				w.write(hex+" ");
			}
			
			//for JAE
			if(s.contains("JAE") && s.charAt(2) == 'E') {
				
				String JAE[] = s.split(" ");
				
				JAE[0] = "1101";
				
				String registers[] = JAE[1].split(",");
				
				int address = Integer.parseInt(registers[0]);
				
				String addr = signedToBinary(12,address);
				
				String binary = JAE[0] + addr;
				String hex = BinToHex(binary);
				
				w.write(hex+" ");
			}
			
			//for JB
			if(s.contains("JB") && s.charAt(2) != 'E') {
				
				String JB[] = s.split(" ");
				
				JB[0] = "1011";
				
				String registers[] = JB[1].split(",");
				
				int address = Integer.parseInt(registers[0]);
				
				String addr = signedToBinary(12,address);
				
				String binary = JB[0] + addr;
				String hex = BinToHex(binary);
		
				w.write(hex+" ");
			}
			
			//for JBE
			if(s.contains("JBE") && s.charAt(2) == 'E') {
				
				String JBE[] = s.split(" ");
				
				JBE[0] = "1100";
				
				String registers[] = JBE[1].split(",");
				
				int address = Integer.parseInt(registers[0]);
				
				String addr = signedToBinary(12,address);
				
				String binary = JBE[0] + addr;
				String hex = BinToHex(binary);
			
				w.write(hex+" ");
			}
			
			//for JE
			if(s.contains("JE")) {
				String JE[] = s.split(" ");
				
				JE[0] = "1001";
				
				String registers[] = JE[1].split(",");
				
				int address = Integer.parseInt(registers[0]);
				
				String addr = signedToBinary(12,address);
				
				String binary = JE[0] + addr;
				String hex = BinToHex(binary);
		
				w.write(hex+" ");
			}
			
		}
		//writer file is closed
		w.close();
		input.close();
        System.out.println("File is written.");  
		}catch (IOException e) {  
            e.printStackTrace();  
        }   
     
	}
	
	
	public static String registerToBinary(String reg) {
		 
		       switch(reg)
		        {
		            case "R0" : return "000";
		            case "R1" : return "001";
		            case "R2" : return "010";
		            case "R3" : return "011";
		            case "R4" : return "100";
		            case "R5" : return "101"; 
		            case "R6" : return "110";
		            case "R7" : return "111";
		        }
			return null;  
		    }
	
	public static String reverseBinary(String binary) {

		String reversed = "";
		for(int i = binary.length() - 1;i>=0;i--) {
			reversed = reversed + binary.charAt(i);
		}
		return reversed;
	}

	//this function returns signed integer to binary form
	public static String signedToBinary(int size,int pSigned) {

		String binary ="";
		String oneBinary = "";
		if(size==6) {
			 oneBinary ="000001";
		}
		else if(size==9) {
			 oneBinary ="000000001";
		}
		else if(size == 12) {
			 oneBinary ="000000000001";
		}
		int i=0;
		    //if the signed integer is positive
		    if(pSigned >= 0) {
		    	while(i<size) {
		    	if(pSigned % 2 == 0)
		    		binary += "0";
		    	else binary += "1";
		    	pSigned = pSigned / 2;
		    	i++;
		    }	   
		    	binary = reverseBinary(binary);
		    }
		    //if the signed integer is negative.
		    else {
		    	pSigned = pSigned * -1;
		    	while(i <size) {
		    	if(pSigned % 2 == 0)
		    		binary += "1";
		    	else binary += "0";
		    	pSigned = pSigned / 2;
		    	i++;
		    	}
		    	binary = reverseBinary(binary);
		    	binary = addBinary(binary,oneBinary);
		    }
		return binary;
	}

	//method to add two binary String
	static String addBinary(String x,String y) {
		String result = "";
		int sum = 0;
		int i = x.length() -1;
		int j = y.length() -1;
		while(i>=0 || j >= 0 || sum == 1) {
			sum += ((i >= 0) ? x.charAt(i) - '0': 0);
			sum += ((j >= 0) ? y.charAt(i) - '0': 0);
			
			result = (char)(sum % 2 + '0') + result;
			
			sum /= 2;
			
			i--;
			j--;
		}
		
		return result;
	}
	
	public static String BinToHex(String binary) {
		 
		 //In this method, binary is converted to hexadecimal
		 
		 binary.trim();
			while (binary.length() % 4 != 0) binary = "0" + binary;
			
           //takes four digits to convert to hexadecimal value
		    StringBuilder number = new StringBuilder();
		    for (int i = 0; i < binary.length(); i += 4) {
		       String num = binary.substring(i, i + 4);

		       switch(num)
		        {
		            case "0000" : number.append("0"); break;
		            case "0001" : number.append("1"); break;
		            case "0010" : number.append("2"); break;
		            case "0011" : number.append("3"); break;
		            case "0100" : number.append("4"); break;
		            case "0101" : number.append("5"); break;
		            case "0110" : number.append("6"); break;
		            case "0111" : number.append("7"); break;
		            case "1000" : number.append("8"); break;
		            case "1001" : number.append("9"); break;
		            case "1010" : number.append("a"); break;
		            case "1011" : number.append("b"); break;
		            case "1100" : number.append("c"); break;
		            case "1101" : number.append("d"); break;
		            case "1110" : number.append("e"); break;
		            case "1111" : number.append("f"); break;
		        }             
		        
		    }
		    String hexnumber = number.toString();
		    return hexnumber;
	 }
	
	}

