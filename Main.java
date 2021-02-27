import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
public class Main {

	
	static int depth;
	
	public static void main (String [] args) {

		System.out.print("Game begins\n");
		
		
		
		Scanner sc= new Scanner(System.in);
		System.out.println("Choose your game: \n 1.4X4 Reversi \n 2.8X8 Reversi \n Your choice?(enter 1 or 2)");
		int gameChoice=sc.nextInt();
		System.out.println("Choose your role: \n1.X\n2.O\n Your choice? (enter 1 or 2)");
		int roleChoice =sc.nextInt();
		
		System.out.print("Choose your opponent: \n1.Human vs Human\n2.An agent that plays randomly\n3.An agent that uses MINIMAX\n4.An agent that uses MINIMAX with alpha"
				+ "-beta pruning\n5.An agent uses H-MINIMAX with fixed depth cutoff and a-b pruning\n Your Choice?");
		int opponent=sc.nextInt();
		
		//System.out.println(gameChoice +"," +roleChoice+","+opponent);
		
		State st;
		if(gameChoice==1) {
			
			if(roleChoice==1) {
				st=new State(4,-1);
			}
			else {
				 st=new State(4,1);
			}
			
		}else {
			if(roleChoice==1) {
				st=new State(8,-1);
			}
			else {
				st=new State(8,1);
				
			}
		}
		int human=st.turn();
		
		Scanner sc2= new Scanner(System.in);
		if(opponent==1) {
			
			while(true) {
				if(isTerminal(st)) {
					if(!isTerminal2(st)) {
						System.out.println("No legal move left, changing turn!");
						emptyMove(st);
						continue;
					}
					printState(st);
					int numB=blackCounter(st);
					int numW=whiteCounter(st);
					if(numB>numW) {
						System.out.println("X wins!");
						break;
					} 
					else if (numW>numB) {
						System.out.println("O wins!");
						break;
						
					}
					else {
						System.out.println("Draw!");
						break;
					}
				}
				printState(st);
				System.out.print("Next move: ");
				if(st.turn()==-1) System.out.println("X");
				else System.out.println("O");
				
				System.out.println("\n\nYour move? (for example a1):");
				String move=sc2.next();
				
				int [] action= conversion(move);
				if(legalMove(st,action[0],action[1])) {
					st=result(st,action);
					
				}
				else {
					System.out.println("Illegal move, try agian!\n");
					continue;
				}
			}
		}
		else if(opponent==2) {//random move
			while (true) {
				
				
				if(isTerminal(st)) {
					if(!isTerminal2(st)) {
						System.out.println("No legal move left, changing turn!");
						emptyMove(st);
						continue;
					}
					printState(st);
					int numB=blackCounter(st);
					int numW=whiteCounter(st);
					if(numB>numW) {
						System.out.println("X wins!");
						break;
					} 
					else if (numW>numB) {
						System.out.println("O wins!");
						break;
						
					}
					else {
						System.out.println("Draw!");
						break;
					}
				}
				
				printState(st);
				System.out.print("Next move: ");
				if(st.turn()==-1) System.out.println("X");
				else System.out.println("O");
				
				if(human==st.turn()) {

					System.out.println("\n\nYour move? (for example a1 or enter \"h\" for help):");
					String move=sc2.next();
					if(move.equals("h")) { 
						
						helpPrinter(st);
						System.out.println("Your move?");
						move =sc2.next();
					}
					
					int [] action= conversion(move);
					if(legalMove(st,action[0],action[1])) {
						st=result(st,action);
						
					}
					else {
						System.out.println("Illegal move, try agian!\n");
						continue;
					}
				
				}
				else {
					ArrayList<int[]> actions =new ArrayList<>();
					actions= allActions(st);
					Random rand=new Random();
					int r=rand.nextInt(actions.size());
					st=result(st,actions.get(r));
					//System.out.println(actions.get(r)[1]);
					
					char col=(char) (actions.get(r)[1]+48);
					int temp= col+49;
					
					//System.out.println("temp; " +temp);
					col=(char) temp;
					
					int row=actions.get(r)[0]+1;
					System.out.println("Agent moved at " +col+""+row);
				}
				
				
			}
		}
		else if(opponent==3) {
			int agent;
			if(st.turn()==1) agent=-1;
			else agent=1;
			
			while (true) {
				
				
				if(isTerminal(st)) {
					if(!isTerminal2(st)) {
						System.out.println("No legal move left, changing turn!");
						emptyMove(st);
						continue;
					}
					printState(st);
					int numB=blackCounter(st);
					int numW=whiteCounter(st);
					if(numB>numW) {
						System.out.println("X wins!");
						break;
					} 
					else if (numW>numB) {
						System.out.println("O wins!");
						break;
						
					}
					else {
						System.out.println("Draw!");
						break;
					}
				}
				
				printState(st);
				System.out.print("Next move: ");
				if(st.turn()==-1) System.out.println("X");
				else System.out.println("O");
				
				if(human==st.turn()) {

					System.out.println("\n\nYour move? (for example a1 or enter \"h\" for help):");
					String move=sc2.next();
					if(move.equals("h")) { 
						
						helpPrinter(st);
						System.out.println("Your move?");
						move =sc2.next();
					}
					
					int [] action= conversion(move);
					if(legalMove(st,action[0],action[1])) {
						st=result(st,action);
						
					}
					else {
						System.out.println("Illegal move, try agian!\n");
						continue;
					}
				
				}
				else {
					double start=System.currentTimeMillis();
					int [] move;
					if(agent==1) {
						System.out.println("agent=1");
						move =miniMax(st,1);
						st=result(st,move);
						
					}
					else {
						System.out.println("agent=1");
						move =miniMax(st,-1);
						st= result(st,move);
					}
					double end=System.currentTimeMillis();
					//printState(st);
					char col=(char) (move[1]+48);
					int temp=col+49;
					col=(char) temp;
					int row=move[0]+1;
					System.out.println("The agent moved at "+col+""+row+", and it used "+ (end-start)/1000+"s");
					
				}
			}
			
		}
		else if (opponent==4) {
			
			int agent;
			if(st.turn()==1) agent=-1;
			else agent=1;
			
			while (true) {
				
				
				if(isTerminal(st)) {
					if(!isTerminal2(st)) {
						System.out.println("No legal move left, changing turn!");
						emptyMove(st);
						continue;
					}
					printState(st);
					int numB=blackCounter(st);
					int numW=whiteCounter(st);
					if(numB>numW) {
						System.out.println("X wins!");
						break;
					} 
					else if (numW>numB) {
						System.out.println("O wins!");
						break;
						
					}
					else {
						System.out.println("Draw!");
						break;
					}
				}
				
				printState(st);
				System.out.print("Next move: ");
				if(st.turn()==-1) System.out.println("X");
				else System.out.println("O");
				
				if(human==st.turn()) {

					System.out.println("\n\nYour move? (for example a1 or enter \"h\" for help):");
					String move=sc2.next();
					if(move.equals("h")) { 
						
						helpPrinter(st);
						System.out.println("Your move?");
						move =sc2.next();
					}
					
					int [] action= conversion(move);
					if(legalMove(st,action[0],action[1])) {
						st=result(st,action);
						
					}
					else {
						System.out.println("Illegal move ");
						continue;
					}
				}
				else {
					double start=System.currentTimeMillis();
					int [] move;
					if(agent==1) {
						move=abSearch(st,1);
						st=result(st,move);
					}
					else {
						move=abSearch(st,-1);
						st=result(st,move);
					}
					double end =System.currentTimeMillis();
					char col=(char) (move[1]+48);
					int temp=col+49;
					col=(char) temp;
					int row=move[0]+1;
					System.out.println("The agent moved at "+col+""+row+", and it used "+ (end-start)/1000+"s");
				}
			}
			
		}
		else if (opponent==5) {
			Scanner sc3= new Scanner (System.in);
			System.out.println("Choose depth of search(1,2,3,4....)");
			depth =sc3.nextInt();
			int agent;
			if(st.turn()==1) agent=-1;
			else agent=1;
			
			while (true) {
				
				
				if(isTerminal(st)) {
					if(!isTerminal2(st)) {
						System.out.println("No legal move left, changing turn!");
						emptyMove(st);
						continue;
					}
					printState(st);
					int numB=blackCounter(st);
					int numW=whiteCounter(st);
					if(numB>numW) {
						System.out.println("X wins!");
						break;
					} 
					else if (numW>numB) {
						System.out.println("O wins!");
						break;
						
					}
					else {
						System.out.println("Draw!");
						break;
					}
				}
				
				printState(st);
				System.out.print("Next move: ");
				if(st.turn()==-1) System.out.println("X");
				else System.out.println("O");
				
				if(human==st.turn()) {

					System.out.println("\n\nYour move? (for example a1 or enter \"h\" for help):");
					String move=sc2.next();
					if(move.equals("h")) { 
						
						helpPrinter(st);
						System.out.println("Your move?");
						move =sc2.next();
					}
					int [] action= conversion(move);
					if(legalMove(st,action[0],action[1])) {
						st=result(st,action);
						
					}
					else {
						System.out.println("Illegal move ");
						continue;
					}
				}
				else {
					double start =System.currentTimeMillis();
					int [ ] move ;
					
					if(agent==1) {
						System.out.println("agent is 1");
						move=H_MINIMAX(st,1);
						st=result(st,move);
					}
					else {
						System.out.println("agent is -1");
						move=H_MINIMAX(st,-1);
						st=result(st,move);
					}
					double end =System.currentTimeMillis();
					char col=(char) (move[1]+48);
					int temp=col+49;
					col=(char) temp;
					int row=move[0]+1;
					System.out.println("The agent moved at "+col+""+row+", and it used "+ (end-start)/1000+"s");
				}
			}
			
			
		}
	}
	
