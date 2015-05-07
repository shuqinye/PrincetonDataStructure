/**
 * This method is an improved version of Percolation class. Only one UF object is used instead.
 * Memory use is decreased tremendously with an increasing value of dimension N.
 * 
 * TODO:
 * 1. As normal, always notice the instance/global variables and keep an eye on them for every
 *    single method! Make sure they are updated.
 * 2. For the class definition, specifically in open() method, we open the site one by one and this
 *    is a potential platform for a recursive algorithm. If we check at the end of opening all 
 *    sites, we have a recursive algorithm with non-constant running time, however, if we use
 *    the chance of opening a site and do this step by step, this is just to dissect the recursive
 *    algorithm and make it as moving forward step-by-step. Then this yields a constant running
 *    time. AWESOME!!!!!!!!!!!!!!!!!
 * 3. In union-find, the concept of root, component are very important. The root is the key!!!!
 *    not every individual site!!!!!!! The root information, most of the time, actually represents
 *    information of ALL OTHER NODES in the same component. So tracking the root is to track all 
 *    components in the tree!!!!!!! - then all roots means the whole system!!!!
 * 4. When writing programs, you have to see both big picture and small picture - details and also
 *    relax and stand back to see all the testing cases (esp. corner cases)!!!!!!!!
 * 5. Please make sure you write down all the things to help you think, imagination will only make
 *    the thinking process more difficult.!!!
 * 6. For repetitive actions, there are two cases that we need to take into consideration - base
 *    case and end case: normally we take care of these cases first, or we take the case where it's
 *    different from normal case first. We can also call both end case and end case - base case.
 *    Remember to take care of these FIRST!!!!!!!!!!!!! Analyze this before hand!!!!!!!!!!
 * 
 * @author ShuqinYe
 *
 */
public class Percolation {

	private WeightedQuickUnionUF uf;		// Union find data structure to check whether a site
	 										// is connected to the top row.
	private boolean[] connectTop;	// Check whether a site is connected to the top row.
	private boolean[] connectBottom;	// Check whether a site is connected to the bottom row.
	private boolean[] openStatus;	// Track whether one site is open or blocked, true for open sites.
									// false for blocked sites.
	
	
	private boolean isPercolate;	// Track whether the system percolates.
	private final int max;	// The size of the arrays that check open status.
	private final int dim;	// The number of sites per row/col.
	
	
	/**
	 * Construct a NxN grid site, with all sites blocked.
	 * @param N the grid dimension
	 */
	public Percolation(int N) {
		
		if (N <= 0) {
			throw new IllegalArgumentException("The input N must be positive!");
		}
		
		dim = N;
		max = N * N + 1;
		isPercolate = false;	// The system does not percolate when all sites are blocked.
		
		uf = new WeightedQuickUnionUF(max);		// Create a UF object to model the system.
		openStatus = new boolean[max];	// Array to track whether a site is open or blocked.
		connectBottom = new boolean[max]; // Track whether a site is connected to the bottom row.
		connectTop = new boolean[max]; // Whether a site is connected to the top row.
		
		for (int i = 0; i < max; i++) {
			openStatus[i] = false;		// All sites are blocked as default.
			connectBottom[i] = false;	// Initialize the status as false since they are blocked.
			connectTop[i] = false;	// Initialize the status as false since they are blocked.
		}

	}
	
	/**
	 * This method checks whether the input indices are valid row and column indices.
	 * If not, an IndexOutOfBoundsException is thrown.
	 * @param i row index
	 * @param j column index
	 */
	private void validateIndex(int i, int j) {
		if (i < 1 || i > dim || j > dim || j < 1) {
			throw new IndexOutOfBoundsException("The site index is out of bound!");
		}
	}
	
	/**
	 * This method gets the corresponding index in the union-find data structure for the site
	 *  at row i col j.
	 * @param i row index of the site
	 * @param j col index of the site
	 * @return
	 */
	private int getIndex(int i, int j) {
		return dim * (i - 1) + j;
	}
	
	/**
	 * This method checks whether a site is connected to both top and bottom row.
	 * If yes, the system percolates, if not, the system does not percolate.
	 * @param i row index
	 * @param j col index
	 * @return whether the site is connected to both top and bottom row of the system.
	 */
	private boolean checkPercolate(int i, int j) {
		int index = dim * (i - 1) + j;
		return connectBottom[uf.find(index)] && connectTop[uf.find(index)];
			// If the site is connected to both the top and bottom after it's open, the
			// system percolates.
	}
	
