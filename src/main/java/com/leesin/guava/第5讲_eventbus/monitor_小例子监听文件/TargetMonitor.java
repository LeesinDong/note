package com.leesin.guava.第5讲_eventbus.monitor_小例子监听文件;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public interface TargetMonitor
{

    void startMonitor() throws Exception;

    void stopMonitor() throws Exception;
}
