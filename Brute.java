
/**
 * This program examines 4 points at a time and checks whether they all lie
 *  on the same line segment, printing out any such line segments to standard output and drawing
 *  them using standard drawing. To check whether the 4 points p, q, r, and s are collinear,
 * check whether the slopes between p and q, between p and r, and between p and s are all equal.
 * The order of growth of the running time of your program is N4 in the worst case and it uses
 *  space proportional to N.
 *  
 * 
 *Takeaway:
 *1. Combinations of points: just use nested loop.
 *2. For insertion sort, the trick lies in indexing! A lot of sorting and tracing algorithms depend
 *   on tracking indices!!! A problem can be as easy as designing several tracking indices!!!
 *  
 * @author ShuqinYe
 *
 */

public class Brute {
	
	public static void main(String[] args) {

		// Rescale the coordinate system.
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0,32768);
		
//	Testing using my own method:
//		TextIO.readFile("rs1423.txt");
//		int n = TextIO.getInt();
//		Point[] points = new Point[n];	// Make an array of n elements to store the n Point objects.
//		for (int i = 0; i < n; i++) {
//			points[i] = new Point(TextIO.getInt(), TextIO.getInt());
//			points[i].draw();
//		}	

		
		In readFile = new In(args[0]);	// Take in the input file.
		int n = readFile.readInt();		// Read the number of points and store it in n.

		Point[] points = new Point[n]; // Make an array of n elements to store the n Point objects.
	
		for (int i = 0; i < n; i++) { // Initiate the Point object in each array element.
			points[i] = new Point(readFile.readInt(), readFile.readInt());
			points[i].draw();
		}
		
		
		for (int a = 0; a < n; a++) {
			for (int b = a + 1; b < n; b++) {
				for (int c = b + 1; c < n; c++) {
					for (int d = c + 1; d < n; d++) {
						// Check whether the slopes of points a, b, c, d are the same, aka, they 
						// lie on the same line segment.
						if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[c])
								&& points[a].slopeTo(points[b]) == points[a].slopeTo(points[d])) {
							
							// Copy the four points into another Point array for sorting.
							Point[] temp = new Point[4];
							temp[0] = points[a];
							temp[1] = points[b];
							temp[2] = points[c];
							temp[3] = points[d];
							
							// Sort the four points using insertion sort.
							// There are no equal points in the four points.
							for (int i = 1; i < 4; i++) {
								for (int j = i; j > 0; j--) {
									if (temp[j].compareTo(temp[j - 1]) < 0) {
										// Swap temp[j] and temp[j - 1];
										Point swap = temp[j];
										temp[j] = temp[j - 1];
										temp[j - 1] = swap;
									}
									else break;
								}
							}
 							
							// Print out the four points.
							System.out.print(temp[0]);
							System.out.print(" -> ");
							System.out.print(temp[1]);
							System.out.print(" -> ");
							System.out.print(temp[2]);
							System.out.print(" -> ");
							System.out.print(temp[3]);
							System.out.println();
							
							// Draw the line segments
							temp[0].drawTo(temp[3]);
						}
							
					}
				}
			}
		}

	}


}