/**
 * 
 */
package sxb180026;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Surajit
 *
 */
public class ABGame {

	private static int position_evaluated = 0;
	private static int ab_estimate = 0;	
	
	
	/**
	 * @param b,board
	 * @param depth
	 * @param alpha
	 * @param beta
	 * @return the board position after max's play
	 */
	public char[] maxMin(char[] b, int depth, int alpha, int beta) {
		
		if(depth==0) {
			position_evaluated++;
		}else {
			char[] max_option = new char[b.length];
			char[] min_option;
			ArrayList<char[]> wChildren = new ArrayList<char[]>();
			
			wChildren = MorrisUtils.generateMovesMidgameEndgame(b);
			
			int v=Integer.MIN_VALUE;
			
			for(int i=0;i<wChildren.size();i++) {	
				
				min_option = minMax(wChildren.get(i), depth-1, alpha, beta);
				int sEstimation=MorrisUtils.staticEstimationMidEnd(b);
				if(sEstimation>v) {
					v = sEstimation;
					ab_estimate = v;
					max_option = wChildren.get(i);
				}
				if(v>=beta) {
					
					return max_option;
				}
				else {
					
					alpha = Math.max(v,alpha);
				}				
			}
			return max_option;
		}
		
		
		return b;
	}

	/**
	 * @param b, board
	 * @param depth
	 * @param  alpha
	 * @param beta
	 * @return
	 */
	public char[] minMax(char[] b, int depth, int alpha, int beta) {
		if(depth==0) {
			position_evaluated++;
		}
		else{
		    char[] min_option= new char[b.length];
			char[] max_option ;
			
			ArrayList<char[]> bchildren = new ArrayList<char[]>();
		    bchildren = MorrisUtils.generateBlackMovesMidEnd(b);
			int v=Integer.MAX_VALUE;
			
			for(int i=0;i<bchildren.size();i++) {
				max_option = maxMin(bchildren.get(i), depth-1, alpha, beta);
				int sEstimation=MorrisUtils.staticEstimationMidEnd(b);
				if(sEstimation<v) {
					v = sEstimation;
					min_option = bchildren.get(i);
				}
				if(v<=alpha) {
					
					return min_option;
				}
				else {
					
					beta = Math.min(v,beta);
				}
			}
			return min_option;
		}
		
		return b;
	} 
	
	
	

	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);
		int depth = Integer.parseInt(args[2]);
		int x=Integer.MIN_VALUE;
		int y=Integer.MAX_VALUE;
		FileInputStream fis = new FileInputStream(inputFile);
		PrintWriter out = new PrintWriter(new FileWriter(outputFile));
		Scanner sc = new Scanner(fis);

		while (sc.hasNextLine()) {
			String str = sc.next();
			char[] board = str.toCharArray();
			ABGame abg = new ABGame();
			System.out.println("given board : " + new String(board));
			char[] d = abg.maxMin(board, depth,x,y);
			out.println("Board Position : " + new String(d));
			out.println("Positions evaluated by static estimation : " + abg.position_evaluated);
			out.println("MiniMax estimate : " + abg.ab_estimate);
			out.println();
		}
		fis.close();
		out.close();

	}
}
