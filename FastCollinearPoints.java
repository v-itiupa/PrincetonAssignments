/**
 * Created by v-itiupa on 12/17/2016.
 */

import edu.princeton.cs.algs4.ResizingArrayStack;
import java.util.Arrays;


public class FastCollinearPoints {
    private ResizingArrayStack<LineSegment> lineSegmentsStack = new ResizingArrayStack<LineSegment>();
    // finds all line segments containing 4 or more points
    private Point first;
    private Point last;
    private Point pPoint;
    private Point[] naturalOrder, naturalOrderWithSlope;

    public FastCollinearPoints(Point[] points) {

        if (points == null) throw new java.lang.NullPointerException("Input argument is null");

        int pointsLength = points.length;
        naturalOrder = new Point[pointsLength];
        System.arraycopy(points, 0, naturalOrder, 0, pointsLength);
        Arrays.sort(naturalOrder);

        for (int k = 0; k < pointsLength; k++) {
            if (naturalOrder[k] == null)
                throw new java.lang.NullPointerException("Point argument is null");
            // if number in array after current number in array is the same
            if (k > 0 && naturalOrder[k].compareTo(naturalOrder[k - 1]) == 0)
                throw new java.lang.IllegalArgumentException("Duplicated points");
        }

        for (int p = 0; p < pointsLength - 2; p++) {
            pPoint = naturalOrder[p];
            // re-sort by slope with preserving natural order because of merge sort
            naturalOrderWithSlope = new Point[pointsLength];
            System.arraycopy(naturalOrder, 0, naturalOrderWithSlope, 0, pointsLength);
            Arrays.sort(naturalOrderWithSlope, pPoint.slopeOrder());

            for (int i = 1; i < pointsLength; i++) {

                double currentSlope = pPoint.slopeTo(naturalOrderWithSlope[i]);
                int m = 1;
                while (m + i  < pointsLength  && currentSlope == pPoint.slopeTo(naturalOrderWithSlope[i + m]))
                {
                    if (last == null) {
                        last = naturalOrderWithSlope[i + m];
                    }
                    else {
                        if (last.compareTo(naturalOrderWithSlope[i + m]) < 0) {
                            last = naturalOrderWithSlope[i + m];
                        }
                    }

                    if (first == null) {
                        first = pPoint;
                    }
                    else {
                        if (first.compareTo(naturalOrderWithSlope[i + m]) > 0) {
                            first = naturalOrderWithSlope[i + m];
                        }
                    }

                    m++;
                }
                if (m >= 3 ) {
                    if (first == last) break;
                    LineSegment ls = new LineSegment(first, last);
                    lineSegmentsStack.push(ls);
                    last = null;
                    first = null;

                }
                i = i + m - 1;
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
        for(LineSegment individualSegment : lineSegmentsStack) {
            allSegmentsArray[i++] = individualSegment;
        }
        return allSegmentsArray;
    }


}