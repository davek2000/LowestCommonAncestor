class Node:
    def __init__(self,data):
        self.data=data  # this.data=data
        self.right=None # right=null
        self.left=None  # left=null

def findLCA(node,n1,n2):
    if node is None:    # if(node==null)
        return None     # return null
    
    if node.data==n1 or node.data==n2:  # if(node.data==n1 || node.data==n2)
        return node                     #   return node
    
    left_lca = findLCA(node.left,n1,n2) # Node left_lca = findLCA(node.left,n1,n2)
    right_lca = findLCA(node.right,n1,n2)   # Node right_lca = findLCA(node.right,n1,n2)

    if left_lca!=None and right_lca!=None: # if(left_lca!=null && right_lca!=null)
        return node                        # return node

    if left_lca!=None: # if(left_lca!=null)
        return left_lca # return left_lca

    else:
        return right_lca
        #testing testing

#Main
root = Node(1)
root.left = Node(2)
root.right = Node(3)
root.left.left = Node(4)
root.left.right = Node(5)
root.right.left = Node(6)
root.right.right = Node(7)

print ("LCA(4,5) = %d" %(findLCA(root,4,5).data))
    



