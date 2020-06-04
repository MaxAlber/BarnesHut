import java.util.Iterator;
import java.util.Stack;

// this is an Iterator over the Octree
// to iterate over the octree a stack is used which stores all of the bodies of the tree
public class OctreeIterator implements Iterator<CelestialBody>
{

    Stack<CelestialBody> stack;

    public OctreeIterator(Stack<CelestialBody> stack)
    {
        this.stack = stack;
    }

    @Override
    public boolean hasNext()
    {
        return !stack.isEmpty();
    }

    @Override
    public CelestialBody next()
    {
        return stack.pop();
    }
}
