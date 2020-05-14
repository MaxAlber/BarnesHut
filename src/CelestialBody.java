import java.awt.*;


public class CelestialBody {

    // gravitational constant
    public static final double G = 6.6743e-11;

    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 velocity;
    private Color color; // for drawing the body.

    public CelestialBody(String name, double mass, double radius, Vector3 position,
                         Vector3 velocity, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.velocity = velocity;
        this.color = color;
    }

    public CelestialBody(CelestialBody body) {
        this.name = body.name;
        this.mass = body.mass;
        this.radius = body.radius;
        this.position = body.position;
        this.velocity = body.velocity;
        this.color = body.color;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Vector3 getPosition() {
        return position;
    }

    public double getMass() {
        return mass;
    }

    public double distanceTo(CelestialBody body) {
        return this.position.distanceTo(body.position);
    }

    public void resetForce() {
        position.setX(0);
        position.setY(0);
        position.setZ(0);
    }

    // Returns a vector representing the gravitational force exerted by 'body' on this celestial body.
    public Vector3 gravitationalForce(CelestialBody body) {
        Vector3 direction = body.position.minus(this.position);
        double r = direction.length();
        direction.normalize();
        double force = Simulation.G * this.mass * body.mass / (r * r);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force.)
    public void move(Vector3 force) {
        //Methode wird sowie move(fx,fy,fz) aus Klasse Body erstellt
        Vector3 oldPosition = this.position;
        Vector3 newPosition = this.position.plus(this.velocity).plus(force.times(1 / mass));
        velocity = newPosition.minus(oldPosition);
        position = newPosition;

    }

    // Returns a string with the information about this celestial body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    public String toString() {
        return name + ", " + mass + " kg, radius: " + radius + " m, position: " + position.toString() +
                " m, movement: " + velocity.toString() + " m/s.";
    }


    // Prints the information about this celestial body including
    // name, mass, radius, position and current movement, to the console (without newline).
    // Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s.
    public void print() {
        System.out.print(toString());
    }


    // Draws the celestial body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    public void draw()
    {
        position.drawAsDot(1e9 * Math.log10(radius), color);
    }

}
