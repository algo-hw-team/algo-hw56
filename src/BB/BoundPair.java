package BB;

public class BoundPair implements Comparable<BoundPair>{
	public int id;
	public double bound;
	
	public BoundPair(int id, double bound) {
		this.id = id;
		this.bound = bound;
	}
	
	@Override
	public int compareTo(BoundPair o) {
		BoundPair e1 = (BoundPair)o;
        if(e1.bound == this.bound)
            return 0;
        return e1.bound < this.bound ? 1 : -1;
	}
}
