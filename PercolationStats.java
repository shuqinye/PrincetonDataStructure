
/**
 * TODO:
 * 1. Always don't forget to validate the input values!!!!!!!!!! My default thinking pattern is to
 *    take the input values for granted. Don't do this in the future!!!!!!! Cultivate the good
 *    habit!!!!!!!!!!
 * 2. Type cast has to be followed by a whitespace!!!!!!
 * 3. Local variables are immediately destroyed after execution! So there is an advantage of that.
 *    Especially when one needs a variable as a pointer to an object, the variable is the best 
 *    to be a local variable, so that the object is destroyed after being used instead of being 
 *    kept but with no pointer.
 * 4. The difference between an instance variable and a local variable also lies in the memory
 *    usage and allocation process!!!! Instance variable memory is allocated when an object is
 *    created; A local variable is destroyed once the method is called and done.
 * 
 * @author ShuqinYe
 *
 */


public class PercolationStats {
	private double mean;	// Sample mean of the percolation threshold.
	private double stddev;	// Sample standard deviation.
	private double confidenceLo;	// Low endpoint of 95% confidence interval.
	private double confidenceHi;	// High endpoint of the 95% confidence interval.
	
	/**
	 * This method performs T independent experiments on an N-by-N grid
	 * @param N N-by-N grid, the grid dimension is N.
	 * @param T the number of experiments the method conducts.
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		
		double[] threshold = new double[T];
							// Array to store the percolation threshold for each experiment.
							// threshold[0] for experiment 1, threshold[1] for experiment 2, etc.
		
		for (int i = 0; i < T; i++) {
			threshold[i] = experiment(N);
		}
		
		double sum = 0.0; // The sum of all threshold values.
		for (int i = 0; i < T; i++) {
			sum = sum + threshold[i];
		}
		
		mean = sum / T;		// Mean of the threshold values of T times experiment.
		
		double variance = 0.0; // The variance of all threshold values.
		for (int i = 0; i < T; i++) {
			variance = variance + (threshold[i] - mean) * (threshold[i] - mean);
		}
		stddev = Math.sqrt(variance / (T - 1));
				// Standard deviation of the threshold values of T times experiment.
		
		confidenceLo = mean - 1.96 * stddev / Math.sqrt((double) T);
			// Confidence interval lower value of the threshold values of T times experiment.
		confidenceHi = mean + 1.96 * stddev / Math.sqrt((double) T);
			// Confidence interval higher value of the threshold values of T times experiment.
	}
	
	
	/**
	 * This method performs the experiment one time.
	 * @return the fraction of sites that are opened when the system percolates.
	 */
	private double experiment(int N) {
		Percolation testPerco = new Percolation(N);	// Create a fully blocked system to start the experiment.
		int row, col;	// row and column indices of the site.
		int count = 0;		// The number of open sites.
		
		while (!testPerco.percolates()) {
			row = (int) (Math.random() * N) + 1;
			col = (int) (Math.random() * N) + 1;
			if (testPerco.isOpen(row, col)) continue;
			testPerco.open(row, col);
			count++;
		}
		
		return (double) count / (N * N);
		
	}
	
	public double mean() { return mean; }
	public double stddev() { return stddev; }
	public double confidenceLo() { return confidenceLo; }
	public double confidenceHi() { return confidenceHi; }

	public static void main(String[] args) {
		PercolationStats stats;
		stats = new PercolationStats(800, 90);
		
		System.out.println(stats.confidenceLo());
		System.out.println(stats.confidenceHi());
		System.out.println(stats.mean());
		System.out.println(stats.stddev());
		
	}
	
}

