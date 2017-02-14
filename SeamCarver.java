/**
 * Created by v-itiupa on 2/3/2017.
 */
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Picture;

import java.util.ArrayList;

public class SeamCarver {
    private Picture mypicture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        this.mypicture = new Picture(picture);

    }
    // current picture
    public Picture picture()
    {
        return this.mypicture;
    }
    // width of current picture
    public int width()
    {
        return this.mypicture.width();
    }
    // height of current picture
    public int height()
    {
        return this.mypicture.height();
    }
    // energy of pixel at column x and row y
    public  double energy(int x, int y)
    {
    // energy of a pixel at the border of the image should be 1000
        if ((y == 0 || y == mypicture.height() - 1) || (x == 0 || x == mypicture.width() - 1))
        {
            return 1000.00;
        }

    // the differences in the red, green, and blue components
    // between pixel (x + 1, y) and pixel (x ? 1, y)
        int Rx1 = mypicture.get(x + 1, y).getRed();
        int Gx1 = mypicture.get(x + 1, y).getGreen();
        int Bx1 = mypicture.get(x + 1, y).getBlue();

        int Rx2 = mypicture.get(x - 1, y).getRed();
        int Gx2 = mypicture.get(x - 1, y).getGreen();
        int Bx2 = mypicture.get(x - 1, y).getBlue();

        int rx = Math.abs(Rx1 - Rx2);
        int gx = Math.abs(Gx1 - Gx2);
        int bx = Math.abs(Bx1 - Bx2);


        int Ry1 = mypicture.get(x, y + 1).getRed();
        int Gy1 = mypicture.get(x, y + 1).getGreen();
        int By1 = mypicture.get(x, y + 1).getBlue();

        int Ry2 = mypicture.get(x, y - 1).getRed();
        int Gy2 = mypicture.get(x, y - 1).getGreen();
        int By2 = mypicture.get(x, y - 1).getBlue();

        int ry = Math.abs(Ry1 - Ry2);
        int gy = Math.abs(Gy1 - Gy2);
        int by = Math.abs(By1 - By2);

        return Math.sqrt(rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by);


    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()
    {
        // transpose picture before building graph
        Picture transposedPicture = transposePicture(this.mypicture);
        int width = transposedPicture.width();
        int height = transposedPicture.height();
        int auxV = height * width;
        int auxW = auxV + 1;

        int[] result = new int[height];
        ArrayList<Integer> arrL = new ArrayList<>();
        AcyclicSP sp = new AcyclicSP(fromPictureToDigraph(transposedPicture), auxV);
        if (sp.hasPathTo(auxW)) {
            for (DirectedEdge e : sp.pathTo(auxW)) {
                arrL.add(e.to());
            }

        }

        for (int i = 0; i < height; i++)
        {
            // Y = INDEX_VALUE - X(our i)*WIDTH
            result[i] = arrL.get(i) - i * width;
            // System.out.println(result[i]);

        }
        this.mypicture = transposePicture(transposedPicture);

        return result;

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {
        int[] result = new int[height()];
        ArrayList<Integer> arrL = new ArrayList<>();

        int width = this.mypicture.width();
        int auxV = this.mypicture.height() * width;
        int auxW = auxV + 1;

        AcyclicSP sp = new AcyclicSP(fromPictureToDigraph(this.mypicture), auxV);

        if (sp.hasPathTo(auxW)) {
            for (DirectedEdge e : sp.pathTo(auxW)) {
                arrL.add(e.to());
            }

        }

        for (int i = 0; i < height(); i++)
        {
        // X = INDEX_VALUE - Y(our i)*WIDTH
            result[i] = arrL.get(i) - i * width;
        // System.out.println(result[i]);

        }

        return result;

    }
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        if (seam == null) {
            throw new java.lang.NullPointerException();
        }
        if (this.height() <= 1 || seam.length != this.width()) {
            throw  new java.lang.IllegalArgumentException();
        }

        // update current picture by removing pixels with coordinates X: i=0;i<w;i++ and Y are sem[i])

        int nw = width();
        int nh = height() - 1;
        Picture result = new Picture(nw, nh);

        for (int w = 0; w < nw; w++) {
            for (int h = 0; h < seam[w]; h++)
            {
                result.set(w, h, mypicture.get(w, h));
            }
            for (int h = seam[w]; h < result.height(); h++)
            {
                result.set(w, h, mypicture.get(w, h + 1));
            }
        }

        this.mypicture = result;

    }
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)
    {
        if (seam == null) {
            throw new java.lang.NullPointerException();
        }

        if (this.width() <= 1 || seam.length != this.height()) {
            throw  new java.lang.IllegalArgumentException();
        }

        // update current picture by removing pixels with coordinates Y: i=0;i<h;i++ and X are seam[i])
        int nw = width() - 1; // 5
        int nh = height(); // 5
        Picture result = new Picture(nw, nh);
        // (5, 0), (4, 1), (3, 2), (2, 3), and (3, 4)
        // 5,4,3,2,3
        for (int h = 0; h < nh; h++) {
            for (int w = 0; w < seam[h]; w++)
            {
                result.set(w, h, mypicture.get(w, h));
            }
            for (int w = seam[h]; w < result.width(); w++)
            {
                result.set(w, h, mypicture.get(w + 1, h));
            }
        }

        this.mypicture = result;
    }

    private boolean isValidSeam(int[] seamCandidate)
    {

        return false;
    }

    private Picture transposePicture(Picture sourcePicture)
    {
        int newWidth = sourcePicture.height();
        int newHeight = sourcePicture.width();
        Picture result = new Picture(newWidth, newHeight);
        for (int c = 0; c < newWidth; c++)
        {
            for (int r = 0; r < newHeight; r++)
            {
                result.set(c, r, sourcePicture.get(r, c));
            }

        }
        return result;
    }

