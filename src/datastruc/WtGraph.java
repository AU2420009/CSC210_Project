package datastruc;
import java.util.*;

class Vertex {
    String place;

    public Vertex(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex other = (Vertex) obj;
        return place.equals(other.place);
    }

    @Override
    public int hashCode(){
        return place.hashCode();
    }

    @Override
    public String toString() {
        return place;
    }
}

class AdjPair {
    public Vertex vertex;
    public int weight;

    public AdjPair(Vertex v, int w) {
        this.vertex = v;
        this.weight = w;
    }

    @Override
    public String toString() {
        return "(" + vertex + ", " + weight + ")";
    }
}

public class WtGraph {
    private Map<Vertex, List<AdjPair>> adjList;

    public WtGraph() {
        this.adjList = new HashMap<>();
    }

    public void addVertex(String place) {
        Vertex v = new Vertex(place);
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    public void removeVertex(String place) {
        Vertex v = new Vertex(place);

        // remove vertex from adjacency lists
        for (List<AdjPair> list : adjList.values()) {
            list.removeIf(pair -> pair.vertex.equals(v));
        }

        // remove vertex entry
        adjList.remove(v);
    }

    public void addEdge(String place1, String place2, int wt) {
        Vertex v1 = new Vertex(place1);
        Vertex v2 = new Vertex(place2);

        // add vertices if missing 
        adjList.putIfAbsent(v1, new ArrayList<>());
        adjList.putIfAbsent(v2, new ArrayList<>());

        adjList.get(v1).add(new AdjPair(v2, wt));
        adjList.get(v2).add(new AdjPair(v1, wt));
    }

    public void removeEdge(String place1, String place2) {
        Vertex v1 = new Vertex(place1);
        Vertex v2 = new Vertex(place2);

        List<AdjPair> list1 = adjList.get(v1);
        List<AdjPair> list2 = adjList.get(v2);

        if (list1 != null)
            list1.removeIf(pair -> pair.vertex.equals(v2));

        if (list2 != null)
            list2.removeIf(pair -> pair.vertex.equals(v1));
    }

    public void printGraph() {
        for (var entry : adjList.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }


    public List<String> dijkstra(String startPlace, String endPlace) {
    
        Vertex start = new Vertex(startPlace);
        Vertex end = new Vertex(endPlace);

        // dist map
        Map<Vertex, Integer> dist = new HashMap<>();
        // parent map to track path
        Map<Vertex, Vertex> prev = new HashMap<>();

        // prio queue: distance, vertex
        PriorityQueue<Vertex> pq = new PriorityQueue<>(
            Comparator.comparingInt(dist::get)
        );

        // init distance to INT_MAX (or whatever its called in java)
            for (Vertex v : adjList.keySet()) {
                dist.put(v, Integer.MAX_VALUE);
            }
            dist.put(start, 0);

            pq.add(start);

        while (!pq.isEmpty()) {
            Vertex current = pq.poll();

            // reached dest
            if (current.equals(end))
                break;

            List<AdjPair> neighbors = adjList.get(current);
            if (neighbors == null) continue;

            for (AdjPair adj : neighbors) {
                Vertex neighbor = adj.vertex;
                int weight = adj.weight;

                int newDist = dist.get(current) + weight;

                if (newDist < dist.get(neighbor)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current);
                    pq.remove(neighbor);  // update prio
                    pq.add(neighbor);
                }
            }
        }

        // get back path
        List<String> path = new LinkedList<>();
        Vertex curr = end;

        if (!prev.containsKey(curr) && !curr.equals(start)) {
            return null; // no path :(
        }

        while (curr != null) {
            path.add(0, curr.place);
            curr = prev.get(curr);
        }

        return path;
    }


    public int shortestDistance(String start, String end) {
        List<String> path = dijkstra(start, end);
        if (path == null) return Integer.MAX_VALUE;

        int dist = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex v1 = new Vertex(path.get(i));
            for (AdjPair adj : adjList.get(v1)) {
                if (adj.vertex.place.equals(path.get(i + 1))) {
                    dist += adj.weight;
                    break;
                }
            }
        }
        return dist;
    }

}

