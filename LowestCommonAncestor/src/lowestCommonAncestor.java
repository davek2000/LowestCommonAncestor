package lowestCommonAncestor.src;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
public class lowestCommonAncestor 
{
    public static final int FOUND_A=-2;
    public static final int FOUND_B=-3;

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

    class binaryTree
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
    
    public class DAG
    {
        class dagNode
        {
            public ArrayList<dagNode> children;
            public int data;
            dagNode(int data)
            {
                children=new ArrayList<dagNode>();
                this.data=data;
            }

        }
        public int findLCA(DAG dag,int n1,int n2)
        {
            DAG.dagNode curNode = dag.head;

            int lca=findLCA(curNode,n1,n2);
            if(lca<0)
            {
                return -1;
            }
            return lca;
        }

        public int findLCA(dagNode curNode,int n1,int n2)
        {
            int curNodeVal=curNode.data;
            boolean foundA=false;
            boolean foundB=false;

            for(int i=0;i<curNode.children.size();i++)
            {
                int subTreeOf=findLCA(curNode.children.get(i), n1, n2);
                if(subTreeOf>=0)
                {
                    return subTreeOf;
                }
                else
                {
                    if(subTreeOf==FOUND_B)
                    {
                        foundB=true;
                    }
                    if(subTreeOf==FOUND_A)
                    {
                        foundA=true;
                    }
                }
            }
            if((foundA && (foundB || curNodeVal==n2)) || (foundB && (curNodeVal==n1)))
            {
                return curNodeVal;
            }
            if(curNodeVal==n1 && curNodeVal==n2)
            {
                return curNodeVal;
            }
            if(foundA || curNodeVal==n1)
            {
                return FOUND_A;
            }
            if(foundB || curNodeVal==n2)
            {
                return FOUND_B;
            }
            return -1;
        }
        dagNode head = null;

        public void addNode(int data,ArrayList<dagNode> parents)
        {
            dagNode theNode = new dagNode(data);
            if(head==null)
            {
                head=theNode;
            }
            if(parents!=null)
            {
                for(dagNode parentNode:parents)
                {
                    if(parentNode!=null)
                    {
                        parentNode.children.add(theNode);
                    }
                }
            }
        }

        public dagNode getNodeWithData(int data)
        {
            if(head!=null)
            {
                return getNodeRecursive(data,head);
            }
            return null;
        }

        private dagNode getNodeRecursive(int data,dagNode node)
        {
            for(dagNode child:node.children)
            {
                dagNode theRetNode = getNodeRecursive(data,child);
                if(theRetNode!=null)
                {
                    return theRetNode;
                }
            }
            if(node.data==data)
            {
                return node;
            }
            return null;
        }
    }

    @Test
    public void testLCA()
    {
        
        binaryTree tree = new binaryTree();
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
        
        /*
                         0
                       /   \
                      1     2
                     / \   / \
                    3   4 /   \
                         5     6
        */            
        DAG directedGraph = new DAG();
        directedGraph.addNode(0,null);
        directedGraph.addNode(1, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph.head)));
		directedGraph.addNode(2, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph.head)));
		directedGraph.addNode(3, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph.getNodeWithData(1))));
		directedGraph.addNode(4, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph.getNodeWithData(1))));
		directedGraph.addNode(5, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph.getNodeWithData(2))));
		directedGraph.addNode(6, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph.getNodeWithData(2))));

        assert(directedGraph.findLCA(directedGraph, 0, -1) == -1);
		assert(directedGraph.findLCA(directedGraph, 2, lowestCommonAncestor.FOUND_A) == -1);
		assert(directedGraph.findLCA(directedGraph, 2, lowestCommonAncestor.FOUND_B) == -1);
        assert(directedGraph.findLCA(directedGraph, lowestCommonAncestor.FOUND_B, lowestCommonAncestor.FOUND_A) == -1);
        assert(directedGraph.findLCA(directedGraph,5,6)==2);


        /*
                            0
                          /   \
                         1     2
                          \   /
                            3
                          /   \
                         4     5
                        / \   / \
                       6   7 /   \
                            8     9
                             \   /
                               10 

        */
        DAG directedGraph2 = new DAG();
		directedGraph2.addNode(0, null);
		directedGraph2.addNode(1, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.head)));
		directedGraph2.addNode(2, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.head)));
        directedGraph2.addNode(3, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(1),directedGraph.getNodeWithData(2))));
        directedGraph2.addNode(4, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(3))));
        directedGraph2.addNode(5, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(3))));
        directedGraph2.addNode(6, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(4))));
        directedGraph2.addNode(7, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(4))));
        directedGraph2.addNode(8, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(5))));
        directedGraph2.addNode(9, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(5))));
        directedGraph2.addNode(10, new ArrayList<lowestCommonAncestor.DAG.dagNode>(Arrays.asList(directedGraph2.getNodeWithData(8),directedGraph.getNodeWithData(9))));
		

        System.out.println("findLCA DAG function:");
        
        System.out.println("\t\t\treturns 0 for DAG.findLCA(1,2)");
        int answer=directedGraph2.findLCA(directedGraph2, 1, 2);
		assertEquals(0,answer);
        
        System.out.println("\t\t\treturns 0 for DAG.findLCA(1,0)");
        answer=directedGraph2.findLCA(directedGraph2, 1, 0);
        assertEquals(0,answer);
        
        System.out.println("\t\t\treturns 3 for DAG.findLCA(4,5)");
        answer=directedGraph2.findLCA(directedGraph2, 4, 5);
        assertEquals(3,answer);
        
        System.out.println("\t\t\treturns 4 for DAG.findLCA(6,7)");
        answer=directedGraph2.findLCA(directedGraph2, 6, 7);
        assertEquals(4,answer);
        
        System.out.println("\t\t\treturns 5 for DAG.findLCA(8,9)");
        answer=directedGraph2.findLCA(directedGraph2, 8, 9);
        assertEquals(5,answer);
        
        System.out.println("\t\t\treturns 3 for DAG.findLCA(10,7)");
        answer=directedGraph2.findLCA(directedGraph2, 10, 7);
        assertEquals(3,answer);

        System.out.println("\t\t\treturns 3 for DAG.findLCA(3,4)");
        answer=directedGraph2.findLCA(directedGraph2, 3, 4);
        assertEquals(3,answer);
    }
}
//https://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/