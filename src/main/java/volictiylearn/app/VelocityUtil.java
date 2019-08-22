package volictiylearn.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtil {
	public void initVecocity() {
		 VelocityEngine ve=new VelocityEngine();
		 //设置模板加载路径，这里设置的是class下  
		 ve.setProperty(Velocity.RESOURCE_LOADER, "class");
		 ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	        try {   
	            //进行初始化操作   
	            ve.init();   
	            //加载模板，设定模板编码
	            Template t=ve.getTemplate("volictiylearn/template/hello.vm","utf-8");
	            //设置初始化数据   
	            VelocityContext context = new VelocityContext();   
	            //设置输出   
	            PrintWriter writer = new PrintWriter("D:\\helloword.html");   
	            //将环境数据转化输出   
	            t.merge(context, writer);   
	            writer.close();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }  
	}
	
	
public void initByProp() throws IOException {
		
	 VelocityEngine ve=new VelocityEngine();
	 //设置模板加载路径，这里设置的是class下  
	 ve.setProperty(Velocity.RESOURCE_LOADER, "class");
	 ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	
	
		Properties prop = new Properties();
//		// 配置模版目录
//		prop.put("file.resource.loader.path","classpath:static/template");
		prop.put("input.encoding", "UTF-8");// 输入编码
		prop.put("output.encoding", "UTF-8");//输出编码
		
		 //进行初始化操作   
	     ve.init(prop);   
		
		VelocityContext context = new VelocityContext();
		FileOutputStream outStream = null;
		OutputStreamWriter writer = null;
	
		File file = new File("D:/catalog");
	    if (!file.exists()) {
	      file.mkdirs();
	    }
	   
	    Map<String,Object> map=new HashMap<String,Object>();
	    map.put("data", "\t这是一段测试数据");
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		
		while(it.hasNext()){
			String key = it.next();
			context.put(key,map.get(key));
		}
		
		
		Template template =ve.getTemplate("volictiylearn/template/hello.vm","utf-8");
		File destFile = new File("D:/catalog","studyCard.html");
		outStream = new FileOutputStream(destFile);
		writer = new OutputStreamWriter(outStream,"utf-8");
		BufferedWriter sw = new BufferedWriter(writer);
		template.merge(context, sw);
		sw.flush();
		sw.close();
		writer.close();
		outStream.close();
	}

}
