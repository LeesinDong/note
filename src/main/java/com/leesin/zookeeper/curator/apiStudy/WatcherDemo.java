package com.leesin.zookeeper.curator.apiStudy;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @description:
 * @author: Leesin.Dong
 * @date: Created in 2020/4/12 16:40
 * @version: ${VERSION}
 * @modified By:
 */
public class WatcherDemo {
    private static String CONNECTION_STR = "192.168.8.111:2181,192.168.8.112:2181,192.168.8.113:2181";

    public static void main(String[] args) throws Exception {
        //PathChildCache  --针对于子节点的创建、删除和更新 触发事件
        //NodeCache  针对当前节点的变化触发事件
        //TreeCache  综合事件

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();
        addListenerWithChild(curatorFramework);
        // addListenerWithNode(curatorFramework);
        System.in.read();
    }

    //配置中心
    //创建、修改、删除
    private static void addListenerWithNode(CuratorFramework curatorFramework) throws Exception {
        //false 数据压缩，先不管
        //针对哪个节点设置事件
        NodeCache nodeCache=new NodeCache(curatorFramework,"/watch",false);
        NodeCacheListener nodeCacheListener= () -> {
            System.out.println("receive Node Changed");
            System.out.println(nodeCache.getCurrentData().getPath()+"---"+new String(nodeCache.getCurrentData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    //实现服务注册中心的时候，可以针对服务做动态感知
    private static void addListenerWithChild(CuratorFramework curatorFramework) throws Exception {
        //是否缓存
        PathChildrenCache pathChildrenCache = new PathChildrenCache( curatorFramework, "/watch", true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework1, pathChildrenCacheEvent) -> {
            //触发的事件类型     触发时间之后的数据
            System.out.println(pathChildrenCacheEvent.getType() + "---" + pathChildrenCacheEvent.getData().getData());
        };

        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        //这里没有讲，后面会讲
        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }
}
