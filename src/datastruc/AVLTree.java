package datastruc;
public class AVLTree 
{

    public class AVLNode 
    {
     public String title;
     int height;
     AVLNode left;
     AVLNode right;
     AVLNode(String title){this.title = title; this.height = 1;}
    }     

    public AVLNode root;

    public int getHeight(AVLNode node){ // just a null-safe wrapper
     if (node == null){return 0;}
     else return node.height; 
    }

    public int BF(AVLNode node){
     if (node == null){return 0;}
     else return getHeight(node.left) - getHeight(node.right); 
    }

    private AVLNode rotateLeft(AVLNode p){
     AVLNode x = p.right;
     AVLNode y = x.left;
     x.left = p;
     p.right = y;
     p.height = 1 + Math.max(getHeight(p.left), getHeight(p.right));
     x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
     return x;
    }

    private AVLNode rotateRight(AVLNode p){
     AVLNode x = p.left;
     AVLNode y = x.right;
     x.right = p;
     p.left = y;
     p.height = 1 + Math.max(getHeight(p.left), getHeight(p.right));
     x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
     return x;
    }

    private AVLNode rebalance(AVLNode n){
    if (n == null){return null;}
    n.height = 1 + Math.max(getHeight(n.left), getHeight(n.right));
    int BF = BF(n);
     if (BF > 1){ // left-heavy
        if (getHeight(n.left.left) >= getHeight(n.left.right)) // simple left-rotation
         {n = rotateRight(n);}
        else // left-heavy but left subtree is right-heavy: LR rotation
         {
          n.left = rotateLeft(n.left);
          n = rotateRight(n);
         }
      }
     else if (BF < -1){ // right-heavy
        if (getHeight(n.right.right) >= getHeight(n.right.left)) // simple right-rotation
         {n = rotateLeft(n);}
        else // right-heavy but right subtree is left-heavy: RL rotation
         {
          n.right = rotateRight(n.right);
          n = rotateLeft(n);
         }
     }
    return n;
    }

    public AVLNode insert(AVLNode n, String insTitle){
    if (n == null){return new AVLNode(insTitle);} // insert new node straightaway
    int cmp = n.title.compareTo(insTitle);
    if (cmp > 0){n.left = insert(n.left, insTitle);} // recurse left
    else if (cmp < 0){n.right = insert(n.right, insTitle);} // recurse right
    else {System.out.println("Error: title exists in records."); return n;}
    //System.out.println("DEBUG: insert finished, now to rebalance. current tree:");
    return(rebalance(n)); // recursive rebalance at every stage ensures balanced tree at the end :D
    }

    public AVLNode delete(AVLNode n, String delTitle){
    if (n == null){return n;}
    int cmp = n.title.compareTo(delTitle);
    if (cmp > 0){n.left = delete(n.left, delTitle);} // recurse left
    else if (cmp < 0){n.right = delete(n.right, delTitle);} // recurse right
    else { // found node to be deleted
      if (n.left == null || n.right == null) // node doesn't have two children; can replace it by its single child, or directly delete if has no children
       {
        n = (n.left == null) ? n.right : n.left;
       }
      else // tree is filled on both sides
       {
            AVLNode leftmost = n.right; // get leftmost child of right subtree
            while (leftmost.left != null) 
            {
                leftmost = leftmost.left;
            }
        n.title = leftmost.title; // set the current node's title to be that child's
        n.right = delete(n.right, n.title); // delete that child out of right subtree (here it's guaranteed to be a leaf)
       }
    }
    return(rebalance(n)); // recursive rebalance at every stage ensures balanced tree at the end :D
    }

    public AVLNode Search(String srchTitle){
    AVLNode curr = root;
    
    while (curr!=null){
     int cmp = (curr.title).compareTo(srchTitle);
     if (cmp == 0){break;}
     else if (cmp < 0){curr = curr.right;}
     else {curr = curr.left;}
     }
    return curr;    
    }

    public void PrefixSearch(String srchTitle){
    AVLNode curr = root;
    while (curr!=null){
     if (curr.title.startsWith(srchTitle)){System.out.println(curr.title);}
     int cmp = (curr.title).compareTo(srchTitle);
     if (cmp > 0){curr = curr.left;}
     else {curr = curr.right;}
     }
    }

    void preOrderTraverse(AVLNode n) 
     { 
         if (n != null) 
          { 
            System.out.print(n.title + ", "); 
            preOrderTraverse(n.left); 
            preOrderTraverse(n.right); 
          }
     }

    public void alphabeticTraverse(AVLNode n) 
     { 
         if (n != null) 
          { 
            alphabeticTraverse(n.left); 
            System.out.print(n.title + ", "); 
            alphabeticTraverse(n.right); 
          }
     }


    void printTree(AVLNode n) 
     { 
         if (n != null) 
          { 
            System.out.print(n.title + ", "); 
            System.out.print("Left child of " + n.title + ":");  printTree(n.left); 
            System.out.print("Right child of "+ n.title + ":");  printTree(n.right); 
          }
     }
}