	static void helpPrinter(State s) {
		ArrayList<int []> actions =new ArrayList<>();
		
		actions=allActions(s);
		int size =actions.size();
		for (int i=0;i<size;i++) {
			int [] action= actions.get(i);
			int row =action[0];
			int col=action[1];
			char c =(char)( col+97);
			System.out.print(c+""+(row+1)+" ");
			
			
			
		}
		System.out.println();
		return;
		
	}
	static int utility(State s, int MAX) { //MAX is 1 or -1. 1 for white -1 for black
		
		
		int w= whiteCounter(s);
		int b=blackCounter(s);
		if(isTerminal(s)) {
			if(MAX==-1) {
				if(b>w) return 1;
				else if(b==w) return 0;
				else return -1;
			}
			else {
				if(w>b) return 1;
				else if(b==w) return 0;
				else return -1;
			}
		}
		
		return 0;
	}
	
	
	static int [] miniMax(State s,int MAX) {
		int maxIndex=-1;
		ArrayList<int[]> actions=new ArrayList<>();
		actions =allActions(s);
		int numAct= actions.size();
		int v=-10;
		//System.out.println("AVAILABLE ACTION:" +  numAct);
		for(int i=0;i<numAct;i++) {
			//System.out.print("i=" + i+"\n");
			int k= MIN_VALUE(result2(s,actions.get(i)),MAX);
			if (k>v) {
				v=k;
				maxIndex=i;
			}
			
			
			
		}
		
		return actions.get(maxIndex);
	}
	
