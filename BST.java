import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.max;

public class BST {
    public Queue<String> logs = new LinkedList<>();
    public Node getRoot() {
        return root;
    }

    protected Node root;
    protected Boolean isAvl = false;
    public BST(Node a){
        this.root = a;
    }
    private Node getMin(Node temp){
        if(temp == null){
            return null;
        }else if(temp.getLeft() == null){
            return temp;
        }else{
            return getMin(temp.getLeft());
        }

    }
    public void print(Node a){
        if(a==null){
            return;
        }
        StringBuilder build = new StringBuilder();
        Queue<Node> ans = new LinkedList<>();
        Queue<Node> second = new LinkedList<>();
        ans.add(a);
        while(!ans.isEmpty()){
            Node Temp = ans.poll();
            if (Temp != null) {
                build.append(Temp.getVal());
                second.add(Temp.getLeft()) ;
                second.add(Temp.getRight());
            }else{
                build.append("O");
            }
            build.append(" ");
            if(ans.isEmpty()){
                build.append("\n");
                while(!second.isEmpty()){
                    ans.add(second.poll());
                }
            }
        }
        System.out.println(build);
    }
    private Node find(Node Temp,String val){
        //returns parent of the searched node
        if (Temp.getVal().compareTo(val)<0){
            if(Temp.getRight().getVal().equals(val)){
                return Temp;
            }else {
                return find(Temp.getRight(), val);
            }
        }else{
            if(Temp.getLeft().getVal().equals(val)){
                return Temp;
            }else {
                return find(Temp.getLeft(), val);
            }
        }
    }
    public void delete(String val,Node enter){
        // finds node to be deleted and which case it is
        // logs the action according to the case
        // finally calls helper function delete2 to do actual deletion
        if(val.equals(root.getVal())){
            return;
        }
        Node par = find(enter,val);
        Node temp;
        if(par.getLeft()!=null){
            if(par.getLeft().getVal().equals(val)){
                temp = par.getLeft();
            }else {
                temp = par.getRight();
            }
        }else{
            temp = par.getRight();
        }
        if (temp.getLeft()!=null&temp.getRight()!=null){
            Node min = getMin(temp.getRight());
            logs.add(par.getVal()+": Non Leaf Node Deleted; removed: "+val+" replaced: "+min.getVal());
            val = min.getVal();
            if(min.getRight()!=null){
                delete2(val,root,1);
            }else{
                delete2(val,root,0);
            }
            temp.setVal(val);
        } else if (temp.getRight()!=null) {
            logs.add(par.getVal()+": Node with single child Deleted: " + val);
            delete2(val,root,1);
        } else if (temp.getLeft()!=null) {
            logs.add(par.getVal()+": Node with single child Deleted: " + val);
            delete2(val,root,2);
        }else{
            logs.add(par.getVal()+": Leaf Node Deleted: "+val);
            delete2(val,root,0);

        }
    }
    private Node delete2(String val,Node Temp,int type){
        // this is private function to help deletion operation
        // reaches the given node recursively by going down through the tree
        // rebalances in the upward direction after deletion
        if(Temp.getVal().equals(val)){
            switch (type){
                case 1:
                    return Temp.getRight();
                case 2:
                    return Temp.getLeft();
                default:
                    return null;
            }
        } else if (Temp.getVal().compareTo(val)<0) {
            Temp.setRight(delete2(val,Temp.getRight(),type));
        }else{
            Temp.setLeft(delete2(val,Temp.getLeft(),type));
        }
        if(isAvl){
            Temp = rebalance(Temp);
        }
        Temp.setHeight(max(nodeHeight(Temp.getRight()),nodeHeight(Temp.getLeft()))+1);
        return Temp;
    }

    public Node addNode(String val,Node Temp){
        // adds node according to the bst tree rules
        // reaches the parent node recursively by going down through the tree
        // rebalances in the upward direction after insertion
        if(Temp==null){
            return new Node(val,null,null);
        }else{
            logs.add(Temp.getVal()+": New node being added with IP:"+val);
            if(Temp.getVal().compareTo(val)>0){
                Temp.setLeft(addNode(val,Temp.getLeft()));
            }else{
                Temp.setRight(addNode(val,Temp.getRight()));
            }
        }
        if(isAvl){
            Temp = rebalance(Temp);
        }
        Temp.setHeight(max(nodeHeight(Temp.getRight()),nodeHeight(Temp.getLeft()))+1);
        return Temp;

    }

