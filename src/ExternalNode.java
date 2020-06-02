import java.awt.*;

public class ExternalNode implements Node
{

    private final CelestialBody body;
    private final Node[] children = new ExternalNode[8];

    public ExternalNode(CelestialBody body, Vector3 position, double length)
    {
        this.body = body;
        Vector3 position1 = new Vector3(0, 0, 0);
        position1.setX(position.getX());
        position1.setY(position.getY());
        position1.setZ(position.getZ());
    }

    @Override
    public void add(CelestialBody b)
    {

    }

    @Override
    public Vector3 calculateForce(CelestialBody body)
    {
        Vector3 force = new Vector3(0,0,0);

        if(this.body.getPosition().distanceTo(body.getPosition())!=0) {
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

    @Override
    public void calculateForces(InternalNode root) {
    }

    public CelestialBody getBody()
    {
        return this.body;
    }
}