	static int MAX_VALUE(State s,int MAX) {
		//System.out.println("max called");
		if(isTerminal(s)) return utility(s,MAX);
		
		int v=-10;
		ArrayList<int [] > actions= new ArrayList<>();
		actions =allActions(s);
		for(int i=0;i<actions.size();i++) {
		
			int k=MIN_VALUE(result2(s,actions.get(i)),MAX);
			if(v<k){
				v=k;
			}
		}
		
		return v;
	}
	
	

	
	
	static int MIN_VALUE(State s, int MAX) {
	//	System.out.println("min called");
		if(isTerminal(s)) return utility(s,MAX);
		
		int v=10;
		ArrayList<int []> actions =new ArrayList<>();
		actions=allActions(s);
		for(int i=0; i<actions.size();i++) {
			//System.out.println("min loop i="+i);
		//	printState(s);
			//System.out.println("actions: "+actions.get(i)[0]+""+actions.get(i)[1]);
			int k=MAX_VALUE(result2(s,actions.get(i)),MAX);
			//System.out.println("reached");
			if(v>k) {
				v=k;
			}
		}
		
		return v;
	}
	
	/**
	 * 
	 * I assume having more own color and having stone on corner are advantages.
	 * 
	 * @param state
	 * @param the role of agent(MAX)
	 * @return value of EVAL function
	 */
	static int hutility(State s,int MAX) {
		
		if(MAX==1) {
			//System.out.println("----------------------");
			//printState(s);
			
		//	System.out.println("white score:"+(whiteCounter(s)+3*atCorner(s,1)));
			return whiteCounter(s)+3*atCorner(s,1);
			
		}
		else {
			return blackCounter(s)+3*atCorner(s,-1);
		}
		
		
	}
	static int atCorner(State s,int MAX) {
		
		//System.out.println("used");
		int [][] state =s.getter();
		int count=0;
		if(state[0][0]==MAX) count ++;
		if(state[0][s.size()-1]==MAX) count ++;
		if(state[s.size()-1][s.size()-1]==MAX) count++;
		if(state[s.size()-1][0]==MAX) count++;
		return count;
	}
	
	
	static int[] H_MINIMAX(State s,int MAX) {
		ArrayList<int [] > actions =new ArrayList<>();
		actions=allActions(s);
		int d=0;
		int maxIndex=-1;
		int a,b;
		a=-1000;
		b=1000;
		int k;
		int v=-1000;
		//System.out.println("perspective:"+s.turn());
		for(int i=0;i<actions.size();i++) {
			k=H_MIN(result2(s,actions.get(i)),a,b,d+1,MAX);
		//	System.out.println("k="+k);
			if(k>v) { 
				v=k;
				maxIndex=i;
			}
			if(v>=a) a=v;
		}
		return actions.get(maxIndex);
	}
	
