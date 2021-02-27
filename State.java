import java.util.ArrayList;
import java.util.Arrays;


public class State {
	// -1 for black, 1 for white  0 for nothing
	private int [][] state;
	
	private int turn;
	private int size;
	//private int utility;
	
	public State() {
		
	}
	public State(int i,int turn) {
		size=i;
		state=new int[size][size];
		this.turn=turn; //human always first. let player choose which one to move first.
		if(size==4) {
			state[1][1]=1;
			state[1][2]=-1;
			state[2][1]=-1;
			state[2][2]=1;
		}
		else if (size==8) {
			state[3][3]=1;
			state[3][4]=-1;
			state[4][3]=-1;
			state[4][4]=1;
		}
	}
	public State(int i, int turn,int[][] newState) {
		size=i;
		this.turn=turn;
		state=newState;
		
	}
	
	
	public int [][] getter(){
		
		return state;
	}
	public int size() {
		return size;
		
	}
	public int turn () {
		return turn;
	}
	
	public void changeT() {
		if (turn==1) turn =-1;
		else turn =1;
	}
}
