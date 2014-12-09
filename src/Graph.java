 // Project Member : Vaishali Shah & Shabad Sehgal

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides an abstract data type for a Graph.
 * 
 * @author sxs14030 & vxs135730
 *
 * @param <T>
 *         Type of data that vertices in the graph will be holding.
 */
public class Graph<T> {
	private final int INFINITY = Integer.MAX_VALUE;
	/**
	 * This holds the number of vertex in the graph.
	 */
	private int V;
	/**
	 * This holds the number of edges in the graph.
	 */
	private int E;
	/**
	 * This holds array of Vertices of type T.
	 */
	private ArrayList<Vertex<T>> vertices;	

	/**
	 * This initializes the graph with V vertices and values.
	 * 
	 * @param V
	 * @param values
	 */
	@SuppressWarnings("unchecked")
	public Graph(int V, T[] values) {
		this.V = V;
		// Array is 1 based indexing.
		//this.vertices = new Vertex[V + 1];
		this.vertices = new ArrayList<Vertex<T>>();
		vertices.add(null);
		//this.adjMatrix = new int[V + 1][V + 1];
		for (int i = 1, j = 0; i < V + 1; i++, j++) {
			this.vertices.add(i,new Vertex<T>(values[j]));
			
		}
	}
	
	/**
	 * this method is used to add new vertex to graph
	 * 
	 * */
	public Vertex<Integer> addVertex(Vertex<Integer> newVertex){
		this.V = this.V+1;		
		this.vertices.add((Vertex<T>) newVertex);
		return newVertex;
	}

	/**
	 * This method adds an edge between two vertices in a graph.
	 * 
	 * @param u
	 * @param v
	 * @param w
	 */
	public void addEdge(int u, int v, int w) {
		int fromIdx = this.vertices().indexOf(new Vertex<Integer>(u));
		int toIdx = this.vertices().indexOf(new Vertex<Integer>(v));
		Vertex<T> from = this.vertices.get(fromIdx);
		Vertex<T> to = this.vertices.get(toIdx);
		boolean toFlag=false,fromFlag=false;
		for (Edge<Vertex<T>> edge : from.adjacencyList()){
			if(edge.equals(new Edge<Vertex<T>>(from, to, w, 'o'))){
				if(edge.getW()>w)
					edge.setW(w);
				fromFlag=true;
			}
			
		}
		for (Edge<Vertex<T>> edge : to.adjacencyList()){
			if(edge.equals(new Edge<Vertex<T>>(to, from, w, 'i'))){
				if(edge.getW()>w)
					edge.setW(w);
					toFlag=true;	
			}
			
		}
		if(!fromFlag)
		from.adjacencyList().add(new Edge<Vertex<T>>(from, to, w, 'o'));
		if(!toFlag)
		to.adjacencyList().add(new Edge<Vertex<T>>(to, from, w, 'i'));		
		E++;
	}

	/**
	 * this method is used to remove given vertex from graph
	 * 
	 * */
	public void removeVertex(Vertex<Integer> remVertex) {

		this.vertices.remove(remVertex);
		this.V=this.V-1;
		
	}

	/**
	 * 
	 * this method is used to remove given edge from graph both outgoing and incoming edge 
	 * 
	 * */
	public int removeEdge(Edge<Vertex<T>> removingEdge) {
		int f =0,t=0;
		int fromIdx = this.vertices().indexOf(removingEdge.getU());
		int toIdx = this.vertices().indexOf(removingEdge.getV());
		if(fromIdx>0)
		f = this.vertices().get(fromIdx).adjacencyList().remove(new Edge<Vertex<T>>(removingEdge.getU(), removingEdge.getV(), removingEdge.getW(),removingEdge.geteType()));
		if(toIdx>0)
		t = this.vertices().get(toIdx).adjacencyList().remove(new Edge<Vertex<T>>(removingEdge.getV(), removingEdge.getU(), removingEdge.getW(), (removingEdge.geteType()== 'i')?'o':'i'));
		return 0;
	}
	
	
	/**
	 * remove edge for a cycle 
	 * 
	 * */
	public int removeCycleEdge(Edge<Vertex<T>> removingEdge,boolean isFrom) {
		int f =0,t=0;
		
		int fromIdx = this.vertices().indexOf(removingEdge.getU());
		int toIdx = this.vertices().indexOf(removingEdge.getV());
		if(isFrom)
		f = this.vertices().get(fromIdx).adjacencyList().remove(new Edge<Vertex<T>>(removingEdge.getU(), removingEdge.getV(), removingEdge.getW(), 'o'));
		else
		t = this.vertices().get(toIdx).adjacencyList().remove(new Edge<Vertex<T>>(removingEdge.getV(), removingEdge.getU(), removingEdge.getW(), 'i'));
		
		if((f==1)||(t==1)){
			System.out.println("not removed f="+f+" t="+t);
		return 1;
		}
		return 0;
	}
	
	/**
	 * this method is used to calculate minimum incoming edges weight and then reduce the weight by min weight 
	 *  
	 * */
	public int findMinWeightEdge(Vertex<Integer> V,int source){		
		int weight = INFINITY;		
		for (Edge<Vertex<Integer>> edge : V.adjacencyList()) {				
			
			// for other vertices find min incoming weight
			
			// checking if it incoming edge or not
			if(edge.geteType()=='i'){				
				if (weight > edge.weight()){
					weight= edge.weight();					
				}
			}
		}
		Vertex<Integer> outGoingEdge = null;
		for (Edge<Vertex<Integer>> edge : V.adjacencyList()) {
			
			if(edge.geteType()=='i'&&edge.weight()!=0){					 
			edge.setW(edge.weight()-weight);
			outGoingEdge= edge.to();	
			for (Edge<Vertex<Integer>> edge1 : outGoingEdge.adjacencyList()) {
				
				if(edge1.geteType()=='o'&&edge1.to().equals(V)){					 
				edge1.setW(edge1.weight()-weight);
						break;
					}
				}
			
				}
			}
		return weight;
	}
	
	/**
	 * This method returns the number of vertices in the graph.
	 * 
	 * @return
	 */
	public int noOfVertices() {
		return V;
	}

	/**
	 * This method returns the array of vertices.
	 * 
	 * @return
	 */
	public ArrayList<Vertex<T>> vertices() {
		return this.vertices;
	}

	/**
	 * This method returns the number of edges in the graph.
	 * 
	 * @return
	 */
	public int noOfEdges() {
		return E;
	}
}
