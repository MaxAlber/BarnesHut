import java.awt.*;

// This class represents vectors in a 3D vector space.
public class Vector3 {
    private double x;
    private double y;
    private double z;



    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }

    public void setX(double x)
    {
        this.x = x;
    }
    public void setY(double y)
    {
        this.y = y;
    }
    public void setZ(double z)
    {
        this.z = z;
    }

    // Returns the sum of this vector and vector 'v'.
    public Vector3 plus(Vector3 v)
    {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {
        return new Vector3(this.x * d, this.y * d, this.z * d);
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {
        Vector3 result = new Vector3(this.x, this.y, this.z);
        result.x = result.x - v.x;
        result.y = result.y - v.y;
        result.z = result.z - v.z;
        return result;
    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {

        //TODO: implement method.
        return this.minus(v).length();
    }

    // Returns the length (norm) of this vector.
    public double length() {

        //TODO: implement method.
        Vector3 result = new Vector3(this.x, this.y, this.z);
        double dX = result.x;
        double dY = result.y;
        double dZ = result.z;

        return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }

    // Normalizes this vector: changes the length of this vector such that it becomes one.
    // The direction and orientation of the vector is not affected.
    public void normalize() {

        //TODO: implement method.
        double length = length();
        this.x /= length;
        this.y /= length;
        this.z /= length;

    }

    // Draws a filled circle with the center at (x,y) coordinates of this vector
    // in the existing StdDraw canvas. The z-coordinate is not used.
    public void drawAsDot(double radius, Color color) {

        //TODO: implement method.
        StdDraw.setPenColor(color);
        StdDraw.point(x, y);

    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {

        //TODO: implement method.
        return "[" + x + "," + y + "," + z + "]";
    }
}

