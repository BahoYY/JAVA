package supermarket_management;
public class hash_node<K,V> {
    private K key;
    private V value;

    int counter =0;
    public hash_node<K,V> link = null;


    public hash_node(K key, V value)
    {
        this.key=key;
        this.value=value;
        this.counter = 0;
        link=null;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
    public hash_node<K,V> getLink() {
        return link;
    }
    public void setLink(hash_node<K,V> link) {
        this.link = link;
    }
    public int getCounter() {
        return counter;
    }
    public void upCounter(){counter++;}
}
