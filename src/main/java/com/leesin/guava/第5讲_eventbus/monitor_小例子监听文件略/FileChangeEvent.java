package com.leesin.guava.第5讲_eventbus.monitor_小例子监听文件略;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class FileChangeEvent
{

    private final Path path;

    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind)
    {
        this.path = path;
        this.kind = kind;
    }

    public Path getPath()
    {
        return path;
    }

    public WatchEvent.Kind<?> getKind()
    {
        return kind;
    }
}
