import java.awt.*;

public class Node {
    public static final double AU = 150e9;

    private CelestialBody body;
    private double totalMass; //Gesamtmasse vom Teilbaum
    private Vector3 centerOfMass; //Schwerpunkt vom Teilbaum
    private Node[] children = new Node[8]; //8 Knoten je Ebene

    private double x,y,z, length;


    /*
     *children[0]: (x,y,z)=(-,-,-) 000
     *children[1]: (x,y,z)=(-,-,+) 001
     *children[2]: (x,y,z)=(-,+,-) 010
     *children[3]: (x,y,z)=(-,+,+) 011
     *
     *children[4]: (x,y,z)=(+,-,-) 100
     *children[5]: (x,y,z)=(+,-,+) 101
     *children[6]: (x,y,z)=(+,+,-) 110
     *children[7]: (x,y,z)=(+,+,+) 111
     */

    public Node(CelestialBody body, double x, double y, double z, double length) {
        /*
        StdDraw.setPenColor(Color.white);
        StdDraw.circle(x,y,20);
        StdDraw.line(x-length/2, y+length/2, x+length/2, y+length/2);
        StdDraw.line(x+length/2, y+length/2, x+length/2, y-length/2);
        StdDraw.line(x+length/2, y-length/2, x-length/2, y-length/2);
        StdDraw.line(x-length/2, y-length/2, x-length/2, y+length/2);
        */
        this.body = body;
        this.totalMass = body.mass();
        this.centerOfMass = new Vector3(body.position()); //// neue Vector3 erzeugen, vermeiden Identität Problem
        this.x = x;
        this.y = y;
        this.z = z;
        this.length = length;
    }

    // this method calculates the schwerpunkt and gesamtmasse
    private void calcmass(CelestialBody b) { //berechnen Gesamtmasse und Schwerpunkt der Node
        this.totalMass = this.totalMass + b.mass();
        double x, y, z;

        x = (this.centerOfMass.x() * (this.totalMass - b.mass()) + b.x() * b.mass()) / this.totalMass;
        y = (this.centerOfMass.y() * (this.totalMass - b.mass()) + b.y() * b.mass()) / this.totalMass;
        z = (this.centerOfMass.z() * (this.totalMass - b.mass()) + b.z() * b.mass()) / this.totalMass;

        this.centerOfMass = new Vector3(x, y, z);

    }

    // this methods adds bodys to the tree
    public void add(CelestialBody b)
    {
        if(!this.isExternal())
        {
            this.calcmass(b);
            this.findNode(b);
        }
        else if(this.isExternal())
        {
            CelestialBody b1 = this.body;
            this.totalMass = 0;
            this.centerOfMass = new Vector3(0,0,0);
            this.findNode(b1);
            this.findNode(b);
            this.calcmass(b1);
            this.calcmass(b);
        }
    }

    // this method checks, which node should be filled
    public void findNode(CelestialBody b)
    {
        if(b.x()<x)
        {
            if(b.y()<y)
            {
                if(b.z()<z)
                {
                    //6
                    if(this.children[6] == null)
                    {
                        this.children[6] = new Node(b, x - (length / 4), y - (length / 4), z - (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[6].add(b);
                    }
                }
                else
                {
                    //2
                    if(this.children[2] == null)
                    {
                        this.children[2] = new Node(b, x - (length / 4), y - (length / 4), z + (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[2].add(b);
                    }
                }
            }
            else
            {
                if(b.z()<z)
                {
                    //5
                    if(this.children[5] == null)
                    {
                        this.children[5] = new Node(b,x - (length / 4), y + (length / 4), z - (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[5].add(b);
                    }
                }
                else
                {
                    //1
                    if(this.children[1] == null)
                    {
                        this.children[1] = new Node(b, x - (length / 4), y + (length / 4), z + (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[1].add(b);
                    }
                }
            }
        }
        else
        {
            if(b.y()<y)
            {
                if(b.z()<z)
                {
                    //7
                    if(this.children[7] == null)
                    {
                        this.children[7] = new Node(b, x + (length / 4), y - (length / 4), z - (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[7].add(b);
                    }
                }
                else
                {
                    //3
                    if(this.children[3] == null)
                    {
                        this.children[3] = new Node(b, x + (length / 4), y - (length / 4), z + (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[3].add(b);
                    }
                }
            }
            else
            {
                if(b.z()<z)
                {
                    //4
                    if(this.children[4] == null)
                    {
                        this.children[4] = new Node(b, x + (length / 4), y + (length / 4), z - (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[4].add(b);
                    }
                }
                else
                {
                    //0
                    if(this.children[0] == null)
                    {
                        this.children[0] = new Node(b, x + (length / 4), y + (length / 4), z + (length / 4),
                                (length / 2));
                    }
                    else {
                        this.children[0].add(b);
                    }
                }
            }
        }
    }



    // iterate over every body to calculate force for every body
    public Vector3 calculateForce(CelestialBody body)
    {

        // min is the min distance to calculate force (standard: 0.5)
        double min = 0.5;

        // s = width of the region of the node
        double s = this.length;

        // d = distance between body and nodes center of mass
        double d = this.centerOfMass.distanceTo(body.position());

        Vector3 force = new Vector3(0,0,0);

        // check if node is external node
        boolean externalNode = isExternal();

        // if externalNode, calculate force and return
        if(externalNode && d!=0)
        {
            CelestialBody bodytmp = new CelestialBody("name", this.totalMass, 1,
                    this.centerOfMass, new Vector3(0,0,0), Color.GRAY);
            force = body.gravitationalForce(bodytmp);
            return force;

        }
        // if not external node:
        // calculate ratio s/d for the node
        else if ((s / d) < min)
        {
            // TODO calculate force
            CelestialBody bodytmp = new CelestialBody("name", this.totalMass, 1,
                    this.centerOfMass, new Vector3(0,0,0), Color.GRAY);
            force = body.gravitationalForce(bodytmp);
            return force;
        }
        else {
            for(int i = 0; i<8; i++)
            {
                // if children not null
                if(this.children[i] != null)
                {
                    // if s/d > min, recursively call calculateForce on child node
                    force = force.plus(children[i].calculateForce(body));
                }
            }
            return force;
        }
    }

    public void calculateForce(Node root)
    {
        for(int i = 0; i<8; i++)
        {
            if(this.children[i] != null)
            {
                // check if node is external node
                boolean externalNode = this.children[i].isExternal();

                if (externalNode)
                {
                    Vector3 force = root.calculateForce(this.children[i].body);
                    this.children[i].body.move(force);
                    this.children[i].body.draw();
                }
                // else go to child note
                else {
                    this.children[i].calculateForce(root);
                }
            }
        }
    }

    // method checks if node is an external node
    public boolean isExternal()
    {
        // check if node is external node
        boolean externalNode = true;
        for(int i = 0; i<8; i++)
        {
            if(this.children[i] != null)
            {
                externalNode = false;
                break;
            }
        }
        return externalNode;
    }

    public Node[] getChildren()
    {
        return this.children;
    }

    public CelestialBody getBody()
    {
        return this.body;
    }

}
