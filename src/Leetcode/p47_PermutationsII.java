package Leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class p47_PermutationsII {
    List<List<Integer>> ret;
    int[] mark;
    public List<List<Integer>> permuteUnique(int[] nums){
        Arrays.sort(nums);
        ret = new ArrayList<>();
        mark = new int[nums.length];
        ArrayList<Integer> cur = new ArrayList<>();
        return permute(nums,cur);
    }
    private List<List<Integer>> permute(int[] nums,List<Integer> cur){
        if(cur.size()==nums.length){
            ret.add(new ArrayList<Integer>(cur));
            return ret;
        }
        for(int i=0;i<nums.length;++i){
            if(mark[i]==0) {
                if (i == 0 || nums[i] != nums[i - 1]||mark[i-1]==1) {
                    mark[i]=1;
                    cur.add(nums[i]);
                    permute(nums,cur);
                    mark[i]=0;
                    cur.remove(cur.size()-1);
                }
            }
        }
        return ret;
    }
    public static void main(String[] argv){
        int[] nums={2,2,2,3};
        p47_PermutationsII inst = new p47_PermutationsII();
        List<List<Integer>> per=inst.permuteUnique(nums);
        int a =1;
    }
}
