package cs.vsu.oop.frolov_vl_a;

/*
* Делаю свою структуру B*-Tree
* */


import java.util.ArrayList;
import java.util.List;

public class BTree<T> {

    private class Node<T extends Comparable<T>> {
        List<T> data = new ArrayList<T>();  //хранит в себе значения нашего узла
        List<Node> children = new ArrayList<Node>(); //дочерние узлы
        int t; //количество ключей узла
        boolean isLeaf; //является ли узел листом

        public Node(int t) {
            this.t = t;
        }

        public void add(T value) throws TreeIsNullException {
            if (data.size() < t) {
                for (int i = 0; i < data.size(); i++) {
                    if (value.compareTo(data.get(i)) > 0) {
                        data.set(i - 1, value);
                    }
                }
            }else {
                throw new TreeIsNullException("Node is overloaded");
            }
        }

    }

    private Node root;
    private int t;

    public BTree(int t) {
        this.t = t;
        root = null;
    }


}
