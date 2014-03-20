package server.controller;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import server.dao.TestMapper;
import server.netty4.ChatServer;

public class MyLoadListenner extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ApplicationContext context = getCurrentWebApplicationContext();
        TestMapper mapper =context.getBean(TestMapper.class);
        new Thread(new ChatServer(9527,mapper)).start();
    }
}