    private Node rebalance(Node temp) {
        // rebalances the node according to its and its children's balance factor
        // does one of the four different rotations
        if(isAvl){
        if(getBalance(temp)>1){
            if (getBalance(temp.getLeft()) < 0) {
                logs.add("Rebalancing: left-right rotation");
                temp.setLeft(leftRotation(temp.getLeft()));
            }else{
                logs.add("Rebalancing: right rotation");
            }
            return rightRotation(temp);
        }else if(getBalance(temp)<-1){
            if (getBalance(temp.getRight()) > 0) {
                logs.add("Rebalancing: right-left rotation");
                temp.setRight(rightRotation(temp.getRight()));
            }else{
                logs.add("Rebalancing: left rotation");
            }
            return leftRotation(temp);
        }else{
            return temp;
        }
        }else{
            return null;
        }
    }
    private int nodeHeight(Node temp){
        //returns node's height
        //returns 0 if node is null
        return (temp !=null) ? temp.getHeight() : 0;
    }
    private int getBalance(Node temp) {
        //returns node's balance factor
        return nodeHeight(temp.getLeft())-nodeHeight(temp.getRight());
    }

    private Node rightRotation(Node temp){
        // does the right rotation and sets necessary links and fields
        Node child = temp.getLeft();
        temp.setLeft(child.getRight());
        temp.setHeight(max(nodeHeight(temp.getRight()),nodeHeight(temp.getLeft()))+1);
        child.setRight(temp);
        child.setHeight(max(nodeHeight(child.getRight()),nodeHeight(child.getLeft()))+1);
        if(root.equals(temp)){
            root=child;
        }
        return child;
    }
    private Node leftRotation(Node temp){
        // does the right rotation and sets necessary links and fields
        Node child = temp.getRight();
        temp.setRight(child.getLeft());
        temp.setHeight(max(nodeHeight(temp.getRight()),nodeHeight(temp.getLeft()))+1);
        child.setLeft(temp);
        child.setHeight(max(nodeHeight(child.getRight()),nodeHeight(child.getLeft()))+1);
        if(root.equals(temp)){
            root=child;
        }
        return child;
    }

    public void sendMessage(String sender,String receiver) {
        //reaches sender and receiver by going through the tree and records the paths
        //removes all the same elements of the lists except last same element
        //concatenates two lists and sends message from one end to another end of the list
        ArrayList<Node> rec = new ArrayList<>();
        ArrayList<Node> send = new ArrayList<>();
        Node Temp= root;
        while(!Temp.getVal().equals(sender)){
            send.add(Temp);
            if(Temp.getVal().compareTo(sender)>0){
                Temp = Temp.getLeft();
            }else{
                Temp = Temp.getRight();
            }
        }
        send.add(Temp);
        Temp= root;
        while(!Temp.getVal().equals(receiver)){
            rec.add(Temp);
            if(Temp.getVal().compareTo(receiver)>0){
                Temp = Temp.getLeft();
            }else{
                Temp = Temp.getRight();
            }
        }
        rec.add(Temp);
        Temp= root;
        Iterator<Node> rec1 = rec.iterator();
        Iterator<Node> send1 = send.iterator();
        while(send1.hasNext() & rec1.hasNext()) {
            Node a = send1.next();
            Node b = rec1.next();
            if (a.getVal().compareTo(b.getVal()) == 0) {
                Temp = a;
                send1.remove();
                rec1.remove();
            } else {
                break;
            }
        }
        send.add(0, Temp);
        for (Node node : rec) {
            send.add(0, node);
        }
        for(int i=send.size()-1;i>=0;i--){
            if(i==send.size()-1){
                logs.add(sender+": Sending message to: "+receiver);
            }else if(i==0){
                logs.add(receiver+": Received message from: "+sender);
            }else{
                logs.add(send.get(i).getVal()+": Transmission from: "+send.get(i+1).getVal()+" receiver: "+receiver+" sender:"+sender);
            }
        }
    }
}
