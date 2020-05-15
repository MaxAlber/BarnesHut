import java.awt.*;

public class Node {

    private CelestialBody body;
    private double gesamtmasse; //Gesamtmasse vom Teilbaum
    private Vector3 schwerpunkt; //Schwerpunkt vom Teilbaum
    private Node[] children = new Node[8]; //8 Knoten je Ebene

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

    public Node(CelestialBody body) {
        this.body = body;
        this.gesamtmasse = body.getMass();
        this.schwerpunkt = new Vector3(body.getPosition()); //// neue Vector3 erzeugen, vermeiden Identität Problem
    }

    private void GMundSP(CelestialBody b) { //berechnen Gesamtmasse und Schwerpunkt der Node
        gesamtmasse = gesamtmasse + b.getMass();

        double x, y, z;
        x = (schwerpunkt.getX() * (gesamtmasse - b.getMass()) + b.getPosition().getX() * b.getMass()) / gesamtmasse;
        y = (schwerpunkt.getY() * (gesamtmasse - b.getMass()) + b.getPosition().getY() * b.getMass()) / gesamtmasse;
        z = (schwerpunkt.getZ() * (gesamtmasse - b.getMass()) + b.getPosition().getZ() * b.getMass()) / gesamtmasse;

        schwerpunkt = new Vector3(x, y, z);

    }

    private boolean hilf; //eine Hilfsvariable, um unnötige Rechnen in der Methode add() zu vermeiden

    public void setHilf(boolean hilf) { // vom Außen anwenden (z.B. Klasse Tree)
        this.hilf = hilf;
    }

    public void add(CelestialBody b, double punktX, double punktY, double punktZ, double length)
    {
        // (punktX, punktY, punktZ) ist der Ursprung von der aktuellen Rekursion
        // length ist Länge der x- y- z-Achse
        double halflength = length / 2; //rechnen den Bereich der nächsten Rekursion

        if (hilf) { //berechnen Gesamtmasse und Schwerpunkt von this Node und Celestialbody b
            GMundSP(b);
        }

        // (punktX, punktY, punktZ) ist der Ursprung von der aktuellen Rekursion
        if (b.getPosition().getX() >= punktX) {
            if (b.getPosition().getY() >= punktY) {
                if (b.getPosition().getZ() >= punktZ) { //children[0]: (x,y,z)=(+,+,+)
                    if (children[0] == null) { //gefundete Node ist leer, CelestialBody b wird darin abgelegt.
                        children[0] = new Node(b);
                        if (body != null) { //Node ist besetzt, darin abgelegte CelestialBody wird wieder durch add in Node hinzufügen
                            CelestialBody body1 = new CelestialBody(body); // neue CelestialBody erzeugen, vermeiden Identität Problem
                            body = null; //this Node wird als einen inneren Konten gekennzeichnet
                            hilf = false; //Daten von body schon in der Node abgelegt, muss nicht wiederrechnen
                            add(body1, punktX, punktY, punktZ, length); //add() Methode in this Node wieder mal aufrufen
                        }
                    } else {
                        children[0].hilf = true; //damit Daten von body auf children[0] ablegen
                        children[0].add(b, punktX + halflength, //eine Node in kleinerem Bereich erzeugen
                                punktY + halflength,
                                punktZ + halflength, halflength);
                    }
                } else {
                    if (children[4] == null) { //children[4]: (x,y,z)=(+,+,-)
                        children[4] = new Node(b);
                        if (body != null) {
                            CelestialBody body1 = new CelestialBody(body);
                            body = null;
                            hilf = false;
                            add(body1, punktX, punktY, punktZ, length);
                        }
                    } else {
                        children[4].hilf = true;
                        children[4].add(b, punktX + halflength,
                                punktY + halflength,
                                punktZ - halflength, halflength);
                    }
                }
            } else {
                if (b.getPosition().getZ() >= punktZ) {
                    if (children[3] == null) { //children[3]: (x,y,z)=(+,-,+)
                        children[3] = new Node(b);
                        if (body != null) {
                            CelestialBody body1 = new CelestialBody(body);
                            body = null;
                            hilf = false;
                            add(body1, punktX, punktY, punktZ, length);
                        }
                    } else {
                        children[3].hilf = true;
                        children[3].add(b, punktX + halflength,
                                punktY - halflength,
                                punktZ + halflength, halflength);
                    }
                } else {
                    if (children[7] == null) { //children[7]: (x,y,z)=(+,-,-)
                        children[7] = new Node(b);
                        if (body != null) {
                            CelestialBody body1 = new CelestialBody(body);
                            body = null;
                            hilf = false;
                            add(body1, punktX, punktY, punktZ, length);
                        }
                    } else {
                        children[7].hilf = true;
                        children[7].add(b, punktX + halflength,
                                punktY - halflength,
                                punktZ - halflength, halflength);
                    }
                }
            }
        } else {
            if (b.getPosition().getY() >= punktY) {
                if (b.getPosition().getZ() >= punktZ) {
                    if (children[1] == null) { //children[1]: (x,y,z)=(-,+,+)
                        children[1] = new Node(b);
                        if (body != null) {
                            CelestialBody body1 = new CelestialBody(body);
                            body = null;
                            hilf = false;
                            add(body1, punktX, punktY, punktZ, length);
                        }
                    } else {
                        children[1].hilf = true;
                        children[1].add(b, punktX - halflength,
                                punktY + halflength,
                                punktZ + halflength, halflength);
                    }
                } else {
                    if (children[5] == null) { //children[5]: (x,y,z)=(-,+,-)
                        children[5] = new Node(b);
                        if (body != null) {
                            CelestialBody body1 = new CelestialBody(body);
                            body = null;
                            hilf = false;
                            add(body1, punktX, punktY, punktZ, length);
                        }
                    } else {
                        children[5].hilf = true;
                        children[5].add(b, punktX - halflength,
                                punktY + halflength,
                                punktZ - halflength, halflength);
                    }
                }
            } else {
                if (b.getPosition().getZ() >= punktZ) {
                    if (children[2] == null) { //children[2]: (x,y,z)=(-,-,+)
                        children[2] = new Node(b);
                        if (body != null) {
                            CelestialBody body1 = new CelestialBody(body);
                            body = null;
                            hilf = false;
                            add(body1, punktX, punktY, punktZ, length);
                        }
                    } else {
                        children[2].hilf = true;
                        children[2].add(b, punktX - halflength,
                                punktY - halflength,
                                punktZ + halflength, halflength);
                    }
                } else {
                    if (children[6] == null) { //children[6]: (x,y,z)=(-,-,-)
                        children[6] = new Node(b);
                        if (body != null) {
                            CelestialBody body1 = new CelestialBody(body);
                            body = null;
                            hilf = false;
                            add(body1, punktX, punktY, punktZ, length);
                        }
                    } else {
                        children[6].hilf = true;
                        children[6].add(b, punktX - halflength,
                                punktY - halflength,
                                punktZ - halflength, halflength);
                    }
                }
            }
        }
    }

