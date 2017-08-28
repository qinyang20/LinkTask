# LinkTask
一个简单易用的任务链，简化版的RxJava
主要实现了任务链功能，做完一个任务，再开始另外一个任务。如：

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
