import java.awt.*;

public class Node {
    public static final double AU = 150e9;

    private CelestialBody body;
    private double gesamtmasse; //Gesamtmasse vom Teilbaum
    private Vector3 schwerpunkt; //Schwerpunkt vom Teilbaum
    private Node[] children = new Node[8]; //8 Knoten je Ebene

    private double x,y,z, length;


    /*
     *children[0]: (x,y,z)=(+,+,+)
     *children[1]: (x,y,z)=(-,+,+)
     *children[2]: (x,y,z)=(-,-,+)
     *children[3]: (x,y,z)=(+,-,+)
     *
     *children[4]: (x,y,z)=(+,+,-)
     *children[5]: (x,y,z)=(-,+,-)
     *children[6]: (x,y,z)=(-,-,-)
     *children[7]: (x,y,z)=(+,-,-)
     *
     * in add() Methode:
     * + bedeutet: body.x > PunktX
     * - bedeutet: body.x < PunktX
     */

    public double getGesamtmasse() {
        return gesamtmasse;
    }

    public Vector3 getSchwerpunkt() {
        return schwerpunkt;
    }

    public Node(CelestialBody body, double x, double y, double z, double length)
    {
        /*
        StdDraw.setPenColor(Color.white);
        StdDraw.circle(x,y,20);
        StdDraw.line(x-length/2, y+length/2, x+length/2, y+length/2);
        StdDraw.line(x+length/2, y+length/2, x+length/2, y-length/2);
        StdDraw.line(x+length/2, y-length/2, x-length/2, y-length/2);
        StdDraw.line(x-length/2, y-length/2, x-length/2, y+length/2);
        */
        this.body = body;
        this.gesamtmasse = body.getMass();
        this.schwerpunkt = new Vector3(body.getPosition()); //// neue Vector3 erzeugen, vermeiden Identität Problem
        this.x = x;
        this.y = y;
        this.z = z;
        this.length = length;
    }

    // this method calculates the schwerpunkt and gesamtmasse
    private void calcmass(CelestialBody b) { //berechnen Gesamtmasse und Schwerpunkt der Node
        this.gesamtmasse = this.gesamtmasse + b.getMass();

        double x, y, z;
        x = (this.schwerpunkt.getX() * (this.gesamtmasse - b.getMass()) + b.getPosition().getX() * b.getMass()) /
                this.gesamtmasse;
        y = (this.schwerpunkt.getY() * (this.gesamtmasse - b.getMass()) + b.getPosition().getY() * b.getMass()) /
                this.gesamtmasse;
        z = (this.schwerpunkt.getZ() * (this.gesamtmasse - b.getMass()) + b.getPosition().getZ() * b.getMass()) /
                this.gesamtmasse;

        this.schwerpunkt = new Vector3(x, y, z);

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
            this.gesamtmasse = 0;
            this.schwerpunkt = new Vector3(0,0,0);
            this.findNode(b1);
            this.findNode(b);
            this.calcmass(b1);
            this.calcmass(b);
        }
    }

    // this method checks, which node should be filled
    public void findNode(CelestialBody b)
    {
        if(b.getPosition().getX()<x)
        {
            if(b.getPosition().getY()<y)
            {
                if(b.getPosition().getZ()<z)
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
                if(b.getPosition().getZ()<z)
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
            if(b.getPosition().getY()<y)
            {
                if(b.getPosition().getZ()<z)
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
                if(b.getPosition().getZ()<z)
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
        double d = this.schwerpunkt.distanceTo(body.getPosition());

        Vector3 force = new Vector3(0,0,0);

        // check if node is external node
        boolean externalNode = isExternal();

        // if externalNode, calculate force and return
        if(externalNode && d!=0)
        {
            CelestialBody bodytmp = new CelestialBody("name", this.gesamtmasse, 1,
                    this.schwerpunkt, new Vector3(0,0,0), Color.GRAY);
            force = body.gravitationalForce(bodytmp);
            return force;

        }
        // if not external node:
        // calculate ratio s/d for the node
        else if ((s / d) < min)
        {
            // TODO calculate force
            CelestialBody bodytmp = new CelestialBody("name", this.gesamtmasse, 1,
                    this.schwerpunkt, new Vector3(0,0,0), Color.GRAY);
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