	static int H_MAX(State s, int a,int b,int depth,int MAX) {
		
		if(cutOff(s,depth)) {
			return hutility(s,MAX);
		}
		int v=-1000;
		ArrayList <int [] > actions = new ArrayList<>();
		actions = allActions(s);
		int k;
		for (int i=0;i<actions.size();i++) {
			k=H_MIN(result2(s,actions.get(i)),a,b,depth+1,MAX);
			
			if(k>v) v=k;
			if(v>=b) return v;
			if(a<=v) a=v;
			
		}
		
		return v;
	}
	
	static int H_MIN(State s, int a,int b, int depth,int MAX) {
		
		if(cutOff(s,depth)) {
			return hutility(s,MAX);
		}
		int v=1000;
		ArrayList <int []> actions = new ArrayList<>();
		actions=allActions(s);
		int k;
		for(int i=0;i<actions.size();i++) {
			k=H_MAX(result2(s,actions.get(i)),a,b,depth+1,MAX);
			if(k<v) v=k;
			if(v<=a ) return v;
			if(b>=v) b=v;
		}
		
		return v;
	}
	
	
	static boolean cutOff(State s ,int d) {
		
		if(isTerminal(s)) return true;
		else {
			//set cutoff level here!
			if (d>=depth) {
				return true;
			}
			
			
			return false;
		}
		
	}
	
	
	
