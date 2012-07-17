import java.io.*;
import java.util.*;

class Dijkstra{
    public static void main(String[] args) throws IOException, FileNotFoundException {

        final int N = 200; // number of vertices in the graph
        BufferedReader br = new BufferedReader(new FileReader("dijkstraData.txt"));
        int matrix[][] = new int[N][N];
        for (int i=0; i<N; i++)
            for (int j=0; j<N; j++) 
                matrix[i][j] = 0;

        int computed[] = new int[N]; // whether the shortest distance to a vertex has already been computed or not
        int dijk[] = new int[N];     // the shortest distance from source to each vertex

        String line, str;
        StringTokenizer st1, st2;

        // store the graph as a adjacency matrix
        while((line = br.readLine()) != null){
            st1 = new StringTokenizer(line);
            int i = Integer.parseInt(st1.nextToken());
            while(st1.hasMoreTokens()){
                str = st1.nextToken();
                st2 = new StringTokenizer(str, ",");
                int v = Integer.parseInt(st2.nextToken());
                int d = Integer.parseInt(st2.nextToken());
                matrix[i-1][v-1] = d;
                matrix[v-1][i-1] = d;
            }
        }

        // initially only shortest distance for source vertex is known and the shortest distance is always 0
        for (int i=0; i<N; i++){
            computed[i] = 0;
            dijk[i] = 1000000;
        }
        computed[0] = 1;
        dijk[0] = 0;


        // execute 200-1 times so that shortest distance to all vertices are computed
        for (int i=0; i<N-1; i++){
            int min = 1000000;
            int min_start = 0;
            int min_end = 0;

            // for each vertex 'v' which already has a shortest distance computed...
            for (int v=0; v<N; v++){
                if (computed[v] == 1){
                    // for each vertex 'z' to which the shortest distance has not yet been computed...
                    for (int z=0 ; z<N; z++){
                        if (computed[z] == 0 && matrix[v][z] != 0){
                            int a = dijk[v] + matrix[v][z];
                            // compute the minimum value of (dijk[v] + matrix[v][z])
                            if (a < min){
                                min = a;
                                min_start = v;
                                min_end = z;
                            }
                        }
                    }
                }
            }

            // change the computed[] and dijk[] arrays to include the one new vertex to which the shortest distance is calculated
            // in this iteration... i.e, one new vertex completed in one iteration.
            computed[min_end] = 1;
            dijk[min_end] = dijk[min_start] + matrix[min_start][min_end];
        }

        int t[] = {7,37,59,82,99,115,133,165,188,197};
        for (int i : t) System.out.print(dijk[i-1] + " ");
        System.out.println();
    }
}
