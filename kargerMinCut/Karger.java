import java.io.*;
import java.util.*;

class Karger{
    public static void main(String args[]) throws FileNotFoundException, IOException {

        long startTime = System.currentTimeMillis();
        final int SIZE = 200;
        int mat[][] = new int[SIZE][SIZE];
        int i = 0, j = 0, k = 0, n = SIZE;
        long crossover = 99999999;
        String str;
        StringTokenizer st;
        Random generator = new Random(); // used to generate random numbers
        BufferedReader f; 

        
        for(int z=0; z<210000; z++){        /* perform the whole algorithm 210000 times to get correct answer */

            System.out.print("Executing " + z + "/210000 iterations\r");

            /* a new adjacency matrix must be built each time */
            f = new BufferedReader(new FileReader("kargerMinCut.txt"));

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
            /* close the file each time */
            f.close();


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

            /* store only the smallest mincut value in 210000 iterations */
            if (crossover > mat[i][k])  crossover = mat[i][k];
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\n\n\nMinCut =  " + crossover);

        System.out.println("\n\nExecution Time =  "+ ((float)(endTime-startTime)/1000) + " seconds\n\n\n");

    }
}
