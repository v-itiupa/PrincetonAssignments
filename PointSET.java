import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdDraw;
/**
 * Created by v-itiupa on 1/8/2017.
 */
public class PointSET {

    private TreeSet<Point2D> points;

    public PointSET()                               // construct an empty set of points
    {
        points = new TreeSet<>();

    }
    public boolean isEmpty()                      // is the set empty?
    {
        return points.isEmpty();
    }
    public int size()                         // number of points in the set
    {
        return points.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) {
            throw new java.lang.NullPointerException("null cannot be added to the set");
        }
        points.add(p);

    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) {
            throw new java.lang.NullPointerException("null cannot be in a set");
        }
        return points.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect)  // all points that are inside the rectangle
    {
        TreeSet<Point2D> setInRectangle = new TreeSet<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                setInRectangle.add(p);
            }
        }
        return setInRectangle;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (isEmpty()) {
            return null;
        }
        Point2D nearestPoint = p;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Point2D candidate : points) {
            double distance = p.distanceTo(candidate);
            if (distance <= nearestDistance && candidate != p) {
                nearestPoint = candidate;
                nearestDistance = distance;
            }
        }
        return nearestPoint;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args)
    {
/*
        Point2D a = new Point2D(3,1);
        PointSET ps = new PointSET();
        System.out.println(ps.isEmpty());
        ps.insert(a);
        System.out.println(ps.isEmpty());
        System.out.println(ps.size());
        System.out.println(ps.nearest(a).toString());
        Point2D b = new Point2D(1,0);
        Point2D c = new Point2D(2,0);
        Point2D d = new Point2D(1,0);
        ps.insert(b);
        ps.insert(c);
        ps.insert(d);
        System.out.println(ps.size());
        System.out.println(ps.nearest(a).toString());

        Point2D e = new Point2D(1,1);
        Point2D f = new Point2D(2,1);
        Point2D g = new Point2D(1,2);
        Point2D j = new Point2D(1,3);
        ps.insert(e);
        ps.insert(f);
        ps.insert(g);
        ps.insert(j);

        for (Point2D i : ps.points){
            System.out.println(i.toString());
          }
        System.out.println(ps.range(new RectHV(0,0,2,1)));
*/
    }

}