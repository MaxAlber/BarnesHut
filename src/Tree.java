public class Tree {
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;
    private Node root;

    public void add(CelestialBody b)
    {
        if (root == null) {
            root = new Node(b);
        } else {
            root.setHilf(true);
            root.add(b, 0, 0, 0, AU);
        }
    }

    public Node getRoot() {
        return root;
    }
    public void calculateForces()
    {
        root.calculateForce(root);
    }

    public Tree rebuild(Tree tree, Node node)
    {
        for(int i = 0; i<8; i++)
        {
            if(node.getChildren()[i] != null)
            {
                // check if node is external node
                boolean externalNode = node.getChildren()[i].isExternal();

                if (externalNode)
                {
                    tree.add(node.getChildren()[i].getBody());
                }
                // else go to child note
                else {
                    tree = this.rebuild(tree, node.getChildren()[i]);
                }
            }
        }
        return tree;
    }

    public void drawNodes()
    {
        root.drawNodes();
    }

}
