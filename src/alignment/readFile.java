package alignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class readFile {
	String method = "";
	int score[] = new int[3];
	String sa;
	String sb;
	static int count = 0;
	
	void read_file() throws IOException {
		String pathname = "/Users/caotao/Documents/HXT/EECS458-bioinformatics/input.txt";		
		File filename = new File(pathname); 	
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); 	
		BufferedReader br = new BufferedReader(reader); 		

		method = br.readLine();

		String[] numbers = br.readLine().split(",");

		for (int i=0; i<3; i++) {
			score[i] = Integer.parseInt(numbers[i]);
		}
		sa = br.readLine();
		sb = br.readLine();

		br.close();
	}
	
	public static void show(Tree t, Alignment alignment) {
		LinkedList<String> stack = new LinkedList<String>();
		LinkedList<String> pathList = new LinkedList<String>();
		t.buildPath(stack, t, pathList);
		//System.out.println("number of solutions: " + count++);	
		for (int i=0; i<pathList.size(); i++) {
			System.out.println("solution" + (++count) + "...");
			t.split(pathList.get(i));
		}
	}
	
	public static void main(String[] args) throws IOException {
		readFile rf = new readFile();
		rf.read_file();
		Alignment alignment;
		if(rf.method.equals("g")) {
			System.out.println("Global...\n");
			alignment = new myGlobal(rf.sa, rf.sb, rf.score[0], rf.score[1], rf.score[2]);
			alignment.align();
			Tree t;
			t = alignment.Backtree(rf.sa.length(), rf.sb.length());
			show(t,alignment);
			System.out.println("number of solutions: " + count);
			System.out.println("optimal score: " + alignment.score());
			
		} else {
			System.out.println("Local...\n");
			alignment = new myLocal(rf.sa, rf.sb, rf.score[0], rf.score[1], rf.score[2]);
			alignment.align();
			LinkedList<Tree> trees = new LinkedList<Tree> ();
			trees = alignment.Backtree();
			for (int index=0; index<trees.size(); index++) {
				show(trees.get(index), alignment);
			}
			System.out.println("number of solutions: " + count);
			System.out.println("optimal score: " + alignment.score());
		}
		
	}

}
