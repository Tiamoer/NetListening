# NetListening

## 一、设计要求

设计一个窗口，窗口内含有一排小灯，程序的作用是监听本机的某一个或多个端口，当监听到端口被连接时，窗口内的红色小灯变为绿色小灯

## 二、设计思路

使用Java中的Swing和AWT来绘制图形化界面，状态灯的变化可以使用两张不同颜色的图片（一张红色，一张绿色）的替换来实现；程序可以使用多线程来实现同时监听多个端口，端口用户可自定义；使用状态输出台和状态栏来为用户提供程序运行状态显示。

## 三、基本类和变量表

**类（class）：**

| 类名            | 注释                                                         |
| --------------- | ------------------------------------------------------------ |
| myJFrame        | 基本窗口类，内部实现对主窗口的绘制和基本功能实现             |
| portFrame       | 端口设置界面的类，内部实现自定义端口的功能，并将定义的端口返回myJFrame |
| ServerListening | 服务监听类，继承Thread类，实现多线程监听端口                 |

**接口（Interface）：**

​	*menu接口（定义菜单栏的各种菜单）：*

| 属性名      | 类型              | 注释                      |
| ----------- | ----------------- | ------------------------- |
| jMenuBar    | JMenuBar          | 菜单栏组件                |
| option      | JMenu             | 选项菜单                  |
| start       | JMenuItem         | 选项菜单子项-开始监听     |
| stop        | JMenuItem         | 选项菜单子项-停止监听     |
| exit        | JMenuItem         | 选项菜单子项-退出系统     |
| set         | JMenu             | 监听配置菜单              |
| port        | JMenuItem         | 监听配置菜单子项-端口设置 |
| statusBar_V | JCheckBoxMenuItem | 监听配置菜单子项-状态栏   |
| about       | JMenu             | 关于菜单                  |
| aboutMe     | JMenuItem         | 关于菜单子项-关于我       |

​	component接口（定义窗口组件）：

| 属性名            | 类型        | 注释             |
| ----------------- | ----------- | ---------------- |
| statusText        | JTextArea   | 状态输出台       |
| statusBar         | JLabel      | 状态栏           |
| statusText_Scroll | JScrollPane | 状态输出台容器   |
| red               | ImageIcon   | 红色灯的图片对象 |
| green             | ImageIcon   | 绿色灯的图片对象 |
| portLamp(n)       | JLabel      | 接口n的灯        |
| infoLamp(n)       | JLabel      | 接口n的文字说明  |

**myJFrame类属性表：**

| 变量，常量或方法名      | 类型            | 注释                                                        |
| ----------------------- | --------------- | ----------------------------------------------------------- |
| serverListening1        | ServerListening | ServerListening的实例化对象1                                |
| serverListening2        | ServerListening | ServerListening的实例化对象2                                |
| serverListening3        | ServerListening | ServerListening的实例化对象3                                |
| jFrame                  | JFrame          | 窗口对象                                                    |
| isPortOK(int localPort) | return boolean  | 判断端口是否可以正常开启的方法，可以返回true，失败返回false |

**portFrame类属性表：**

| 变量或方法名  | 类型                | 注释                                                         |
| ------------- | ------------------- | ------------------------------------------------------------ |
| portNum(n)    | int                 | 用来接收输入的端口号                                         |
| pFrame        | JFrame              | 端口设置窗口的窗口对象                                       |
| port1-3       | JLabel              | 文字标签                                                     |
| maskFormatter | MaskFormatter       | 端口输入界面的格式控制对象，只可以输入4位数字                |
| portn_text    | JFormattedTextField | 端口n的输入框                                                |
| submit        | JButton             | 确认按钮，使用监听器方法将输入的端口号传送至portNum(n)中，待主窗口调用 |
| con           | Container           | Container容器                                                |

**ServerListening类属性表：**

| 变量或方法名 | 类型         | 注释                         |
| ------------ | ------------ | ---------------------------- |
| startFlag    | boolean      | 控制线程是否继续运行的标识符 |
| serverSocket | ServerSocket | 服务器套接字对象             |
| localPort    | int          | 监听的端口号，由set方法赋值  |
| isRun        | boolean      | 判断线程的状态，是否存活     |