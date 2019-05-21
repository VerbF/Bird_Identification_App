
# Bird_Identification_App

基于 Mask-Rcnn 的鸟类识别app

## 服务端

### 配置

需要修改 Bird_Identification_Server  文件夹下的 server.py 中 ip_address 和 port 参数，其它保持不变。  

```python
#基础设置
ip_address = '192.168.1.105'
port = '8888'
```

另一个需要修改的是 Bird_Identification_Server 文件夹下的 detect.py 文件，需要对数据库的参数进行配置。

```python
#数据库设置
DB_HOST = 'localhost'
DB_PORT = 3306
DB_USER_NAME = 'root'
DB_PASSWORD = '704865074'
DB_NAME = 'bird_identification_app'
DB_CHARSET = 'utf8'
```

### 启动

运行脚本 server.py ,博主使用了 Anaconda ，启动时只要在终端里输入  

```python
python server.py
```

## 用户端配置

### 配置

需要修改 Bird_Identification_App\app\src\main\java\com\example\xxs\bird_identification_app\ 路径下的 MainActivity.java 文件。  
需要把这里的 url 改成和服务端的 ip_address , port 相对应。

```python
final String url = "http://192.168.1.105:8888/uploadImg";
```

## 数据库

数据库使用 mysql ，在 Bird_Identification_Server\sql\ 路径下有 bird_identification_app.sql 脚本，需要先创建一个名为 bird_identification_app 的数据库（编码格式为 utf8），在该数据库下执行此脚本即可。

## 注意

- 使用路由器分配的 ip 地址时，要保证 安装 app 的手机和服务器接入同一局域网，否则无法使用。
- 运行 server.py 和 detect.py 脚本时，可能会出现模块找不到的问题，需要安装相应的 python 库。