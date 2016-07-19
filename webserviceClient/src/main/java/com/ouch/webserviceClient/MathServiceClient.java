package com.ouch.webserviceClient;

import java.net.MalformedURLException;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;


public class MathServiceClient {
	
	private static MathService mathService;
	
	private MathServiceClient(){}
	
	public static MathService getMathService(String serviceUrl) throws MalformedURLException{
		if(mathService == null){
			//创建服务
			Service srvcModel = new ObjectServiceFactory().create(MathService.class);
			//创建XFire对象
			XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
			//调用Web服务
			mathService = (MathService) factory.create(srvcModel, serviceUrl);
			
			//设置客户端调用的属性
			Client client = Client.getInstance(mathService);
			client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "300");
			client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
			client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
			//如果需要设置代理
			//client.setProperty(CommonsHttpMessageSender.HTTP_PROXY_HOST, "10.3.1.6" ); 
			//client.setProperty(CommonsHttpMessageSender.HTTP_PROXY_PORT, "8080");
		}
		
		return mathService;
	}
	
	public static void main(String[] args) throws MalformedURLException{
		MathService service = MathServiceClient.getMathService("http://localhost:8080/ouch/services/MathService");
		int result = service.add(1, 14);
		System.out.println("get the result: " + result);
	}
}
