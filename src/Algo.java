import java.util.*;

public class Algo {

    private Map map;
    private String algoName;
    private boolean oldFirst;
    private boolean withOpenList;
    private Node startNode;
    private Node goalNode = null;

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
                AStar();
                break;

            case "IDA*":
                IDAStar();
                break;

            case "DFBnB":
                DFBnB();
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

        Hashtable<Position, Node> openList = new Hashtable<>();
        Hashtable<Position, Node> closedList = new Hashtable<>();
        Queue<Node> Q = new LinkedList<>();

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

    public String AStar() {

        Hashtable<Position, Node> openList = new Hashtable<>();
        Hashtable<Position, Node> closedList = new Hashtable<>();
        PriorityQueue<Node> PQ = new PriorityQueue<>(new AStarComparator());

        PQ.add(startNode);
        openList.put(startNode.getPos(), startNode);

        while (!PQ.isEmpty()) {

            Node currNode = PQ.poll();
            openList.remove(currNode.getPos());

//            print(currNode);

            if (map.isGoal(currNode)) {

                goalNode = currNode;
                return path(currNode);
            }


            closedList.put(currNode.getPos(), currNode);

            // Expanding currNode:
            for (Direction direction : map.allowedOperators(currNode)) {

                Node newNode = map.operator(currNode, direction);

                if (!(openList.containsKey(newNode.getPos()) || closedList.containsKey(newNode.getPos()))) {

                    PQ.add(newNode);
                    openList.put(newNode.getPos(), newNode);
                }

                else if (openList.containsKey(newNode.getPos())) {

                    if (openList.get(newNode.getPos()).getFunc() > newNode.getFunc()) {

                        PQ.remove(openList.put(newNode.getPos(), newNode));
                        PQ.add(newNode);
                    }
                }
            }
        }
        return "no path";
    }

    public String IDAStar() {

        Stack<Node> S = new Stack<>();
        Hashtable<Position, Node> openList = new Hashtable<>();
        int threshold = startNode.getWeight();
        int counter = 0;

        while (threshold != Integer.MAX_VALUE) {

             counter = 0;

            System.out.println("kkkk");

            // Minimum f(x) value we have seen through an iteration
            int minF = Integer.MAX_VALUE;

            startNode.setOut(false);
            S.add(startNode);
            openList.put(startNode.getPos(), startNode);

            while ( !S.isEmpty() ) {

//                System.out.println(counter++);
//
//                System.out.println(threshold);
//
//                System.out.println(Arrays.toString(S.toArray()));

                Node currNode = S.pop();

//                if (withOpen()) {
//                    System.out.println(currNode);
//                }

                if (currNode.isOut()) {

                    openList.remove(currNode.getPos());
                }

                else {

                    // Mark the node to be removed next time it's added to the stack
                    currNode.setOut(true);
                    // Push back to the stack
                    S.add(currNode);



                    // Expanding currNode:
                    for (Direction direction : map.allowedOperators(currNode)) {

                        Node newNode = map.operator(currNode, direction);

                        if (newNode.getFunc() > threshold) {

                            minF = Math.min(minF, newNode.getFunc());
                            continue;
                        }

                        if (openList.containsKey(newNode.getPos()) && openList.get(newNode.getPos()).isOut()) {
//                            System.out.println("llllllll");
//                            return "llll";
                            continue;
                        }

                        if (openList.containsKey(newNode.getPos()) && !openList.get(newNode.getPos()).isOut()) {

                            if (openList.get(newNode.getPos()).getFunc() > newNode.getFunc()) {


                                S.remove(openList.get(newNode.getPos()));
                                openList.remove(newNode.getPos());
                            }

                            else {
                                continue;
                            }
                        }

                        if (map.isGoal(newNode)) {

                            goalNode = newNode;
                            return path(newNode);
                        }

                        S.add(newNode);
                        openList.put(newNode.getPos(), newNode);
                    }
                }
            }
            threshold = minF;
        }
        return "no path";
    }

    public String DFBnB() {

        Stack<Node> S = new Stack<>();
        Hashtable<Position, Node> openList = new Hashtable<>();
        List<Node> N = new ArrayList<>();

        S.add(startNode);
        openList.put(startNode.getPos(), startNode);

        String result = "no path";
        int threshold = Integer.MAX_VALUE;

        while (!S.isEmpty()) {

            Node currNode = S.pop();
//            print(currNode);

            if (currNode.isOut()) {

                openList.remove(currNode.getPos());
            }

            else {

                currNode.setOut(true);
                S.add(currNode);

                for (Direction direction : map.allowedOperators(currNode)) {

                    Node newNode = map.operator(currNode, direction);
                    N.add(newNode);
                }

                // Sort the nodes in N according to their `f` heuristic values.
                N.sort(new AStarComparator());

                List<Node> tempN = new ArrayList<>(N);

                for (Node childNode : tempN) {

                    if (N.contains(childNode)) {

                        if (childNode.getFunc() >= threshold) {
                            // Remove all nodes after `child` (including)
                            int index = N.indexOf(childNode);

                            N.subList(index, N.size()).clear();
                        }
                        else if (openList.containsKey(childNode.getPos()) && openList.get(childNode.getPos()).isOut()) {

                            N.remove(childNode);
                        }
                        else if (openList.containsKey(childNode.getPos()) && (!openList.get(childNode.getPos()).isOut())) {

                            if (openList.get(childNode.getPos()).getFunc() <= childNode.getFunc()) {

                                N.remove(childNode);
                            }

                            else {

                                S.remove(openList.get(childNode.getPos()));
                                openList.remove(childNode.getPos());
                            }
                        }

                        else if (map.isGoal(childNode)) {
                            // If we've reached here -
                            // then f(child) < t
                            threshold = childNode.getFunc();
                            goalNode = childNode;
                            result = path(childNode);
                            int index = N.indexOf(childNode);
                            N.subList(index, N.size()).clear();
                        }
                    }
                }

                Collections.reverse(N);
                S.addAll(N);

                for (Node node : N) {

                    openList.put(node.getPos(), node);
                }

                N.clear();
            }
        }

        return result;
    }

    class AStarComparator implements Comparator<Node> {

        @Override
        public int compare(Node n1, Node n2) {

            return Integer.compare(n1.getFunc(),  n2.getFunc());
        }
    };




    public void print(Node n) {

        if (withOpenList) {

            System.out.println(n);
        }
    }

    public static void main(String[] args) {

        Hashtable<Position, Node> openList = new Hashtable<>();

        Node n1 = new Node(1,1,"S", null, null, 0);
        Node n2 = new Node(2,2,"S", null, null, 0);
        Node n3 = new Node(3,3,"R", n2, Direction.UP, 0);
        Node n4 = new Node(4,4,"R", n2, Direction.UP, 0);
        Node n5 = new Node(5,5,"R", n2, Direction.UP, 0);
        Node n6 = new Node(6,6,"R", n2, Direction.UP, 0);
        Node n7 = new Node(7,7,"R", n2, Direction.UP, 0);
        Node n8 = new Node(8,8,"R", n2, Direction.UP, 0);
        System.out.println(openList);
        openList.put(n1.getPos(), n1);
        openList.put(n2.getPos(), n2);
        openList.put(n3.getPos(), n3);
        openList.put(n4.getPos(), n4);
        openList.put(n5.getPos(), n5);
        openList.put(n6.getPos(), n6);

        System.out.println(openList);

        ArrayList<Integer> N = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        System.out.println(N);
        N.subList(1, N.size()).clear();
        System.out.println(N);









    }


}


