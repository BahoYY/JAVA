package Paris_metro;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class main {
    //Controlling the datas in the array
    public static boolean check(String[] arr, String scs){
        boolean sts = false;

        if (arr[0] != null){
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != null && arr[i].equals(scs)){
                    sts = true;
                    break;
                }
            }
        }
        return sts;
    }


    public static void main(String[] args) {
        boolean userinter = true;
        //while loop for user interface
        while (userinter){

        BasicGraphInterface<String> scs = new DirectedGraph<>();

        String[] acc = new String[400];

        File text = new File("C:\\Paris_RER_Metro_v2.csv");

        int control = 0;

        //reading the file for the first time to get all the names of the stations
        try {
            Scanner sc = new Scanner(text);
            sc.nextLine();

            while (sc.hasNextLine()){
                String chc = sc.nextLine();
                String[] arr = chc.split(",");
                if (!check(acc, arr[1])){
                    acc[control] = arr[1];
                    control++;
                }
            }
        }
        catch (FileNotFoundException ignored){

        }

        //adding the station names to the graph
        for (int i = 0; i < acc.length; i++) {
            if (acc[i] == null)
                break;
            scs.addVertex(acc[i]);
        }



        //creating the graph
        try {
            Scanner sc = new Scanner(text);
            sc.nextLine();

            String[] arr1 = sc.nextLine().split(",");

            while (sc.hasNextLine()){
                String[] arr2 = sc.nextLine().split(",");
                String line = arr1[5];
                //checking the stop sequence from file
                if (arr2[5].equals(line) && (Integer.parseInt(arr2[3]) - Integer.parseInt(arr1[3]) == 1)){
                    scs.addEdge(arr1[1], arr2[1],Math.abs((Integer.parseInt(arr1[2])-Integer.parseInt(arr2[2]))));
                    scs.addEdge(arr2[1], arr1[1],Math.abs(Integer.parseInt(arr1[2])-Integer.parseInt(arr2[2])));
                    arr1 =arr2;
                }
                else {
                    line = arr2[5];
                    arr1 = arr2;
                }

            }
        }catch (FileNotFoundException ignored){

        }

            Scanner read = new Scanner(System.in);
            // getting the necesarry inputs
            System.out.println();
            System.out.print("Enter the origin station:");
            String origin = read.nextLine();
            System.out.println();
            System.out.print("Enter the destination station:");
            String dest = read.nextLine();
            System.out.println();
            System.out.print("Select a preferetion '1' for time '0' for distance:");
            String answ = read.nextLine().toLowerCase();
            System.out.println();
           
            StackInterface<String> linkedst = new LinkedStack();
            double pathcost = 0;
            //output according to the users need
            try {
                if (answ.equals("1")){
                    pathcost = scs.getShortestPath(origin,dest,linkedst);
                    System.out.println((int) pathcost+" stations");
                }
                else if (answ.equals("0")){
                    pathcost = scs.getCheapestPath(origin,dest,linkedst);
                    System.out.println((int) pathcost / 60+" minutes");
                }
            }
            catch (NullPointerException e){
                System.out.println("Please choose a correct option!!");
            }

            //
            System.out.print("Do you want to look for an other route(Y/N): ");
            String loop = read.nextLine().toLowerCase();
            System.out.println();
            if (loop.equals("y"))
                continue;
            else if (loop.equals("n"))
                userinter = false;
        }



    }
}
