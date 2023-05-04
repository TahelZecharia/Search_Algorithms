import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class Algo {

    private Map map;
    private String algoName;
    private boolean oldFirst;
    private boolean withOpenList;
    private Node startNode;
    private Hashtable<Position, Node> openList = new Hashtable<>();
    private Hashtable<Position, Node> closedList = new Hashtable<>();
    private Queue<Node> Q = new LinkedList<>();


    public Algo(Map map, String algoName, Boolean oldFirst, Boolean withOpenList, int startX, int startY) {

        this.map = map;
        this.algoName = algoName;
        this.oldFirst = oldFirst;
        this.withOpenList = withOpenList;
        this.startNode = map.createNode(startX, startY, null, null);
    }

    public String path(Node node) {

        if (node == null) return "";
        if (node.getParent() == null) return "" + node.getParentOperator();
        return "" + path(node.getParent()) + "-" + node.getParentOperator();
    }

    public String BFS() {

        Q.add(startNode);
        openList.put(startNode.getPos(), startNode);

        while (!Q.isEmpty()) {

            Node currNode = Q.poll();

//            print(currNode);

            openList.remove(currNode.getPos());
            closedList.put(currNode.getPos(), currNode);

            for (Direction direction : map.allowedOperators(currNode)) {

                Node newNode = map.operator(currNode, direction);

                if (!(openList.containsKey(newNode.getPos()) || closedList.containsKey(newNode.getPos()))) {

                    if (map.isGoal(newNode)) {

                        return path(newNode);
                    }

                    openList.put(newNode.getPos(), newNode);
                    Q.add(newNode);
                }
            }
        }
        return "no path";
    }


    public void print(Node n) {

        if (withOpenList) {

            System.out.println(n);
        }
    }

}
