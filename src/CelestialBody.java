import java.awt.*;

public class CelestialBody {

    // gravitational constant
    public static final double G = 6.6743e-11;

    private final String name;
    private final double mass;
    private Vector3 position;
    private Vector3 velocity;

    public CelestialBody(String name, double mass, Vector3 position) {
        this.name = name;
        this.mass = mass;
        this.position = position;
        this.velocity = new Vector3(0,0,0);
    }


    public Vector3 getPosition()
    {
        return position;
    }

    public double getMass() {
        return mass;
    }

    // Returns a vector representing the gravitational force exerted by 'body' on this celestial body.
    public Vector3 gravitationalForce(CelestialBody body) {
        Vector3 direction = body.position.minus(this.position);
        double r = direction.length();
        direction.normalize();
        double force = G * this.mass * body.mass / (r * r);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force.)
    public void move(Vector3 force) {
        Vector3 oldPosition = this.position;
        Vector3 newPosition = this.position.plus(this.velocity).plus(force.times(1 / mass));
        velocity = newPosition.minus(oldPosition);
        position = newPosition;

    }

    // Returns a string with the information about this celestial body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    public String toString() {
        return name + ", " + mass + " kg, position: " + position.toString() +
                " m, movement: " + velocity.toString() + " m/s.";
    }

    // Draws the celestial body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    public void draw()
    {
        position.drawAsDot(Color.white);
    }

}
