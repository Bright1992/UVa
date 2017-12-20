#include <iostream>

using namespace std;

void swap(int &a, int &b){
    int c=a;
    a=b;
    b=c;
}

void reverse(int unsorted[], int start, int end){
    for(int i=start, j=end-1;i<j;++i,--j){
        swap(unsorted[i],unsorted[j]);
    }
}

void shift(int unsorted[], int start, int mid, int end){
    reverse(unsorted, start, mid);
    reverse(unsorted, mid, end);
    reverse(unsorted, start, end);
}

void merge(int unsorted[], int start, int mid, int end){
    int i=start,j=mid;
    for(;i<mid;++i)
        if(unsorted[i]>unsorted[j]){
            for(;j<end;++j)
                if(unsorted[j]>unsorted[i]) break;
            shift(unsorted, i,mid,j);
            if(j==end)  break;
            int tmp=i-start;
            i=i+j-mid;
            start=i;
            mid=j;
        }
}

void merge_sort(int unsorted[], int start, int end){
    int mid=(start+end)/2;
    if(mid>start)   merge_sort(unsorted,start,mid);
    if(end>mid+1) merge_sort(unsorted,mid,end);
    merge(unsorted,start,mid,end);
}

int main(){
    int unsorted[]{5,6,4,23,34,45,3,312,5,78,5,4,3,-10,6,8,5,3,45,6,43,2,5,7,89,9,6,4,23};
    int size=sizeof(unsorted)/sizeof(int);
    merge_sort(unsorted,0,size);
    for(int i=0;i<size;++i) cout<<unsorted[i]<<" ";
    cout<<endl;
}
