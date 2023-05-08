import java.util.*;

public class Map {

    private String[][] board;
    private int size;
    private boolean clockwise;
    private Position goalPos;
    private Direction[] operators = {Direction.RIGHT, Direction.RIGHT_DOWN, Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT, Direction.LEFT_UP, Direction.UP, Direction.RIGHT_UP};

    public Map(String[][] board, int size, boolean clockwise, int goalX, int goalY) {

        this.board = board;
        this.size = size;
        this.clockwise = clockwise;
        this.goalPos = new Position(goalX, goalY);

        if (! clockwise) {
            Collections.reverse(Arrays.asList(operators));
            System.out.println(Arrays.toString(operators));
        }
        for (String[] strings : board) {

            System.out.println(Arrays.toString(strings));
        }
    }

    public Node createNode(int x, int y, Node parent, Direction parentOperator) {

        if (x > size || y > size || x < 1 || y < 1) {

            return null;
        }

        return new Node(x, y, board[x-1][y-1], parent, parentOperator);
    }

    public boolean isGoal(Node node) {

        return node.getTerrain().equals("G");
    }

    public String getTerrain(Position pos) {

        if (pos.getX() > size || pos.getY() > size || pos.getX() < 1 || pos.getY() < 1) {

            return null;
        }

        return board[pos.getX() - 1][pos.getY() - 1];
    }

    public String getTerrain(int x, int y) {

        if (x > size || y > size || x < 1 || y < 1) {

            return null;
        }

        return board[x - 1][y - 1];
    }

    public boolean allowedOperator(Node node, Position newPos) {

        if (node.getParent() != null) {
            if (node.getParent().getPos().equals(newPos)) return false;
        }

        String terrain = getTerrain(newPos);
        if (terrain == null || terrain.equals("X")) return false;

        return true;

    }

    public ArrayList<Direction> allowedOperators(Node node) {

        ArrayList<Direction> allowedOp = new ArrayList<>();
        Position nodePose = node.getPos();
        Position tempPos = new Position(0,0);

        for (Direction d : operators) {

            switch (d) {

                case RIGHT:

                    tempPos.setX(nodePose.getX());
                    tempPos.setY(nodePose.getY() + 1);
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

                case RIGHT_DOWN:

                    tempPos.setX(nodePose.getX() + 1);
                    tempPos.setY(nodePose.getY() + 1);
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

                case DOWN:

                    tempPos.setX(nodePose.getX() + 1);
                    tempPos.setY(nodePose.getY());
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

                case LEFT_DOWN:

                    tempPos.setX(nodePose.getX() + 1);
                    tempPos.setY(nodePose.getY() - 1);
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

                case LEFT:

                    tempPos.setX(nodePose.getX());
                    tempPos.setY(nodePose.getY() - 1);
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

                case LEFT_UP:

                    tempPos.setX(nodePose.getX() - 1);
                    tempPos.setY(nodePose.getY() - 1);
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

                case UP:

                    tempPos.setX(nodePose.getX() - 1);
                    tempPos.setY(nodePose.getY());
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

                case RIGHT_UP:

                    tempPos.setX(nodePose.getX() - 1);
                    tempPos.setY(nodePose.getY() + 1);
                    if (allowedOperator(node, tempPos)) {
                        allowedOp.add(d);
                    }
                    break;

            }
        }
        return allowedOp;
    }

    public Node operator(Node node, Direction direction) {

        Position nodePose = node.getPos();

        switch (direction) {

            case RIGHT:

                return createNode(nodePose.getX(), nodePose.getY() + 1, node, Direction.RIGHT);

            case RIGHT_DOWN:

                return createNode(nodePose.getX() + 1, nodePose.getY() + 1, node, Direction.RIGHT_DOWN);

            case DOWN:

                return createNode(nodePose.getX() + 1, nodePose.getY(), node, Direction.DOWN);

            case LEFT_DOWN:

                return createNode(nodePose.getX() + 1, nodePose.getY() - 1, node, Direction.LEFT_DOWN);

            case LEFT:

                return createNode(nodePose.getX(), nodePose.getY() - 1, node, Direction.LEFT);

            case LEFT_UP:

                return createNode(nodePose.getX() - 1, nodePose.getY() - 1, node, Direction.LEFT_UP);

            case UP:

                return createNode(nodePose.getX() - 1, nodePose.getY(), node, Direction.UP);

            case RIGHT_UP:

                return createNode(nodePose.getX() - 1, nodePose.getY() + 1, node, Direction.RIGHT_UP);

            default:

                throw new IllegalStateException("Unexpected value: " + direction);

        }
    }
}
