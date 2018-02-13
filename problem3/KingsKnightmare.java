import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

	
/**
 * @author abhanshu 
 * This class is a template for implementation of 
 * HW1 for CS540 section 2
 */
/**
 * Data structure to store each node.
 */
class Location {
	private int x;
	private int y;
	private Location parent;

	public Location(int x, int y, Location parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Location getParent() {
		return parent;
	}
	

	@Override
	public String toString() {
		return x + " " + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Location) {
			Location loc = (Location) obj;
			return loc.x == x && loc.y == y;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * (hash + x);
		hash = 31 * (hash + y);
		return hash;
	}
}

public class KingsKnightmare {
	//represents the map/board
	private static boolean[][] board;
	//represents the goal node
	private static Location king;
	//represents the start node
	private static Location knight;
	//y dimension of board
	private static int n;
	//x dimension of the board
	private static int m;
	//enum defining different algo types
	enum SearchAlgo{
		BFS, DFS, ASTAR, PRINT;
		
	}
	
	
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			//loads the input file and populates the data variables
			SearchAlgo algo = loadFile(args[0]);
			if (algo != null) {
				switch (algo) {
					case DFS :
						executeDFS();
						break;
					case BFS :
						executeBFS();
						break;
					case ASTAR :
						executeAStar();
						break;
					default :
						break;
				}
			}
		}
	}

	/**
	 * Implementation of Astar algorithm for the problem
	 */

	private static void executeAStar() {
		//TODO: Implement A* algorithm in this method
		System.out.println("Astar started");
		final int cost = 3;

		// Initialization 
		PriorityQ<Location> frontier = new PriorityQ<>();
		boolean[][] visited = new boolean [m][n];
		boolean done = false;
		
		// Initialization : Have the visited false for every position
		for (int i=0; i<m; i++) {
			for (int j=0; j<n ; j++) {
				visited[i][j] = false;
			}
		}
		
		int gScore = 0;
		int hScore = 0;
		int fScore = 0;
		
		// heuristice calculation
		// movement calculation
		// f score calculation 
		
		frontier.add(knight,fScore);
		int start_X = knight.getX();
		int start_Y = knight.getY();
		
		SimpleEntry<Location, Integer> itr;
		List<Location> next = null;
		int expandedCount = 0;
		
		while (!frontier.isEmpty() && !done) {
			itr = frontier.poll();
			visited[itr.getKey().getX()][itr.getKey().getY()] = true;

			next = successor(itr.getKey());
			//System.out.println(itr);
			//System.out.println(next);
			
			
			for (int i=0; i < next.size(); i++) {
				int check_X = next.get(i).getX();
				int check_Y = next.get(i).getY();
				
				hScore = Math.abs( (check_X - king.getX()) +
						 Math.abs(check_X - king.getY())) ;
				gScore = i*3;
				fScore = hScore + gScore;
				
				
				if(next.get(i).equals(king)) {
					printPath(next.get(i));
					done = true;
					break;
				}
				
				if (visited[check_X][check_Y] != true 
						&& !frontier.exists(next.get(i))) {
					// if it's not visited yet 
					
					frontier.add(next.get(i),fScore);
					 
				}
			}
		}
		
		
		
		System.out.println("Expanded Nodes: " + expandedCount);

	}
	

	

	/**
	 * Implementation of BFS algorithm
	 */
	private static void executeBFS() {
		//TODO: Implement bfs algorithm in this method
		//System.out.println("bfs started");
		
		// Initialization 
		Queue<Location> frontier = new LinkedList<>();
		boolean[][] visited = new boolean [m][n];
		boolean done = false;
		
		// Initialization : Have the visited false for every position
		for (int i=0; i<m; i++) {
			for (int j=0; j<n ; j++) {
				visited[i][j] = false;
			}
		}
		
		frontier.add(knight);
		int start_X = knight.getX();
		int start_Y = knight.getY();
		
		Location itr;
		List<Location> next = null;
		int expandedCount = 0;
		
		while (!frontier.isEmpty() && !done) {
			itr = frontier.poll();
			visited[itr.getX()][itr.getY()] = true;
			expandedCount++;

			next = successor(itr);
			//System.out.println(itr);
			//System.out.println(next);
			
			for (int i=0; i < next.size(); i++) {
				int check_X = next.get(i).getX();
				int check_Y = next.get(i).getY();
				
				if(next.get(i).equals(king)) {
					printPath(next.get(i));
					done = true;
					break;
				}
				
				if (visited[check_X][check_Y] != true && !frontier.contains(next.get(i))) {
					// if it's not visited yet 
					frontier.add(next.get(i));
					 
				}
			}
		}
		
		
		
		System.out.println("Expanded Nodes: " + expandedCount);

		
	}
	
	/**
	 * Implemention of DFS algorithm
	 */
	private static void executeDFS() {
		//TODO: Implement dfs algorithm in this method
		System.out.println("DFS started");
		
		// Initialization 
		Stack<Location> frontier = new Stack<>();
		boolean[][] visited = new boolean [m][n];
		boolean done = false;
		
		// Initialization : Have the visited false for every position
		for (int i=0; i<m; i++) {
			for (int j=0; j<n ; j++) {
				visited[i][j] = false;
			}
		}
		// Push the current root node
		frontier.push(knight);
		int start_X = knight.getX();
		int start_Y = knight.getY();
		
		Location itr;
		List<Location> next = null;
		int expandedCount = 0;
		
		while (!frontier.isEmpty() && !done) {
			// Pop a Location node from stack and print
			itr = frontier.peek();
			frontier.pop();
			
			visited[itr.getX()][itr.getY()] = true;
			expandedCount++;

			next = successor(itr);
			//System.out.println(itr);
			//System.out.println(next);
			
			for (int i=0; i < next.size(); i++) {
				int check_X = next.get(i).getX();
				int check_Y = next.get(i).getY();
				
				if(next.get(i).equals(king)) {
					printPath(next.get(i));
					done = true;
					break;
				}
				
				if (visited[check_X][check_Y] != true && 
						!frontier.contains(next.get(i))) {
					// if it's not visited yet 
					frontier.push(next.get(i));
					 
				}
			}
		}
		
		if (expandedCount <= 1) {
			System.out.println("NOT REACHABLE");
			System.out.println("Expanded Nodes: " + expandedCount);
		}
		
		else {
			System.out.println("Expanded Nodes: " + expandedCount);
		}
		
	}
	
	
	private static void printPath(Location e) {
		
		Location endNode = e;
		List<Location> shortestPath = new ArrayList<Location>();
		
		shortestPath.add(e);
		while (endNode.getParent() != null) {
			shortestPath.add(endNode.getParent());
			endNode = endNode.getParent();
		}
		//System.out.println(shortestPath);
		
		for (int i=shortestPath.size()-1 ; i >= 0 ; i--) {
			System.out.println(shortestPath.toArray()[i]);
		}
	}
	
	
	private static List<Location> successor(Location e) {
		
		List<Location> successors = new ArrayList<Location>();
		
		
		int x[] = {+2,+1,-1,-2,-2,-1,+1,+2};
		int y[] = {+1,+2,+2,+1,-1,-2,-2,-1};
		
		//int startPos_x = e.getX();
		//int startPos_y = e.getY();
		
		for (int i=0; i< x.length; i++) {
			//System.out.println(dx[i]);
			int nextPos_x = e.getX() + x[i];
			int nextPos_y = e.getY() + y[i];
			if (nextPos_x>=0 && nextPos_x < m && 
					nextPos_y>=0 && nextPos_y < n &&
					!board[nextPos_y][nextPos_x] ) {
			successors.add(new Location(nextPos_x, nextPos_y, e));
			}
		}
		
		return successors;
		
	}
	
	/**
	 * 
	 * @param filename
	 * @return Algo type
	 * This method reads the input file and populates all the 
	 * data variables for further processing
	 */
	private static SearchAlgo loadFile(String filename) {
		File file = new File(filename);
		try {
			Scanner sc = new Scanner(file);
			SearchAlgo algo = SearchAlgo.valueOf(sc.nextLine().trim().toUpperCase());
			n = sc.nextInt();
			m = sc.nextInt();
			sc.nextLine();
			board = new boolean[n][m];
			for (int i = 0; i < n; i++) {
				String line = sc.nextLine();
				for (int j = 0; j < m; j++) {
					if (line.charAt(j) == '1') {
						board[i][j] = true;
					} else if (line.charAt(j) == 'S') {
						knight = new Location(j, i, null);
					} else if (line.charAt(j) == 'G') {
						king = new Location(j, i, null);
					}
				}
			}
			sc.close();
			return algo;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
