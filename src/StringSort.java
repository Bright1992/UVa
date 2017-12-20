public class StringSort {
    static void exch(String[] a, int src, int dst) {
        String tmp = a[src];
        a[src] = a[dst];
        a[dst] = tmp;
    }

    static int charAt(String s, int idx){
        if(idx<s.length()&&idx>=0)    return (int)s.charAt(idx);
        return -1;
    }

    public static void sort(String[] a, int from, int to, int d) {
        if(from>=to)    return;
        int l = from, t = to, i=l+1;
        while(i<=t){
            if(charAt(a[i],d)<charAt(a[l],d))
                exch(a,i++,l++);
            else if(charAt(a[i],d)>charAt(a[l],d))
                exch(a,i,t--);
            else
                i++;
        }
        sort(a,from,l-1,d);
        if(charAt(a[l],d+1)!=-1)
            sort(a,l,t,d+1);
        sort(a,t+1,to,d);
    }

    public static int kmp(String text, String ptn){
        int[] next = new int[ptn.length()];
        next[0]=-1;
        int i=0,j=next[i];
        while(i<ptn.length()-1){
            if(j<0||ptn.charAt(i)==ptn.charAt(j)){
                i++;j++;next[i]=j;
            }
            else
                j=next[j];
        }
        i=0;j=0;
        while(i<text.length()){
            if(j==ptn.length()) break;
            else if(j==-1||text.charAt(i)==ptn.charAt(j)){
                i++;j++;
            }
            else    j=next[j];
        }
        if(j==ptn.length()) return i-j;
        return -1;

    }

    public static void main(String[] argv){
        String[] a={"asdf","fdsa","asd","fiefji","vifdsv","feicdfa"};
        sort(a,0,a.length-1,0);
        for(String s:a)
            System.out.println(s);
        System.out.println(kmp("asdf.dfe","f.d"));
        System.out.println(kmp("asdf","asdf"));
        System.out.println(kmp("asdf","fdsa"));
    }
}
