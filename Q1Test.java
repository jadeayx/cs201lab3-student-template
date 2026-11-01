public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){
          int[][] traversals;
          try {
               traversals = new int[][] {
                    parseTraversal(traversal1),
                    parseTraversal(traversal2),
                    parseTraversal(traversal3)
               };
          } catch (IllegalArgumentException e) {
               return "Invalid traversals";
          }

          int size = traversals[0].length;
          if (traversals[1].length != size || traversals[2].length != size) {
               return "Invalid traversals";
          }

          // A BST's inorder traversal must be strictly increasing.
          for (int inorder = 0; inorder < 3; inorder++) {
               boolean increasing = true;
               for (int i = 1; i < size; i++) {
                    if (traversals[inorder][i - 1] >= traversals[inorder][i]) {
                         increasing = false;
                         break;
                    }
               }
               if (!increasing) {
                    continue;
               }

               for (int preorder = 0; preorder < 3; preorder++) {
                    if (preorder == inorder) {
                         continue;
                    }
                    int postorder = 3 - inorder - preorder;
                    if (verifySubtree(traversals[inorder], traversals[preorder],
                              traversals[postorder], 0, size, 0, 0)) {
                         String[] orders = new String[3];
                         orders[inorder] = "Inorder";
                         orders[preorder] = "Preorder";
                         orders[postorder] = "Postorder";
                         return "Traversal 1 - " + orders[0]
                                   + ", Traversal 2 - " + orders[1]
                                   + ", Traversal 3 - " + orders[2];
                    }
               }
          }
          return "Invalid traversals";
     }

     private static int[] parseTraversal(String traversal) {
          if (traversal == null || traversal.trim().isEmpty()) {
               throw new IllegalArgumentException("Empty traversal");
          }
          String[] values = traversal.split("-", -1);
          int[] nodes = new int[values.length];
          for (int i = 0; i < values.length; i++) {
               nodes[i] = Integer.parseInt(values[i].trim());
          }
          return nodes;
     }

     private static boolean verifySubtree(int[] inorder, int[] preorder, int[] postorder,
               int start, int end, int preStart, int postStart) {
          if (start == end) {
               return true;
          }

          int root = preorder[preStart];
          int size = end - start;
          if (root != postorder[postStart + size - 1]) {
               return false;
          }
          int rootIndex = java.util.Arrays.binarySearch(inorder, start, end, root);
          if (rootIndex < 0) {
               return false;
          }

          int leftSize = rootIndex - start;
          return verifySubtree(inorder, preorder, postorder,
                    start, rootIndex, preStart + 1, postStart)
                    && verifySubtree(inorder, preorder, postorder,
                    rootIndex + 1, end, preStart + leftSize + 1, postStart + leftSize);
     }
}