	/**
	 * This method opens site (row i, column j) if it is not opened already.
	 * 
	 */
	public void open(int i, int j) {
		
		validateIndex(i, j);
		
		int index = getIndex(i, j);
		if (openStatus[index]) return;	// The site is already opened.
		openStatus[index] = true;	// Open the site.
		
		if (dim == 1) {		// If the system has only one site.
			isPercolate = true;		// The system percolates when the one site is open.
			connectBottom[uf.find(index)] = true;
			connectTop[uf.find(index)] = true;
			return;		// No need to check anything else.
		}
		
		if (i == 1 && j == 1) {	// Upper-left corner site.
			connectTop[uf.find(index)] = true;
			checkRight(i, j);
			checkBelow(i, j);
		}
		
		else if (i == 1 && j == dim) {
									// Upper-right corner site.
			connectTop[uf.find(index)] = true;
			checkLeft(i, j);
			checkBelow(i, j);
			
		}
		
		else if (i == dim && j == 1) {
									// Lower-left corner site.
			connectBottom[uf.find(index)] = true;
			checkAbove(i, j);
			checkRight(i, j);
			
		}
		
		else if (i == dim && j == dim) {
									// Lower-right corner site.
			connectBottom[uf.find(index)] = true;
			checkAbove(i, j);
			checkLeft(i, j);
			
		}
		
		else if (i == 1 && j < dim && j > 1) {	
									// Top row sites except corners.
			connectTop[uf.find(index)] = true;
			checkLeft(i, j);
			checkBelow(i, j);
			checkRight(i, j);
			
		}
		
		else if (i == dim && j < dim && j > 1) {
									// Bottom row sites except corners.
			connectBottom[uf.find(index)] = true;
			checkLeft(i, j);
			checkRight(i, j);
			checkAbove(i, j);
			
		}
		
		else if (j == 1 && i > 1 && i < dim) {	
									// Left-most column sites except corners.
			checkAbove(i, j);
			checkRight(i, j);
			checkBelow(i, j);
		}
		
		else if (j == dim && i > 1 && i < dim) {
									// Right-most column sites except corners.
			checkAbove(i, j);
			checkBelow(i, j);
			checkLeft(i, j);
		}
		
		else {	// The rest of the sites, aka, sites that have 4 sites around it.
			checkAbove(i, j);
			checkBelow(i, j);
			checkLeft(i, j);
			checkRight(i, j);
		}
		
		if (checkPercolate(i, j)) isPercolate = true;	// Once the site percolates, the system
											// percolates from this time onwards.

	}
	
	
	
	/**
	 * These 4 private methods checks all site status around the site to be opened, aka the site 
	 * on the left, right, top and bottom. The method then makes them the same component if the
	 * Neighboring site is also open. The connectTop and connectBottom flags are also updated.
	 * 
	 * @param i row index of the site to be opened.
	 * @param j col index of the site to be opened.
	 */
	private void checkRight(int i, int j) {
		
		int index = getIndex(i, j);
		
		if (isOpen(i, j + 1)) { // Check the site on the right of the site to be opened.
			int root1 = uf.find(index);
			int root2 = uf.find(index + 1);
			uf.union(index, index + 1); // If the site on the right is also opened, connect. 
			connectBottom[uf.find(index)] = connectBottom[root1] || connectBottom[root2];
			connectTop[uf.find(index)] = connectTop[root1] || connectTop[root2];
		}
	}
	
	private void checkBelow(int i, int j) {	// Check the site on the right of the target site.
		int index = getIndex(i, j);
		
		int root1 = uf.find(index);
		int root2 = uf.find(index + dim);
		if (isOpen(i + 1, j)) { // Check the site below the site to be opened.
			uf.union(index, index + dim);	// connect.
			connectBottom[uf.find(index)] = connectBottom[root1] || connectBottom[root2];
			connectTop[uf.find(index)] = connectTop[root1] || connectTop[root2];
		}
	}
	
	private void checkLeft(int i, int j) {	// Check the left site of the target site.
		int index = getIndex(i, j);
		
		int root1 = uf.find(index);
		int root2 = uf.find(index- 1);
		if (isOpen(i, j - 1)) { // Check the site on the left of the site to be opened.
			uf.union(index, index - 1);	// If the site on the left is open, connect.
			connectBottom[uf.find(index)] = connectBottom[root1] || connectBottom[root2];
			connectTop[uf.find(index)] = connectTop[root1] || connectTop[root2];
		}
	}
	
	private void checkAbove(int i, int j) {
		int index = getIndex(i, j);
		
		int root1 = uf.find(index);
		int root2 = uf.find(index- dim);
		if (isOpen(i - 1, j)) { // Check the site above the site to be opened.
			uf.union(index, index - dim);	// If the site above is open, connect.
			connectBottom[uf.find(index)] = connectBottom[root1] || connectBottom[root2];
			connectTop[uf.find(index)] = connectTop[root1] || connectTop[root2];
		}
	}

	

	/**
	 * This method returns true if a site specified at (row i, column j) is open, false otherwise.
	 * @param i row index for the site.
	 * @param j col index for the site.
	 * @return true if a site specified at (row i, column j) is open, false otherwise.
	 */
	public boolean isOpen(int i, int j) {
		
		validateIndex(i, j);
		
		int index = getIndex(i, j);	// Index of the site in the UF class.
		return openStatus[index];
	}
	
	
	/**
	 * This method returns true if a site specified at (row i, column j) is full, false otherwise.
	 * @param i row index for the site.
	 * @param j col index for the site.
	 * @return true if a site specified at (row i, column j) is full, false otherwise.
	 */
	public boolean isFull(int i, int j) {
		
		validateIndex(i, j);
		
		int index = getIndex(i, j);	// Index of the site in the UF class.
		return connectTop[uf.find(index)];
		
	}
	
	
	
	/**
	 * This method tells whether the system percolates. It returns true if yes, false otherwise.
	 * @return true if the system percolates, else otherwise.
	 */
	public boolean percolates() {
		return isPercolate;
	}
	
	
	public static void main(String[] args) {
		
		Percolation testPerco = new Percolation(4);
		
		testPerco.open(1, 3);		
		testPerco.open(1, 4);
		testPerco.open(2, 1);
		testPerco.open(2, 2);
		testPerco.open(2, 4);	
		testPerco.open(3, 1);		
		testPerco.open(3, 4);	
		testPerco.open(4, 1);
		testPerco.open(4, 2);
		testPerco.open(4, 4);
		

		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 5; j++) {
				System.out.println("Whether the site is open = " + i + j + " " 
			+ testPerco.isOpen(i, j));
			}
			System.out.println();
		}
		
		
		System.out.println();
		
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 5; j++) {
				System.out.println("Whether the site is full = " + i + j + " " 
			+ testPerco.isFull(i, j));
			}
			System.out.println();
		}

		System.out.println();
		System.out.println("Whether the site percolates: " + testPerco.percolates());	
		
		
	}
	
}
