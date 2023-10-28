package cs.vsu.oop.frolov_vl_a;


import java.io.IOException;

public class Main {

    public static void main(String[] args){
        BTree<Integer> btree = new BTree(3);
        try {
            for (int i = 0; i < 15; i++) {
                btree.add(i);
            }
            for (int i = 0; i < 2; i++){
                System.out.println("--------------------------------");
            }
        }catch (TreeIsNullException e) {
            System.out.println("Error in main");
        }
    }

}
