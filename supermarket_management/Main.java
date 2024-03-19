package supermarket_management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        double  startindexing = System.currentTimeMillis();

        HashTable<String,String> scs = new HashTable<>();


        File text = new File("C:\\Users\\Bahadır\\IdeaProjects\\untitled\\src\\HW_1\\supermarket_dataset_50K.csv");
        //File text = new File("C:\\Users\\Bahadır\\IdeaProjects\\untitled\\src\\HW_1\\customer_1K.txt");
        int con = 0;

        try{
            Scanner sc = new Scanner(text);
            while (sc.hasNextLine()){
                if (con== 0){
                    con++;
                    String chc = sc.nextLine();
                } else if (con >= 1) {
                    String chc = sc.nextLine();
                    String[] arr = chc.split(",");
                    if (arr.length == 1){
                        scs.add(arr[0],null,null,null);
                    }
                    else{
                        scs.add(arr[0],arr[1],arr[2],arr[3]);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        double endindexing = System.currentTimeMillis();


        scs.get("dabf5ded-dc94-48c9-8e9d-c522b976aaa1");


        double endsearch = System.currentTimeMillis();

        System.out.println("Collision number: "+scs.collision_counter);

        System.out.println("Indexing Time: "+String.format("%.2f", (endindexing-startindexing)/1000));
        System.out.println("Search Time: "+String.format("%.2f", (endsearch-endindexing)/1000));


    }
}
