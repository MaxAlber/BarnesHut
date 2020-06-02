public class Tree {
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;
    private InternalNode root;

    public void add(CelestialBody b)
    {
        if (root == null)
        {
            root = new InternalNode(new Vector3(0,0,0), 10*AU);
            root.add(b);
        } else
        {
            root.add(b);
        }
    }

    public void calculateForces()
    {
        root.calculateForces(root);
    }
}
