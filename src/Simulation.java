public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;
    public static final int n = 10000;

    public static void main(String[] args) throws InterruptedException {
        Tree testTree = new Tree();

        CelestialBody bodies[] = new CelestialBody[n];

        // generate bodies
        for(int i = 0; i<n; i++) {
            bodies[i] = new CelestialBody("b"+i, Math.random()*2e37, 1,
                    new Vector3(Math.random()*AU, Math.random()*AU, Math.random()*AU),
                    new Vector3(0,0,0), StdDraw.WHITE);
            testTree.add(bodies[i]);
        }


        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setXscale(-5*AU,5*AU);
        StdDraw.setYscale(-5*AU,5*AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);


        while(true) {
            StdDraw.clear(StdDraw.BLACK);
            testTree.calculateForces();
            testTree = new Tree();
            for(int i = 0; i<n; i++) {
                if(bodies[i].x()<5*AU && bodies[i].y() <5*AU && bodies[i].z()<5*AU
                        && bodies[i].x()>-5*AU && bodies[i].y()>-5*AU && bodies[i].z()>-5*AU)
                    testTree.add(bodies[i]);
            }
            StdDraw.show();
        }

    }
}
