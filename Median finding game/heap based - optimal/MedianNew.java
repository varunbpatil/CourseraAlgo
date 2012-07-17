import java.io.*;
import java.util.*;

class Maxheap {

	public int heap[] = new int[10000];
	public int n = 0, child, parent, child1, child2;

	public void insert(int value){
		heap[n] = value; // insert the new value in the last position of the array
		
		parent = (n+1)/2 - 1;
		if(parent < 0) {n++; return;} // if the only node present is the root, then return immediately
		child = n;

        // do bubble up if there are more than two nodes in the tree
		while(true){

            // only need to compare newly inserted node and its parent 
			if(heap[child] > heap[parent]){

                // swap the child and the parent if the child is greater than parent in case of max heap
				int temp = heap[child];
				heap[child] = heap[parent];
				heap[parent] = temp;

                // stop bubble up when the root is reached
				if(parent == 0) break;

                // update the child and parent. The previous parent becomes the new child
				child = parent;
				parent = (parent + 1)/2 - 1;
			}
			else break;
		}
		n++; 
	}

    // function which deletes the max element(or root) of the heap
	public int del(){

        // bring the last element to the first position of the array
		int ele = heap[0];
		heap[0] = heap[n-1];

		parent = 0;

		while(true){
			child1 = parent*2 + 1;
			child2 = parent*2 + 2;

            // condition to stop bubble down
			if(child1 > n-1 || child2 > n-1) break;

            // swap the parent with the largest of its two children
			if(heap[parent] < heap[child1] || heap[parent] < heap[child2]){
				if(heap[child1] > heap[child2]){
					int temp = heap[child1];
					heap[child1] = heap[parent];
					heap[parent] = temp;
					parent = child1; // update the new parent
				}
				else{
					int temp = heap[child2];
					heap[child2] = heap[parent];
					heap[parent] = temp;
					parent = child2; // update the new parent
				}
			}
			else break;
		}	
		n--;
		return ele; // return the max element that was deleted from the max heap(the root)
	}

    // function to simply return the max element of the heap(or root) without deleting it
	public int getMax(){
		return heap[0];
	} 
}

class Minheap {

	public int heap[] = new int[10000];
	public int n, child, parent, child1, child2;

	public void insert(int value){
		heap[n] = value; // insert the new value into the end of the array
	
		parent = (n+1)/2 - 1;
		if(parent < 0) {n++; return;} // if the heap contains only one node, then return immediately
		child = n;

        // bubble up
		while(true){
			if(heap[child] < heap[parent]){
				int temp = heap[child];
				heap[child] = heap[parent];
				heap[parent] = temp;
				if(parent == 0) break;
				child = parent;
				parent = (parent + 1)/2 - 1;
			}
			else break;
		}
		n++;
	}

	public int del(){

        // bring the last element of the array into the first position
		int ele = heap[0];
		heap[0] = heap[n-1];

		parent = 0;

        // bubble down
		while(true){
			child1 = parent*2 + 1;
			child2 = parent*2 + 2;

            // condition for when to stop bubble down
			if(child1 > n-1 || child2 > n-1) break;

            // swap the parent with the least of its children in the case of min heap
			if(heap[parent] > heap[child1] || heap[parent] > heap[child2]){
				if(heap[child1] < heap[child2]){
					int temp = heap[child1];
					heap[child1] = heap[parent];
					heap[parent] = temp;
					parent = child1;
				}
				else{
					int temp = heap[child2];
					heap[child2] = heap[parent];
					heap[parent] = temp;
					parent = child2;
				}
			}
			else break;
		}	
		n--;
		return ele; // return the root(or min) element of the heap
	}

    // function to simply return the min element(or root) of the heap without deleting it
	public int getMin(){
		return heap[0];
	}
}

class MedianNew {

    // imagine max heap on the left and min heap on the right
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Maxheap maxh = new Maxheap();
		Minheap minh = new Minheap();
		int sum = 0, median = 0;

		BufferedReader br = new BufferedReader(new FileReader("Median.txt"));
		String line;
		int count = 0;
		while((line = br.readLine()) != null){
			int next = Integer.parseInt(line);
			
            // for the first time, always insert into the max heap
			if(count == 0){
				maxh.insert(next);
				sum += maxh.getMax();
				count++;
				continue;
			}

            // get the median value from the previous insertion which is either the __max ele from max heap__ or 
            // __min ele from min heap__
			int maxno = maxh.n;
			int medianno = (count % 2 == 0)?(count/2):((count+1)/2);
			if(medianno == maxno){
				median = maxh.getMax();
			}
			else if(medianno > maxno){
				median = minh.getMin();
			}

            // once you know the old median, if the new element to be inserted >= median, insert it into the min heap(right heap)
            // if new element to be inserted < median, insert it into the max heap(left heap)
			if(next < median){
				maxh.insert(next);
				count++;
			}
			else if(next >= median){
				minh.insert(next);
				count++;
			}

            // after you have inserted the new element, the two heaps might not be the same size
            // after each insertion, the two heaps can differ in size by atmost 1
            // if they differ in size by more than 1, then move the max(or min) ele from one heap to the other
			if(maxh.n - minh.n > 1){
				int temp = maxh.del();
				minh.insert(temp);
			}
			else if(minh.n - maxh.n > 1){
				int temp = minh.del();
				maxh.insert(temp);
			}

            // after the new insertion, once again obtain median and add it to the total median sum
            // again, this median is either the __max ele from max heap__ or __min ele from min heap__
			maxno = maxh.n;
			medianno = (count % 2 == 0)?(count/2):((count+1)/2);
			if(medianno == maxno){
				median = maxh.getMax();
			}
			else if(medianno > maxno){
				median = minh.getMin();
			}

            // keep a running sum of the medians
			sum += median;

		}

        // Print out the last four digits of the running sum of medians
		System.out.println(sum%10000);
	}
}
