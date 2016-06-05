/* Implementation of Link State Routing Protocol - Dijkstra's Algorithm */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFileChooser;

/* Dijkstra.java */
public class Dijkstra {

	static int Max_rooters;

	static int DistGrph[][] = null;                    //Initialization and Declarations of variables
	static int DistRow[] = null;
	static int source = -1,destination = -1;
	class ConTableEntry {
		boolean flg; 
		int length;
		int []ids;
		int depth;
	}

	static ConTableEntry []conTable = null;            //Connection Table initialization

	/* Main Method */	
	public static void main(String[] args) {

		Dijkstra psFrame = new Dijkstra();

	}

	public int nodeid(int in){
		return (in+1);
	}
	/* ReadTextfileToBuildGraph Method  */
	public void ReadTextfileToBuildGraph() {
		try
		{

			System.out.println("Enter Text File name to read Network Topology Matrix:"); 
			Scanner in1 = new Scanner(System.in);              //Takes from the user the input file name 
			String fiename = in1.nextLine();

			FileReader fr = new FileReader(fiename);           //Creation of FileReader object 
			String val=new String();
			BufferedReader br = new BufferedReader(fr);        //Creation of BufferedReader Object	
			String[] temp;
			val=br.readLine();

			temp = val.split(" ");                            //to find size of the matrices or number of routers
			DistGrph = new int [temp.length][temp.length];
			br.close();
			fr.close();
			System.out.println("<=======:Graph Size Read:=======>"+temp.length);

			fr = new FileReader(fiename);                    //Read the content of file into the FileReader object    
			val=new String();
			br = new BufferedReader(fr);	
			int i = 0;			
			while((val=br.readLine())!=null)
			{
				String[] temp1;
				temp1 = val.split(" ");                  //Splitting with space as a Delimiter
				for (int dist =0; dist < temp1.length; dist++ ){
					DistGrph[i][dist] =  Integer.parseInt(temp1[dist]);
				}
				i++;
			}
			br.close();
			fr.close();			
			System.out.println("<=======:Graph Table Initialized:=======>");
			Max_rooters = i;


			String imageName = "%3d" ;                      //Formatting to display the Content in the Matrix format
			System.out.println();
			System.out.print("ID|");
			for (int j =0; j < Max_rooters; j++ ){
				System.out.print(String.format( imageName,nodeid(j)));
			}
			System.out.println();
			System.out.println("-------------------------------------------------------------------");
			for (int j =0; j < Max_rooters; j++ ){
				imageName = "%2d|" ;                   //Formatting to display the Content in the Matrix format
				System.out.print(String.format( imageName,nodeid(j)));
				imageName = "%3d" ;
				for (int k =0; k < Max_rooters; k++ )
					System.out.print( String.format( imageName, DistGrph[j][k]));
				System.out.println();
			}
			System.out.println("-------------------------------------------------------------------");
		} catch(Exception e){
			System.out.println("File Not Found, Please Enter a Valid File !");    //To Handle File Not Found Exception
		}
	}
	/* ComputeConnectionTable Method*/	
	public void ComputeConnectionTabel(){

		if (DistGrph[source][source] == 0) {
			conTable = new ConTableEntry[Max_rooters];    //Creates a ConTable Object with the Number of Routers

			for (int j = 0;j<Max_rooters;j++) {	
				ConTableEntry ce = new ConTableEntry();
				ce.flg = true;
				ce.length = -1;                      //Initializing the variables to start processing            
				ce.ids = new int[Max_rooters];
				ce.ids[0] = source ;
				ce.depth = 1;
				for (int i = 1;i<Max_rooters;i++) ce.ids[i] = -1;
				conTable[j] = ce;
			}

			//initializing source in working row 
			int tmpsorce = source;
			conTable[tmpsorce].length = 0;
			conTable[tmpsorce].ids[0]=source;
			conTable[tmpsorce].flg = false;
			//int nodedepth = 1;

			for (int loopcnt = 0 ; loopcnt<Max_rooters; loopcnt++) {

				for (int k = 0 ;  k< Max_rooters ; k++)
				{  
					if (conTable[k].flg)
					{
						if (DistGrph[tmpsorce][k]!= -1){

							if ((conTable[k].length != -1) ) {
								// smaller ( selected node length+ tableentry,previous entry path) 
								if (conTable[k].length > conTable[tmpsorce].length + DistGrph[tmpsorce][k]) {
									conTable[k].length = conTable[tmpsorce].length + DistGrph[tmpsorce][k];
									for (int idx = 0; idx< conTable[tmpsorce].depth ;idx ++)
										conTable[k].ids[idx] = conTable[tmpsorce].ids[idx];
									conTable[k].depth = conTable[tmpsorce].depth ;								
									conTable[k].ids[conTable[k].depth] = k;
									conTable[k].depth++;
								}
							}
							else 
							{  //selected node length is added to length table entry for new length
								conTable[k].length = conTable[tmpsorce].length + DistGrph[tmpsorce][k];

								for (int idx = 0; idx< conTable[tmpsorce].depth ;idx ++){
									conTable[k].ids[idx] = conTable[tmpsorce].ids[idx];
								}


								conTable[k].depth = conTable[tmpsorce].depth ;	
								conTable[k].ids[conTable[k].depth] = k;
								conTable[k].depth++;
							}
						}

					}
				}

				for (int i = 0; i<Max_rooters; i++){
					//System.out.print(" "+conTable[i].length);
				}
				System.out.println();
				//initalize smallest dist
				int small = 0;
				int indx_small = 0;

				for (int i = 0; i<Max_rooters; i++){

					if (conTable[i].flg){
						if(conTable[i].length !=-1 ){
							small = conTable[i].length;
							indx_small = i;
							break;
						}
					}				
				}
				//find source for next iteration
				for (int i = 0; i<Max_rooters; i++){
					if (conTable[i].flg){
						if(conTable[i].length != -1 ){
							if (small > conTable[i].length){						
								small = conTable[i].length;
								indx_small = i;
							}
						}
					}			
				}
				tmpsorce = indx_small;
				conTable[tmpsorce].flg = false;

			}	

			System.out.println("Router [" + nodeid(source) + "] "+ "Connection Table:");
			System.out.println("============================");
			System.out.println("Destination        Interface");	         //Printing the Connection Table
			for (int i = 0; i<Max_rooters; i++){
				String tmp = String.valueOf(conTable[i].ids[1]+1);
				if (conTable[i].ids[1] == -1) tmp = "-1";                //Check the Router ID if it is -1
				if (i == source) tmp = "-";                              //Source to Source Router will be "-"
				System.out.print("      "+  nodeid(i) + "                "+ tmp);
				System.out.println();
			}
		}
		else {
			System.out.println("Router [" + nodeid(source) + "] "+ "Connection Table:");
			System.out.println("============================");
			System.out.println("Destination        Interface");              //If there is no Interface to the router then assign -1 
			for (int i = 0; i<Max_rooters; i++){
				System.out.print("      "+  nodeid(i) + "                -1");
				System.out.println();
			}

		}
	}
	/* PrintConnectionTable Method */	
	public void PrintConnectionTabel() {	
		System.out.println("Enter Source Rooter Id< 1 - "+ (Max_rooters)+" >:"); 
		Scanner in1 = new Scanner(System.in);       //Takes input from the User for the Source Router ID                      
		String str_source = in1.nextLine();
		source = Integer.parseInt(str_source);
		source--;                                   //Decrements the Source ID by 1
		ComputeConnectionTabel();                   //Invoke ComputeConnectionTable Method
	}
	/* PrintShortPathToDestination Method */
	public void PrintShortPathToDestination() {

		System.out.println("Enter Destination Rooter Id< 1 - "+ (Max_rooters)+" >:"); 
		Scanner in1 = new Scanner(System.in);       //Takes from the user the Destination Router ID as input 
		String str_dest = in1.nextLine();
		destination = Integer.parseInt(str_dest);
		destination--;                              //Decrements Destination Router ID
		if (DistGrph[source][source] == 0) {
			if (DistGrph[destination][destination] == 0) {

				System.out.print("Shortest Path from Rooter:["+nodeid(source) +"] to ["+ nodeid(destination) + "] is: ");

				if (conTable[destination].length > 0) {
					for (int n = 0;n< conTable[destination].depth; n++ ) {
						if (-1 != conTable[destination].ids[n]) System.out.print(" "+ nodeid(conTable[destination].ids[n]));
					}
					System.out.println();
					System.out.println("The total cost is "+ conTable[destination].length);
				}  else System.out.println("Path Not Available");
			}  else System.out.println("Destination Rooter is Down");    //If Destination Router is down
		} else System.out.println("Source Rooter is Down");                  //If Source Rooter is down

	}
	/* ChangeNetworkTopology */
	public void ChangeNetworkTopology(){

		System.out.println("Enter Rooter Id< 1 - "+ (Max_rooters)+" > to Down:");                       
		Scanner in1 = new Scanner(System.in);          //Takes from the user the Router ID to Down as input 
		String str_delt = in1.nextLine();
		int delid = Integer.parseInt(str_delt);
		delid--;

		for (int j =0; j < Max_rooters; j++ ){
			DistGrph[j][delid] = -1 ;             //Assigns -1 to the Down Router row
		}

		for (int l =0; l < Max_rooters; l++ ){
			DistGrph[delid][l] = -1 ;            //Assigns -1 to the Down Router column
		}
		System.out.println("Modified Topology:");

		//insert
		String imageName = "%3d" ;                  //Formatting the content in Matrix format
		System.out.println();
		System.out.print("ID|");
		for (int j =0; j < Max_rooters; j++ ){
			System.out.print(String.format( imageName,nodeid(j)));
		}
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		for (int j =0; j < Max_rooters; j++ ){
			imageName = "%2d|" ;               //Formatting the content in Matrix format
			System.out.print(String.format( imageName,nodeid(j)));
			imageName = "%3d" ;
			for (int k =0; k < Max_rooters; k++ )
				System.out.print( String.format( imageName, DistGrph[j][k]));
			System.out.println();
		}
		System.out.println("-------------------------------------------------------------------");

	}
	/* MENU */	
	public Dijkstra() {

		while (true){
			System.out.println("===========================================================\n");
			System.out.println("Dijkstra's Algorithm - Link State Routing Simulator:");
			System.out.println("===========================================================\n");
			System.out.println("Enter The Option :\n==================\n1. Create a Network Topology\n \n2. Build a Connection Table \n \n3. Shortest Path to Destination Router \n \n4. Modify a topology \n \n5. Exit\n");	
			System.out.println("Command:");
			Scanner in = new Scanner(System.in);
			String regmessage = in.nextLine();

			if (regmessage.equals("1")){				
				ReadTextfileToBuildGraph();             //ReadTextFiletoBuildGraph method call                       
				for (int n = 0;n<Max_rooters;n++ ) {    //EXTRA FEATURE IMPLEMENTATION -> TO DISPLAY CONNECTION TABLE FOR ALL NODES
					source = n;
					ComputeConnectionTabel();       //ComputeConnectionTable method call
					System.out.println();
				}
			}				
			if (regmessage.equals("2")){
				PrintConnectionTabel();                 //PrintConnectionTable method call     
			}		
			if (regmessage.equals("3")){
				PrintShortPathToDestination();          //PrintShortPathToDestination method call        
			}		
			if (regmessage.equals("4")){
				ChangeNetworkTopology();                //ChangeNetworkTopology method call
				if ((source >-1) && (source < Max_rooters)){
					ComputeConnectionTabel();           //ComputeConnectionTable method call
					if (DistGrph[source][source] == 0) {
						if ((destination >-1) && (destination < Max_rooters)){
							if (DistGrph[destination][destination] == 0) {
								System.out.print("Shortest Path from Rooter:["+nodeid(source) +"] to ["+ nodeid(destination) + "] is: ");
								if (conTable[destination].length > -1) {
									for (int n = 0;n<Max_rooters;n++ ) {
										if (-1 != conTable[destination].ids[n]) System.out.print(" "+ nodeid(conTable[destination].ids[n]));
									}
									System.out.println();
									System.out.println("The total cost is "+ conTable[destination].length);
								}
								else System.out.println("Not Available");

							} else System.out.println("Destination Rooter is Down");
						} else System.out.println("Destination node is not selected");
					} else System.out.println("Source Rooter is Down");           //Router Check conditions

				}else System.out.println("Source node is not selected");
			}
			if (regmessage.equals("5")){				
				System.out.println("Exit LinkStateRouting project. Good Bye!.");
				System.exit(0);   	//Exit system call	
			}	
		}
	}
}
