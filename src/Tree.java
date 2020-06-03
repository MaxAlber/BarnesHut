import java.util.Iterator;

public class Tree
{
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;
    private Node root;

    public void add(CelestialBody b)
    {
        if (root == null)
        {
            root = new InternalNode(new Vector3(0,0,0), 10*AU);
            root.add(b, new Vector3(0,0,0), 0);
        } else
        {
            root.add(b, new Vector3(0,0,0), 0);
        }
    }

    public void calculateForces()
    {
        root.calculateForces(root);
    }
}
