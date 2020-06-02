import java.awt.*;

public class InternalNode implements Node
{

    private double totalMass; //Gesamtmasse vom Teilbaum
    private Vector3 centerOfMass; //Schwerpunkt vom Teilbaum
    private Node[] children = new Node[8]; //8 Knoten je Ebene

    private double length;
    private Vector3 position = new Vector3(0,0,0);


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
        /*
        StdDraw.setPenColor(Color.white);
        StdDraw.circle(x,y,20);
        StdDraw.line(x-length/2, y+length/2, x+length/2, y+length/2);
        StdDraw.line(x+length/2, y+length/2, x+length/2, y-length/2);
        StdDraw.line(x+length/2, y-length/2, x-length/2, y-length/2);
        StdDraw.line(x-length/2, y-length/2, x-length/2, y+length/2);
         */

        this.centerOfMass = new Vector3(0,0,0);
        this.position.setX(position.getX());
        this.position.setY(position.getY());
        this.position.setZ(position.getZ());
        this.length = length;
    }

    // this method calculates the schwerpunkt and gesamtmasse
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

    // this methods adds bodys to the tree

    // this method checks, which node should be filled
    public void add(CelestialBody b)
    {
        int index = 7;
        Vector3 newposition = new Vector3(
                position.getX() + length/4,
                position.getY() + length/4,
                position.getZ() + length/4);

        if(b.getPosition().getX()<position.getX())
        {
            // if body.x < x, subtract 4 from index
            index = index - 4;

            // calculate new x position
            newposition.setX(position.getX() - (length / 4));
        }

        if(b.getPosition().getY()<position.getY())
        {
            // if body.y < y, subtract 2 from index
            index = index - 2;

            // calculate new y position
            newposition.setY(position.getY() - (length / 4));
        }

        if(b.getPosition().getZ()<position.getZ())
        {
            // if body.z < z, subtract 1 from index
            index = index - 1;

            // calculate new z position
            newposition.setZ(position.getZ() - (length / 4));
        }

        // add to node
        if(this.children[index] == null)
        {
            this.children[index] = new ExternalNode(b, newposition, (length / 2));
            this.calcmass(b);
        }
        else
            {
            if(this.children[index].isExternal())
            {
                this.calcmass(b);
                CelestialBody b1 = this.children[index].getBody();
                this.children[index] = new InternalNode(newposition, length/2);
                this.children[index].add(b);
                this.children[index].add(b1);
            }
            else
            {
                this.calcmass(b);
                this.children[index].add(b);
            }
        }
    }



    // iterate over every body to calculate force for every body
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
        // calculate ratio s/d for the node
        if ((s / d) < min)
        {
            // TODO calculate force
            CelestialBody bodytmp = new CelestialBody("name",
                    this.totalMass,
                    1,
                    this.centerOfMass,
                    new Vector3(0,0,0),
                    Color.GRAY);
            force = body.gravitationalForce(bodytmp);
            return force;
        }
        else {
            for(int i = 0; i<8; i++)
            {
                // if children not null
                if(this.children[i] != null)
                {
                    // if s/d > min, recursively call calculateForce on
                    // child node
                    force = force.plus(children[i].calculateForce(body));
                }
            }
            return force;
        }
    }

    public void calculateForces(InternalNode root)
    {
        for(int i = 0; i<8; i++)
        {
            if(this.children[i] != null)
            {
                // check if node is external node
                boolean externalNode = this.children[i].isExternal();

                if (externalNode)
                {
                    Vector3 force = root.calculateForce(this.children[i].getBody());
                    this.children[i].getBody().move(force);
                    this.children[i].getBody().draw();
                }
                // else go to child note
                else {
                    this.children[i].calculateForces(root);
                }
            }
        }
    }

    // method checks if node is an external node
    public boolean isExternal()
    {
        return false;
    }

    public CelestialBody getBody()
    {
        return null;
    }
}
