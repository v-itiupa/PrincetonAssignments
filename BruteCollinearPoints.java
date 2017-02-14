/**
 * Created by v-itiupa on 12/17/2016.
 */

import edu.princeton.cs.algs4.ResizingArrayStack;
import java.util.Arrays;


public class BruteCollinearPoints {
    private ResizingArrayStack<LineSegment> lineSegmentsStack = new ResizingArrayStack<LineSegment>();
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)  {
        /*
        * Throw a java.lang.NullPointerException either the argument to the constructor is null
        * or if any point in the array is null.
        * Throw a java.lang.IllegalArgumentException if the argument to the constructor
        * contains a repeated point.
        */
        int pointsLength = points.length;
        if (points == null) throw new java.lang.NullPointerException("Input argument is null");

        Point[] naturalOrder = new Point[pointsLength];
        System.arraycopy(points, 0, naturalOrder, 0, pointsLength);
        Arrays.sort(naturalOrder);

        for (int i = 0; i < pointsLength; i++) {
            if (naturalOrder[i] == null)
                throw new java.lang.NullPointerException("Point argument is null");
            // if number in array after current number in array is the same
            if (i > 0 && naturalOrder[i].compareTo(naturalOrder[i - 1]) == 0)
                throw new java.lang.IllegalArgumentException("Duplicated points");
        }

        // check whether the three slopes between p and q, between p and r,
        // and between p and s are all equal.
        Point p, q, r, s;
        double pqSlope, prSlope, psSlope;
        Point[] pointsOnSegment = new Point[4];
        LineSegment ls;

        for (int pi = 0; pi < pointsLength; pi++) {
            p = naturalOrder[pi];
            for (int qi = pi + 1; qi < pointsLength; qi++) {
                q = naturalOrder[qi];
                pqSlope = p.slopeTo(q);
                for (int ri = qi + 1; ri < pointsLength; ri++) {
                    r = naturalOrder[ri];
                    prSlope = p.slopeTo(r);
                    if (pqSlope == prSlope) {
                        for (int si = ri + 1; si < pointsLength; si++) {
                            s = naturalOrder[si];
                            psSlope = p.slopeTo(s);

                            if (prSlope == psSlope) {
                                pointsOnSegment[0] = p;
                                pointsOnSegment[1] = q;
                                pointsOnSegment[2] = r;
                                pointsOnSegment[3] = s;
                                Arrays.sort(pointsOnSegment);

                                ls = new LineSegment(pointsOnSegment[0], pointsOnSegment[3]);
                                lineSegmentsStack.push(ls);
                            }
                        }


                    }

                }

            }

        }
    }
    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentsStack.size();
            }

    // the line segments
    public LineSegment[] segments() {
        int i = 0;
        int numberOfSegments = numberOfSegments();
        LineSegment[] allSegmentsArray = new LineSegment[numberOfSegments];
        for (LineSegment individualSegment : lineSegmentsStack) {
            allSegmentsArray[i++] = individualSegment;
        }
        return allSegmentsArray;
    }

}