	static int[] abSearch(State s, int MAX) {
		
		ArrayList<int []> actions=new ArrayList<>();
		
		actions = allActions(s);
		int maxIndex=-1;
		int a,b;
		a=-100;
		b=100;
		int k;
		int v=-100;
		for(int i=0;i<actions.size();i++) {
			
			k=MIN_VALUE(result2(s,actions.get(i)),a,b,MAX);
			if (k>v) {
				v=k;
				maxIndex=i;
			}
			
			if(a<=v) a=v;
			
			
		}
		
		return actions.get(maxIndex);
		
	}
	static int MAX_VALUE(State s, int a,int b, int MAX) {
		//System.out.println("abmax called");
		if (isTerminal(s)) return utility(s,MAX);
		int v=-1000;
		ArrayList<int []> actions =new ArrayList<>();
		
		actions=allActions(s);
		int k;
		for(int i=0;i<actions.size();i++) {
			
			k=MIN_VALUE(result(s,actions.get(i)),a,b,MAX);
			if (k>v) v=k;
			if (v>=b) return v;
			if(a<=v) a=v;
			
			
		}
		return v;
	}
	static int MIN_VALUE(State s, int a, int b ,int MAX) {
		
		if(isTerminal(s)) return utility(s,MAX) ;
		
		int v= 1000;
		ArrayList<int [] > actions=new ArrayList<>();
		actions=allActions(s);
		int k;
		for(int i=0;i<actions.size();i++) {
			
			k=MAX_VALUE(result2(s,actions.get(i)),a,b,MAX);
			if (k<v) v=k;
			if(v<=a) return v;
			if(b>=v) {
				b=v;
			}
			
			
		}
		return v;
	}
	
	
	
