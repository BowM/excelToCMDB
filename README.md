# excelToCMDB
通过调整配置文件，使得程序自动读取符合模板的Excel文件，并将其中的信息在UCMDB中进行添加或更新。
## poi
使用到apache的poi进行Excel文件的读取与简单验证
## JDBC
使用JDBC连接cmdb的oracle数据库进行查询的只读操作，方便之后匹配操作
## CMDB的API
主要使用CMDB的Web Service API做具体操作
## 读取配置文件
目前为读取属性文件，希望之后更改为读取的Json格式的配置信息
9/2 已取代

配置文件样例
9/2 已取代

>properties
+ filePath=D:/ApplicationSystem.xlsx //Excel文件路径
+ type=Table_DB //cmdb属性名
+ Excel中字段名=cmdb中字段名 //匹配字段名 

调整完对用的字段名后，实现自动根据路径读取Excel中的数据，并根据CIID判断他是否存在，如存在则更新，不存在则添加。
## 2019/6/28 第一次上传 数据库信息自己加

## 2019/9/2  简化配置 做出简单的窗口 
启动test，点击openfile选择文件，输入要操作的类，点击Update
