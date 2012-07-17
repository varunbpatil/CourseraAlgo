import java.io.*;
import java.util.*;

class Median{
    public static int median(int[] a, int i){
        System.out.println(i);
        int b[] = new int[i+1];
        for(int j = 0; j < i+1; j++) b[j] = a[j];
        for(int j=1; j < i+1; j++)
            for(int k=0; k<i+1-j; k++)
                if (b[k] > b[k+1]){
                    int temp = b[k+1];
                    b[k+1] = b[k];
                    b[k] = temp;
                }
        if (i%2 == 0){
            return(b[i/2]);
        }
        else return(b[(i+1)/2 - 1]);
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("Median.txt"));
        int a[] = new int[10000];
        String str; int sum = 0, i = 0;
        while((str = br.readLine())!=null) a[i++] = Integer.parseInt(str);
        for (i = 0; i < 10000; i++){
            sum += median(a, i);        
        }
        System.out.println(sum%10000);
    }
}
