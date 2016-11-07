##  概述  ##

接口只有一个

http://服务器地址:服务器服务端口/servlet/WocServlet

通过指定不同的method，去执行对应的方法操作。

所有请求方式一律为POST方式

返回的具体格式(正确):

```json
{
  "statusCode":200,//响应码
  "success":true,//是否请求成功
  "message":"xxx成功!"//成功提示
  "result":[] //如果是查询类的操作，成功将会含有这个字段，失败将没有这个字段 |PS:这个字段恒定是个List
}
```

返回的具体格式(错误):

```json
{
  "statusCode":201, //响应码
  "success":false, //是否请求成功
  "message":"参数不正确!" //错误原因
}
```



##  具体接口说明：  ##

###  APP端接口  ###

####  获取所有的文章列表  ####

http://服务器地址:服务器服务端口/servlet/WocServlet

请求方式：POST

| 需求参数   | 参数类型   | 参数说明      | 是否必须 |
| ------ | ------ | --------- | ---- |
| method | String | 恒定为:index | 否    |

返回样例(成功):

```js
{
  "statusCode":200,
  "success":true,
  "message":"查询成功",
    "result":[
      {
        "categoryId":1,
        "categoryName":"000:副本0",
        "articles":[
          {
            "aId":2,
            "url":"651be769-5cf4-4357-8598-56108b50444b",
            "title":"标题",
            "desc":"描述",
            "img":"img"
          }
        ]
      },
      {
        "categoryId":2,
        "categoryName":"111:副本1",
        "articles":[]
      }
}
```

不存在失败情况，result字段恒定存在。

###  Web操作端接口  ###

#### 添加分类 ####

接口地址:

http://服务器地址:服务器服务端口/servlet/WocServlet

请求方式：POST

| 需求参数   | 参数类型   | 参数说明            | 是否必须 |
| ------ | ------ | --------------- | ---- |
| method | String | 恒定为:addCategory | 是    |
| pwd    | String | 管理员密码           | 是    |
| cName  | String | 分类名称            | 是    |