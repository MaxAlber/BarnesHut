public class NullNode implements Node
{

    @Override
    public Node add(CelestialBody b, Vector3 position, double length)
    {
        return new ExternalNode(b, position, length);
    }

    @Override
    public Vector3 calculateForce(CelestialBody body)
    {
        return new Vector3(0,0,0);
    }

    @Override
    public CelestialBody getBody()
    {
        return null;
    }

    @Override
    public void calculateForces(Node node)
    {

    }

    @Override
    public boolean isExternal()
    {
        return false;
    }
}
