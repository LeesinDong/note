package com.leesin.arithmetic.tree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * @description: 二叉树遍历
 * @author: Leesin Dong
 * @date: Created in 2020/6/30 0030 22:46
 * @modified By:
 */
public class Traverse {
    //递归的方法，遍历二叉树比较简单
    //总结：这里的left right理解为左子树、右子树而不是单单一个节点
    //理解：先想最顶层的一个二叉树

    /**
     * @description: 非递归实现前序遍历
     * @name: preOrder
     * @param: node
     * @return: void
     * @date: 2020/6/30 0030 22:51
     * @auther: Administrator
    **/
    public static void preOrder(TreeNode head) {
        //采用栈
        if (head != null) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(head);//根节点push进去，进栈
            while (!stack.empty()) {
                TreeNode pop = stack.pop();
                //根左右   --》 根【右 左】  头先出来，所以 头正序 ，左右逆序
                System.out.print(pop.value + " ");
                if (pop.right!=null) {//右子树是不是为空，不为空的时候压栈
                    stack.push(pop.right);//注意这里先压右子树，再压左子树 pop.right注意是pop，即下次while上去的
                }
                if (pop.left != null) {
                    stack.push(pop.left);
                }
            }
        }
    }

    /**
     * @description: 非递归实现中序遍历
     * @name: inOrder
     * @param: head
     * @return: void
     * @date: 2020/6/30 0030 23:00
     * @auther: Administrator
    **/
    public static void inOrder(TreeNode head) {
        //不断的把左子树往栈里面压，直到没有，然后弹出，接着把弹出右子树再往里面压
        if (head != null) {
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {//stack 不为空，或者head不为null
                if (head !=null){//如果head不为null的时候，不断的把左子树压进去
                    stack.push(head);
                    head = head.left;   //先把左子树压入栈，直到没有，下面一层一层的读出左子树，读出左子树的同时，压入右子树
                }else{//否则出栈
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
    }

    /**
     * @description: 非递归实现后序遍历
     * @name: postOrder
     * @param: head
     * @return: void
     * @date: 2020/6/30 0030 23:11
     * @auther: Administrator
    **/
    public static void postOrder(TreeNode head) {
        //两个栈实现
        if (head != null) {
            Stack<TreeNode> stack1 = new Stack<>();
            Stack<TreeNode> stack2 = new Stack<>();
            stack1.push(head);//栈1先把headpush进去
            while (!stack1.isEmpty()) {
                TreeNode pop = stack1.pop();//栈1的元素弹出来
                //左右根->stack2输出，所以全部反过来->【根 右 左】  根【左右】 根先输出，所以根正序，右左逆序
                stack2.push(pop);//栈1弹出的元素放到栈2里面 压栈     =======和左序主要这里不一样
                if (pop.left != null) {//pop.left不为空
                    stack1.push(pop.left);//把left压入
                }
                if (pop.right!=null) {//这里和前序遍历不一样，这里先判断left后判断right，前序遍历正好相反
                    stack1.push(pop.right);
                }
            }
            //while把结果输出
            while (!stack2.isEmpty()) {//stack2不为空输出结果
                System.out.print(stack2.pop().value+" ");
            }
        }
    }

    /**
     * @description: 层次遍历  和前面数据结构不一样，用到了队列的数据结构
     * @name: levelOrder
     * @param: head
     * @return: void
     * @date: 2020/6/30 0030 23:18
     * @auther: Administrator
    **/
    public static void levelOrder(TreeNode head) {
        if (head != null) {
            Queue<TreeNode> queue = new ArrayDeque<>();//声明一个queue
            queue.offer(head);//queue加入head值
            while (!queue.isEmpty()) {//queue不为空的时候
                int levelNum = queue.size();//获取当前层的节点数
                //【每层都是一样的操作】
                for (int i = 0; i < levelNum; i++) {
                     //======这里开始又和前序有点像了
                    TreeNode poll = queue.poll();//出队
                    System.out.print(poll.value+ " ");//把出队的元素输出

                    if (poll.left != null) {
                        queue.offer(poll.left);//poll的left入队
                    }
                    if (poll.right != null) {
                        queue.offer(poll.right); //右子树入队
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TreeNode A  = new TreeNode("A");
        TreeNode B  = new TreeNode("B");
        TreeNode C  = new TreeNode("C");
        TreeNode D  = new TreeNode("D");
        TreeNode E  = new TreeNode("E");
        TreeNode F  = new TreeNode("F");
        TreeNode G  = new TreeNode("G");
        TreeNode H  = new TreeNode("H");
        TreeNode I  = new TreeNode("I");
        TreeNode J  = new TreeNode("J");
        TreeNode K  = new TreeNode("K");

        A.left = B;
        A.right = C;
        B.left = D;
        B.right = E;
        D.left = H;
        D.right = I;
        E.right = J;
        C.left = F;
        C.right = G;
        F.right = K;

        preOrder(A);//A B D H I E J C F K G 
        System.out.println();
        inOrder(A);//H D I B E J A F K C G
        System.out.println();
        postOrder(A);//H I D J E B K F G C A
        System.out.println();
        levelOrder(A);//A B C D E F G H I J K
    }
}
