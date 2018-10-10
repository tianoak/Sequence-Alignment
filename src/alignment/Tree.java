package alignment;

import java.util.LinkedList;

/* 
** reference for DFS: http://ikeycn.iteye.com/blog/700632
** author: HuanTian
** date: 10/10/2018
*/

public class Tree {
		String data;
		Tree parent;
		LinkedList<Tree> childlist;
		
		Tree()
		{
			data = null;
			childlist = new LinkedList<Tree>();
			parent = null;
		}
		
		Tree(String d) {
			data = d;
			childlist = new LinkedList<Tree>();
			parent = null;
		}
		
		public void split(String ss) {
			
			int L = ss.length();
			
			String s1 = "";
			String s2 = "";
			for (int index = 0; index < L/2; index++) {
				s1 = ss.charAt(index*2) + s1;  
				s2 = ss.charAt(index*2+1) + s2;  
			}
			System.out.println(s1);
			System.out.println(s2);
			System.out.println();
		}
		
		public void buildPath(LinkedList<String> stack, Tree root, LinkedList<String> pathList) {  
			  
	        if (root != null) {  
	        	if (root.data != null)
	        		stack.add(root.data);  
	        	else
	        		stack.add("");
	            if (root.childlist.size() == 0) {  
	                changeToPath(stack, pathList);   
	            } else {  
	                LinkedList<Tree> childs = root.childlist;  
	                for (int i = 0; i < childs.size(); i++) {  
	                    buildPath(stack, childs.get(i), pathList);  
	                }  
	            }  
	            stack.remove(stack.size() - 1);  
	        }  
	    }  
	  
	    /** 
	     * @param path 
	     * @param pathList 
	     */  
	    public void changeToPath(LinkedList<String> path, LinkedList<String> pathList) {  
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < path.size(); i++) {  
	            if (path.get(i) != null) {  
	                sb.append(path.get(i) + "");  
	            }  
	  
	        }  
	        pathList.add(sb.toString().trim());  
	  
	    }  
}



