package pnlclass_teacher.pnlyy.com.linktask.linktask;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ================================================
 * 作    者：轻风
 * 版    本：1.0
 * 创建日期：2017/8/24 11:34
 * 描    述：任务链（完成一个任务，再一下一个任务）
 * 优点：解决迷之缩进问题,让一套逻辑看起来更清晰
 * Q    Q：2319118797
 * ================================================
 */
public class LinkTask {

    //队列保存每一个任务
    Queue<Task> queue=new LinkedList<>();

    //handler用来回到主线程
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Task task=queue.poll();
            if(task!=null){
                task.task(new Next() {
                    @Override
                    public void next(Object obj) {
                        Message msg=new Message();
                        msg.obj=obj;
                        handler.sendMessage(msg);
                    }
                },msg.obj);
            }else {
                Log.i("LinkTask","所有任务结束了");
            }
        }
    };

    /**
     * 任务开始
     * @param task
     * @return
     */
    public LinkTask start(Task task){
        task.task(new Next() {
            @Override
            public void next(Object obj) {
                Message msg=new Message();
                msg.obj=obj;
                handler.sendMessage(msg);
            }
        },"");
        return this;
    }

    /**
     * 下一个任务
     * @param task
     * @return
     */
    public LinkTask next(Task task){
        queue.offer(task);
        return this;
    }

    public interface Task {
        /**
         * 里边执行一个任务
         * @param next 下一个任务的引用
         * @param obj 上一个任务的结果
         */
        void task(Next next,Object obj);
    }

    public interface Next {
        /**
         * 开始一下一个任务
         * @param obj 下一个任务所需参数
         */
        void next(Object obj);
    }
}