	static int blackCounter(State s) {
		
		int [][] state=s.getter();
		int size=s.size();
		int count=0;
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					if(state[i][j]==-1) count++;
				}
			}
		
		
		
		return count;
	}
	static int whiteCounter(State s) {
		
		int [][] state=s.getter();
		int size=s.size();
		int count=0;
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					if(state[i][j]==1) count++;
				}
			}
		
		
		
		return count;
	}
	
	static boolean isTerminal(State s) {
		
		
		int [][] state=s.getter();
		int size=s.size();
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(legalMove(s,i,j)) return false;
			}
		}
		
		return true;
	}
	
	
	/*
	 * isTerminal2 is used to help isTerminal to detect again whether there is truly no legal move for 2 players.
	 */
	
	static boolean isTerminal2(State s) {
		
		State tempS =new State(s.size(),s.turn(),s.getter());
		tempS.changeT();
		if(isTerminal(tempS)) return true;
		
		
		return false;
		
	}
	static void emptyMove(State s) {
		s.changeT();
	}
	
	static int[] conversion(String command) {
		
		
		char ccol=command.charAt(0);
		char crow=command.charAt(1);
		int col=ccol-97;
		int row=crow-49;
		
		int [] point= new int[2];
		point[0]=row;
		point[1]=col;
		return point;
	}
	
	
	static void printState(State s) {
		int [][] state=s.getter();
		int size= s.size();
		//System.out.println(" a b c d");
		char c =97;
		int pos;
		System.out.print(" ");
		for(int i=0; i<size;i++) {
			pos=i+97;
			c=(char) pos;
			System.out.print(c+" ");
		}
		System.out.println();
		
		for(int i=1;i<=size;i++) {
			System.out.print(i);
			for(int j=0;j<size;j++) {
				if (state[i-1][j]==0) System.out.print("  ");
				else if(state[i-1][j]==1) System.out.print("O ");
				else  System.out.print("X ");
			}
			System.out.print(i);
			System.out.println();
		}
		//System.out.println(" a b c d");
		System.out.print(" ");
		for(int i=0; i<size;i++) {
			pos=i+97;
			c=(char) pos;
			System.out.print(c+" ");
		}
		System.out.println();
		
		
	}
	
	static State result(State s,int[] action) {
		
		int player=s.turn();
		
		int other;
		if(player==-1) other=1;
		else other=-1;
		
		int row,col;
		row=action[0];
		col=action[1];
		
		int [][] ori;
		ori=s.getter();
		ori[row][col]=player;
		changeLine(s,row,col);
		
		
		return new State(s.size(),other,ori);
	}
	static State result2(State s,int[] action) {
		
		int player=s.turn();
		int size=s.size();
		int [][] newState= new int[size][size];
		int[][] state =s.getter();
		
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				newState[i][j]=state[i][j];
			}
		}
		int other;
		if(player==-1) other=1;
		else other=-1;
		
		int row,col;
		row=action[0];
		col=action[1];
	
		newState[row][col]=player;
		State st2= new State(size,player,newState);
		changeLine(st2,row,col);
		//System.out.println("************************");
		//printState(st2);
		//System.out.println("************************");
		st2.changeT();
		return st2;
	}
	
	static void changeLine(State s,int row, int col) {
		
		int [][] state=s.getter();
		int size=s.size();
		
		int player = s.turn();
		
		int other;
		
		if(player==-1) other=1;
		else other =-1;
		
		
		boolean [] direction=new boolean[8];
		
		
 		//0-w 1-nw 2-n 3-ne 4-e 5-se 6-s 7-sw clockwise from west
		if(col-1>=0) {
			if(state[row][col-1]==other) direction[0]=true;
			if(row-1>=0) {
				if(state [row-1][col-1]==other ) direction[1]=true;
			}
			if(row+1<=s.size()-1) {
				if(state[row+1][col-1]==other) direction[7]=true;
			}
			
		}
		if(col+1<s.size()) {
			if(state[row][col+1]==other) direction[4]=true;
			if(row-1>=0) {
				if(state[row-1][col+1]==other) direction[3]=true;
				
			}
			if(row+1<s.size()) {
				if(state[row+1][col+1]==other) direction[5]=true;
			}
		}
		if(row+1<s.size()) {
			if(state[row+1][col]==other) {
				direction[6]=true;
			}
			
		}
		if(row-1>=0) {
			if(state[row-1][col]==other) direction[2]=true;
		}
			
		for (int i=0; i<8;i++) {
			
			if (direction[i]&&checkLine(i,s,row,col)) {
				
				int dif=1;
				while(true) {
					
					if(i==0) {
						if(col-dif<0||state[row][col-dif]==player) break;
						else if(state[row][col-dif]==other) {
							state[row][col-dif]=player;
							dif++;
							continue;
						}
					}
					
					else if(i==1) {
						
						if(col-dif<0||row-dif<0||state[row-dif][col-dif]==player) break;
						else if(state[row-dif][col-dif]==other) {
							
							state[row-dif][col-dif]=player;
							dif++;
							continue;
							
						}
					}
					else if(i==2) {
						
						if(row-dif<0||state[row-dif][col]==player) break;
						else if(state[row-dif][col]==other) {
							
							state[row-dif][col]=player;
							dif++;
							continue;
						}
					}
					else if(i==3) {
						if(row-dif<0||col+dif>size-1||state[row-dif][col+dif]==player) break;
						else if(state[row-dif][col+dif]==other) {
							
							state[row-dif][col+dif]=player;
							dif++;
							continue;
						}
						
					}
					
					else if(i==4) {
						
						if(col+dif>size-1||state[row][col+dif]==player) break;
						else if (state [row][col+dif]==other) {
							state[row][col+dif]=player;
							dif++;
							continue;
						}
					}
					
					else if(i==5) {
						
						if(row+dif>size-1||col+dif>size-1||state[row+dif][col+dif]==player) break;
						else if (state[row+dif][col+dif]==other) {
							
							state[row+dif][col+dif]=player;
							dif++;
							continue;
						}
						
					}
					else if (i==6) {
						if(row+dif>size-1||state[row+dif][col]==player) break;
						else if(state [row+dif][col]==other) {
							
							state[row+dif][col]=player;
							dif++;
							continue;
						}
					}
					else if (i==7) {
						//System.out.print(row+","+ col+"-----"+dif+"size: "+size);
						if(row+dif>size-1||col-dif<0||state[row+dif][col-dif]==player) break;
						else if (state[row+dif][col-dif]==other) {
							
							state[row+dif][col-dif]=player;
							dif++;
							continue;
						}
					}
				}
			}
			
			
		}
		
		
	}
	
	static ArrayList<int[]> allActions(State s) {
		ArrayList<int[]> actions =new ArrayList();	
		int [][]state=s.getter();
		int[] point;
		for(int i=0;i<s.size();i++) {
			for(int j=0;j<s.size();j++) {
				
				
				if(state[i][j]==0&&legalMove(s,i,j)) {
					point=new int[2];
					point[0]=i;
					point[1]=j;
					actions.add(point);
				}
			}
		}
		return actions;
	}
	
	private static void debug_AllAct(ArrayList actions) {
		
		//int [] point;
		for(int [] a: (ArrayList<int []>)actions) {
			System.out.println(a[0]+","+a[1]);
		}
	}
	static boolean legalMove(State s,int row, int col) {
		int [][] state=s.getter();
		
		int player = s.turn();
		int other;
		
		if(player==-1) other=1;
		else other =-1;
		
		if(state[row][col]!=0) return false;
		boolean [] direction=new boolean[8];
		
		
 		//0-w 1-nw 2-n 3-ne 4-e 5-se 6-s 7-sw clockwise from west
		if(col-1>=0) {
			if(state[row][col-1]==other) direction[0]=true;
			if(row-1>=0) {
				if(state [row-1][col-1]==other ) direction[1]=true;
			}
			if(row+1<=s.size()-1) {
				if(state[row+1][col-1]==other) direction[7]=true;
			}
			
		}
		if(col+1<s.size()) {
			if(state[row][col+1]==other) direction[4]=true;
			if(row-1>=0) {
				if(state[row-1][col+1]==other) direction[3]=true;
				
			}
			if(row+1<s.size()) {
				if(state[row+1][col+1]==other) direction[5]=true;
			}
		}
		if(row+1<s.size()) {
			if(state[row+1][col]==other) {
				direction[6]=true;
			}
			
		}
		if(row-1>=0) {
			if(state[row-1][col]==other) direction[2]=true;
			
		}
		
		//System.out.println(row+","+col+Arrays.toString(direction));
		
		for (int i=0; i<8;i++) {
			//if(direction[i]==false) continue;
			if (direction[i]&&checkLine(i,s,row,col)) {//fixme
				return true;
			}
			
			
		}
		
		
		
		return false;
	}
	static private  boolean checkLine(int dir,State s,int row, int col) {
		int [][] state =s.getter();
		int player =s.turn();
		
		
		int difference =2;
		while (true) {
			
			if(dir==0) {
				if(col-difference <0||state[row][col-difference]==0) return false;
				else if(state[row][col-difference]==player) return true;
				else {
					difference ++;
					continue;
				}
				
			}
			else if(dir==1) {
				if(row-difference<0||col-difference<0||state[row-difference][col-difference]==0) {
					return false;
				}
				else if(state[row-difference][col-difference]==player) return true;
				else {
					difference++;
					continue;
				}
			}
			else if (dir==2) {
				if(row-difference<0||state[row-difference][col]==0) return false;
				else if (state[row-difference ][col]==player) return true;
				else {
					
					difference ++;
					continue;
				}
			}
			else  if ( dir ==3) {
				if(row-difference<0||col+difference>s.size()-1||state[row-difference][col+difference]==0) return false;
				else if (state[row-difference][col+difference]==player) return true;
				else {
					difference ++;
					continue;
				}
			}
			else if(dir==4) {
				
				if(col+difference>s.size()-1||state[row][col+difference]==0) return false;
				else if(state[row][col+difference]==player) return true;
				else {
					difference ++;
					continue;
				}
			}
			else if (dir==5) {
				if(row+difference >s.size()-1||col+difference>s.size()-1||state[row+difference][col+difference]==0) return false;
				else if (state[row+difference][col+difference]==player) return true;
				else {
					difference ++;
					continue;
				}
			}
			else if (dir==6) {
				if(row+difference>s.size()-1||state[row+difference][col]==0) return false;
				else if(state[row+difference][col]==player) return true;
				else {
					difference ++;
					
					continue;
				}
			}
			else if(dir==7) {
				if(row+difference >s.size()-1||col-difference<0||state[row+difference][col-difference]==0) return false;
				else if (state[row+difference][col-difference]==player) return true;
				else {
					difference ++;
					continue;
				}
			}
			return false;
		}
		
		
	}
	
}
