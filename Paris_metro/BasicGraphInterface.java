package Paris_metro;

public interface BasicGraphInterface <T>{

    public boolean addVertex(T vertexLabel);
    public boolean addEdge(T begin, T end, double edgeWeight);
    public boolean addEdge(T begin, T end);
    public boolean hasEdge(T begin, T end);
    public boolean isEmpty();
    public double getCheapestPath(T begin, T end, StackInterface<T> path);
    public int getShortestPath(T begin, T end, StackInterface<T> path);
    public int getNumberOfVertices();

    public int getNumberOfEdges();
    public void clear();
}