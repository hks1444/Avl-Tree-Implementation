public class Node {
    //Node implementation
    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }
    public void setVal(String val){
        this.val = val;
    }
    private Node left;
    private Node right;
    private String val;
    int height =1;
    public Node(String a,Node left,Node right){
        this.val=a;
        this.left=left;
        this.right=right;
    }
    public void setHeight(int a){
        this.height=a;
    }
    public int getHeight(){
        return height;
    }
    public String getVal(){
        return val;
    }

}
