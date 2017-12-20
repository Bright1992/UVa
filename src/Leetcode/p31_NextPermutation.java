package Leetcode;

import java.util.Scanner;

public class p31_NextPermutation {
    public void nextPermutation(int[] nums) {
        int len=nums.length;
        if(len<=1)
            return;
        int i = len-2;
        for(;i>=0;--i)
            if(nums[i]<nums[i+1])   break;
        if(i==-1){
            reverse(nums,0,nums.length-1);
            return;
        }
        int j=len-1;
        for(;j>i;--j)
            if(nums[j]>nums[i]) break;
        swap(nums,i,j);
        reverse(nums,i+1,nums.length-1);
    }
    void reverse(int[] nums,int s, int e){
        for(int i=s,j=e;i<j;++i,--j)
            swap(nums,i,j);
    }
    void shift(int[] nums, int i, int j){
        int tmp=nums[j];
        for(int k=j;k>i;--k)
            nums[k]=nums[k-1];
        nums[i]=tmp;
    }
    void swap(int[] nums, int i, int j){
        int tmp=nums[i];
        nums[i]=nums[j];
        nums[j]=tmp;
    }
    public static void main(String[] argv){
        //Testcase
    }
}
