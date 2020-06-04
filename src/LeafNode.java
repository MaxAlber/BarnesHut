import java.awt.*;

public class LeafNode implements Node
{

    private final CelestialBody body;

    public LeafNode(CelestialBody body, Vector3 position, double length)
    {
        this.body = body;
    }

    // creates a new internal Node and adds the body that is given to this method as a parameter
    // and the body of this node to the new internal Node.
    // Then it returns the new internal Node
    @Override
    public Node add(CelestialBody b, Vector3 position, double length)
    {
        InternalNode newnode = new InternalNode(position, length);
        newnode.add(b, new Vector3(0,0,0), length);
        newnode.add(this.body, new Vector3(0,0,0), length);
        return newnode;
    }

    // calculates the force of this body on the body that is given to the method as a parameter.
    // if the the distance of this body to the body that is given as a parameter is zero,
    // it means they are the same body and it returns (0,0,0) as force
    @Override
    public Vector3 calculateForce(CelestialBody body)
    {
        Vector3 force = new Vector3(0,0,0);

        if(this.body.getPosition().distanceTo(body.getPosition())!=0)
        {
            // calculate force and return
            CelestialBody bodytmp = new CelestialBody("name",
                    this.body.getMass(),
                    this.body.getPosition());
            force = body.gravitationalForce(bodytmp);
        }
        return force;
    }

    @Override
    public boolean isExternal()
    {
        return true;
    }

    public CelestialBody getBody()
    {
        return this.body;
    }

    @Override
    public void calculateForces(Node node)
    {

    }
}
