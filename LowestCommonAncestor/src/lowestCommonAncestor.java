package lowestCommonAncestor.src;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class lowestCommonAncestor 
{
    class Node
    {
        int data;
        Node left,right;

        public Node(int data)
        {
            this.data=data;
            left=null;
            right=null;
        }
    }

    class BinaryTree
    {
        Node root;

        Node findLCA(int n1, int n2)
        {
            //return findLCA(root,n1,n2);
            Node answer = findLCA(root,n1,n2);
            if(answer==null)
            {
                return null;
            }
            else return answer;
        }

        Node findLCA(Node node, int n1,int n2)
        {
            //Base case
            if(node==null)
            {
                return null;
            }
            if(node.data==n1 || node.data==n2)
            {
                return node;
            }

            Node left_lca = findLCA(node.left,n1,n2);
            Node right_lca = findLCA(node.right,n1,n2);

            if(left_lca!=null && right_lca!=null)
            {
                return node;
            }

            if(left_lca!=null)
            {
                return left_lca;
            }
            /*else
            {
                return right_lca;
            }*/
            if(right_lca!=null)
            {
                return right_lca;
            }
            else
            {
                return null;
            }
        }
    }

    @Test
    public void testLCA()
    {
        
        BinaryTree tree = new BinaryTree();
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);
        tree.root.right.left = new Node(6);
        tree.root.right.right = new Node(7);

        BinaryTree tree2 = new BinaryTree();
        tree2.root=new Node(0);
        tree2.root.left=new Node(4);
        tree2.root.right=new Node(5);
        
        System.out.println("findLCA function:");
        System.out.println("\t\treturns 2 for findLCA(4,5)");
        assertEquals(2,tree.findLCA(4,5).data);
    }
}

//https://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/
