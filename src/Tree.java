public class Tree {
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;
    private Node root;

    public void add(CelestialBody b)
    {
        if (root == null)
        {
            root = new Node(b, 0, 0, 0, 10*AU);
        } else
        {
            root.add(b);
        }
    }

    public void calculateForces()
    {
        root.calculateForce(root);
    }



}
