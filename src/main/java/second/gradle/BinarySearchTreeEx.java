package second.gradle;

import java.util.Random;

class Node{
    Node left;
    Node right;
    private int data;

    Node (int data){left =null; right = null; this.data = data;}

    public int getData(){return data;}
    public void setData(int data){this.data = data;}

}

class Tree {
    Node root;
    public Node getRoot(){return root;}

    Tree() {
        root = null;
    }

    public void insert(int data) {

        System.out.println(data);
        if (root == null) {
            root = new Node(data);
            System.out.println("root : " + data);
        } else {
            // 원래 루트의 위치가 사라지지 않도록 대신 경로를 순회할 변수 t를 설정
            Node t = root;
            // left, right 를 식별할 수 있게 변수 추가
            int flag = 0;

            Node p = t;
            while (t != null) {
                if (t.getData() < data) {
                    p = t;
                    t = t.right;
                    flag = 0;
                } else if (t.getData() > data) {
                    p = t;
                    t = t.left;
                    flag = 1;
                    // 하나의 트리에 같은 값이 돌아오면 return
                } else {
                    return;
                }
            }
            if (flag == 0) {
                p.right = new Node(data);
            } else if (flag == 1) {
                p.left = new Node(data);
            }
        }
    }

    public void inorder(Node root) {
        Node p = root;
        if (p == null) return;
        inorder(p.left);
        if(p==this.root) System.out.print("(");
        System.out.print(p.getData() + " > ");
        if(p==this.root) System.out.print(")");

        inorder(p.right);
    }

    public Node inorderSucc(Node current) {
        Node q = current;
        Node p = current.right;

        while (p.left != null){
            q=p;
            p = p.left;
        }


        if (checkLeaf(p)) {
            if(q.left==p)
                q.left = null;
            else
                q.right = null;
        }

        return p;
    }

    public Node inorderPre(Node current) {
        // p.right == null 일경우 while문 자체를 안 돌기 때문에 q는 current로 설정
        Node q = current;
        Node p = current.left;

        while (p.right != null) {
            q = p;
            p = p.right;
        }

        if (checkLeaf(p)) {
            //while 문을 안 돌경우 q.left == p 이므로 q.left 를 null 만들어야 함
            if(q.left==p)
                q.left = null;
            else
                q.right = null;
        }
        // 노드는 잘려있어도 값자체는 살아있다.
        return p;
    }

    public boolean checkLeaf(Node current) {
        if (current.left == null && current.right == null) return true;
        else return false;
    }

    public int delete(int x) {
        int tag = -1;
        int flag = 0;
        Node p = root;
        Node q = root;
        Node temp = root;
        // 노드가 하나만 남았을 때
        if(root.getData()==x&&root.left==null&&root.right==null){
            root=null;
        }
        while (p != null) {
            if (p.getData() > x) {
                q = p;
                p = p.left;
                flag = 0;
            } else if (p.getData() < x) {
                q = p;
                p = p.right;
                flag = 1;
            } else if (p.getData() == x) {
                tag = x;
                if (checkLeaf(p)) {
                    if (flag == 0) q.left = null;
                    else if (flag == 1) q.right = null;
                    return tag;
                } else {
                    break;
                }
            }
        }
        if(p == null) return tag;

        //  노드가 살아 있을 때
        while(!checkLeaf(p)) {
            if (p.left != null) {
                temp = inorderPre(p);
                p.setData(temp.getData());
                p = temp;
            } else {
                temp = inorderSucc(p);
                p.setData(temp.getData());
                p = temp;
            }
        }
        return tag;
    }

    public int depth(Node t) {
        Node temp = t;
            if (temp == null) return 0;
            return 1+Math.max(depth(temp.left), depth(temp.right));
    }



    public Node split(Tree t){
        Node temp = root;
        Node q = root;
        Node p = root;
        if(depth(root.left)-depth(root.right)>4) {
            for (int i = 0; i < depth(root.left)  / 2; i++) {
                q = temp;
                temp = temp.left;
            }
            p=temp;
            q.left = null;
            t.root = p.left;
            p.left=null;
            //System.out.println(p.getData());
        }else if(depth(root.right)-depth(root.left)>4){
            for (int i = 0; i <depth(root.right)/2 ; i++) {
                q=temp;
                temp = temp.right;
            }
            p = temp;
            q.right = null;
            t.root = p.right;
            p.right = null;
            //System.out.println(p.getData());
        }else{
            System.out.println("This don`t need split");
        }
        return p;
    }

    public void join(Node r, Tree t){

        Node temp = root;

        root = r;

        if(temp.getData()<t.root.getData()) {
            root.left = temp;
            root.right = t.root;
        }
        else{
            root.right = temp;
            root.left = t.root;
        }
    }
}


public class BinarySearchTreeEx {

//    static void insert(Tree b) {
//        int n = 15;
//        Random r = new Random();
//        for (int i = 0; i < n; i++) {
//            b.insert(r.nextInt(30));
//            //b.insert(i);
//        }
//    }
    static void insert(Tree b) {
        b.insert(5);
        b.insert(4);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(8);
        b.insert(9);
        b.insert(10);
        b.insert(11);
        b.insert(12);

    }

    static void deleteTest(Tree t) {
        for (int i = 0; i < 15; i++) {
            if (t.getRoot() == null) break;
            if (t.delete(i) == -1)
                System.out.println("No exists");
            else {
                System.out.println("i + \"is deleted\" = " + i + " is deleted");
                t.inorder(t.root);
                System.out.println();
            }
        }
    }



    public static void main(String[] args) {
        Tree b = new Tree();
        insert(b);
        b.inorder(b.root);
        System.out.println();
        //deleteTest(b);
        System.out.println("left : " + b.depth(b.root.left));
        System.out.println("right : " + b.depth(b.root.right));

        Tree t = new Tree();
        Node root = b.split(t);
        System.out.println("새로운 루트 : " +root.getData());

        System.out.println("원래 루트 : " +b.root.getData());
        System.out.println("뒤 따라오는 루트 : "+t.root.getData());

        b.join(root,t);
        b.inorder(b.root);

    }
}
