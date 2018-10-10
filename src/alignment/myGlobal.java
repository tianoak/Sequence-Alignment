package alignment;

/* reference for output sequences: https://courses.cs.washington.edu/courses/cse143/13wi/exploration/DNAAlignment.java
** author: HuanTian
** date: 10/10/2018
*/
public class myGlobal extends Alignment{
	
	String s1;
	String s2;
	public int count;
	int m;
	int n;
	int F[][];
	int num;
	
	
	int scoreMatch;
    int scoreMisMatch;
	int scoreInsDel;
	
	public myGlobal(String sa, String sb) {
		s1 = sa;
		s2 = sb;
		m = s1.length();
		n = s2.length();
		F = new int[m+1][n+1];
	}
	
	public myGlobal(String sa, String sb, int score1, int score2, int score3) {
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
		return F[m][n];		
	}
	
	
	public void align() {
		
		F[0][0] = 0;
		for (int i=1; i<=m; i++)
			F[i][0] = scoreInsDel;
		for (int j=1; j<=n; j++)
			F[0][j] = scoreInsDel;
		
		for (int i=1; i <= m; i++) {
			for (int j=1; j <= n; j++) {
				int score1 = F[i-1][j-1] + isequal(i-1, j-1);
				int score2 = F[i-1][j] + scoreInsDel;
				int score3 = F[i][j-1] + scoreInsDel;
				F[i][j] = Math.max(Math.max(score1, score2), score3);
			}
		}
	}	
	
	
	int isequal(int i, int j) {
		return s1.charAt(i) == s2.charAt(j)?scoreMatch:scoreMisMatch;
	}
	
public Tree Backtree(int i, int j) {
		
		Tree gtree = new Tree();
	
		if (i == 0 && j == 0) {
			return gtree;
		} else if (i == 0) {
			String data = "-" + "" + s2.charAt(j-1);
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			e.childlist.add(Backtree(i, j-1));
			return gtree;
		} else if (j == 0) {
			String data = s1.charAt(i-1) + ""  + "-";
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			e.childlist.add(Backtree(i-1, j));
			return gtree;
		}
		
		if (F[i][j] == F[i-1][j-1] + isequal(i-1, j-1)) {
			String data = s1.charAt(i-1) + "" + s2.charAt(j-1);
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			e.childlist.add(Backtree(i-1, j-1));
		}
		if (F[i][j] == F[i-1][j] + scoreInsDel) {
			String data = s1.charAt(i-1) + "" + "-";
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			Tree ee = Backtree(i-1, j);
			e.childlist.add(ee);
		}
		if (F[i][j] == F[i][j-1] + scoreInsDel) {
			String data = "-" + "" + s2.charAt(j-1);
			Tree e = new Tree(data);
			gtree.childlist.add(e);
			e.parent = gtree;
			Tree ee = Backtree(i, j-1);
			e.childlist.add(ee);
		}
	    return gtree;
	}
	
}
