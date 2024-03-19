package supermarket_management;


public class HashTable<K,V> {
    private hash_node[] sas;
    int size = 0;
    int collision_counter = 0;

    boolean stt = false;

    private static final int DEFAULT_CAPACITY = 7013;

    private int capacity;

    private hash_node<K,V>[] table;
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int cpc) {
        this.capacity = cpc;
        this.size = 0;
        this.table = new hash_node[capacity];
    }


    public void  add(K key, V value, K key1, V value1){

        sizecontrol50();

        double index = hashFuncpaf((String) key);

        double liner =doublehas((String) key, (int)index);

       if (table[(int)index] == null){
            hash_node<K,V> sc = new hash_node<>(key, value);
            table[(int)index] = sc;
            addtransaction((int)index,(String) key,key1,value1);
            table[(int)index].upCounter();
            size++;
        } else if (table[(int)index] != null && table[(int)index].getKey().equals(key)) {
           addtransaction((int)index,(String) key,key1,value1);
           table[(int)index].upCounter();
           size++;
       } else if (table[(int)index] != null && table[(int)liner] == null) {
           hash_node<K,V> sc = new hash_node<>(key, value);
           table[(int)liner] = sc;
           addtransaction((int)liner,(String) key,key1,value1);
           table[(int)liner].upCounter();
           size++;
           collision_counter++;

       } else if (table[(int)index] != null && table[(int)liner] != null && table[(int)liner].getKey().equals(key)) {
           addtransaction((int)liner,(String) key,key1,value1);
           table[(int)liner].upCounter();
           size++;
           collision_counter++;
       }
    }

    public void addtransaction(int inde,String mainkey, K key, V value){

        if (table[inde].getKey().equals(mainkey)){
            hash_node<K, V> temp = table[inde];
            hash_node<K, V> nedat= new hash_node<>(key,value);
            while (temp.getLink()!= null){
                temp = temp.getLink();

            }
            temp.setLink(nedat);
        }


    }
    public void display(){
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null){
                System.out.println(table[i].getKey()+" "+ table[i].getValue());
                System.out.println(i);
            }
        }
    }





    public int linearprb(String key, int shldindex){

        int newindex = 0;
        boolean tst = false;



        for (int i = shldindex; i < table.length; i++) {
            if (table[i] != null && this.table[i].getKey().equals(key)){
                newindex = i;
                tst = true;
                break;
            }
        }

        if (!tst){
            for (int i = shldindex; i < table.length; i++) {
                if (this.table[i] == null){
                    newindex = i;
                    tst = true;
                    break;
                }
            }
        }

        if (!tst){
            for (int i = 0; i < shldindex; i++) {
                if (table[i] != null && this.table[i].getKey().equals(key) ){
                    newindex = i;
                    tst = true;
                    break;
                }
            }
        }

        if (!tst){
            for (int i = 0; i < shldindex; i++) {
                if (this.table[i] == null){
                    newindex = i;
                    tst = true;
                    break;
                }
            }
        }
        return newindex;
    }

    public double doublehas(String key, double shldindex1){
        stt = true;

        double newindex = shldindex1;

        int k = 1;

        while (table[(int) newindex] != null ){
            if (newindex >= table.length){
                newindex = 0;
            }
            newindex= (shldindex1 + k*(13- (shldindex1 % 13))) % table.length;
            k++;
        }
        return newindex;
    }

    public int hashFuncssf(String key) {
        key = key.toLowerCase();
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += key.charAt(i)-96;
        }

        if (sum < 0)
            return -sum % table.length;
        else
            return sum % table.length;
    }

    public double hashFuncpaf(String key){
        key = key.toLowerCase();
        double sous = 0;
        stt = true;

            for (int i = 0; i < key.length(); i++) {
                sous +=  ((key.charAt(i)- 96) * Math.pow(7,(key.length()-(1+i))));
            }

            if (sous < 0){
                return (-1*sous) % table.length;
            }

            else
                return sous % table.length;


    }

    public int find(String key){
        for (int i = 0; i < table.length; i++) {
            if (table[i]!= null && table[i].getKey().equals(key))
                return i;
        }
        return 0;
    }

    public boolean contains(String key){
        for (int i = 0; i < table.length; i++) {
            if (table[i]!= null && table[i].getKey().equals(key))
                return true;
        }
        return false;
    }

    public int trnst(String key){
        int sum =0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && table [i].getKey().equals(key))
                sum += table[i].getCounter();
        }
        return sum;
    }

    public void get(String key){
        double as =hashFuncpaf(key);
        double ts = doublehas(key, (int)as);

        if (stt){
            int t = find(key);
            int f = trnst(key);
            System.out.println();
            System.out.println(">Search:" + table[t].getKey());
            System.out.println();
            System.out.println(f+ " transactions found for "+ table[t].getValue());
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null && table [i].getKey().equals(key)){
                    hash_node temp = table[i];
                    temp =temp.getLink();
                    while (temp.getLink()!= null){
                        System.out.println(temp.getKey()+", "+temp.getValue());
                        temp = temp.getLink();
                    }
                    System.out.println(temp.getKey()+", "+temp.getValue());
                }
            }
        }
        else {
            if (table[(int)as] == null)
                System.out.println("Customer cannot be found!");
            else if(table[(int)as] != null  && table[(int)as].getKey().equals(key)) {
                hash_node temp = table[(int)as];
                System.out.println();
                System.out.println(">Search:" + table[(int)as].getKey());
                System.out.println();
                System.out.println(table[(int)as].getCounter()+ " transactions found for "+ table[(int)as].getValue());
                temp =temp.getLink();
                while (temp.getLink()!= null){
                    System.out.println(temp.getKey()+", "+temp.getValue());
                    temp = temp.getLink();
                }
                System.out.println(temp.getKey()+", "+temp.getValue());
            }
            else if (table[(int)as] != null  &&  table[(int)ts] != null && table[(int)ts].getKey().equals(key)){
                hash_node temp = table[(int)ts];
                System.out.println();
                System.out.println(">Search:" + table[(int)ts].getKey());
                System.out.println();
                System.out.println(table[(int)ts].getCounter()+ " transactions found for "+ table[(int)ts].getValue());
                temp =temp.getLink();
                while (temp.getLink()!= null){
                    System.out.println(temp.getKey()+", "+temp.getValue());
                    temp = temp.getLink();
                }
                System.out.println(temp.getKey()+", "+temp.getValue());
            }
        }



    }

    public void sizecontrol50(){
        if ((double)size > (double) table.length/2){
            hash_node[] temp = new hash_node[table.length*2];
            System.arraycopy(table, 0, temp, 0, table.length);
            table = new hash_node[temp.length];
            table = temp;
        }
    }
    public void sizecontrol80(){
        if ((double)size > (double) table.length * (0.8)){
            hash_node[] temp = new hash_node[table.length*2];
            System.arraycopy(table, 0, temp, 0, table.length);
            table = new hash_node[temp.length];
            table = temp;
        }
    }
    public int getSize() {
        return size;
    }
}
