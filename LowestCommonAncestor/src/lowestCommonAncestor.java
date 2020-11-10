package lowestCommonAncestor.src;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class lowestCommonAncestor 
{
    //DAG changes
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
             return findLCA(root,n1,n2);
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
            else
            {
                return right_lca;
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

        System.out.println("findLCA function:");
        
        System.out.println("\t\treturns 2 for findLCA(4,5)");
        assertEquals(2,tree.findLCA(4,5).data);

        System.out.println("\t\treturns 3 for findLCA(6,7)");
        assertEquals(3,tree.findLCA(6,7).data);

        System.out.println("\t\treturns 1 for findLCA(4,7)");
        assertEquals(1,tree.findLCA(4,7).data);

        System.out.println("\t\treturns n1 when n2 not in tree for findLCA(4,8)");
        assertEquals(4,tree.findLCA(4,8).data);

        System.out.println("\t\treturns n2 when n1 not in tree for findLCA(9,7)");
        assertEquals(7,tree.findLCA(9,7).data);
    }
}

//https://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/
