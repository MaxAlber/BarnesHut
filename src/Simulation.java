public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static void main(String[] args) {
        Tree testTree = new Tree();
        //CelestialBody b1 = new CelestialBody("b1", 1.898e27, 69911e3,new Vector3(19e7,90e1,-15e2),
        //        new Vector3(0,0,0), StdDraw.ORANGE);
        CelestialBody b2 = new CelestialBody("b2", 2 ,1822e3,new Vector3(15e4,12e5,18e6),
                new Vector3(0,0,0), StdDraw.YELLOW);

        CelestialBody b3 = new CelestialBody("b3", 3 ,189e3,new Vector3(14e4,-12e5,-18e6),
                new Vector3(0,0,0), StdDraw.RED);
        CelestialBody b4 = new CelestialBody("b4", 7 ,1322e3,new Vector3(-10e4,-12e5,18e6),
                new Vector3(0,0,0), StdDraw.GREEN);
        CelestialBody b5 = new CelestialBody("b5", 6 ,1822e3,new Vector3(0.76*AU,0.76*AU,0.76*AU),
                new Vector3(0,0,0), StdDraw.YELLOW);



        //testTree.add(b1);

        System.out.println("add b2");
        testTree.add(b2);
        System.out.println("--------------------");


        System.out.println("add b3");
        testTree.add(b3);
        System.out.println("--------------------");

        System.out.println("add b4");
        testTree.add(b4);
        System.out.println("--------------------");

        System.out.println("add b5");
        testTree.add(b5);
        System.out.println("--------------------");




    }


}
