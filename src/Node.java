import java.awt.*;

public interface Node
{
    CelestialBody body = null;

    // add adds a body to the tree
    void add(CelestialBody b);

    // calculates the force of all bodys or nodes on the parameter body
    // and returns the force
    Vector3 calculateForce(CelestialBody body);

    // this method iterates over all bodys in the tree and calculates the force for
    // every body with the method calculateForce
    void calculateForces(InternalNode root);

    // returns true if the node is an external node,
    // if its an internal node it returns false
    boolean isExternal();

    // returns the body of the node
    CelestialBody getBody();
}