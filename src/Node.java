public class Node {

    private Position pos;
    private String terrain;
    private Node parent;
    private Direction parentOperator;
    private int key;
    private static int nodeCounter = 0;
//    private int depth;
//    private int weight;

    public Node(int x, int y, String terrain, Node parent, Direction parentOperator){

        this.pos = new Position(x, y);
        this.terrain = terrain;
        this.parent = parent;
        this.parentOperator = parentOperator;
        key = nodeCounter++;
    }

    public Position getPos() {
        return pos;
    }

    public Node getParent() {
        return parent;
    }

    public Direction getParentOperator() {
        return parentOperator;
    }
}
