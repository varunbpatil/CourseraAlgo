import java.io.*;
import java.util.*;

class Globals{
    public static int crossover[] = new int[210000];
}

class Fast implements Runnable{
    int iteration;
    final int SIZE = 200;
    int[][] mat = new int[SIZE][SIZE];
    int i,j,k,n;
    static Globals g = new Globals();
    Random generator = new Random(); // used to generate random numbers
    public Thread t;

    Fast(int z, int[][] mat1){  /* arrays are passed by reference. Make a copy if you don't want to alter the original array */
        n = SIZE;

        /* making a copy of the array passed by reference */
        for (i=0;i<200;i++)
            for (j=0;j<200;j++)
                mat[i][j] = mat1[i][j];

        iteration = z;
        t = new Thread(this);
        t.start();
    }

    public void run(){
        n = SIZE;
        while(n>2){ /* stop the algorithm when there are only two vertices left */

            /* generate two random vertices, or one edge */
            do {
                i = generator.nextInt(SIZE);
                j = generator.nextInt(SIZE);
            } while(mat[i][j] <= 0);

            /* fuse the two vertices i and j */
            mat[i][j] = 0; 
            mat[j][i] = 0;

            /* modify other matrix elements according to the vertices that are fused */
            for (k=0; k<SIZE; k++){
                mat[i][k] = mat[i][k] + mat[j][k];
                mat[k][i] = mat[i][k];
                mat[j][k] = mat[k][j] = 0;
            }
            n--;
        }

        /* get the mincut value */
        for (k=0; k<SIZE; k++){
            if (mat[i][k] > 0) break;
        }

        g.crossover[iteration] = mat[i][k];

        /* free the memory allocated to the array */
        mat = null;
    }
}

class Karger{
    public static void main(String args[]) throws FileNotFoundException, IOException, InterruptedException {


        /* create a StringBuffer with an initial string of length 70 for the progress bar */
        char[] bytes = new char[70];
        Arrays.fill(bytes, ' ');
        String s1 = new String(bytes);
        StringBuffer sb = new StringBuffer(s1);
        /* end of progress bar */

        final int SIZE = 200;
        int mat[][] = new int[SIZE][SIZE];
        int i = 0, j = 0, k = 0, n = SIZE;
        String str;
        StringTokenizer st;
        BufferedReader f = new BufferedReader(new FileReader("kargerMinCut.txt"));
        Globals g = new Globals();

        /* initialize all elements of matrix to 0 */
        for (i = 0; i < SIZE; i++)
            for (j = 0; j < SIZE; j++)
                mat[i][j] = 0;

        /* read a line of input, tokenize it, and put it into an adjacency matrix */
        while ((str = f.readLine()) != null){
            st = new StringTokenizer(str);
            i = Integer.parseInt(st.nextToken());
            i--;
            while (st.hasMoreTokens()){
                j = Integer.parseInt(st.nextToken());
                j--;
                mat[i][j] = 1;
            }
        }
        f.close();

        Fast ob[] = new Fast[210000]; 
        int c = 1, d = 0;
        System.out.println("\n");

        /* set the start and end brackets for the progress bar carefully */
        sb.setCharAt(0,'[');
        sb.setCharAt(52,']');
       
        /* start timing the program */
        long startTime = System.currentTimeMillis();

        for(int z=0; z<210000; z++){        /* perform the whole algorithm 210000 times to get correct answer */

            //System.out.print("executing " + z + "/210000 iterations\r");

            /* progress bar */
            if (z % 4200 == 0 && z != 0){
                sb.setCharAt(c,'=');
                sb.setCharAt(c+1,'>');
                c++;
            }
            if (z % 2100 == 0){
                String pc = d + " %";
                d++;
                sb.replace(54,65,pc);
            }
            System.out.print(sb + "\r");
            /* end of progress bar */

            /* make a copy of the original adjacency matrix each time */
            ob[z] = new Fast(z, mat); /* calls 2-arg constructor. executes the run() function */

            /* ob.t.join();  NOTE: u cannot put join() here coz it will essentially wait for one thread to complete before 
                             starting another thread */

        }

        sb.setCharAt(c,'=');
        sb.setCharAt(c+1, '>');
        String pc = d + " %";
        sb.replace(54,65,pc);
        System.out.print(sb + "\r");


        /* free the memory allocated to the array */
        mat = null;

        /* wait for all 210000 threads to finish execution */
        for (i=0;i<210000;i++){
            ob[i].t.join();
        }

        /* find the smallest of 210000 elements in O(n) */
        int small = g.crossover[0];
        for (i=1;i<210000;i++){
            if (g.crossover[i] < small) small = g.crossover[i];
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\n\n\nMinCut =  " + small);
        
        System.out.println("\n\nExecution Time =  "+ ((float)(endTime-startTime)/1000) + " seconds\n\n\n");

    }
}
