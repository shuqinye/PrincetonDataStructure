/*************************************************************************
 * Name: Shuqin Ye
 * Email: helen.shuqin.ye@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

/**
 * Note:
 * 1. For comparator implementation, note the following points:
 *    - write a nested class, whether it's static or non-static depends on what variables the
 *      comparator class needs access to. If it's static, it belongs to the static body, if not, 
 *      it belongs to the non-static body. I need to see these two groups separately.
 *    - non-static class is normally used only inside the containing class, hence we write private
 *      nested classes.
 *    - We still use the class as a normal class, just the nonmenclature has some changes.
 *
 *
 *Takeaways:
 *1. When writing the comparator class, it depends what variables the comparator needs access to!!!
 *	 This is actually not that complicated. It's quite straight forward in every sense!!!!
 *2. Static VS non-static is essentially the same with the function of the comparator, the same with
 *   what variables it needs to use, the same with the human reasoning behind the surface, the same
 *   with the logic and usage of the comparator!!!!!! Everything is connected actually!!!
 *3. Use the ternary operator for easier code ( boolean expr. ? value-if-true : value-if-false)!
 *4. For Double.NEGATIVE_INFINITY & Double.POSITIVE_INFINITY, they are different from MIN_VALUE and
 *   MAX_VALUE, the latter two values are actual values which can be used in arithmetic operations.
 *   The first 2 are NOT VALUES!!!!!! They are NaN, if we use them in arithmetic operations, we will
 *   have a result of NaN as well!!!!! So please be careful about this!!! 
 *   - We can only compare INFINITY, but we cannot compare NaN, because Double.NaN is not a number!!
 *   - We can compare INFINITY, but we cannot do arithmetic operations on INFINITY!!!
 *   - INFINITY is represented as INFINITY, it's a enum type.
 *   - equal() or Double.compare() to compare NaN to get the correct result.
 *5. Integer.MIN_VALUE: the negative value of the MIN_VALUE is still the same value!!!! because of
 *   the mechanism behind!!!
 *6. There is a difference between positive zero and negative zero!!!
 *
 */
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    /**
     * This comparator compares points by the slopes they make with the invoking point (x0, y0).
     * The point (x1, y1) is less than the point (x2, y2) iff the slope (y1 − y0) / (x1 − x0) 
     * is less than the slope (y2 − y0) / (x2 − x0). The slopes of Horizontal, vertical, 
     * and degenerate line segments are the same as in the slopeTo() method.
     * @author ShuqinYe
     *
     */
    private class BySlope implements Comparator<Point> {
    	
    	public int compare(Point a, Point b) {
    		double slopeA = slopeTo(a), slopeB = slopeTo(b);
    		return slopeA == slopeB ? 0 : (slopeA < slopeB ? -1 : 1);
    		}
    	
    }
    
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     *  slope between this point and that point.
     *  the slope of a horizontal line segment is positive zero;
     *  the slope of a vertical line segment is positive infinity;
     *  the slope of a degenerate line segment (between a point and itself) is negative infinity.
     * @param that the point to be connected to measure the slope
     * @return the slope between this point and that point.
     */
    public double slopeTo(Point that) {
        if (this.x != that.x && that.y == this.y)
        	return +0.0;
        else if (this.y == that.y) return Double.NEGATIVE_INFINITY;
        else if (this.x != that.x)
        	return (double) (that.y - this.y) / (that.x - this.x);
        else return Double.POSITIVE_INFINITY;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    // the invoking point (x0, y0) is less than the argument point (x1, y1) if and only 
    // if either y0 < y1 or if y0 = y1 and x0 < x1.
    public int compareTo(Point that) {
        if (this.y == that.y) {
        	if (this.x < that.x) return -1;
        	else if (this.x > that.x) return 1;
        	else return 0;
        }
        else if (this.y < that.y) return -1;
        else return 1;
        
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}