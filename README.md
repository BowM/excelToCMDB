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

配置文件样例

filePath=D:/ApplicationSystem.xlsx //Excel文件路径
type=Table_DB //cmdb属性名
Excel中字段名=cmdb中字段名 //匹配字段名 

调整完对用的字段名后，实现自动根据路径读取Excel中的数据，并根据CIID判断他是否存在，如存在则更新，不存在则添加。
