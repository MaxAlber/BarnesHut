import java.util.Stack;

public class InternalNode implements Node
{

    // total Mass of this subtree
    private double totalMass;

    // center of Mass of this subtree
    private Vector3 centerOfMass;

    // all nodes of this subtree
    public final Node[] children = new Node[8];

    // the length of this node (length of the octree field)
    private final double length;

    // the position of this node (position of octree field)
    private final Vector3 position = new Vector3(0,0,0);


    // + = 1, - = 0;
    // e.g. : (+,+,+) = 111(binary) = 7(decimal)
    // so (+,+,+) = index 7
    /*
     *children[0]: (x,y,z)=(-,-,-)
     *children[1]: (x,y,z)=(-,-,+)
     *children[2]: (x,y,z)=(-,+,-)
     *children[3]: (x,y,z)=(-,+,+)
     *
     *children[4]: (x,y,z)=(+,-,-)
     *children[5]: (x,y,z)=(+,-,+)
     *children[6]: (x,y,z)=(+,+,-)
     *children[7]: (x,y,z)=(+,+,+)
     *
     * in add() Methode:
     * + bedeutet: body.x > PunktX
     * - bedeutet: body.x < PunktX
     */

    public InternalNode(Vector3 position, double length)
    {

        // uncomment this to draw the octree

        //StdDraw.setPenColor(Color.white);
        /*
        StdDraw.line(position.getX()-length/2, position.getY()+length/2,
                position.getX()+length/2, position.getY()+length/2);
        StdDraw.line(position.getX()+length/2, position.getY()+length/2,
                position.getX()+length/2, position.getY()-length/2);
        StdDraw.line(position.getX()+length/2, position.getY()-length/2,
                position.getX()-length/2, position.getY()-length/2);
        StdDraw.line(position.getX()-length/2, position.getY()-length/2,
                position.getX()-length/2, position.getY()+length/2);
        */

        this.centerOfMass = new Vector3(0,0,0);

        //TODO this.position = position
        this.position.setX(position.getX());
        this.position.setY(position.getY());
        this.position.setZ(position.getZ());
        this.length = length;

        for(int i = 0; i<8; i++)
        {
            this.children[i] = new NullNode();
        }
    }

    // this method calculates the centerOfMass and totalMass
    public void calcmass(CelestialBody b)
    {
        this.totalMass = this.totalMass + b.getMass();
        double x, y, z;

        x = (this.centerOfMass.getX() * (this.totalMass - b.getMass()) + b.getPosition().getX() *
                b.getMass()) / this.totalMass;
        y = (this.centerOfMass.getY() * (this.totalMass - b.getMass()) + b.getPosition().getY() *
                b.getMass()) / this.totalMass;
        z = (this.centerOfMass.getZ() * (this.totalMass - b.getMass()) + b.getPosition().getZ() *
                b.getMass()) / this.totalMass;

        this.centerOfMass = new Vector3(x, y, z);
    }

    // this method calculates the index and adds a new body to the node with the calculated index
    // if there is a null node, it creates a leaf node
    // if there is a leaf node, it creates a internal node and adds both bodies (new body and body of leaf node)
    // to the subnodes of the new internal node.
    // if there is a internal node, it adds the body to one of the subnodes of the internal node
    public Node add(CelestialBody b, Vector3 vector3, double length)
    {

        int index = 7;
        Vector3 newposition = new Vector3(
                this.position.getX() + this.length/4,
                this.position.getY() + this.length/4,
                this.position.getZ() + this.length/4);

        if(b.getPosition().getX()<this.position.getX())
        {
            // if body.x < x, subtract 4 from index
            index = index - 4;

            // calculate new x position
            newposition.setX(this.position.getX() - (this.length / 4));
        }

        if(b.getPosition().getY()<this.position.getY())
        {
            // if body.y < y, subtract 2 from index
            index = index - 2;

            // calculate new y position
            newposition.setY(this.position.getY() - (this.length / 4));
        }

        if(b.getPosition().getZ()<this.position.getZ())
        {
            // if body.z < z, subtract 1 from index
            index = index - 1;

            // calculate new z position
            newposition.setZ(this.position.getZ() - (this.length / 4));
        }
        this.calcmass(b);
        this.children[index] = this.children[index].add(b, newposition, this.length/2);
        return this;
    }



    // iterate over every body to calculate force for every body
    // returns the force that is calculated for the body
    @Override
    public Vector3 calculateForce(CelestialBody body)
    {
        // min is the min distance to calculate force (standard: 0.5)
        double min = 1;

        // s = width of the region of the node
        double s = this.length;

        // d = distance between body and nodes center of mass
        double d = this.centerOfMass.distanceTo(body.getPosition());

        Vector3 force = new Vector3(0,0,0);

        // if s/d < min, calculate the force on the body
        if ((s / d) < min)
        {
            // TODO calculate force
            CelestialBody bodytmp = new CelestialBody("name",
                    this.totalMass,
                    this.centerOfMass);
            force = body.gravitationalForce(bodytmp);
            return force;
        }
        // if s/d >= min, recursively call calculateForce on
        // child node
        else {
            for(int i = 0; i<8; i++)
            {
                force = force.plus(children[i].calculateForce(body));
            }
            return force;
        }
    }

    // iterates over all subtrees and adds all the bodies to the stack
    public Stack<CelestialBody> iterate(Stack<CelestialBody> stack)
    {
        for(int i = 0; i<8; i++) {
            stack = this.children[i].iterate(stack);
        }
        return stack;
    }

    public void calculateForces(Node root, OctreeIterator iterator)
    {

        while(iterator.hasNext())
        {
            CelestialBody body = iterator.next();
            Vector3 force = root.calculateForce(body);
            body.move(force);
            body.draw();
        }
    }

}
