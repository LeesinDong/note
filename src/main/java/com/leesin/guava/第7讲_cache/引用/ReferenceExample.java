package com.leesin.guava.第7讲_cache.引用;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class ReferenceExample
{

    public static void main(String[] args) throws InterruptedException
    {
        //Strong Reference
        /*int counter = 1;

        List<Ref> container = new ArrayList<>();

        for (; ; )
        {
            int current = counter++;
            container.add(new Ref(current));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.MILLISECONDS.sleep(500);
        }*/

//        SoftReference<Ref> reference = new SoftReference<>(new Ref(0));

        /**
         * cr detected the JVM process will be OOM then try to GC soft reference.
         *  只有当检测到快要oom的时候会回收软引用
         *  但是还是会oom，因为快要oom的时候在gc，还没gc完放入的频率很快，导致oom，所以需要加入的频率慢一点
         */

        //soft reference
       /* int counter = 1;

        List<SoftReference<Ref>> container = new ArrayList<>();

        for (; ; )
        {
            int current = counter++;
            container.add(new SoftReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.SECONDS.sleep(1);
        }*/

        /**
         * cr Weak reference The reference will be collected when GC.
         * minor gc、full gc 都会回收弱引用
         * 会oom原因同soft reference
         */
        /*int counter = 1;

        List<WeakReference<Ref>> container = new ArrayList<>();

        for (; ; )
        {
            int current = counter++;
            container.add(new WeakReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.MILLISECONDS.sleep(200);
        }*/

        Ref ref = new Ref(10);
        ReferenceQueue queue = new ReferenceQueue<>();
        MyPhantomReference reference = new MyPhantomReference(ref, queue, 10);
        ref = null;

        System.out.println(reference.get());

        System.gc();
//        TimeUnit.SECONDS.sleep(1);
        Reference object = queue.remove();
        ((MyPhantomReference) object).doAction();

       /* System.gc();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(ref);
        System.out.println(reference.get().index);*/

        /**
         * FileCleanTracker
         *
         * File file = new File();
         * file.create();
         */
    }

    private static class MyPhantomReference extends PhantomReference<Object>
    {

        private int index;

        public MyPhantomReference(Object referent, ReferenceQueue<? super Object> q, int index)
        {
            super(referent, q);
            this.index = index;
        }

        public void doAction()
        {
            System.out.println("The object " + index + " is GC.");
        }
    }

    private static class Ref
    {

        private byte[] data = new byte[1024 * 1024];

        private final int index;

        private Ref(int index)
        {
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable
        {
            System.out.println("The index [" + index + "] will be GC.");
        }
    }
}
