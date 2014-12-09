 // Project Member : Vaishali Shah & Shabad Sehgal


/**
 * This class provides an abstract data type for an Edge between any two
 * vertices of any type.
 * 
 * @author sxs140230 & vxs135730
 *
 * @param <T>
 *            Type of objects between which there is an edge.
 */
public class Edge<T> {
	/**
	 * This holds the source of this edge.
	 */
	private T u;
	
	/**
	 * This holds the destination of this edge.
	 */
	private T v;
	
	/**
	 * This holds the weight of the edge.
	 */
	private int w;

	/**
	 * This tells if it is incoming or outgoing.
	 */
	private char eType;
	/**
	 * This constructor initializes an Edge.
	 * @param from
	 * @param to
	 * @param w
	 * @param eType
	 */
	public Edge(T from, T to, int w,char eType) {
		this.u = from;
		this.v = to;
		this.w = w;
		this.eType = eType;
	}

	/**
	 * This method returns the start object of the edge.
	 * @return
	 */
	public T from() {
		return u;
	}

	/**
	 * This method returns the end object of the edge.
	 * @return
	 */
	public T to() {
		return v;
	}

	/**
	 * This method returns the weight of the edge.
	 * @return
	 */
	public int weight() {
		return w;
	}

	/**
	 * @return the eType
	 */
	public char geteType() {
		return eType;
	}

	/**
	 * @param eType the eType to set
	 */
	public void seteType(char eType) {
		this.eType = eType;
	}

	/**
	 * @return the u
	 */
	public T getU() {
		return u;
	}

	/**
	 * @param u the u to set
	 */
	public void setU(T u) {
		this.u = u;
	}

	/**
	 * @return the v
	 */
	public T getV() {
		return v;
	}

	/**
	 * @param v the v to set
	 */
	public void setV(T v) {
		this.v = v;
	}

	/**
	 * @return the w
	 */
	public int getW() {
		return w;
	}

	/**
	 * @param w the w to set
	 */
	public void setW(int w) {
		this.w = w;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eType;
		result = prime * result + ((u == null) ? 0 : u.hashCode());
		result = prime * result + ((v == null) ? 0 : v.hashCode());
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
		Edge other = (Edge) obj;
		if (eType != other.eType)
			return false;
		if (u == null) {
			if (other.u != null)
				return false;
		} else if (!u.equals(other.u))
			return false;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		return true;
	}



}
