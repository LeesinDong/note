package com.leesin.heightConcurrent.forkJoin;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

//上面的例子要实现的是将 CountTask task = new CountTask(0, 20000L);
// 这句话中的start到end进行累加运算，如果end-start<ThRESHOLD,就直接累加，如果超过这个阈值就通过ForkJoin将任务分成多个子任务，进行累加，所有的子任务完成之后再求和。
public class CountTask extends RecursiveTask<Long> {
    private static final int ThRESHOLD = 10000;
    private long start;
    private long end;
    public CountTask(long start,long end){
        this.start = start;
        this.end = end;
    }
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) < ThRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        }else{
            //分成100个小任务
            long step = (start + end) / 100;
            ArrayList<CountTask> subTasks = new ArrayList<CountTask>();
            long pos = start;
            for (int i = 0; i <100 ; i++) {
                long lastOne = pos + step;
                if (lastOne>end) {
                    lastOne  = end;
                }
                CountTask subTask = new CountTask(pos, lastOne);
                pos += step + 1;

                subTasks.add(subTask);
                //将现任务加进去
                subTask.fork();
            }
            for (CountTask t : subTasks) {
                //等待所有的任务完成后再继续相应的操作
                sum += t.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0, 20000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long res = result.get();

            System.out.println("sum="+res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

