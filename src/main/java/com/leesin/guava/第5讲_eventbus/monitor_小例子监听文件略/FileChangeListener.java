package com.leesin.guava.第5讲_eventbus.monitor_小例子监听文件略;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class FileChangeListener
{

    private final static Logger LOGGER = LoggerFactory.getLogger(FileChangeListener.class);

    @Subscribe
    public void onChange(FileChangeEvent event)
    {
        LOGGER.info("{}-{}", event.getPath(), event.getKind());
    }
}
