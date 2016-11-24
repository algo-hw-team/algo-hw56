package BB;

class Edge implements Comparable<Edge>
{
    int v1,v2;
    double distance; 

    Edge(int v1, int v2, double distance)
    {
        this.v1=v1;
        this.v2=v2;
        this.distance=distance;
    }

    @Override
    public int compareTo(Edge o) {
        Edge e1 = (Edge)o;
        if(e1.distance==this.distance)
            return 0;
        return e1.distance < this.distance ? 1 : -1;
    }
    
}