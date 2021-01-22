package com.lyf.service;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {


    static Log log = LogFactory.getLog(WebSocketServer.class);

   // static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);


    // 用来记录当前的在线连接数
    private static int onlineCount = 0;

    // concurrent包下的线程安全SET，用来存放客户端对应的MyWebSocket对象
    private static CopyOnWriteArraySet<WebSocketServer>webSocketServers
            = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;

    private String sid="";

    @OnOpen
    public void onOpen(Session session, @PathParam("sid")String sid)
    {
        this.session = session;
        webSocketServers.add(this);
        //onlineCount++;
        addOnlineCount();
        logger.info("有新的窗口开始监听"+sid+",当前在线人数为"+onlineCount);
        this.sid=sid;
        try{
            // 发送消息
          sendMessage("连接成功");
        }catch (IOException e){
            e.printStackTrace();
            logger.error("websocketIO异常");
        }
    }

    public void onClose(){
        webSocketServers.remove(this);
       // onlineCount--;
        subOnlineCount();
        logger.info("有一连接关闭,当前人数为"+onlineCount);
    }



    // 实现服务器主动推送
    public void sendMessage(String message)throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    // 群发自定义消息
    public static void sendInfo(String message,@PathParam("sid")String sid)throws IOException{

        logger.info("推送消息到窗口"+sid+",内容为"+message);
        for(WebSocketServer item:webSocketServers){
               item.sendMessage(message);
        }
    }


    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        WebSocketServer.onlineCount--;
    }





}
