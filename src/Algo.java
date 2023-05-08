import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class Algo {

    private Map map;
    private String algoName;
    private boolean oldFirst;
    private boolean withOpenList;
    private Node startNode;
    private Node goalNode = null;
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

    public String FindPath() {

        long startTime = System.currentTimeMillis();

        switch (algoName) {

            case "BFS":
                BFS();
                break;

            case "DFID":
                DFID();
                break;

            case "A*":
                break;

            case "IDA*":
                break;

            case "DFBnB":
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + algoName);
        }

        if (goalNode == null) {

            return "no path" +
                    "\nNum: " + Node.getNodeCounter() +
                    "\nCost: inf" +
                    "\ntime: " + (System.currentTimeMillis() - startTime) / 1000.0;
        }

//        if (withOpen) {
//            System.out.println("Goal State found:\n" + goal);
//        }
        return path(goalNode) +
                "\nNum: " + Node.getNodeCounter() +
                "\nCost: " + goalNode.getCost() +
                "\ntime: " + (System.currentTimeMillis() - startTime) / 1000.0;
    }

    public String path(Node node) {

        if (node == null) return "";
        if (node.getParent() == null) return "";
        if (node.getParent().getParentOperator() == null) return "" + node.getParentOperator();
        return "" + path(node.getParent()) + "-" + node.getParentOperator();
    }

    public String getCost(){

        if (goalNode == null) return "Cost: inf";

        return "Cost: " + goalNode.getCost() + "";
    }

    public String BFS() {

        Q.add(startNode);
        openList.put(startNode.getPos(), startNode);

        while (!Q.isEmpty()) {

            Node currNode = Q.poll();

//            print(currNode);

            openList.remove(currNode.getPos());
            closedList.put(currNode.getPos(), currNode);

            System.out.println("open list: " + openList);
            System.out.println("closed list: " + closedList);

            for (Direction direction : map.allowedOperators(currNode)) {

                Node newNode = map.operator(currNode, direction);

                if (!(openList.containsKey(newNode.getPos()) || closedList.containsKey(newNode.getPos()))) {

                    if (map.isGoal(newNode)) {

                        goalNode = newNode;
                        return path(newNode);
                    }

                    System.out.println("newNode: " + newNode);
                    openList.put(newNode.getPos(), newNode);
                    Q.add(newNode);
                }
            }
        }
        return "no path";
    }

    public String DFID() {

        Hashtable<Position, Node> H = new Hashtable<>(); // Saving the current route we are on. Used to loop avoidance.
        int limit = Integer.MAX_VALUE;
        String result;

        for (int i = 1; i < limit; ++i) {

            H.clear();
            result = LimitedDFS(startNode, i, H);

            if (!result.equals("cutoff")) {

                return result;
            }
        }
        return "no path";
    }

    private String LimitedDFS(Node currNode, int limit, Hashtable<Position, Node> H) {

        //        print(curr);

        if (map.isGoal(currNode)) {

            goalNode = currNode;
            return path(currNode);
        }

        else if (limit == 0) {

            return "cutoff";
        }

        H.put(currNode.getPos(), currNode);
        boolean isCutoff = false;
        String result;

        for (Direction direction : map.allowedOperators(currNode)) {

            Node newNode = map.operator(currNode, direction);

            if (H.containsKey(newNode.getPos())) {continue;}

            result = LimitedDFS(newNode, limit-1, H);

            if (result.equals("cutoff")) {

                isCutoff = true;
            }

            else if (!result.equals("fail")) {

                return result;
            }
        }

        H.remove(currNode.getPos());

        if (isCutoff) {

            return "cutoff";
        }

        return "fail";
    }




    public void print(Node n) {

        if (withOpenList) {

            System.out.println(n);
        }
    }

    public static void main(String[] args) {

        Hashtable<Position, Node> openList = new Hashtable<>();

        Node n1 = new Node(1,1,"X", null, null);
        Node n2 = new Node(1,1,"Y", null, null);
        System.out.println(openList);
        openList.put(n1.getPos(), n1);
        System.out.println(openList);
        System.out.println(openList.containsKey(n1.getPos()));
        boolean a = openList.containsKey(n2.getPos());
        System.out.println(openList.containsKey(n2.getPos()));
        openList.remove(n1.getPos());
        System.out.println(openList);




    }


}


