import java.util.Stack;

public class NullNode implements Node
{

    // returns a new leaf Node, with the body given to the method as new body in the node.
    @Override
    public Node add(CelestialBody b, Vector3 position, double length)
    {
        return new LeafNode(b, position, length);
    }

    // returns (0,0,0), since a NullNode has no body and there is no force to calculate
    // on a body
    @Override
    public Vector3 calculateForce(CelestialBody body)
    {
        return new Vector3(0,0,0);
    }

    // returns the stack, since there is no body to add
    @Override
    public Stack iterate(Stack<CelestialBody> stack) {
        return stack;
    }
}
