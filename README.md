
## LinkTask 介绍

LinkTask 主要解决迷之缩进，将一连串的任务串在一起，做完一个任务再开始下一个任务，让一整套逻辑更为清晰。

**LinkTask库源码地址**：https://github.com/qinyang20/LinkTaskLib

## 前言 
目前只是极简的版本，功能单一，但相对的就容易使用。可以拿去自行做扩展。  


## LinkTask 功能
1. 解决迷之缩进问题
2. 将任务串联起来，看起来更清晰
3. 同步任务和异步任务都可以

## 为什么要使用 LinkTask ？

我们可以使用RxJava,RxJava功能很强大，是一个很牛逼的库，有LinkTask的功能而且更好，但是RxJava有一个缺点：就是入手太难。而LinkTask简单得多非常容易使用。

### 仓库依赖

Step 1. 在项目根build.gradle文件中增加JitPack仓库依赖。
```gradle
allprojects {
    repositories {
	//...
	maven { url 'https://jitpack.io' }
    }
}
```
Step 2. 在app的build.gradle里添加
```gradle
dependencies {
    compile 'com.github.qinyang20:LinkTaskLib:v1.0'
}
```

## 使用
#### 为什么说它简洁易用吗 ？ 看看下面代码

```
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
        });

```
直接把要做的任务写在 task里就行，可以写同步任务和异步任务。一个任务完成后可以把结果传给下一个任务。



## 注意事项
* 现在并没有取消任务的功能，如果任务开始又马上退出了当前页面，可能会导致当前页面无法销毁


## 更新日志
* v_1.0.0 简单的功能 。


## 下个版本预告
* 加入取消
* 优化内存使用


## 有问题或者有更好的建议
* [![QQ0Group][qq0groupsvg]][qq0group]


## 关于我
一个位于上海的 Android 开发者 ， 如果你有问题 ， 请进入QQ群 348736627 联系群主：轻风

[qq0groupsvg]: https://img.shields.io/badge/QQ群-348736627-fba7f9.svg
[qq0group]: https://jq.qq.com/?_wv=1027&k=5GvKCyg


### 最后如果该库对你有帮助不妨对右上角点点 Star 对我支持 ， 感谢万分 ! 


