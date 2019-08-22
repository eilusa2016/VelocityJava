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
