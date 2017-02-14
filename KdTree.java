import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by v-itiupa on 1/8/2017.
 */
public class KdTree {

    private String RED = "RED";
    private String BLUE = "BLUE";
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    private class Node {
        private Point2D p;
        private String color;
        private Node left, right;
        private Point2D prevPoint;
        private boolean isLeft;


        public Node(Point2D p, String color, Point2D pp, boolean direction) {
            this.p = p;
            this.color = color;
            this.prevPoint = pp;
            this.isLeft = direction;
        }

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("null cannot be added to the set");
        }
        root = insert(root, p, RED, null, true);
    }

    private Node insert(Node node, Point2D p, String color, Point2D prevPoint, boolean isLeft) {
        if (node == null)
        {
            size = size + 1;
            return new Node(p, color, prevPoint, isLeft);
        }

        if (node.p.equals(p)) {
            return node;
        }

        if (node.color.equals(RED)) {
            color = BLUE;
        }
        else {
            color = RED;
        }

        prevPoint = node.p;

        if (node.color.equals(RED) && p.x() < node.p.x() || node.color.equals(BLUE) && p.y() < node.p.y()) {
            node.left = insert(node.left, p, color, prevPoint, true);
        } else {
            node.right = insert(node.right, p, color, prevPoint, false);
        }
        return node;

    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("null cannot be in a set");
        }
        return get(p) != null;
    }

    private Point2D get(Point2D p) {
        return get(root, p);
    }

    private Point2D get(Node node, Point2D p) {
        if (node == null) return null;
        if (node.p.equals(p)) {
            return p;
        }
        if (node.color.equals(RED) && p.x() < node.p.x() || node.color.equals(BLUE) && p.y() < node.p.y()) {
            return get(node.left, p);
        }
        else return get(node.right, p);
    }


    public void draw() {

        draw(root, RED);
    }

    private void draw(Node node, String color) {
        if (node == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.p.x(), node.p.y());

        if (node.color.equals(RED)) {

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            if (node == root) {
                StdDraw.line(node.p.x(), 0, node.p.x(), 1);
            }
            else if (node.isLeft) {
                StdDraw.line(node.p.x(), 0, node.p.x(), node.prevPoint.y());
            }
            else {
                StdDraw.line(node.p.x(), 1, node.p.x(), node.prevPoint.y());
            }

            if (node.left != null) {
                draw(node.left, BLUE);
            }
            if (node.right != null) {
                draw(node.right, BLUE);
            }
        }
        if (node.color.equals(BLUE)) {

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);

            if (node.isLeft) {
                StdDraw.line(0, node.p.y(), node.prevPoint.x(), node.p.y());
            }
            else {
                StdDraw.line(node.prevPoint.x(), node.p.y(), 1, node.p.y());
            }

            if (node.left != null) {
                draw(node.left, RED);
            }
            if (node.right != null) {
                draw(node.right, RED);
            }

        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> setIsInRectangle = new Queue<Point2D>();
        inrange(root, setIsInRectangle, rect);
        return setIsInRectangle;
    }

    private void inrange(Node node, Queue<Point2D> q, RectHV rect) {
        if (node == null) return;
        if (rect.contains(node.p)) {
            q.enqueue(node.p);
        }
        inrange(node.left, q, rect);
        inrange(node.right, q, rect);
    }

    public Point2D nearest(Point2D p) {
        if (size() == 0)
            return null;
        return nearest(root, root.p, p);

    }

    private Point2D nearest(Node node, Point2D closestPoint, Point2D queryPoint) {
        Point2D closestPointLeft, closestPointRight;
        closestPointLeft = closestPoint;
        closestPointRight = closestPoint;

        if (node.left != null) {
            if (closestPoint.distanceTo(queryPoint) > node.left.p.distanceTo(queryPoint) || node.left.right != null || node.left.left != null)
                closestPointLeft = nearest(node.left, node.left.p, queryPoint);
        }
        if (node.right != null) {
            if (closestPoint.distanceTo(queryPoint) > node.right.p.distanceTo(queryPoint) || node.right.right != null || node.right.left != null)
                closestPointRight = nearest(node.right, node.right.p, queryPoint);
        }
        if (closestPointLeft.distanceTo(queryPoint) > closestPointRight.distanceTo(queryPoint)) {
            return closestPointRight;
        }
        else
            return closestPointLeft;

    }

    public static void main(String[] args) {
/*
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.13126, 0.38097)) ;
        kd.insert(new Point2D(0.33776, 0.56723)) ;
        kd.insert(new Point2D(0.45403, 0.31891)) ;
        kd.nearest(new Point2D(0.34064, 0.12102));
        System.out.println(kd.nearest(new Point2D(0.34064, 0.12102))) ;

        /* student nearest() = (0.13126, 0.38097) - reference nearest() = (0.45403, 0.31891)

        Point2D q = new Point2D(0.1,0.1);

        Point2D a = new Point2D(0.7,0.2);
        kd.insert(a);
        Point2D b = new Point2D(0.5,0.4);
        kd.insert(b);
        Point2D c = new Point2D(0.2,0.3);
        kd.insert(c);
        System.out.println(kd.nearest(q).toString());
        kd.insert(c);

       // System.out.println(kd.isEmpty());
       // System.out.println(kd.isEmpty());
       // System.out.println(kd.size());
       // System.out.println(kd.size());

        Point2D d = new Point2D(0.4,0.7);
        kd.insert(d);
        Point2D e = new Point2D(0.9,0.6);
        kd.insert(e);
        Point2D e1 = new Point2D(0.9,0.6789);
        kd.insert(c);

        System.out.println(kd.contains(b));
        System.out.println(kd.contains(e1));

        kd.draw();


       // Point2D e = new Point2D(1,1);
        Point2D f = new Point2D(2,1);
        Point2D g = new Point2D(1,2);
        Point2D j = new Point2D(1,3);
        kd.insert(e);
        kd.insert(f);
        kd.insert(g);
        kd.insert(j);

        System.out.println(kd.range(new RectHV(0, 0, 2, 1)));
*/
    }
}
