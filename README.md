# java模板引擎：velocity
------
在java中，同类型的模块代码结构往往是相似的。以Spring为例，一个工程都会包含controller，service，dao，modal等类型的代码。而dao用于数据库的访问，不同的表所对应的dao类都会有get，set，select，delete等操作，若手动编写dao，则这些dao有很多代码是类似的。于是希望自动生成这部分代码。
一个非常直观的思路就是：写一个String模板，然后使用字符串替换的方式，将该模板中的某些预留字符串替换为具体值。这样就得到了目标文件的内容。然后将这个替换后的String写入到目标文件中即可。
velocity的功能就是读入一个模板，然后执行替换操作，从而生成目标String。

##  引入velocity

		<dependency>
			<groupId>org.apache.velocity</groupId>
			<artifactId>velocity</artifactId>
			<version>1.7</version>
		</dependency>

------

## 编写一个模板文件
模板文件使用vm文件。在main/resources/下创建一个vm文件夹，然后在下面添加一个controller.java.vm文件：
```python
package ${package}.controller;
public class ${className}Controller {
    public void get${Object}() {
    }
    public void set${Object}() {
    }
}
```
其中使用${}包裹的内容为变量，在后面会被替换。未被包裹的内容维持不变，包括空格，Tab和换行。
### 1.  使用
首先需要定义并初始化VelocityEngine：
```python
VelocityEngine ve = new VelocityEngine();
// 设置资源路径
ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
// 初始化
ve.init();
```
**然后将模板载入到一个Template对象中：**
```python
// 载入模板
Template t = ve.getTemplate("vm/controller.java.vm");
```
**使用VelocityContext定义替换的规则：**
```python
VelocityContext ctx = new VelocityContext();
ctx.put("package", "com.spring.template");
ctx.put("className", "test");
ctx.put("Object", "Value");
```
**VelocityContext也可以直接接收一个Map对象：**
```python
Map contextMap = new HashMap();
contextMap.put("package", "com.spring.template");
contextMap.put("className", "test");
contextMap.put("Object", "Value");
VelocityContext ctx = new VelocityContext(contextMap);
```
**定义一个StringWriter来存储合并后的结果：**
```python
StringWriter sw = new StringWriter();
t.merge(ctx, sw);
String r = sw.toString();
```
**运行该代码，最终生成结果为：**
```python
package com.spring.template.controller;
@Mapper
public interface testController {
    public void getValue() {
    }
    public void setValue() {
    }
}
```

### 2. 关于数据库自动生成
类似MyBatis那样，从数据库直接映射出对应的Dao，xml与model类。
关键点在于需要使用select语句查询出指定表的结构。有了表结构，就可以动态生成增删改查的sql语句，同样也可以使用vm来动态生成model类。
vm仅仅作为模板，替换模板中的变量后，生成的内容可以写到各种类型的文件中，例如.java，.xml，.properties等
