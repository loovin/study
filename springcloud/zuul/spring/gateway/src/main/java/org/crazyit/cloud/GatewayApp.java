package org.crazyit.cloud;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(GatewayApp.class).web(true).run(args);
	}

	@PostConstruct
	public void zuulInit() {
		FilterLoader.getInstance().setCompiler(new GroovyCompiler());
		// 读取配置，获取脚本根目录
		String scriptRoot = System.getProperty("zuul.filter.root", "groovy/filters");
		// 获取刷新间隔
		String refreshInterval = System.getProperty("zuul.filter.refreshInterval", "5");
		if (scriptRoot.length() > 0) scriptRoot = scriptRoot + File.separator;
		try {
			FilterFileManager.setFilenameFilter(new GroovyFileFilter());
			FilterFileManager.init(Integer.parseInt(refreshInterval), scriptRoot + "pre",
					scriptRoot + "route", scriptRoot + "post");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
