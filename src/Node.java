import java.awt.*;

public interface Node
{
    CelestialBody body = null;

    // adds a body to the tree
    Node add(CelestialBody b, Vector3 vector, double length);

    // calculates the force of all bodies or nodes on the parameter body
    // and returns the force
    Vector3 calculateForce(CelestialBody body);

    // returns the body of a Node
    CelestialBody getBody();

    // TODO add iterator to tree instead
    void calculateForces(Node node);

    boolean isExternal();
}