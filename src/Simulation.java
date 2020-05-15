public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static void main(String[] args) throws InterruptedException {
        Tree testTree = new Tree();

        // generate bodies
        for(int i = 0; i<10000; i++)
        {
            testTree.add(new CelestialBody("b"+i, 2e38, 1.5,
                    new Vector3(Math.random()*AU, Math.random()*AU, Math.random()*AU),
                    new Vector3(0,0,0), StdDraw.WHITE));
        }

        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setXscale(0,AU);
        StdDraw.setYscale(0,AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);


        while(true) {

            testTree.calculateForces();
            StdDraw.clear(StdDraw.BLACK);
            testTree.drawNodes();
            StdDraw.show();

            /*
            testTree = new Tree();

            testTree.add(b1);
            testTree.add(b2);
            testTree.add(b3);
            testTree.add(b4);
            */

        }



        // calculate force for every body


    }
}
