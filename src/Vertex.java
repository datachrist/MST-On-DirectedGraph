 // Project Member : Vaishali Shah & Shabad Sehgal


/**
 * This class provides an abstract data type for a vertex.
 * 
 * @author sxs140230 & vxs135730
 *
 * @param <T>
 *            Type of data that vertex is holding.
 */
public class Vertex<T> {
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (this.obj == null) {
			if (other.obj != null)
				return false;
		} else if (!this.obj.equals(other.obj))
			return false;
		return true;
	}

	/**
	 * This holds the data that vertex is storing.
	 */
	private T obj;

	/**
	 * This holds the list of edges that starts from this vertex.
	 */
	private Bag<Edge<Vertex<T>>> adj;
	
	// Visited or not 
	private boolean isVisited = false;

	/**
	 * This constructor initializes the vertex with a value.
	 * 
	 * @param value
	 */
	public Vertex(T value) {
		this.obj = value;
		this.adj = new Bag<Edge<Vertex<T>>>();
	}

	/**
	 * This method returns the value the vertex is holding.
	 * 
	 * @return
	 */
	public T getValue() {
		return obj;
	}

	/**
	 * This method returns the list of edges which have this vertex has starting
	 * point.
	 * 
	 * @return
	 */
	public Bag<Edge<Vertex<T>>> adjacencyList() {
		return this.adj;
	}

	/**
	 * This method returns the string interpretation of the valur stored in the
	 * vertex.
	 */
	public String toString() {
		return obj.toString();
	}

	/**
	 * @return the isVisited
	 */
	public boolean isVisited() {
		return isVisited;
	}

	/**
	 * @param isVisited the isVisited to set
	 */
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	

}
