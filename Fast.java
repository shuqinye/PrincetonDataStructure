/**
 * Takeaway:
 * 1. About the index issue for arrays, we can have a starting index of 0 and ending index of 
 *    maximum index + 1, because the end index is not inclusive anyway!!!
 * 2. When one expression needs to be written on two lines, the sign has to lead the second line!!
 */


import java.util.Arrays;



public class Fast {
	public static void main(String[] args) {
		
		// Re-scale the coordinate system before drawing.
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0,32768);
		
//	Testing using my own method:
		TextIO.readFile("input56.txt");
		int n = TextIO.getInt();
		Point[] points = new Point[n];	// Make an array of n elements to store the n Point objects.
		for (int i = 0; i < n; i++) { // Get each point's x & y values from the file.
			points[i] = new Point(TextIO.getInt(), TextIO.getInt());
			points[i].draw();
		}
		
		
//		In readFile = new In(args[0]);	// Take in the input file.
//		int n = readFile.readInt();		// Read the number of points and store it in n.
//		
//		Point[] points = new Point[n]; // Make an array of n elements to store the n Point objects.
//	
//		for (int i = 0; i < n; i++) { // Initiate the Point object in each array element.
//			points[i] = new Point(readFile.readInt(), readFile.readInt());
//			points[i].draw();
//		}
		
		// Track the starting index of the points that need to be sorted next.
		int track = 0;
		
		while (track < n - 3) {	// Stop the loop when there are less than 3 points left.
			
			// Sort the points with point[i] as a reference.
			// points[i] is the smallest and will be at the first position.
			// Find out how many equal values are adjacent from the second value onwards.
			Arrays.sort(points, track, n, points[track].SLOPE_ORDER); 
			
			int i = track + 1,  j = track + 1;	// Initialize the assisting pointers.
			int count = 0;	// The number of points that align with each other.

			while (j < n) {
				
				count = 0;
				i = j;
				
				// Get the group of aligned points.
				while (j < n 
						&& points[track].SLOPE_ORDER.compare(points[i], points[j]) == 0) {

					count++;	// The number of equal points increases by one.
					j++;	// Check the next point.
				}
		
				// If there are 3 or more points that align with points[i], we
				// need to print them out. The indices are from i(inclusive) to
				// j(exclusive).
				if (count >= 3) {	 
					// Copy the first point into another Point array element 0.
					Point[] temp = new Point[count + 1];
					temp[0] = points[track];
					
					// Copy the rest of points into the rest of the array elements for sorting.
					for (int c = 1; c <= count; c++, i++) {
						temp[c] = points[i];
					}
					
					// Sort the aligned points using insertion sort.
					// There are no equal points in all the points. There are (count+1) points.
					// The order is determined by compareTo() method in the class Point.
					for (int a = 1; a < count + 1; a++) {
						for (int b = a; b > 0; b--) {
							if (temp[b].compareTo(temp[b - 1]) < 0) {
								// Swap temp[b] and temp[b - 1];
								Point swap = temp[b];
								temp[b] = temp[b - 1];
								temp[b - 1] = swap;
							}
							else break;
						}
					} // Insertion sort done.
					
					// Print out all the aligned points in the specified order.
					for (int c = 0; c < count; c++) {
						// Print out all points except the last one with a "->" after every point.
						System.out.print(temp[c]);
						System.out.print(" -> ");
					}
					System.out.print(temp[count]);	// Print out the last point.
					
					// Print out a new line, get ready for the next group of points.
					System.out.println();	

					// Draw the line segments
					temp[0].drawTo(temp[count]);
					
					
				} // End of the if statement, end of finding one line segment.
		
			} // End of while loop.
			  // The next loop starts to find the next sub-group of aligned points.

			track++; // The reference point doesn't need to be checked again in the next round.
			
		} // End of for loop. The aligned points are found for every point.
		
	}
}
