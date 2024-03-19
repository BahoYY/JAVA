package Paris_metro;
import java.util.Iterator;


public class Vertex<T> implements VertexInterface<T>{
    private T label;
    private ListWithIteratorInterface<Edge> edgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double cost;

    public Vertex(T vertexLabel){
        label=vertexLabel;
        edgeList= new LinkedListWithIterator<>();
        visited=false;
        previousVertex=null;
        cost=0;
    }

    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public void visit() {
        visited = true;
    }

    @Override
    public void unvisit() {
        visited = false;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    public boolean connect(VertexInterface<T> endVertex, double edgeWeight)
    {
        boolean result = false;

        if (!this.equals(endVertex))
        {  // Vertices are distinct
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;

            while (!duplicateEdge && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            } // end while

            if (!duplicateEdge)
            {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            } // end if
        } // end if

        return result;
    } // end connect

    public boolean connect(VertexInterface<T> endVertex)
    {
        return connect(endVertex, 0);
    } // end connect

    @Override
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        Iterator<Edge> edgeIterator = edgeList.getIterator();
        return new Iterator<VertexInterface<T>>() {
            @Override
            public boolean hasNext() {
                return edgeIterator.hasNext();
            }

            @Override
            public VertexInterface<T> next() {
                return edgeIterator.next().getEndVertex();
            }
        };
    }

    @Override
    public Iterator<Double> getWeightIterator() {
        Iterator<Edge> edgeIterator = edgeList.getIterator();
        return new Iterator<Double>() {
            @Override
            public boolean hasNext() {
                return edgeIterator.hasNext();
            }

            @Override
            public Double next() {
                return edgeIterator.next().getWeight();
            }
        };
    }

    @Override
    public boolean hasNeighbor() {
        return edgeList.getLength() > 0;
    }

    @Override
    public VertexInterface<T> getUnvisitedNeighbor() {
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext()) {
            VertexInterface<T> neighbor = neighbors.next();
            if (!neighbor.isVisited()) {
                return neighbor;
            }
        }
        return null;
    }

    @Override
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }

    @Override
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }

    @Override
    public boolean hasPredecessor() {
        return previousVertex != null;
    }

    @Override
    public void setCost(double newCost) {
        cost = newCost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    protected class Edge
    {
        private VertexInterface<T> vertex;
        private double weight;
        protected Edge(VertexInterface<T> endVertex,double edgeWeight){
            vertex=endVertex;
            weight=edgeWeight;
        }
        protected VertexInterface<T> getEndVertex(){
            return vertex;
        }
        protected double getWeight(){
            return weight;

        }
    }
}
