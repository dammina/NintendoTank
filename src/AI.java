import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AI {
	private static String[] move={"UP#","DOWN#","RIGHT#","LEFT#","SHOOT#"};
	private Client myClient;
	private List<Vertex> nodes;
	private List<Edge> edges;
	private static LinkedList<Vertex> path; 
	private int coin_location;
	private int health_location;
	private int minLoc;
	private int min;
	//	private coins min_dist_coin;

	public void AIfunc(){
		myClient=new Client();

		int x,y;
		//		int dist;
		//		int mindist=1000;
		x=Integer.parseInt(WizardGame.xc[WizardGame.playerNo]);//x coordinate at the moment
		y=Integer.parseInt(WizardGame.yc[WizardGame.playerNo]);//y coordinate at the moment

		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();

		/*for(int i=0;i<WizardGame.blocked.size();i++){
	    	System.out.print("#"+WizardGame.blocked.get(i)+"#");
//	    	System.out.print("|");
//	    	System.out.print((WizardGame.blocked.get(i).getY()*1+WizardGame.blocked.get(i).getX()*10)+" ");

	    }*/
		System.out.println();
		//configuring vertexes
		for (int i = 0; i < 400; i++) {
			Vertex location = new Vertex(i+"", i+"");
			nodes.add(location);
		}
		for(int i=0;i<400;i++){

			//configuring graph edges
			if(i+1<400 && !WizardGame.blocked.contains(i) && !WizardGame.blocked.contains(i+1) && i%20!=19){

				Edge ed1=new Edge(i+"",nodes.get(i), nodes.get(i+1),1);
				edges.add(ed1);
				Edge ed2=new Edge(i+"", nodes.get(i+1), nodes.get(i), 1);
				edges.add(ed2);
			}
			//if the cell is blocked it will not added as a graph edge
			if(i+20<400 && !WizardGame.blocked.contains(i) && !WizardGame.blocked.contains(i+20)){
				Edge ed1=new Edge(i+"",nodes.get(i), nodes.get(i+20),1);
				edges.add(ed1);
				Edge ed2=new Edge(i+"", nodes.get(i+20), nodes.get(i), 1);
				edges.add(ed2);
			}
		}
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		minLoc=1000;
		//collect health packs if life level is below 70%
		if(Integer.parseInt(WizardGame.health[WizardGame.playerNo])<=70 && WizardGame.lifes.size()>0){
			for(int i=0;i<WizardGame.lifes.size();i++){	
				dijkstra.execute(nodes.get(x*20+y));
				health_location=WizardGame.lifes.get(i).getX()*20+WizardGame.lifes.get(i).getY();
				path = dijkstra.getPath(nodes.get(health_location));	
				//selecting shortest path
				if(path!=null && path.size()<minLoc){
					minLoc=path.size();
					min=health_location;
				}
			}
			dijkstra.execute(nodes.get(x*20+y));
			path = dijkstra.getPath(nodes.get(min));
		}

		//collect coin piles
		for(int i=0;i<WizardGame.coins.size();i++){	
			dijkstra.execute(nodes.get(x*20+y));
			coin_location=WizardGame.coins.get(i).getX()*20+WizardGame.coins.get(i).getY();
			path = dijkstra.getPath(nodes.get(coin_location));	

			if(path!=null && path.size()<minLoc){
				minLoc=path.size();
				min=coin_location;
			}

		}
		dijkstra.execute(nodes.get(x*20+y));
		path = dijkstra.getPath(nodes.get(min));

		if(path!=null){
			for (int k=1;k<path.size();k++) {
				System.out.print(" "+path.get(k));
			}
		}
		//if there is no coin pile or life pack
		if(path==null || path.getLast().toString().equals("0")){
			if((x==0 || x==1 || x==2 || x==3 || x==4 || x==5 || x==6 || x==7) && !WizardGame.blocked.contains((x+1)*20+y))
				myClient.sendMessage("RIGHT#");
			else if((x==12 || x==13 || x==14 || x==15 || x==16 || x==17 || x==18 || x==19) && !WizardGame.blocked.contains((x-1)*20+y))
				myClient.sendMessage("LEFT#");
			else if((y==0 || y==1 || y==2 || y==3 || y==4 || y==5 || y==6 || y==7) && !WizardGame.blocked.contains((x)*20+(y+1)))
				myClient.sendMessage("DOWN#");
			else if((y==12 || y==13 || y==14 || y==15 || y==16 || y==17 || y==18 || y==19) && !WizardGame.blocked.contains((x)*20+(y-1)))
				myClient.sendMessage("UP#");
			else
				myClient.sendMessage("SHOOT#");
		}
		//select the move
		else if(checkNextTwoBlocks(x, y))
			myClient.sendMessage("SHOOT#");
		else if(path!=null && Integer.parseInt((path.get(1).toString()))/20>x)
			myClient.sendMessage("RIGHT#");			
		else if(path!=null && Integer.parseInt((path.get(1).toString()))/20<x)
			myClient.sendMessage("LEFT#");
		else if(path!=null && Integer.parseInt((path.get(1).toString()))%20>y)
			myClient.sendMessage("DOWN#");
		else if(path!=null && Integer.parseInt((path.get(1).toString()))%20<y)
			myClient.sendMessage("UP#");

	}

	public boolean checkNextTwoBlocks(int x,int y){
		for(int i=1;i<WizardGame.players.length;i++){
			
			if(WizardGame.dir[WizardGame.playerNo].equals("0") && Integer.parseInt(WizardGame.xc[i-1])==x && Integer.parseInt(WizardGame.yc[i-1])<y){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
			}
			else if(WizardGame.dir[WizardGame.playerNo].equals("2") && Integer.parseInt(WizardGame.xc[i-1])==x && Integer.parseInt(WizardGame.yc[i-1])>y){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
			}else if(WizardGame.dir[WizardGame.playerNo].equals("1") && Integer.parseInt(WizardGame.yc[i-1])==y && Integer.parseInt(WizardGame.xc[i-1])>x){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
			}else if(WizardGame.dir[WizardGame.playerNo].equals("3") && Integer.parseInt(WizardGame.yc[i-1])==y && Integer.parseInt(WizardGame.xc[i-1])<x){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
			}
			
			/*if(WizardGame.dir[WizardGame.playerNo].equals("0") && Integer.parseInt(WizardGame.xc[i-1])==x && (Integer.parseInt(WizardGame.yc[i-1])==y-1 || Integer.parseInt(WizardGame.yc[i-1])==y-2 )){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
				
			}else if(WizardGame.dir[WizardGame.playerNo].equals("2") && Integer.parseInt(WizardGame.xc[i-1])==x && (Integer.parseInt(WizardGame.yc[i-1])==y+1 || Integer.parseInt(WizardGame.yc[i-1])==y+2 )){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
			}else if(WizardGame.dir[WizardGame.playerNo].equals("1") && Integer.parseInt(WizardGame.yc[i-1])==y && (Integer.parseInt(WizardGame.xc[i-1])==x+20 || Integer.parseInt(WizardGame.xc[i-1])==x+40 )){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
			}else if(WizardGame.dir[WizardGame.playerNo].equals("3") && Integer.parseInt(WizardGame.yc[i-1])==y && (Integer.parseInt(WizardGame.xc[i-1])==x-20 || Integer.parseInt(WizardGame.xc[i-1])==x-40 )){
				if(WizardGame.health[i-1].equals("0")){
					return false;
				}
				return true;
			}*/
		}
		return false;
	}
}

