import java.io.*;
import java.util.*;

class Hash{
    public Hashtable d;
    public final int N = 100000;
    public final String fileName = "HashInt.txt";
    public int[] a = new int[N];

    Hash() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String str;
        int k = 0;
        while((str = br.readLine()) != null){
            a[k++] = Integer.parseInt(str);
        }
        br.close();
    }

    public int find(int sum){
        d = new Hashtable();
        for (int i=0; i<a.length; i++){
            if(d.containsKey(new Integer(a[i])) == true) continue;
            if(d.containsKey(new Integer(sum - a[i])) == true) return 1;
            d.put(new Integer(a[i]), new Integer(sum - a[i]));
        }
        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Hash h = new Hash();
        int[] sums = {231552,234756,596873,648219,726312,981237,988331,1277361,1283379};
        for (int i : sums) System.out.print(h.find(i));
        System.out.println();
    }
}
