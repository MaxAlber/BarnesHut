import java.util.Stack;

public class Tree implements Iterable
{
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;
    public InternalNode root;

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

    // this method iterates over all the bodies in the tree (with an iterator over the octree)
    // and calculates the forces for the bodies.
    public void calculateForces()
    {
        Stack<CelestialBody> stack = new Stack<>();
        stack = this.root.iterate(stack);

        OctreeIterator iterator = new OctreeIterator(stack);
        root.calculateForces(root, iterator);
    }

    // generates an iterator for the tree
    // to iterate over the tree, a stack is used which stores all of the bodies of the tree
    @Override
    public OctreeIterator iterator()
    {
        Stack<CelestialBody> stack = new Stack<>();
        stack = this.root.iterate(stack);

        OctreeIterator iterator = new OctreeIterator(stack);
        return iterator;
    }
}
