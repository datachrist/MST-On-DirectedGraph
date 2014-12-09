 // Project Member : Vaishali Shah & Shabad Sehgal

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {	

	private static int deltaWeight = 0;

	/**
	 * main driver method
	 * */
	public static void main(String[] args) {
		int tVertices = 0;
		int tEdges = 0;
		int source = 0;
		boolean isMST= false;

		File inFile = new File("p7-in1.txt");
		try {

			Scanner sc = new Scanner(inFile);
			tVertices=sc.nextInt();
			tEdges=sc.nextInt();
			source=sc.nextInt();
			Integer[] values = new Integer[tVertices];
			
			for(int i=0;i<tVertices;i++){
				values[i]=i+1;
			}
			Graph<Integer> G = new Graph<Integer>(values.length, values);
			while (sc.hasNext()) {
				G.addEdge(sc.nextInt(),sc.nextInt(), sc.nextInt());
			}
			for (int i = 0; i < tVertices; i++) {
				values[i] = i + 1;
			}
			int counter = tVertices;
			// Associating zero weights and saving into new array
			ArrayList<Vertex<Integer>> vertices = null;

			Map<Vertex<Integer>, ArrayList<Vertex<Integer>>> globalCycleList = new HashMap<Vertex<Integer>, ArrayList<Vertex<Integer>>>();
			//Starting timer!!
			long start = System.currentTimeMillis();
			while (true) {

				vertices = G.vertices();
				for (int i = 1; i < vertices.size(); i++) {
					
					// reinitialize the visit flag for eack vertex
					vertices.get(i).setVisited(false);

					// for vertex source
					if (vertices.get(i).getValue().intValue() == source) {						
						continue;
					}

					// calling method to find minWeight Incoming Edge in a
					// vertex and decreasing  weight for each incoming Edge
					deltaWeight += G.findMinWeightEdge(vertices.get(i), source);
				}
				
				//obtaining Source Vertex from source Value
				Vertex<Integer> sourceVertex = null;
				for (int i = 1; i < vertices.size(); i++) {
					if (vertices.get(i).getValue() == source) {
						sourceVertex = vertices.get(i);
						break;
					}
				}

				// checking if there it is MST or not
				// running dfs for outgoing zero weight edge
			
				// vertexList contains list of vertices which is reachable by source vertex
				ArrayList<Vertex<Integer>> vertexList = new ArrayList<Vertex<Integer>>();
				dfs_MST(sourceVertex, vertexList);

				 if (vertexList.size() == vertices.size() - 1) {
					isMST= true;
					break;
				}

				// find array vertex list having cycle				

				ArrayList<Vertex<Integer>> cycleList = null;
				for (int i = 1; i < vertices.size(); i++) {
					if (!vertexList.contains(vertices.get(i))) {
						cycleList = dfs_CycleList(vertices.get(i));
						break;
					}
				}

				// now shrinking cycle and adding to original MST
				Vertex<Integer> newVertex = new Vertex<Integer>(++counter);
				G.addVertex(newVertex);
				for (int i = 1; i < G.vertices().size(); i++) {
					Vertex<Integer> V = G.vertices().get(i);
					for (Edge<Vertex<Integer>> edge : V.adjacencyList()) {

						if (edge.geteType() == 'o')
							if (cycleList.contains(edge.to()) && !cycleList.contains(edge.from())) {
								G.addEdge(edge.getU().getValue(), newVertex.getValue(), edge.getW());
								G.removeCycleEdge(edge, true);
							} else if (cycleList.contains(edge.from()) && !cycleList.contains(edge.to())) {
								G.addEdge(newVertex.getValue(), edge.getV().getValue(), edge.getW());
								G.removeCycleEdge(edge, false);
							}
					}
				}

				// removing cycle vertices from graph
				for (Vertex<Integer> cycle : cycleList)
					G.removeVertex(cycle);

				// maintaining all cycles in hashmap
				globalCycleList.put(newVertex, cycleList);

				
				

			}
			
			// Since we found out Mst of shrink graph
			// We need to expand the graph
			
			if(!globalCycleList.isEmpty()){
			
			while (true) {
				int i = 1;
				
				
				// if Final MST is Found
				if (G.vertices().size() - 1 == tVertices) {			
					
					isMST=true;
					break;
				}
				
				// for each new Vertex added  
				for (i = 1; i < G.vertices().size(); i++) {
					if (globalCycleList.containsKey(G.vertices().get(i))) {
						break;
					}

				}
				//  For each new vertex added , finding its cycle list
				ArrayList<Vertex<Integer>> expandedCycleList = globalCycleList.get(G.vertices().get(i));

				// adding cycle vertices from graph
				for (Vertex<Integer> oldvertex : expandedCycleList)
					G.addVertex(oldvertex);

				// removing new Vertex, new vertex Edges and adding old vertex edges
				for (Edge<Vertex<Integer>> edge : G.vertices().get(i).adjacencyList()) {
					ArrayList<Edge<Vertex<Integer>>> minWeightEdges = new ArrayList<Edge<Vertex<Integer>>>();
					if ((edge.weight() == 0) && (edge.geteType() == 'i')) {
						for (Vertex<Integer> expandVertex : expandedCycleList) {			

							for (Edge<Vertex<Integer>> cycleEdge : expandVertex.adjacencyList()) {
								if (((cycleEdge.geteType() == 'i')) && (cycleEdge.to().equals(edge.to()))) {
									minWeightEdges.add(cycleEdge);

								}
							}

						}
						int minweight = Integer.MAX_VALUE;
						Edge<Vertex<Integer>> edgeTemp = null;
						for (Edge<Vertex<Integer>> minEdge : minWeightEdges) {
							if (minweight > minEdge.weight()) {
								minweight = minEdge.weight();
								edgeTemp = minEdge;
							}
						}
						if (edgeTemp != null && edgeTemp.from() != null)
							for (Edge<Vertex<Integer>> removingEdge : edgeTemp.from().adjacencyList()) {
								if (removingEdge.geteType() == 'i' && removingEdge.weight() == 0) {

									if (globalCycleList.containsKey(removingEdge.to())) {
										for (Vertex<Integer> cycleVertex : globalCycleList.get(removingEdge.to())) {

											for (Edge<Vertex<Integer>> cycleEdge : cycleVertex.adjacencyList()) {
												if (removingEdge.from().equals(cycleEdge.to())
														&& (cycleEdge.geteType() == 'o') && (cycleEdge.weight() == 0))
													cycleVertex.adjacencyList().remove(cycleEdge);

											}
										}
									}
									G.removeEdge(removingEdge);
									
									// adding/updating edge to graph
									G.addEdge(edgeTemp.to().getValue(), edgeTemp.from().getValue(), edge.weight());
									break;
								}								
							}
					}

				}
				
				for (Edge<Vertex<Integer>> edge : G.vertices().get(i).adjacencyList()) {

					// removing each edges in shrink vertex					
					G.removeEdge(edge);
				}
				G.removeVertex(G.vertices().get(i));

			}
			}
			
			// Final MST priting with given way of output
			if(isMST){
				long last = System.currentTimeMillis();
				// printing total weight and time consumed
				System.out.println(deltaWeight+" "+(last-start));
				//printing MST 
				if(G.vertices().size()<=51){
				for (int count = 1; count < G.vertices().size(); count++) {

					for (Edge<Vertex<Integer>> edge : G.vertices().get(count).adjacencyList()) {
						if (edge.geteType() == 'o' && edge.weight() == 0){
							System.out.println(edge.from() + "  " + edge.to());
						break;
						}
					}
				}
			}
				
			
			
			}

		} catch (Exception e) {
			e.printStackTrace();			
		}

	}

	/**
	 * This method is used to run DFS on resultin Graph
	 * 
	 * */
	public static void dfs_MST(Vertex<Integer> v, ArrayList<Vertex<Integer>> vertexList) {
		v.setVisited(true);
		vertexList.add(v);	

		for (Edge<Vertex<Integer>> edge : v.adjacencyList()) {
			if ((edge.geteType() == 'o') && (edge.weight() == 0)) {
				Vertex<Integer> u = edge.to();
				if (!u.isVisited()) {
					dfs_MST(u, vertexList);
				}
			}

		}

	}

	

	/**
	 * THis method is used to find vertices of cycle and generate cyclelist
	 * 
	 * */
	public static ArrayList<Vertex<Integer>> dfs_CycleList(Vertex<Integer> v) {	
		Vertex<Integer> vertexcheck = v;
		ArrayList<Vertex<Integer>> cycleListtemp = new ArrayList<Vertex<Integer>>();
		ArrayList<Vertex<Integer>> cycleList = new ArrayList<Vertex<Integer>>();
		cycleListtemp.add(vertexcheck);
		boolean flag = false;
		while (!flag) {
			vertexcheck.setVisited(true);
			for (Edge<Vertex<Integer>> edge : vertexcheck.adjacencyList()) {

				if (edge.geteType() == 'i' && edge.weight() == 0) {
					Vertex<Integer> vOut = edge.to();
					if (!vOut.isVisited()) {
						// dfs_MST(vOut);
						v.setVisited(true);
						cycleListtemp.add(vOut);
						vertexcheck = vOut;
					} else {
						flag = true;
						vertexcheck = vOut;
						break;
					}

				}
			}

		}
		for (int j = 0; j < cycleListtemp.size(); j++) {
			if (cycleListtemp.get(j).equals(vertexcheck)) {

				for (int k = j; k < cycleListtemp.size(); k++) {
					cycleList.add(cycleListtemp.get(k));
				}
				break;
			}

		}
		return cycleList;
	}

}
