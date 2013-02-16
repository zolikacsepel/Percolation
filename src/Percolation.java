/**
 * User: Roman Vasetskiy
 * Date: 2/7/13
 * Time: 7:42 PM
 */
public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] isOpened;
    private final int size, topIdx, bottomIdx;

    /**
     * create N-by-N grid, with all sites blocked
     *
     * @param N - size
     */
    public Percolation(int N) {
        if (N<=0) throw new IllegalArgumentException("N shall be > 0");

        // size of side
        size = N;
        //index of virtual upper root element
        topIdx = size * size;
        //index of virtual bottom root element
        bottomIdx = topIdx + 1;
        //create UF with size*size array length plus two elements for virtual roots
        uf = new WeightedQuickUnionUF(topIdx + 2);
        //initialize opened/close array with size*size size
        isOpened = new boolean[topIdx];
    }

    /**
     * is site (row i, column j) open?
     *
     * @param i - row index
     * @param j - column index
     * @return is open
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return isOpened[indexOf(i, j)];
    }

    /**
     * is site (row i, column j) full?
     *
     * @param i - row index
     * @param j - column index
     * @return - is full
     */
    public boolean isFull(int i, int j) {
        return isOpen(i, j) && uf.connected(indexOf(i, j), topIdx);
    }

    /**
     * open site (row i, column j) if it is not already
     *
     * @param i - row index
     * @param j - column index
     */
    public void open(int i, int j) {
        validate(i, j);
        int cur = indexOf(i, j);
        if (isOpened[cur]) return;

        isOpened[cur] = true;

        int left   = j > 1    ? indexOf(i, j - 1) : -1;
        int right  = j < size ? indexOf(i, j + 1) : -1;
        int top    = i > 1    ? indexOf(i - 1, j) : -1;
        int bottom = i < size ? indexOf(i + 1, j) : -1;

        //connect left
        if (left != -1 && isOpened[left]) uf.union(cur, left);
        //connect right
        if (right != -1 && isOpened[right]) uf.union(cur, right);

        //connect to top
        if (top != -1 && isOpened[top]) uf.union(cur, top);
            // or to virtual top root
        else if (top == -1) uf.union(topIdx, cur);

        //connect bottom
        if (bottom != -1 && isOpened[bottom]) uf.union(cur, bottom);
            //or to virtual bottom root
        else if (bottom == -1) uf.union(bottomIdx, cur);

    }

    /**
     *  does the system percolate?
     * @return true/false
     */
    public boolean percolates() {
        return uf.connected(topIdx, bottomIdx);
    }

    /**
     * validate indices
     * @param i - row index
     * @param j - column index
     */
    private void validate(int i, int j) {
        if (i >= 1 && i <= size && j >= 1 && j <= size) return;
        throw new IndexOutOfBoundsException("Coordinates shall be in 1 .. " + size + " range");
    }

    /**
     * calculates array index based on given grid coords
     * @param i - row index
     * @param j - column index
     * @return - index in array
     */
    private int indexOf(int i, int j) {
        return (i - 1) * size + (j - 1);
    }

    public static void main(String args[]){
        Percolation p = new Percolation(3);
        p.open(1,3);
        p.open(2,3);
        p.open(3,3);
        p.open(3,1);
        System.out.println("Percolate: " + p.percolates());
        System.out.println("is 3.1 full : " + p.isFull(3,1));


    }

}
