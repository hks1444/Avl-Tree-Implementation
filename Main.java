import java.io.*;
import java.util.Scanner;
//Hasan Kerem Seker
public class Main {
    public static void main(String[] args) throws IOException {
        final boolean isavl = true;
        final boolean isbst = true;
        File input = new File(args[0]);
        //File input = new File(args[0]);
        Scanner reader = new Scanner(input);
        File bst_out = new File(args[1]+"_BST.txt");
        File avl_out = new File(args[1]+"_AVL.txt");
        Writer avl_writer = new FileWriter(avl_out);
        Writer bst_writer = new FileWriter(bst_out);
        String a = reader.next();
        String b;
        String c;
        BST bst = new BST(new Node(a,null,null));
        AVL avl = new AVL(new Node(a,null,null));
        while(reader.hasNext()){
            a = reader.next();
            b = reader.next();
            if(a.equals("ADDNODE")){
                if(isavl){avl.addNode(b,avl.getRoot());}
                if(isbst){bst.addNode(b,bst.getRoot());}
            } else if (a.equals("SEND")) {
                c=reader.next();
                if(isavl){avl.sendMessage(b,c);}
                if(isbst){bst.sendMessage(b,c);}
            }else if(a.equals("DELETE")){
                if(isavl){avl.delete(b,avl.getRoot());}
                if(isbst){bst.delete(b,bst.getRoot());}
            }
        }
        while(!avl.logs.isEmpty()){
            avl_writer.write(avl.logs.poll()+"\n");
        }
        while(!bst.logs.isEmpty()){
            bst_writer.write(bst.logs.poll()+"\n");
        }
        bst_writer.close();
        avl_writer.close();
    }
}