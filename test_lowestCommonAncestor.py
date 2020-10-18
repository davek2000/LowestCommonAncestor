import unittest
import lowestCommonAncestor
from lowestCommonAncestor import Node

class TestLowestCommonAncestor(unittest.TestCase):

    def test_findLCA(self):
        root=Node(1)
        root.left = Node(2)
        root.right = Node(3)
        root.left.left = Node(4)
        root.left.right = Node(5)
        root.right.left = Node(6)
        root.right.right = Node(7)

        print("findLCA function:")
        
        print("\t\treturns 2 for findLCA(4,5)")
        self.assertEqual(lowestCommonAncestor.findLCA(root,4,5).data,2)

        print("\t\treturns 3 for findLCA(6,7)")
        self.assertEqual(lowestCommonAncestor.findLCA(root,6,7).data,3)

        print("\t\treturns 1 for findLCA(4,7)")
        self.assertEqual(lowestCommonAncestor.findLCA(root,4,7).data,1)

        print("\t\treturns n1 when n2 not in tree for findLCA(4,8)")
        self.assertEqual(lowestCommonAncestor.findLCA(root,4,8).data,4)

        print("\t\treturns n2 when n1 not in tree for findLCA(9,7)")
        self.assertEqual(lowestCommonAncestor.findLCA(root,9,7).data,7)

if __name__ == '__main__':
    unittest.main()