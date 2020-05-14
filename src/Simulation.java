public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static void main(String[] args) {
        Tree testTree = new Tree();

        CelestialBody b1 = new CelestialBody("b1", 7, 1822e3, new Vector3(0.76 * AU, 0.76 * AU, 0.76 * AU),
                new Vector3(0, 0, 0), StdDraw.YELLOW);

        CelestialBody b2 = new CelestialBody("b2", 19, 183e3, new Vector3(0.32 * AU, 0.44 * AU, -0.92 * AU),
                new Vector3(0, 0, 0), StdDraw.RED);

        CelestialBody b3 = new CelestialBody("b3", 25, 199e3, new Vector3(-0.77 * AU, 0.56 * AU, 0.29 * AU),
                new Vector3(0, 0, 0), StdDraw.GREEN);

        CelestialBody b4 = new CelestialBody("b4", 36, 259e3, new Vector3(0.7 * AU, -0.03 * AU, -0.57 * AU),
                new Vector3(0, 0, 0), StdDraw.BLUE);


        StdDraw.setXscale(-2 * AU, 2 * AU);
        StdDraw.setYscale(-2 * AU, 2 * AU);
        //StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);


        testTree.add(b1);


        testTree.add(b2);
        /*System.out.println("--------------------");
        System.out.println(testTree.getRoot().getGesamtmasse());
        System.out.println("root " + testTree.getRoot() + " schwerpunkt " + testTree.getRoot().getSchwerpunkt().getX() + ", "
                + testTree.getRoot().getSchwerpunkt().getY() + ", " + testTree.getRoot().getSchwerpunkt().getZ());
        System.out.println();*/

        testTree.add(b3);


        testTree.add(b4);


        while (true) {
            testTree.calculateForces();

            testTree = new Tree();

            testTree.add(b1);
            testTree.add(b2);
            testTree.add(b3);
            testTree.add(b4);

            StdDraw.clear(StdDraw.BLACK);

        }


        // calculate force for every body


    }
}
