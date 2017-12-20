import java.util.Scanner;

public class p1354 {
    static int[] weight;
    static class Node{
        int weight;
        double left,right;
        Node lnode,rnode;
        boolean isLeaf(){
            return left==0&&right==0;
        }
        Node(int weight,Node lnode, Node rnode){
            if(lnode==null&&rnode==null){
                this.weight=weight;return;
            }
            this.lnode=lnode;this.rnode=rnode;
            this.weight=weight+lnode.weight+rnode.weight;
            left=(double)rnode.weight/(lnode.weight+rnode.weight);
            right=1-left;
            left+=Math.max(lnode.left,rnode.left-1);
            right+=Math.max(rnode.right,lnode.right-1);
        }
    }
    static Node[] nodes;
    /*
    0:not present
    1:present and not visited
    2:present and visited
     */
    static int[] state;
    static double maxr;
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int N=scanner.nextInt();
        while(N-->0){
            double r=scanner.nextDouble();
            int s=scanner.nextInt();
            weight=new int[s];
            nodes=new Node[s*3];
            state=new int[s*3];
            for(int i=0;i<s;++i)
                state[i]=1;
            for(int i=0;i<s;++i){
                weight[i]=scanner.nextInt();
                nodes[i]=new Node(weight[i],null,null);
            }
            if(s==0){
                System.out.format("%.15f\n",0);
            }
            maxr=-1;
            dfs(r);
            if(maxr+1<0.5)
                System.out.println("-1");
            else
                System.out.format("%.15f\n",maxr);
        }
    }
    static Node all;
    static void dfs(double r){
        int cnt=0;
        Node n0=null;
        for(int i=0;i<nodes.length;++i){
            if(state[i]==1) {
                cnt++;
                n0 = nodes[i];
            }
            if(cnt==2)  break;
        }
        if(cnt<2){
            if(maxr<n0.left+n0.right&&n0.left+n0.right<=r){
                maxr=n0.left+n0.right;
                all=n0;
            }
            return;
        }
        Node n1=null,n2=null;
        int i1,i2;
        for(i1=0;i1<nodes.length;++i1){
            if(state[i1]==1){
                for(i2=i1+1;i2<nodes.length;++i2) {
                    if (state[i2]==1){
                        state[i1]=state[i2]=2;
                        n1=nodes[i1];n2=nodes[i2];
                        for(int k=weight.length;k<nodes.length;++k){
                            if(state[k]==0){
                                state[k]=1;
                                Node n3=new Node(0,n1,n2);
                                if(n3.left+n3.right<=r){
                                    nodes[k]=n3;
                                    dfs(r);
                                }
                                n3=new Node(0,n2,n1);
                                if(n3.left+n3.right<=r){
                                    nodes[k]=n3;
                                    dfs(r);
                                }
                                state[k]=0;
                                break;
                            }
                        }
                        state[i1]=state[i2]=1;
                    }
                }
            }
        }
    }
}
