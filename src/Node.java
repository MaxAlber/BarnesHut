public class Node {

    private CelestialBody body;
    private double gesamtmasse; //Gesamtmasse der Gruppe
    private Vector3 schwerpunkt; //Schwerpunkt der Gruppe
    private boolean active = false;
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
     */

    public double getGesamtmasse() {
        return gesamtmasse;
    }

    public Node(CelestialBody body) {
        this.body = body;
        this.active = true;
        this.gesamtmasse = body.getMass();
        this.schwerpunkt = body.getPosition();
    }

    private double GesamtMass() {

        for (int i = 0; i < 8; i++) {
            if (children[i] != null) {
                gesamtmasse = gesamtmasse + children[i].gesamtmasse;
            }
        }
        return gesamtmasse;
    }

    private Vector3 Schwerpunkt() {
        double x = 0;
        double y = 0;
        double z = 0;
        for (int i = 0; i < 8; i++) {
            if (children[i] != null) {
                x = x + children[i].body.getPosition().getX() * children[i].body.getMass();
                y = y + children[i].body.getPosition().getY() * children[i].body.getMass();
                z = z + children[i].body.getPosition().getZ() * children[i].body.getMass();
            }
        }

        schwerpunkt.setX(x / gesamtmasse);
        schwerpunkt.setY(y / gesamtmasse);
        schwerpunkt.setZ(z / gesamtmasse);

        return schwerpunkt;
    }


    public void add(CelestialBody b, double punktX, double punktY, double punktZ, double length, Node rootNode) {
        double halflength = length / 2;

        if (b.getPosition().getX() >= punktX) {
            if (b.getPosition().getY() >= punktY) {
                if (b.getPosition().getZ() >= punktZ) {
                    if (children[0] == null) {
                        children[0] = new Node(b);


                        if (rootNode.body != null) {

                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }

                        System.out.println(rootNode + "children[0]");


                    } else {
                        children[0].add(b, punktX + halflength,
                                punktY + halflength,
                                punktZ + halflength, halflength, children[0]);

                    }
                } else {
                    if (children[4] == null) {
                        children[4] = new Node(b);
                        if (rootNode.body != null) {
                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }
                        System.out.println(rootNode + "children[4]");
                    } else {
                        children[4].add(b, punktX + halflength,
                                punktY + halflength,
                                punktZ - halflength, halflength, children[4]);
                    }
                }
            } else {
                if (b.getPosition().getZ() >= punktZ) {
                    if (rootNode.children[3] == null) {
                        rootNode.children[3] = new Node(b);
                        if (rootNode.body != null) {
                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }
                        System.out.println(rootNode + "children[3]");
                    } else {
                        children[3].add(b, punktX + halflength,
                                punktY - halflength,
                                punktZ + halflength, halflength, children[3]);
                    }
                } else {
                    if (children[7] == null) {
                        children[7] = new Node(b);
                        if (rootNode.body != null) {

                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }
                        System.out.println(rootNode + "children[7]");

                    } else {
                        children[7].add(b, punktX + halflength,
                                punktY - halflength,
                                punktZ - halflength, halflength, children[7]);
                    }
                }
            }
        } else {
            if (b.getPosition().getY() >= punktY) {
                if (b.getPosition().getZ() >= punktZ) {
                    if (children[1] == null) {
                        children[1] = new Node(b);
                        if (rootNode.body != null) {
                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }
                        System.out.println(rootNode + "children[1]");
                    } else {
                        children[1].add(b, punktX - halflength,
                                punktY + halflength,
                                punktZ + halflength, halflength, children[1]);
                    }
                } else {
                    if (children[5] == null) {
                        children[5] = new Node(b);
                        if (rootNode.body != null) {
                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }
                        System.out.println(rootNode + "children[5]");
                    } else {
                        children[5].add(b, punktX - halflength,
                                punktY + halflength,
                                punktZ - halflength, halflength, children[5]);
                    }
                }
            } else {
                if (b.getPosition().getZ() >= punktZ) {
                    if (children[2] == null) {
                        children[2] = new Node(b);
                        if (rootNode.body != null) {
                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }
                        System.out.println(rootNode + "children[2]");
                    } else {
                        children[2].add(b, punktX - halflength,
                                punktY - halflength,
                                punktZ + halflength, halflength, children[2]);
                    }
                } else {
                    if (children[6] == null) {
                        children[6] = new Node(b);
                        if (rootNode.body != null) {

                            CelestialBody body1 = new CelestialBody(rootNode.body);
                            rootNode.body = null;
                            add(body1, punktX, punktY, punktZ, length, rootNode);
                        }
                        System.out.println(rootNode + "children[6]");
                    } else {
                        children[6].add(b, punktX - halflength,
                                punktY - halflength,
                                punktZ - halflength, halflength, children[6]);
                    }
                }
            }
        }


    }

}
