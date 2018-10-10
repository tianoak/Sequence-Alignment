package alignment;

import java.util.LinkedList;

/* 
** author: HuanTian
** date: 10/10/2018
*/
public class myLocal extends Alignment{
	
	String s1;
	String s2;
	int count = 0;
	int m;
	int n;
	int F[][];
	LinkedList<Integer> optm = new LinkedList<Integer>();
	LinkedList<Integer> optn = new LinkedList<Integer>();
	int max = Integer.MIN_VALUE;
	int num;
	
	int scoreMatch;
    int scoreMisMatch;
	int scoreInsDel;
	
	public myLocal(String sa, String sb) {
		s1 = sa;
		s2 = sb;
		m = s1.length();
		n = s2.length();
		F = new int[m+1][n+1];
	}
	
	public myLocal(String sa, String sb, int score1, int score2, int score3) {
		s1 = sa;
		s2 = sb;
		m = s1.length();
		n = s2.length();
		F = new int[m+1][n+1];
		scoreMatch = score1;
		scoreMisMatch = score2;
		scoreInsDel = score3;
	}
	
	public int score() {
		return max;	
	}
	
	
	public void align() {
			
		F[0][0] = 0;
		for (int i=1; i<=m; i++)
			F[i][0] = 0;
		for (int j=1; j<=n; j++)
			F[0][j] = 0;
		
		for (int i=1; i <= m; i++) {
			for (int j=1; j <= n; j++) {
				int score1 = F[i-1][j-1] + isequal(i-1, j-1);
				int score2 = F[i-1][j] + scoreInsDel;
				int score3 = F[i][j-1] + scoreInsDel;
				F[i][j] = Math.max(Math.max(score1, score2), Math.max(score3,0));
				if(F[i][j] > max) 
					max = F[i][j];
			}
		}
		for (int i=1; i <= m; i++) {
			for (int j=1; j <= n; j++) {
				if (F[i][j] == max) {
					optm.add(i);
					optn.add(j);
				}
			}
		}
		
	}	
	
	
	int isequal(int i, int j) {
		return s1.charAt(i) == s2.charAt(j)?scoreMatch:scoreMisMatch;
	}
	
	public LinkedList<Tree> Backtree() {
		LinkedList<Tree> trees = new LinkedList<Tree>();
		for(int index=0; index < optm.size(); index++) {
			trees.add(backtree(optm.get(index), optn.get(index)));
		}
		return trees;
	}
	
	public Tree backtree(int i, int j) {
		
		Tree gtree = new Tree();
		
		if (i*j ==0) 
			return gtree;
		
		if (F[i][j] == F[i-1][j-1] + isequal(i-1, j-1)) {
			String data = s1.charAt(i-1) + "" + s2.charAt(j-1);
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			Tree ee = backtree(i-1, j-1);
			e.childlist.add(ee);
		}
		if (F[i][j] == F[i-1][j] + scoreInsDel) {
			String data = s1.charAt(i-1) + "" + "-";
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			Tree ee = backtree(i-1, j);
			e.childlist.add(ee);
		}
		if (F[i][j] == F[i][j-1] + scoreInsDel) {
			String data = "-" + "" + s2.charAt(j-1);
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			Tree ee = backtree(i, j-1);
			e.childlist.add(ee);
		}
		if (F[i][j] == 0) { //termination
			return gtree;
		}
	    return gtree;
	}
	
}