    // iterate over every body to calculate force for every body
    public Vector3 calculateForce(CelestialBody body, double length)
    {

        // min is the min distance to calculate force (standard: 0.5)
        double min = 0.5;

        // s = width of the region of the node
        double s = length;

        // d = distance between body and nodes center of mass
        double d = this.schwerpunkt.distanceTo(body.getPosition());

        Vector3 force = new Vector3(0,0,0);

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

        // if externalNode, calculate force and return
        if(externalNode && d!=0)
        {
            CelestialBody bodytmp = new CelestialBody("name", this.gesamtmasse, 1,
                    this.schwerpunkt, new Vector3(0,0,0), Color.GRAY);
            force = body.gravitationalForce(bodytmp);
            return force;
        }

        // if not external node:

        // calculate ratio s/d for every child

        for(int i = 0; i<8; i++)
        {
            // if children not null
            if(this.children[i] != null)
            {
                // if s/d < min, treat this node as single body and calculate force
                if (s / d < min)
                {
                    // TODO calculate force
                    CelestialBody bodytmp = new CelestialBody("name", this.gesamtmasse, 1,
                            this.schwerpunkt, new Vector3(0,0,0), Color.GRAY);
                    force = body.gravitationalForce(bodytmp);
                    return force;
                }
                // if s/d < min, recursively call calculateForce on child node
                return force.plus(children[i].calculateForce(body, length/2));
            }
        }
        return force;
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
                    Vector3 force = root.calculateForce(this.children[i].body, 150e9);
                    this.children[i].body.move(force);
                    //this.children[i].body.draw();
                }
                // else go to child note
                else {
                    this.children[i].calculateForce(root);
                }
            }
        }
    }

    public void drawNodes()
    {
        for(int i = 0; i<8; i++)
        {
            if(this.children[i] != null)
            {
                // check if node is external node
                boolean externalNode = this.children[i].isExternal();

                if (externalNode)
                {
                    this.children[i].body.draw();
                }
                // else go to child note
                else {
                    this.children[i].drawNodes();
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
