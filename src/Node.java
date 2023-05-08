public class Node {

    private Position pos;
    private String terrain;
    private Node parent;
    private Direction parentOperator;
    private int cost;
    private int key;
    private static int nodeCounter = 0;
//    private int depth;
//    private int weight;

    public Node(int x, int y, String terrain, Node parent, Direction parentOperator){

        this.pos = new Position(x, y);
        this.terrain = terrain;
        this.parent = parent;
        this.parentOperator = parentOperator;
        this.key = nodeCounter++;

        switch (terrain) {

            case "S":
                cost = 0;
                break;

            case "D":
                cost = parent.getCost() + 1;
                break;

            case "R":
                cost = parent.getCost() + 3;
                break;

            case "H":
                if (parentOperator == Direction.RIGHT_DOWN || parentOperator ==  Direction.LEFT_DOWN ||
                    parentOperator == Direction.LEFT_UP || parentOperator == Direction.RIGHT_UP) {

                    cost = parent.getCost() + 10;
                }
                else {

                    cost = parent.getCost() + 5;
                }
                break;

            case "G":
                cost = parent.getCost() + 5;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + terrain);
        }
    }

    public Position getPos() {
        return pos;
    }

    public Node getParent() {
        return parent;
    }

    public String getTerrain() {
        return terrain;
    }

    public int getCost() {
        return cost;
    }

    public static int getNodeCounter() {
        return nodeCounter;
    }

    public Direction getParentOperator() {
        return parentOperator;
    }

    @Override
    public String toString() {

        Position parentPos = parent == null? null: parent.getPos();

        return "Node { " +
                "Pos: '" + pos + '\'' +
                ", key: '" + key + '\'' +
                ", prevPos: '" + parentPos + '\'' +
                ", prevOperator: '" + parentOperator + '\'' +
                "}";
    }


}
