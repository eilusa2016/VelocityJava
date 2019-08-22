package volictiylearn;

import java.io.IOException;

import volictiylearn.app.VelocityUtil;

public class AppVol {
	public static void main(String[] args) {
		VelocityUtil util=new VelocityUtil();
//		util.initVecocity();
		try {
			util.initByProp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
