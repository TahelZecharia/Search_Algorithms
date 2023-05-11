import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ex1 {

    public static void main(String[] args) {

        try{

            // Open output file:
            FileWriter outputFile = new FileWriter("output.txt");

            try{

                // Open input file:
                File inputFile = new File("input.txt");
                Scanner scanner = new Scanner(inputFile);

                // 1) Reads the name of the desired algorithm:
                String algoName = scanner.nextLine();

                // 2) Reads the desired order of creating the nodes:
                String line = scanner.nextLine();
                String[] str = line.split("\\s+");
                boolean clockwise = str[0].equalsIgnoreCase("clockwise");
                boolean oldFirst = false;
                if (str.length > 1) {

                    oldFirst = str[1].equalsIgnoreCase("old-first");
                }

                // 3) Reads whether to print the running time:
                Boolean withTime = scanner.nextLine().equalsIgnoreCase("with time");

                // 4) Reads whether to print the open list:
                Boolean withOpenList = scanner.nextLine().equalsIgnoreCase("with open");

                // 5) Reads the board size:
                int size = Integer.parseInt(scanner.nextLine());

                // 6) Reads the coordinates of the start point and the end point:
                line = scanner.nextLine();
                int startX = Integer.parseInt(line.substring(1, line.indexOf(",")));
                int startY = Integer.parseInt(line.substring(line.indexOf(",")+1, line.indexOf(")")));
                int goalX = Integer.parseInt(line.substring(line.lastIndexOf("(")+1, line.lastIndexOf(",")));
                int goalY = Integer.parseInt(line.substring(line.lastIndexOf(",")+1, line.lastIndexOf(")")));
//                Pattern pattern = Pattern.compile("\\((-?\\d+),(-?\\d+)\\),\\((-?\\d+),(-?\\d+)\\)");
//                Matcher matcher = pattern.matcher(line);
//                System.out.println(line);
//                int startX = Integer.parseInt(matcher.group(1));
//                int startY = Integer.parseInt(matcher.group(2));
//                int goalX = Integer.parseInt(matcher.group(3));
//                int goalY = Integer.parseInt(matcher.group(4));

                // 7) Reads the board:
                String[][] board = new String[size][size];
                for (int row = 0; row < size; row++) {
                    line = scanner.nextLine();
                    for (int col = 0; col < size; col++) {
                        board[row][col] = Character.toString(line.charAt(col));
                    }
                }

                // ********** ALGORITHM **********

                Map map = new Map(board, size, clockwise, goalX, goalY);
                Algo algo = new Algo(map, algoName, oldFirst, withOpenList, startX, startY);

                long startTime = System.currentTimeMillis();
                System.out.println(algo.DFID());
                System.out.println(Node.getNodeCounter());
                System.out.println(algo.getCost());
//                String result = String.format("%.2f", System.currentTimeMillis() - startTime);
                System.out.println(((System.currentTimeMillis() - startTime) / 1000) + " seconds");
//                System.out.println(result + " seconds");





//                while (scanner.hasNextLine()) {
//
//                    String data = scanner.nextLine();
//                    int algo = Integer.parseInt(data.substring(data.length()-1));
//                    String query = data.substring(0, data.length()-2);

//                    Algo myAlgo = new Algo(myNet, query);
//                    double ans = myAlgo.CalculateQuery(algo);
//                    int add = myAlgo.getAddCounter();
//                    int mul = myAlgo.getMulCounter();

//                    outputFile.write(String.format("%.5f", ans) + "," + add + "," + mul);

//                    if (scanner.hasNextLine()) {
//                        outputFile.write("\n");
//                    }
//                }
                scanner.close();
            }
            catch (Exception e){

                e.printStackTrace();
            }

            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
