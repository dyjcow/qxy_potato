# <p align=:center>qxy_potato</p>

<p align="center">
    <a href="https://lxtlovely.top/">
        <img src="https://img.shields.io/badge/author-DYJ-green" alt="author: DYJ (shields.io)" />
    </a>
    <a href="https://o8a5zpir4t.feishu.cn/docx/doxcnmUbNUaIaZMsPGoGhdzPHLd">
    	<img src="https://img.shields.io/badge/%E6%B1%87%E6%8A%A5%E6%96%87%E6%A1%A3-%E9%9D%92%E8%AE%AD%E8%90%A5-blue" alt="Static Badge" />
    </a>
    <a href="https://o8a5zpir4t.feishu.cn/docx/doxcnchwkawOQxN12cFZYvH3QKb">
    	<img src="https://img.shields.io/badge/%E5%8D%8F%E4%BD%9C%E6%96%87%E6%A1%A3-potato-blue" alt="Static Badge" />
    </a>
 </p>







<p align="center">
    <img src="https://pic.lxtlovely.top/blog/202401121750490.png" alt="ic_launcher_round" />
</p>


该项目为 2022 年[第四届字节跳动青训营](https://juejin.cn/post/7111205528293867533) Android 营结营项目，项目在最终评比中获得首屈一指奖(第一名)。答辩文档原文链接：[‌⁢‬﻿﻿﻿⁢⁣⁡‌⁢⁡‍⁣‌‍‍‬‍⁤⁡‬‌‍⁢⁣﻿⁤⁢﻿⁤‌⁤⁤⁢⁡⁢青训营结业项目答辩汇报文档 -- 破忒头 组 - 飞书云文档 (feishu.cn)](https://o8a5zpir4t.feishu.cn/docx/doxcnmUbNUaIaZMsPGoGhdzPHLd)

## **一、项目介绍**

**项目名称**：破忒头(potato)

**项目功能**：

项目实现了抖音的 **个人中心** 以及 **榜单页面** 。

 

**个人中心** 为APP的主页面，实现了以下内容。

1. 个人信息的展示
2. 端侧对数据信息的统计
3. 点击跳转到详情页查看粉丝和关注者列表
4. 个人作品的浏览，浏览时个人背景渐变折叠
5. 点击作品缩略图，页面打开**自动播放**
6. 播放页面实现**视频流**
7. 允许从播放页面打开抖音
8. 登录、登出
9. 登录状态过期自判断，自动跳转提醒重新登录

 

**榜单页面** 为APP的功能页面

1. 单页面切换查看三种类型的榜单
2. 点击榜单时间，浏览往期榜单序号以及可以选择序号查看往期内容
3. 点击榜单的子项，可以下弹页面，提供查看子项的详细信息

 

Github地址：

[dyjcow/qxy_potato (github.com)](https://github.com/dyjcow/qxy_potato)

## **二、项目分工**

|                           团队成员                           | 主要贡献                                                     |
| :----------------------------------------------------------: | ------------------------------------------------------------ |
| [![](https://avatars.githubusercontent.com/u/72329384?s=64&v=4)](https://github.com/dyjcow) | 1. 搭建整体极简抖音使用环境，搭建网络框架以及一个易用MVP架构 <br> 2. 重构榜单页面 <br/> 3. 编写登录页面以及做登录状态自识别 <br/> 4. 整合修改合并的代码 |
| [![](https://avatars.githubusercontent.com/u/81318618?s=64&v=4)](https://github.com/SoulMate-520) | 1. 开发viewPager展示三个榜单，使用MVP开发整合三个Fragment <br/> 2. 开发视频详情模块，解决视频自动播放问题 |
| [![81426359 (1)](https://pic.lxtlovely.top/blog/202401122223193.png)](https://github.com/1227010555) | 1. 开发榜单整体页面背景 <br/> 2. 开发粉丝列表以及关注者列表模块 |
| [![](https://avatars.githubusercontent.com/u/96895440?s=64&v=4)](https://github.com/2631140624) | 1. 开发榜单影视数据 RecyclerView 及 Item <br/> 2. 开发个人主页模块 |
| [![](https://avatars.githubusercontent.com/u/102741041?s=64&v=4)](https://github.com/QminCode) | 1. 开发工具类 <br/> 2. 编写网络状态识别类                    |

下文为开发期间的协助文档

[字节青训营项目协作文档](https://o8a5zpir4t.feishu.cn/docx/doxcnchwkawOQxN12cFZYvH3QKb) 

## **三、项目实现**

### **3.1** **技术选型与相关开发文档**

> 说明项目的技术选项，包含技术栈等信息，同时列举说明对应的技术选型在项目中的作用；开发文档主要阐述本次项目的协作模式、开发模式、如何把项目跑起来等维度信息  

项目相关技术如下

开发语言： Java 、kotlin

开发工具：Android Studio  、Fiddler、Apifox

技术栈：

* 设计模式：**订阅者模式**进行 网络请求 和 通知事件
* 网络框架：使用 **Rxjava+Retrofit** 封装网络请求于P层
* 事件总线：使用 **EventBus** 进行事件发送和订阅，且对其进行简单封装，使用索引加快其编译速度
* 后台服务：使用 **Service** 加持网页的视频播放，**WorkManager** 实现活跃状态的Token自刷新
* 页面编写：使用 **ViewBinding** 加快资源绑定速度，使用 **Glide** 加载网络图片
* UI辅助  ：使用 **RxTool** 进行崩溃捕捉以及使用其弹窗等， **BRVAH** 做 RecyclerView 的管理，**SmartRefresh** 做下拉加载和下拉刷新， **UltimateBarX** 做沉浸式管理，以及 **Android-PickerView** 做滚动选择框
* 数据持久化：使用 **MMKV** 做键值对持久化存储以及对数据进行加密处理
* 性能检测：使用 **Dokit** 对 APP 进行端侧的性能检测

协作开发模式：团队的开发方式时 **DevOps** 模式，以增量开发，以及不断使用提出新需求的形式来完成项目。团队使用的协作开发工具和平台是 **Git** 和 **GitHub**。以功能点为分支，指定组长审阅合并代码

代码提交规范：遵从阿里巴巴的Git规范

代码构建工具：**Gradle**

**代码运行**：到抖音开发平台获取对应的密钥，更改 app/src/main/res/values/Key.xml 该资源文件中的 value_client_key 以及 value_client_secret 即可运行

### 3.2 架构设计

> 整个项目采用的架构设计，可以包含流程图示例、代码示例等

项目的UML图如下，可以点击查看

[Potato UML图](https://pic.lxtlovely.top/blog/Potato-0.svg)

#### 3.2.1 软件架构选择：MVP模式。

![](https://pic.lxtlovely.top/blog/202401122158909.jpeg)

MVP模式将应用分为三层，并且各个对应的层的职责如下：

- **Model层**，主要负责数据的提供。Model层提供业务逻辑的数据结构（比如，实体类），提供数据的获取（比如，从本地数据库或者远程网络获取数据），提供数据的存储。
- **View层**，主要负责界面的显示。View层不涉及任何的业务逻辑处理，它持有Presenter层的引用，当需要进行业务逻辑处理时通知Presenter层。
- **Presenter层**，对于Presenter层他是连接View层与Model层的桥梁并对业务逻辑进行处理。在MVP架构中Model与View无法直接进行交互。所以在Presenter层它会从Model层获得所需要的数据，进行一些适当的处理后交由View层进行显示。这样通过Presenter将View与Model进行隔离，使得View和Model之间不存在耦合，同时也将业务逻辑从View中抽离。

综上可得出使用MVP架构有以下**优点**：

- 模型与视图完全分离，我们可以修改视图而不影响模型；
- 可以更高效地使用模型，因为所有的交互都发生在一个地方——Presenter内部；
- 我们可以将一个Presenter用于多个视图，而不需要改变Presenter的逻辑。这个特性非常的有用，因为视图的变化总是比模型的变化频繁；
- 如果我们把逻辑放在Presenter中，那么我们就可以脱离用户接口来测试这些逻辑（单元测试）。

存在问题及**解决方案**：

- 若业务剧增可能会造成接口类爆炸，Presenter层的代码难以管理。鉴于榜单业务较为简单，使用MVP更容易管理各层
- 可能会造成**内存泄漏**问题：当用户关闭了View层，但此时**Model层仍然进行耗时操作**，因为presenter持有view的引用而无法回收view，造成内存泄漏。其实可以重写view中的onDestroy方法，在view销毁时**强制回收presenter**或者是采用弱引用。

**我们的方案**

基于上述问题，我们封装了一个MVP架构，由于M层多用于网络请求，且我们可以将网络请求封装起来，所以将**M层嵌入到P层**中。这是符合**迪米特原则**的。

[带你封装MVP架构(上)|青训营笔记 - 掘金 (juejin.cn)](https://juejin.cn/post/7132504015908274213)

[带你封装MVP架构(下)|青训营笔记 - 掘金 (juejin.cn)](https://juejin.cn/post/7132874564014931999)

**屏幕适配方案**

在绘制页面的时候，我们以360dp的宽度为基准，然后对多种机型的适配，由其页面渲染的时候进行转换，使得同一个dp在不同设备中都会绘制为同一px。相关代码如下

```Java
/**
 * 设置统一的Density,使得对应的对应的dp转化为同一px。从而适配多种屏幕
 * 
 * @param activity 当前Activity
 */
public static void setCustomDensity( Activity activity ){
    final Application application = MyUtil.getApplication();

    final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

    if (sNonCompatDensity == 0){
        sNonCompatDensity = appDisplayMetrics.density;
        sNonCompatScaleDensity = appDisplayMetrics.scaledDensity;
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(@NonNull Configuration newConfig) {
                if (newConfig.fontScale > 0){
                    sNonCompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {

            }
        });
    }

    final float targetDensity =  ((float) appDisplayMetrics.widthPixels) / 360;
    final float targetScaleDensity = targetDensity * (sNonCompatScaleDensity/sNonCompatDensity);
    final int targetDensityDpi = (int) (160*targetDensity);

    appDisplayMetrics.density = targetDensity;
    appDisplayMetrics.scaledDensity = targetScaleDensity;
    appDisplayMetrics.densityDpi = targetDensityDpi;

    final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
    activityDisplayMetrics.density = targetDensity;
    activityDisplayMetrics.scaledDensity = targetScaleDensity;
    activityDisplayMetrics.densityDpi = targetDensityDpi;
}
```

### 3.2.2 网络架构选择：Retrofit

Retrofit是基于OKHttp的RESTFUL Api请求工具。App应用程序通过Retrofit请求网络，实质上是使用Retrofit接口层封装请求参数、Header、Url等信息，之后由OkHttp来完成后续的请求工作。在服务端返回数据后，OkHttp将原始数据交给Retrofit，Retrofit根据用户需求解析。

尽管Retrofit相对于OkHttp有更大包体积及更高的使用成本，但是它在具有OkHttp的所有优点的同时，还具有支持注解配备请求、可以搭配多种Converter的数据解析等优点。

 **我们封装的特点：**

我们对 Retrofit 的封装，是将其与Rxjava一起封装的。通过订阅者模式，让所有网络请求都加入到一个序列中，序列执行结束后使其接收到结果有序的处理，这会让网络请求十分的优雅，条理清楚。

在封装的时候，我们还配合门面模式，对应加入了拦截器、转换器、以及Cookie过期检测及自缓存策略。如此可以实现 多BaseUrl、添加全局字段、异常拦截自处理、自动更新过期Cookie 等等的功能。

### 3.3 项目代码介绍

#### 3.3.1 package目录介绍（架构说明）

<p align="center">
    <img src="https://pic.lxtlovely.top/blog/202401122200596.png" alt="img" />
</p>

- annotation：注解类，项目的注解存放于此
- app：继承 Application，完成各种初始化操作
- base：各种基类，包括Activity，Fragment，bean等，是封装MVP的关键。其使得后续只需要继承BaseActivity、BaseFragment、BaseView 即可方便的使用，让程序员可以专注于业务的开发。
- bean：数据类，存储包括网络请求解析使用到的数据类，以及EventBus发送事件使用到的类
- common：常量池，存储项目中的各种常量，程序开发中使用这些常量会避免人为失误出现的低级编码错误问题
- http：网络请求框架包，这里进行了网络数据拦截、分发、修改等，是网络框架的核心
- util：工具类模块，这个模块封装了多种工具类，包括 **图片**、**网络**、**颜色**、**时间**、**Log**(可统一设置开关)等等。一个健全的工具类是开发者的高效武器
- moudule：功能模块，我们在图片中可以看到。图中分了多个包，这些包就是不同的模块，开发对应模块的成员就可以在此建立自己的包来进行开发。如下图，这我们在自己模块的模块里面的分包情况。

<p align="center">
    <img src="https://pic.lxtlovely.top/blog/202401122200282.png" alt="img" />
</p>



### 3.3.2 功能难点和解决思路

[![](https://avatars.githubusercontent.com/u/72329384?s=64&v=4)](https://github.com/dyjcow)

**架构：易用解耦的MVP架构**

难点：架构封装的时候思考的无法全面

描述：封装架构的时候，我们会需要思考多种可能的情况，会思考如何提高兼容性，提高性能等。但是无论思考多少，在最终写项目的时候都还是会遇到未考虑到的问题

解决：在最初做架构设计的时候，必然是会无法完全预想未来的问题的。对一个团队而言，一个好的架构就是不断根据实际改进，变得更加适合团队。例如一个以Cookie作为令牌的开发架构迁移去以Token作为令牌的项目时候，以前未有这种业务，直到业务出现改变，发现架构不合适了，才会做出改变。所以说需求才是导致变更的最大因素，一个好的架构的诞生，必然是要有业务的陪练的。just do it

**功能：登录页面，榜单页面**

难点：构造美观页面，且性能不受影响；Token的刷新；多次登出登录崩溃

解决：

编写页面的时候，想要让页面美观，第一点要做的就是如何算是美观。自己的感受可以知道它是否美观，但是凭空设计一个美观的页面还是有些难的；做个人的APP，我是方法是去借鉴市面上多人使用的APP的设计，一般这些经过市场验证的软件其美观度是在线的。例如我的登录页是模仿 “派对岛”，重构的榜单页的Tab吸顶是模仿购物软件的，列表子项是模仿抖音自己的。

页面美观的同时要做到性能不会缺失。我的方案是少用Activity,多用Fragment；页面布局层级尽量少，可以经常使用相对布局；网络请求次数能少尽量少；事件相关用观察者模式；UI(主线程)用的时候多思考任务是否耗时，是否会影响体验。

为减少网络的请求，我把ClientToken的请求尽量做到少。我设置了一个WorkManager的后台任务，让APP在开启状态才每隔2h刷新一次，当APP退出的话就会取消该任务

</br>

[![](https://avatars.githubusercontent.com/u/102741041?s=64&v=4)](https://github.com/QminCode)

**功能：工具类**

工具类帮助开发者解决图片、颜色操作以及检测网络状态。

开发中的难点：

如何从url获取图片对象，如何对不同格式的图片文件进行常见的图片操作。

解决方法：

开发者先从url中获取inputstream，在工具类中实现inputstream到bitmap的转化。工具类提供不同格式图片间的转换方法，开发者可将其他格式的文件转为bitmap。工具类提供各种对bitmap进行常见图片操作的方法，这些方法主要通过bitmap类的自带方法以及canvas实现

</br>

[![](https://avatars.githubusercontent.com/u/96895440?s=64&v=4)](https://github.com/2631140624)

**功能：个人页面ui绘制，榜单页recyclerview绘制。**

难点：在折叠和非折叠状态下，判断是否显示小头像、title，以及打开侧边栏的按钮变色。

解决方法：对折叠栏进行坐标位置监听，判断toolbar与  appbarlayout的相对位置。

难点：reycylerview的分割线绘制。

解决方法：实现ItemDecoration类，重写 getItemOffsets方法，根据不同的recyclerview的LayoutManger判断，进行对item进行不同的分割。

</br>

[![](https://pic.lxtlovely.top/blog/202401122223193.png)](https://github.com/1227010555)

**功能：电影等榜单背景**

难点：抖音原生的榜单背景无法获取，通过截图等手段获取的图片模糊。

解决方法：使用可折叠标题栏作为背景布局，再通过Glide从url动态加载风景图。

**功能：关注粉丝页面**

难点：Tablayout和Viewpager的绑定，Tablayout的切换效果，RecyclerView的加载、下拉刷新和上拉加载。

解决方法：使用TabLayoutMediator绑定Tablayout和Viewpager，在TabLayoutMediator中设置文本内容。分别使用Tablayout和Viewpager的切换监听器，更改选中与未选中的文本效果。通过传入不同type，发起相应的网络请求及设置对应的adapter，从而达到复用fragment的效果。使用SmartRefreshLayout的滑动监听器，判断用户操作从而发起网络请求并更新adapter。

</br>

[![](https://avatars.githubusercontent.com/u/81318618?s=64&v=4)](https://github.com/SoulMate-520)

**功能：基于MVP架构实现碎片展示榜单页，串联背景和榜单item**

难点：presenter层的实现网络请求的相似代码如何复用

解决方法：创建碎片时标注碎片对应的榜单类型，网络请求时通过传入该标注，复用相同的网络  请求代码

**功能：使用WebView实现抖音个人视频播放详情页**

难点：webview的配置及其性能优化，打开网页自动播放

解决方法：

- 使用腾讯x5内核提高性能，同时做冷启动优化，提前初始化内核，并在第一次打开WebView时开启一个服务异步加载WebView；
- 开启webview的缓存功能，提升其在网络请求时的加载速度；
- WebView的生命周期绑定activity的生命周期，activity销毁时要销毁webview并解除对webview的引用，防止内存泄漏；
- 服务器响应的前端页面没有设置视频资源的自动播放，而且页面的加载和资源加载并不是同时完成，webview只有对页面加载完成的回调，并没有资源加载完成的回调。所以不能通过webview简单实现自动播放，要在页面加载完成的回调中，通过在java中调用JavaScript代码，获取视频对象并对视频设置自动播放属性，同时对视频设置资源加载完成的回调函数，在资源加载完成时播放

## 四、测试结果

> 建议从功能测试和性能测试两部分分析，其中功能测试补充测试用例（单元测试等），性能测试补充性能分析报告、可优化点等内容。

### 4.1 **功能测试**

由于时间不充分，未编写单元测试代码，网络请求部分的测试是通过抓包工具进行验证实现

网络抓包验证手机端的网络请求的内容及其格式，还有返回的报文是否与文档要求一致

软件进行了黑盒测试，对软件的各流程路线进行测试，并对测试过程中出现各种各种问题进行修复，经过多轮测试，软件已未出现bug

### 4.2 **性能测试**

项目使用了Dokit对项目进行性能测试。

#### 4.2.1 帧率

| ![img](https://pic.lxtlovely.top/blog/202401122200837.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122201052.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122201681.jpeg) | ![img](https://o8a5zpir4t.feishu.cn/space/api/box/stream/download/asynccode/?code=Y2ViMTExMzNkNjI3N2FmYmE3YjM4MzZhMjdhNWRjMGJfOEd6SEZ2cHZiSmtjOEIyYkRxYWg4c3ZVaEJHc291emVfVG9rZW46Ym94Y25KQUdIZ1ZGRGl5eU9zM3ljMTA0UVZwXzE3MDUwNjgwODY6MTcwNTA3MTY4Nl9WNA) |
| ----------------------------------------------------------- | ----------------------------------------------------------- | ----------------------------------------------------------- | ------------------------------------------------------------ |

在新创建Activity的时刻会少量丢帧，最低为打开WebView的时候变为55。后面帧率会平稳在60帧。出现丢帧的原因，一是由于Activity的创建需要开启建立多项任务，本身就会较为消耗资源；二是页面层级过多，双重缓冲的渲染时间会过长导致页面丢帧。

优化思路：架构换为单Activity、多Fragment的形式，减少不必要的Activity多次创建；页面布局使用 `CoordinatorLayout` ，减少页面层级

#### 4.2.2 CPU

| ![img](https://pic.lxtlovely.top/blog/202401122201967.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122201862.jpeg) |
| ----------------------------------------------------------- | ----------------------------------------------------------- |

普通页面的CPU消耗占比为 0%~14%，WebView页面最高则会接近20%。处于正常区间，当占用并不算低。

#### 4.2.3 内存占用

| ![img](https://pic.lxtlovely.top/blog/202401122202094.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122205849.jpeg) |
| ----------------------------------------------------------- | ----------------------------------------------------------- |

内存占用为150~200M，内存占用较大，已出现内存泄露。

内存较大一个由于APP在开启阶段就会对WebView内核进行初始化，这是为了增强WebView的使用体验，且会同时开启其后台服务。另一个是代码编写的时候，context的获取是持有Activity的，导致其无法GC等等的代码实现问题，导致了内存泄露。

#### 4.2.4 流量监控

| ![img](https://pic.lxtlovely.top/blog/202401122203979.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122204502.jpeg) |
| ----------------------------------------------------------- | ----------------------------------------------------------- |

对所有页面进行抓包，流量的消耗较少45min内不超50k；以及页面的响应速度较快，正常状态下，基本都低于1ms

#### 4.2.5 Crash

| ![img](https://pic.lxtlovely.top/blog/202401122206362.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122206307.jpeg) |
| ----------------------------------------------------------- | ----------------------------------------------------------- |

无Crash情况

#### 4.2.6 卡顿检测

<p align="center">
    <img src="https://pic.lxtlovely.top/blog/202401122207467.jpeg" alt="img" style="zoom:33%;" />
</p>

无超过500ms的卡顿情况

#### 4.2.7 大图检测

<p align="center">
    <img src="https://pic.lxtlovely.top/blog/202401122207822.jpeg" alt="img" style="zoom:33%;" />
</p>

页面存在文件大小超过150k，内存大小超过1MB的图片。

需要服务端发送图片前进行压缩，且端侧显示图片前做好图片压缩

#### 4.2.8 页面启动耗时

| ![img](https://pic.lxtlovely.top/blog/202401122208032.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122208814.jpeg) |
| ----------------------------------------------------------- | ----------------------------------------------------------- |

对Activity的跳转进行耗时检测，新开的Activity平均在100ms左右，WebView会达到150ms，页面耗时不算过高

#### 4.2.9 弱网测试

| ![img](https://pic.lxtlovely.top/blog/202401122208464.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122208584.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122208931.jpeg) |
| ----------------------------------------------------------- | ----------------------------------------------------------- | ----------------------------------------------------------- |

网络超时或是断网情况下，会有网络异常提示和请求时候会对相关异常做出判定。但会显示白屏

优化思路：对Get请求做缓存，无网时候自动显示上一次的缓存。添加空布局，当布局数据为空时候显示空布局页面

## 五、演示 demo （必须）

> 可同步粘贴录屏 + 核心功能截图

### 5.1 录屏

[录屏文件_来自飞书](https://o8a5zpir4t.feishu.cn/docx/doxcnmUbNUaIaZMsPGoGhdzPHLd#doxcngQMiCyK2wUSOsd1FVmGrkd)

### 5.2 截图

| ![img](https://pic.lxtlovely.top/blog/202401122209543.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122209792.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122210906.jpeg) |
| ----------------------------------------------------------- | ----------------------------------------------------------- | ----------------------------------------------------------- |
| ![img](https://pic.lxtlovely.top/blog/202401122210268.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122210440.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122210386.jpeg) |
| ![img](https://pic.lxtlovely.top/blog/202401122211530.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122212986.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122212881.jpeg) |
| ![img](https://pic.lxtlovely.top/blog/202401122213045.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122213729.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122213164.jpeg) |
| ![img](https://pic.lxtlovely.top/blog/202401122213732.jpeg) | ![img](https://pic.lxtlovely.top/blog/202401122214863.jpeg) |                                                             |

## 六、项目总结与反思

> 1. 目前仍存在的问题
>    - 代码冗余，MVP架构解耦带来的问题就是项目的类以及代码的数量都会升多
>    - Activity数量过多，由于架构未作单Activity多Fragment的设计，导致几乎每个页面都要使用到一个Activity，这会明显降低页面打开的速度和增大功耗
>    - 模块的还不够解耦，项目也未做好模块化的工作，没有把基类模块、功能模块拆解（当然，当前业务还没有这种大的需求）
> 2. 已识别出的优化项
>    - 事件总线使用了索引，加快EventBus的使用速率
>    - 资源绑定使用ViewBinding，加快资源寻找速度
>    - 键值对类型存储使用MMKV，其内存映射走的是Binder，加快存储速度
>    - WebView开启多进程，防崩溃且提高APP可使用的内存
>    - 多进程的预加载，减少页面的加载时间
>    - WebView使用X5内核，加快其启动速度以及提高视频播放的体验
> 3. 架构演进的可能性
>    - 架构可以逐渐将其模块化，抽离出譬如网络请求的基本模块，减少复用的成本
>    - 代码语言和架构类型改用kotlin和MVVM，可以有效提高效率
> 4. 项目过程中的反思与总结
>    - 开发协作会议和开发文档十分重要，可以有效提高开发效率
>    - 团队协作中，规定好git的提交格式，分支推送的规则，以及合并的请求十分重要。做好这些规定，能减少代码冲突以及沟通失误导致的时间浪费

[极简抖音中的优化点|青训营笔记 - 掘金 (juejin.cn)](https://juejin.cn/post/7134729289278160904)
