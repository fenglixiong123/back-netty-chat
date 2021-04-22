
# 项目中使用的相关组件的安装和介绍

## 一、注册中心

选择consul作为服务注册中心

下载地址：https://www.consul.io/downloads

### 1.windows安装

* 下载64位安装包

* 放在d:/consul

* 将d:/consul设置进path路径下

* 启动Consul
      
      1.开发者模式启动  
      
      consul agent -dev  
      
      开发模式不会持久化数据，重启之后保存的配置信息就会丢失.
      
      2.生产者模式启动
      
      consul.exe agent -server -bootstrap-expect 1 -data-dir d:/consul/data
      
      在D盘创建consul/data文件夹，用于持久化Consul。
      
      3.启动参数附录
      
        a. agent：consul的核心命令，主要作用有维护成员信息、运行状态检测、声明服务以及处理请求等
      
      　b. -server：就是代表server模式
      
      　c. -bootstrap-expect：代表想要创建的集群数目，官方建议3或者5
      
      　d. -data-dir：数据存储目录
      
      　e. -client：是一个客户端服务注册的地址，可以和当前server的一致也可以是其他主机地址，提供HTTP、DNS、RPC等服务，系统默认是127.0.0.1,所以不对外提供服务，如果你要对外提供服务改成0.0.0.0
      
      　f. -bind：集群通讯地址,集群内的所有节点到地址都必须是可达的，默认是0.0.0.0
      
      　g. -node：代表当前node的名称,节点在集群中的名称，在一个集群中必须是唯一的，默认是该节点的主机名
      
      　h. -ui：代表开启web 控制台
      
      　i. -config-dir：配置文件目录，里面所有以.json结尾的文件都会被加载

### 2.linux安装

* 

### 二、