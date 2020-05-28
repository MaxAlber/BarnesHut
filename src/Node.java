public interface Node
{
    void add(CelestialBody b);
    Vector3 calculateForce(CelestialBody body);
}