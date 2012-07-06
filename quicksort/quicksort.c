#include <stdio.h>
#include <stdlib.h>
int comp = 0;

void swap(int *a, int *b){
    int temp;
    temp = *a; 
    *a = *b;
    *b = temp;
}

int partition(int *num, int l, int r){
    int i=l+1, temp, j=0, pivot, len, mid, small, large, median;

    /********* for case 1 in which first element is pivot
       int pivot = num[l];
    */

    /********* for case 2 in which last element is pivot
        swap(&num[l], &num[r]);
    */

    /********* for case 3 with median of 3 pivot*/
    len = r-l+1; 
    if(len % 2 != 0) mid = l + ((len-1)/2);
    else mid = l + ((len/2) - 1);

    if (num[l] > num[mid]){
        large = l;
        small = mid;
    }
    else{
        large = mid; 
        small = l;
    }

    if(num[r] > num[large]){
        median = large;
    }
    else{
        if(num[r] < num[small]){
            median = small;
        }
        else median = r;
    }
    swap(&num[l], &num[median]);
    /******** end of median of 3 pivot *********/

    for (j=l+1; j<=r; j++){
        if (num[j] < num[l]){
           swap(&num[i], &num[j]);
           i++;
        }
    }
    i--;
    swap(&num[i], &num[l]);
    return i;
}

void quicksort(int *num,int l,int r){
    if(l < r){
        comp += r-l;
        int k = partition(num,l,r);
        quicksort(num,l,k-1);
        quicksort(num,k+1,r);
    }
}

void main()
{
    int i=0;
    FILE *fp = fopen("QuickSort.txt","r");
    int *num = (int*)malloc(10000*sizeof(int));
    if(fp == NULL){
        printf("error");exit(1);
    }
    while(!feof(fp))
    {
        fscanf(fp, "%d", &num[i++]);
    }
    printf("%d\n",num[9999]);

    quicksort(num,0,9999);

    for (i=0; i<9999; i++){
        if(num[i] > num[i+1]){
            printf("Not sorted !!!");exit(1);
        }
    }

    printf("\nNumber of comp = %d\n", comp);
}
