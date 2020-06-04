import java.util.Stack;

public interface Node
{
    // adds a body to the tree
    Node add(CelestialBody b, Vector3 vector, double length);

    // calculates the force of all bodies or nodes on the parameter body
    // and returns the force
    Vector3 calculateForce(CelestialBody body);

    // iterates over the subtree of this node and adds the bodies in the nodes
    // to the stack in the parameter
    // this method is used to generate a iterator over the octree with a stack
    Stack iterate(Stack<CelestialBody> stack);
}