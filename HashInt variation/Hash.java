import java.io.*;
import java.util.*;

class Hash{
    public Hashtable d = new Hashtable();
    public final int N = 500000;
    public final String fileName = "HashInt.txt";
    public int[] a = new int[N];
    public int count = 0;

    Hash() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String str;
        int k = 0;

        // store all numbers in an array as well as hash them into a hash table
        while((str = br.readLine()) != null){
            a[k] = Integer.parseInt(str);
            d.put(new Integer(a[k]), new Integer(a[k]));
            k++;
        }
        br.close();
    }

    public void find(int sum){
        
        System.out.println(sum);
        // for each element "ele" in the array
        for (int i=0; i<a.length; i++){

            // if (sum-ele) is present in the hash table, you have found a match
            if(d.containsKey(new Integer(sum - a[i])) == true && a[i] != (sum - a[i])) {count++; break;}
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Hash h = new Hash();
        for (int i=2500; i<=4000; i++) h.find(i);
        System.out.println(h.count);
    }
}
