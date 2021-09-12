package com.leesin.guava.第5讲_eventbus.monitor_小例子监听文件;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class DirectoryTargetMonitor implements TargetMonitor
{

    private final static Logger LOGGER = LoggerFactory.getLogger(DirectoryTargetMonitor.class);

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath)
    {
        this(eventBus, targetPath, "");
    }

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePaths)
    {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }


    @Override
    public void startMonitor() throws Exception
    {
        // 利用nio相关api
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        LOGGER.info("The directory [{}] is monitoring... ", path);

        this.start = true;
        while (start)
        {
            WatchKey watchKey = null;
            try
            {
                // 阻塞等待
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event ->
                {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    // context是最后面的路径（xxx.png），resolve获得绝对路径
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            } catch (Exception e)
            {
                // 可以通过stopMonitor方法，接收Interrupt异常
                // 发生异常的时候，停止处理
                this.start = false;
            } finally
            {
                // 必须reset，否则下次捕获不到，无法处理
                if (watchKey != null)
                    watchKey.reset();
            }
        }
    }

    @Override
    public void stopMonitor() throws Exception
    {
        LOGGER.info("The directory [{}] monitor will be stop...", path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        LOGGER.info("The directory [{}] monitor will be stop done.", path);
    }
}
