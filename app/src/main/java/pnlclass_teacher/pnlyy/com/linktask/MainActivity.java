package pnlclass_teacher.pnlyy.com.linktask;

import android.com.linktasklib.LinkTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 测试一下
     */
    private void test() {
        //说明:执行完一个任务，调用next.next();去执行下一个任务。
        //next.next();里可以传入上一个任务的结果给下一个任务。
        //每一个任务都可以写同步方法和异步方法
        LinkTask linkTask = new LinkTask();
        linkTask.start(new LinkTask.Task() {
            @Override
            public void task(final LinkTask.Next next, final Object obj) {
                //同步任务
                Log.i("LinkTask", "任务一开始");
                List<Integer> list=new ArrayList<>();
                for (int i = 0; i <100 ; i++) {
                    list.add(i);
                }
                Log.i("LinkTask", "任务一做完了");
                next.next(list.size());
            }
        }).next(new LinkTask.Task() {
            @Override
            public void task(final LinkTask.Next next, final Object obj) {
                //异步任务
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("LinkTask", "任务二开始,参数:" + obj.toString());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i("LinkTask", "任务二做完了");
                        next.next("22222");
                    }
                }).start();
            }
        }).next(new LinkTask.Task() {
            @Override
            public void task(final LinkTask.Next next, final Object obj) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("LinkTask", "任务三开始,参数:" + obj.toString());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i("LinkTask", "任务三做完了");
                    }
                }).start();
            }
        });
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                test();
                break;
        }
    }
}
