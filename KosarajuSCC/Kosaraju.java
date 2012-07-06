import java.io.*;
import java.util.*;

class Kosaraju{

    public final String fileName = "SCC.txt";
    public final int N = 875714;
    public ArrayList<Integer>[] g = new ArrayList[N];
    public ArrayList<Integer>[] gRev = new ArrayList[N];
    public int[] visited = new int[N];
    public int[] finishingTime = new int[N];
    public int[] leader = new int[N];
    public int ft = 0, lead = 0;


    Kosaraju() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String str;
        StringTokenizer st;
        for (int i=0; i<N; i++){
            g[i] = new ArrayList<Integer>(1);
            gRev[i] = new ArrayList<Integer>(1);
            visited[i] = 0;
        }
        while((str = br.readLine()) != null){
            st = new StringTokenizer(str);
            Integer i = Integer.valueOf(st.nextToken());
            Integer j = Integer.valueOf(st.nextToken());
            g[i-1].add(j-1);
            gRev[j-1].add(i-1);
        }
        br.close();
    }


    public void dfs(int i, ArrayList<Integer>[] graph){
        visited[i] = 1; 
        leader[i] = lead;
        for(Integer j : graph[i]){
            if(visited[j.intValue()] == 0) dfs(j.intValue(), graph);
        }
        finishingTime[i] = ++ft;
    }


    public void dfsLoop(int[] nodes, ArrayList<Integer>[] graph, int pass){
        for (int k=N-1; k>=0; k--){
            int i = k;  // for pass 1, the nodes are simply processed from last to first
            if(pass==2) i = nodes[k];   // for pass 2 the nodes are processed starting with the one with highest finishing time
            if(visited[i] == 0) {
                lead = i; 
                dfs(i, graph);
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("loading graph...");
        Kosaraju kos = new Kosaraju();

        System.out.println("1st pass...");
        kos.dfsLoop(null, kos.gRev, 1);

        /* reset the visited[] for the second pass */
        for (int i=0; i<kos.N; i++) kos.visited[i] = 0;
        int[] nodes = new int[kos.N];
        /* store the nodes in increasing order of their finishing times */
        for (int i=0; i<kos.N; i++) nodes[kos.finishingTime[i]-1] = i;
        System.out.println("2nd pass...");
        kos.dfsLoop(nodes, kos.g, 2);

        System.out.print("The sizes of the 5 largest SCC's are : ");
        Arrays.sort(kos.leader);
        ArrayList<Integer> stat = new ArrayList<Integer>(1);
        int pre = 0;
        for (int i=0; i<kos.N-1; i++){
            if (kos.leader[i] != kos.leader[i+1]){
                stat.add(new Integer(i-pre+1));
                pre = i+1;
            }
        }
        stat.add(new Integer(kos.N-pre));
        Integer[] s1 = stat.toArray(new Integer[stat.size()]);
        int[] s = new int[s1.length];
        for (int i=0; i<s1.length; i++) s[i] = s1[i].intValue();
        Arrays.sort(s);
        int i = s.length-1, j = 0;
        /* print only the top 5 largest SCC's */
        for (; j<5; j++,i--) System.out.print(s[i]+" ");
        System.out.println();
    }
}
