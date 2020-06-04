public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    // n is the number of bodys in the simulation
    public static final int n = 10000;

    public static void main(String[] args)
    {
        Tree testTree = new Tree();

        CelestialBody bodies[] = new CelestialBody[n];

        // generate bodies
        for(int i = 0; i<n; i++)
        {
            bodies[i] = new CelestialBody("b"+i, Math.random()*1e36,
                    new Vector3(Math.random()*AU*4-AU, Math.random()*AU*3-AU, Math.random()*AU*3-AU));
            testTree.add(bodies[i]);
        }


        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setXscale(-5*AU,5*AU);
        StdDraw.setYscale(-5*AU,5*AU);
        StdDraw.enableDoubleBuffering();



        // this loop is the simulation loop
        // the forces are calculated and a new octree with new positions is created repeatedly
        while(true)
        {
            StdDraw.clear(StdDraw.BLACK);
            testTree.calculateForces();

            OctreeIterator iterator = testTree.iterator();

            testTree = new Tree();


            while(iterator.hasNext())
            {
                CelestialBody next = iterator.next();
                if(next.getPosition().getX()<5*AU &&
                        next.getPosition().getY() <5*AU &&
                        next.getPosition().getZ()<5*AU &&
                        next.getPosition().getX()>-5*AU &&
                        next.getPosition().getY()>-5*AU &&
                        next.getPosition().getZ()>-5*AU)
                {
                    testTree.add(next);
                }
            }

            StdDraw.show();
        }



    }
}
