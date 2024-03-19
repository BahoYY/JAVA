package HW_2;
//import ADTPackage.*;



public interface GraphAlgorithmsInterface <T>{
    QueueInterface<T> getBreadthFirstTraversal(T origin, T end);

    QueueInterface<T> getDepthFirstTraversal(T origin, T end);

    StackInterface<T> getTopologicalOrder(T origin);

    int getShortestPath(T begin, T end, StackInterface<T> path);

    double getCheapestPath(T begin, T end, StackInterface<T> path);
}
