package com.leesin.arithmetic.greph;

/**
 * @description: 邻接矩阵实现的有向图
 * @author: Leesin Dong
 * @date: Created in 2020/7/1 0001 11:59
 * @modified By:
 */
public class MatrixDG {
    int size;//图顶点个数
    char[] vertexs;//图顶点名称
    int[][] matrix;//图关系矩阵

    //构造方法进行初始化
    public MatrixDG(char[] vertexs, char[][] edges) {
        size = vertexs.length;
        matrix = new int[size][size];//设定图关系矩阵的大小
        this.vertexs = vertexs;

        for (char[] c : edges) {//设置矩阵制  根据两个边的关系，确定两个节点的下标
            int p1 =  getPosition(c[0]);//根据顶点名称确定对应矩阵下标
            int p2  = getPosition(c[1]);

            //因为是无向图，所以有两个方向，所以把两个方向全部赋值成1
            matrix[p1][p2] = 1;//无向图，在两个对称位置存储

            //=====和无向图相比，只有这里不一样，一个方向赋值为1，另一个方向没有赋值
            // matrix[p2][p1] = 1;
        }
    }
    //图的遍历输出
    public void  print(){
        for (int[] i : matrix) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    //根据顶点名称获取对应的矩阵下标
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++)
            if (vertexs[i]==ch)
                return i;
        return -1;
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};
        char[][] edges = new char[][]{
                {'A','C'}, //A和C之间有关系
                {'A','D'}, //A和D之间有关系
                {'A','F'}, //A和F之间有关系
                {'B','C'},
                {'C','D'},
                {'E','G'},
                {'D','G'},
                {'I','J'},
                {'J','G'},
                {'E','H'},
                {'H','K'}};
        MatrixDG pG = new MatrixDG(vexs, edges); //edges 两个边的关系
        pG.print();
    }
}
