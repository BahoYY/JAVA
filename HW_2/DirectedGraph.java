package HW_2;
import java.util.*;


public class DirectedGraph<T> implements GraphInterface<T> {
    public DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph() {
        vertices= new UnsortedLinkedDictionary<>();
        edgeCount = 0;
    }

    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<T>(vertexLabel));
        return addOutcome == null;
    }

    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            result = beginVertex.connect(endVertex, edgeWeight);
        }
        if (result)
            edgeCount++;
        return result;
    }

    @Override
    public boolean addEdge(T begin, T end) {
        return false;
    }

    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            }
        }
        return found;
    }
    public boolean isEmpty(){
        return vertices.isEmpty();
    }
    public void clear(){
        vertices.clear();
        edgeCount=0;
    }





    public int getNumberOfVertices(){
        return vertices.getSize();
    }
    public int getNumberOfEdges(){
        return edgeCount;
    }

    @Override
    public QueueInterface<T> getBreadthFirstTraversal(T origin, T end) {
        resetVertices();
        boolean isDestinationFound = false;
        QueueInterface<T> traversalOrder = new LinkedQueue<>(); // Queue of vertex labels
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>(); // Queue of Vertex objects

        VertexInterface<T> originVertex = vertices.getValue(origin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        originVertex.visit();

        traversalOrder.enqueue(origin);    // Enqueue vertex label
        vertexQueue.enqueue(originVertex); // Enqueue vertex

        while (!isDestinationFound && !vertexQueue.isEmpty()) {
            VertexInterface<T> currentVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = currentVertex.getNeighborIterator();

            while (!isDestinationFound && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel()); // Enqueue vertex label
                    vertexQueue.enqueue(nextNeighbor);
                } // end if
                if (nextNeighbor.equals(endVertex))
                    isDestinationFound = true;
            } // end while
        } // end while

        return traversalOrder;
    }


    @Override
    public QueueInterface<T> getDepthFirstTraversal(T origin, T end) {
        resetVertices();
        boolean isDestinationFound=false;

        QueueInterface<T> travelsalOrder= new LinkedQueue<>();
        StackInterface<VertexInterface<T>> vertexStack= new LinkedStack<>();

        VertexInterface<T> originVertex = vertices.getValue(origin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        originVertex.visit();
        travelsalOrder.enqueue(end);
        vertexStack.push(originVertex);

        while(!isDestinationFound && !vertexStack.isEmpty()){
            VertexInterface<T> currentVertex= vertexStack.pop();

            Iterator<VertexInterface<T>> neighbors= currentVertex.getNeighborIterator();
            travelsalOrder.enqueue(currentVertex.getLabel());

            while(!isDestinationFound && neighbors.hasNext()){
                VertexInterface<T> nextNeighbor = neighbors.next();

                if(!nextNeighbor.isVisited()){
                    nextNeighbor.visit();
                    vertexStack.push(nextNeighbor);
                }
                if(nextNeighbor.equals(endVertex))
                    isDestinationFound=true;
            }
        }
        return travelsalOrder;
    }

    @Override
    public StackInterface<T> getTopologicalOrder(T origin) {
        resetVertices();

        StackInterface<T> vertexStack = new LinkedStack<>();
        int numberOfVertices = getNumberOfVertices();
        for (int counter = 1; counter <= numberOfVertices; counter++)
        {
            VertexInterface<T> nextVertex = findTerminal();
            nextVertex.visit();
            vertexStack.push(nextVertex.getLabel());
        } // end for

        return vertexStack;
    }

    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean flag= false;

        QueueInterface<VertexInterface<T>> vertexQueue= new LinkedQueue<VertexInterface<T>>();

        VertexInterface<T> originVertex= vertices.getValue(begin);
        VertexInterface<T> endVertex= vertices.getValue(end);
        originVertex.visit();
        vertexQueue.enqueue(originVertex);

        while(!flag && !vertexQueue.isEmpty()){
            VertexInterface<T> frontVertex= vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors= frontVertex.getNeighborIterator();

            while(!flag && neighbors.hasNext()){
                VertexInterface<T> nextNeighbor= neighbors.next();
                if(!nextNeighbor.isVisited()){
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1+frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                }
                if(nextNeighbor.equals(endVertex))
                    flag=true;
            }
        }
        int pathLength = (int)endVertex.getCost() + 1;
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        int vertexCounter = 0;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
            vertexCounter++;
        }

        System.out.println("Stations: ");
        System.out.println();
        while (!path.isEmpty()){
            System.out.println(path.pop());
        }

        //printPath(path); // printing path
        return pathLength;
    }
    @Override
    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        resetVertices(); // reset all vertices before starting
        boolean flag = false; // to stop searching if exit spotted
        // using priority queue to get cheapest path
        PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<>();
        // converting start and end position those user gave as parameters to vertex
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        priorityQueue.add(new EntryPQ(originVertex, 0, null));
        // Start traversal
        while (!flag && !priorityQueue.isEmpty()) {
            EntryPQ frontEntry =  priorityQueue.remove();
            VertexInterface<T> frontVertex = frontEntry.vertex;
            //check current vertex
            if (!frontVertex.isVisited()) {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost()); // sum weights
                frontVertex.setPredecessor(frontEntry.getPredecessor());
                // if exit founded
                if (frontVertex.equals(endVertex)) {
                    flag = true;
                }
                else {
                    //creating iterator for current neighbors and weights
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> weights = frontVertex.getWeightIterator();
                    frontVertex.getCost();
                    // walking in neighbors
                    while (neighbors.hasNext()) {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        double weightOfEdgeToNeighbor = weights.next();
                        if (!nextNeighbor.isVisited()) {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
                            priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        }
                    }
                }
            }
        }
        // storing the path
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        int vertexCounter = 0;
        while (vertex.hasPredecessor()) {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
            vertexCounter++;
        }

        System.out.println("Stations: ");
        System.out.println();
        while (!path.isEmpty()){
            System.out.println(path.pop());
        }
        //printPath(path); // printing path
        System.out.print(vertexCounter + 1 + " Station\n");
        return pathCost;
    }

    protected void resetVertices()
    {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    private class EntryPQ implements Comparable<EntryPQ>
    {
        private VertexInterface<T> vertex;
        private VertexInterface<T> previousVertex;
        private double cost; // cost to nextVertex

        private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
        {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        } // end constructor

        public VertexInterface<T> getVertex()
        {
            return vertex;
        } // end getVertex

        public VertexInterface<T> getPredecessor()
        {
            return previousVertex;
        } // end getPredecessor

        public double getCost()
        {
            return cost;
        } // end getCost

        public int compareTo(EntryPQ otherEntry)
        {
            // Using opposite of reality since our priority queue uses a maxHeap;
            // could revise using a minheap
            return (int)Math.signum(otherEntry.cost - cost);
        } // end compareTo

        public String toString()
        {
            return vertex.toString() + " " + cost;
        } // end toString
    } // end EntryPQ
    protected VertexInterface<T> findTerminal()
    {
        boolean found = false;
        VertexInterface<T> result = null;

        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

        while (!found && vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();

            // If nextVertex is unvisited AND has only visited neighbors)
            if (!nextVertex.isVisited())
            {
                if (nextVertex.getUnvisitedNeighbor() == null )
                {
                    found = true;
                    result = nextVertex;
                } // end if
            } // end if
        } // end while

        return result;
    } // end findTerminal




}
