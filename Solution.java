import java.util.*;

public class Solution{
    public static void findMinimumWeightedVertexCoverApprox(ArrayList<Edge> graph, int[] weights){
        //Build String array of integer vertex names if no string names are provided
        String[] integerNames = new String[weights.length];
        for(int i=0; i<weights.length; i++){
            integerNames[i] = i+"";
        }
        findMinimumWeightedVertexCoverApprox(graph, weights, integerNames);
    }

    public static void findMinimumWeightedVertexCoverApprox(ArrayList<Edge> graph, int[] weights, String[] vertexNames){
        int[] remainingWeights = Arrays.copyOf(weights, weights.length);

        ArrayList<String> vertexCoverNodes = new ArrayList<String>();
        int totalWeight = 0;

        for(Edge edge : graph){
            int fromVertex = edge.fromVertex;
            int toVertex = edge.toVertex;
            if(remainingWeights[fromVertex]==0 || remainingWeights[toVertex]==0){		//skip edges if either vertex is already tight
                continue;
            }

            if(remainingWeights[fromVertex] < remainingWeights[toVertex]){		//fromVertex weight is smaller
                int smallerWeight = remainingWeights[fromVertex];
                edge.pricePaid = smallerWeight;
                remainingWeights[fromVertex] = 0;	//1 vertex becomes tight (greedy)
                remainingWeights[toVertex] -= smallerWeight;
                totalWeight += weights[fromVertex];
                vertexCoverNodes.add(vertexNames[fromVertex]);
            }
            else{		//toVertex weight is smaller or they're equal
                int smallerWeight = remainingWeights[toVertex];
                edge.pricePaid = smallerWeight;
                remainingWeights[toVertex] = 0;		//1 vertex becomes tight (greedy)
                remainingWeights[fromVertex] -= smallerWeight;
                totalWeight += weights[toVertex];
                vertexCoverNodes.add(vertexNames[toVertex]);
            }
//            System.out.println("Chose Edge "+edge);
        }

        System.out.println(totalWeight);
        for(int i=0;i<vertexCoverNodes.size();i++)
            System.out.print(vertexCoverNodes.get(i)+" ");
    }



    public static void main(String args[]){
        Scanner scanner= new Scanner(System.in);
        int N= scanner.nextInt();
        int E= scanner.nextInt();
        int weights[]=new int[N];
        String vertexName[]= new String[N];
        for(int i=0;i<N;i++){
            weights[i]=scanner.nextInt();
            vertexName[i]=Integer.toString(i);
        }
        ArrayList<Edge> graph = new ArrayList<Edge>();
        for(int i=0;i<E;i++){
            int from=scanner.nextInt();
            int to=scanner.nextInt();
            graph.add(new Edge(from,to,vertexName));
        }
        Solution.findMinimumWeightedVertexCoverApprox(graph, weights, vertexName);
        //Optimal = a, c (Total weight = 4+5 = 9)

    }
}

class Edge{
    public int fromVertex;
    public int toVertex;
    public int pricePaid = 0;

    private String[] vertexNames;

    public Edge(int fromVertex, int toVertex){
        this.fromVertex=fromVertex;
        this.toVertex=toVertex;
    }

    public Edge(int fromVertex, int toVertex, String[] vertexNames){
        this.fromVertex=fromVertex;
        this.toVertex=toVertex;
        this.vertexNames=vertexNames;
    }

    @Override
    public String toString(){
        if(vertexNames !=null){
            return "("+vertexNames[fromVertex]+", "+vertexNames[toVertex]+") [Price paid="+pricePaid+"]";
        }
        return toStringAsInt();
    }

    public String toStringAsInt(){
        return "("+fromVertex+", "+toVertex+") [Price paid="+pricePaid+"]";
    }
}