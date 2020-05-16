public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static final int n = 1000;

    public static void main(String[] args) throws InterruptedException {
        Tree testTree = new Tree();

        CelestialBody bodies[] = new CelestialBody[n];

        // generate bodies

        for(int i = 0; i<n; i++)
        {
            bodies[i] = new CelestialBody("b"+i, Math.random()*2e45, 3396e1,
                    new Vector3(Math.random()*500*AU, Math.random()*500*AU, Math.random()*500*AU),
                    new Vector3(0,0,0), StdDraw.WHITE);
            testTree.add(bodies[i]);
        }


        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setXscale(-5000*AU,5000*AU);
        StdDraw.setYscale(-5000*AU,5000*AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);


        while(true)
        {
            testTree.calculateForces();
            StdDraw.clear(StdDraw.BLACK);
            testTree.drawNodes();

            testTree = new Tree();
            for(int i = 0; i<n; i++)
            {
                if(bodies[i].getPosition().getX()<50000*AU && bodies[i].getPosition().getY() <50000*AU &&
                        bodies[i].getPosition().getZ()<50000*AU && bodies[i].getPosition().getX()>-50000*AU
                        && bodies[i].getPosition().getY()>-50000*AU && bodies[i].getPosition().getZ()>-50000*AU)
                    testTree.add(bodies[i]);
            }

            StdDraw.show();


        }

    }
}