class Vertex {
	final private String id;
	final private String name;


	public Vertex(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

} 

class Edge  {
	private final String id; 
	private final Vertex source;
	private final Vertex destination;
	private final int weight; 

	public Edge(String id, Vertex source, Vertex destination, int weight) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getId() {
		return id;
	}
	public Vertex getDestination() {
		return destination;
	}

	public Vertex getSource() {
		return source;
	}
	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return source + " " + destination;
	}


} 


class Graph {
	private final List<Vertex> vertexes;
	private final List<Edge> edges;

	public Graph(List<Vertex> vertexes, List<Edge> edges) {
		this.vertexes = vertexes;
		this.edges = edges;
	}

	public List<Vertex> getVertexes() {
		return vertexes;
	}

	public List<Edge> getEdges() {
		return edges;
	}



} 


class DijkstraAlgorithm {

	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Integer> distance;

	public DijkstraAlgorithm(Graph graph) {
		// Create a copy of the array so that we can operate on this array
		this.nodes = new ArrayList<Vertex>(graph.getVertexes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}

	public void execute(Vertex source) {
		settledNodes = new HashSet<Vertex>();
		unSettledNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		predecessors = new HashMap<Vertex, Vertex>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Vertex node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		for (Vertex target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node)
					+ getDistance(node, target)) {
				distance.put(target, getShortestDistance(node)
						+ getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private int getDistance(Vertex node, Vertex target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)
					&& edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)
					&& !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}

	private int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public LinkedList<Vertex> getPath(Vertex target) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target;
		// Check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}

} 

