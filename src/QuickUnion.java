
import static java.lang.System.out;
public class QuickUnion {
    private int[] id;
    private int[] weight;

    public QuickUnion(int N){
        id = new int[N];
        weight = new int[N];

        for (int i = 0; i < id.length;i++){
            this.id[i] = i;
            weight[i] = 1;
        }
    }


    public boolean connected(int p, int q) {
        boolean result = getRootId(p) == getRootId(q);
        out.println(p + " and " + q + (result? " is " : " isn't ") + " connected");
        return result;
    }


    private int getParentId(int x){
        return id[x];
    }

    private int getRootId(int x){

        int rootId = id[x];
        out.println("Root id of " + x + " is " + rootId);
        if (x == rootId) return rootId;
        id[x] = id[id[id[x]]];

        return getRootId(rootId);
    }

    public void union(int p, int q) {
        int pRootId = getRootId(p);
        int qRootId = getRootId(q);

        if (weight[pRootId] >= weight[qRootId]) {
            id[qRootId] = pRootId;
            weight[pRootId] +=weight[qRootId];
        } else {
            id[pRootId] = qRootId;
            weight[qRootId] +=weight[p];
        }

        printArray();
    }

    private void printArray(){

        out.print(" | ");

        for (int i = 0; i < id.length;i++) {
            out.print(i + " | ");
        }

        out.println();
        out.print(" | ");
        for (int i: id) {
            out.print(i + " | ");
        }
        out.println();

    }

}


