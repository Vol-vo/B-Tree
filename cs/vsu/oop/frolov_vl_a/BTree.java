package cs.vsu.oop.frolov_vl_a;

/*
 * Делаю свою структуру B*-Tree
 */

import java.util.ArrayList;
import java.util.List;

public class BTree<T extends Comparable<T>> {

    private class Node<T extends Comparable<T>> {
        private final List<T> data;  //хранит в себе значения нашего узла
        private final List<BTree<T>.Node<T>> children; //дочерние узлы
        private final int t; //количество ключей узла

        private Node(int t, T value) {
            this.data = new ArrayList<>();
            this.children = new ArrayList<>();
            this.t = t;
            this.data.add(value);
        }

        public Node(int t) {
            this.data = new ArrayList<>();
            this.children = new ArrayList<>();
            this.t = t;
        }

        public boolean nodeIsMax() {
            return this.data.size() == this.t;
        }

        public void addChild(BTree<T>.Node<T> child) {
            this.children.add(child);
        }

        public T setNewTAndReturnOldT(T newT) { //Вставляем новый Элемент и удаляем последний
            for (int i = 0; i < this.data.size(); i++){
                if(newT.compareTo(this.data.get(i)) > 0) {
                    this.data.add(i + 1, newT);
                }
            }
            T oldT = this.data.get(this.data.size() - 1);
            this.data.remove(this.data.size() - 1);
            return oldT;
        }

        public boolean isLeaf() {   //Проверяем узел, листовой он или нет
            return this.children.size() == 0;
        }

        public T getValue(int i) {
            return data.get(i);
        }


        public BTree<T>.Node<T> getNecessaryChild(T value) {  //метод возвращает Node нужного ребёнка
            for (int i = 0; i < this.data.size(); i++) {
                if (value.compareTo(this.data.get(i)) < 0) {
                    return this.children.get(i);
                }
            }
            return this.children.get(this.children.size() - 1);
        }

        public List<BTree<T>.Node<T>> getChildren() {
            return this.children;
        }

        public void add(T value) throws TreeIsNullException {
            if (this.data.size() < t) {
                if (this.data.size() == 0) {
                    this.data.add(value);
                    return;
                }
                for (int i = 0; i < this.data.size(); i++) {
                    if (value.compareTo(data.get(i)) > 0) {
                        data.add(i, value);
                        return;
                    }
                }
            } else {
                throw new TreeIsNullException("Node is overloaded");
            }
        }

        public List<T> getData() {
            return data;
        }

    }

    private Node<T> root;
    private final int t;

    public BTree(int t) {
        this.t = t;
        this.root = new Node<>(t);
    }

    public void add(T value) throws TreeIsNullException {
        Node<T> currentNode = root;
        while (currentNode.getChildren().size() != 0) {
            currentNode = currentNode.getNecessaryChild(value); //доходим до нужного листа
        }
        if (!currentNode.nodeIsMax()) {
            currentNode.add(value);

            return;
        }
        balance(value);
    }

    private void balance(T value) throws TreeIsNullException {
        T newValue = balance(value, this.root);
        if (newValue != null) {
            Node<T> newRoot = new Node<>(this.t, newValue);
            newRoot.addChild(this.root);
            newRoot.addChild(new Node<>(this.t));
            this.root = newRoot;
        }
    }

    private T balance(T value, Node<T> currentNode) throws TreeIsNullException {
        if (currentNode.isLeaf()) {
            if (!currentNode.nodeIsMax()){
                currentNode.add(value);
                return null;
            }
            for (int i = 0; i < currentNode.getData().size(); i++) {
                if (value.compareTo(currentNode.getValue(i)) > 0) { //где-то тут ошибка
                    T oldT = currentNode.getValue(currentNode.getData().size() - 1);
                    currentNode.getData().remove(currentNode.getData().size() - 1);
                    currentNode.add(value);
                    return oldT;
                }
            }
        }
        T newT = balance(value, currentNode.getNecessaryChild(value));
        if (!currentNode.nodeIsMax()){
            if (newT != null) {
                currentNode.add(newT);
            }
            return null;
        }
        return currentNode.setNewTAndReturnOldT(newT);
    }
}