// helpers
    private EdgeWeightedDigraph fromPictureToDigraph(Picture picture)
    {

        int hi = picture.height();
        int wi = picture.width();
        int vCnt = hi * wi;
        this.mypicture = picture;

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(vCnt + 2);
        for (int i = 0; i < wi; i++)
        {
            for (int j = 0; j < hi; j++)
            {
                int vertexV = j * wi + i;
                if (j + 1 < hi)
                {
                    int vertexW = (j + 1) * wi + i;

                    DirectedEdge e1 = new DirectedEdge(vertexV, vertexW, energy(i, j + 1));
                    G.addEdge(e1);

                    if (i >= 0 && i < wi-1)
                    {
                        vertexW = (j + 1) * wi + i + 1;
                        DirectedEdge e2 = new DirectedEdge(vertexV, vertexW, energy(i + 1, j + 1));
                        G.addEdge(e2);
                    }

                    if (i >= 1 && i <= wi - 1)
                    {
                        vertexW = (j + 1) * wi + i - 1;
                        DirectedEdge e3 = new DirectedEdge(vertexV, vertexW, energy(i - 1, j + 1));
                        G.addEdge(e3);
                    }

                }

            }
            DirectedEdge top = new DirectedEdge(vCnt, i, 1000.00);
            G.addEdge(top);

        }
        for (int k = vCnt - wi; k < vCnt; k++)
        {
            DirectedEdge bottom = new DirectedEdge(k, vCnt + 1, 1000.00);
            G.addEdge(bottom);
        }

        return G;
    }



    public static void main(String[] args) {
        /*
        Picture inputImg = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(inputImg);

        System.out.println(sc.fromPictureToDigraph(inputImg));

        System.out.println("==============================");
        sc.findVerticalSeam();
        System.out.println("==============================");
        sc.findHorizontalSeam();
        System.out.println("==============================");

        sc.mypicture.show();

        sc.removeVerticalSeam(sc.findVerticalSeam());
        sc.mypicture.show();

        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        sc.mypicture.show();
        */
    }

}