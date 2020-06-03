import java.awt.*;

public class ExternalNode implements Node
{

    private final CelestialBody body;

    public ExternalNode(CelestialBody body, Vector3 position, double length)
    {
        this.body = body;
    }

    @Override
    public Node add(CelestialBody b, Vector3 position, double length)
    {
        InternalNode newnode = new InternalNode(position, length);
        newnode.add(b, new Vector3(0,0,0), length);
        newnode.add(this.body, new Vector3(0,0,0), length);
        return newnode;
    }

    @Override
    public Vector3 calculateForce(CelestialBody body)
    {
        Vector3 force = new Vector3(0,0,0);

        if(this.body.getPosition().distanceTo(body.getPosition())!=0)
        {
            // calculate force and return
            CelestialBody bodytmp = new CelestialBody("name",
                    this.body.getMass(),
                    1,
                    this.body.getPosition(),
                    new Vector3(0, 0, 0),
                    Color.GRAY);
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
