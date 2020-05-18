

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppleServer extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("----------AppleServer---------");

        String message = (String)request.getParameter("message");
        String number =  (String)request.getParameter("count");


        if(message.length()<=0 | number.length()<=0){
            request.getRequestDispatcher("/error.jsp").forward(request,response);
            return;
        }

        Integer count = Integer.valueOf(number);

        List<String> tokens=new ArrayList<String>();
        tokens.add("4e44aa9bb2abcf05c5d440b4600c5e4f2f24bb4b5ef01170d532abad2a1a962f");
        String path="/tomcat/apache-tomcat-9.0.10/webapps/pushDoneServer.p12";
        String password="123456";
        boolean sendCount=false;
        sendpush(tokens, path, password, message, count, sendCount);
        request.getRequestDispatcher("/index.jsp").forward(request,response);

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
         doPost(request,response);
    }




    /************************************************
     测试推送服务器地址：gateway.sandbox.push.apple.com /2195
     产品推送服务器地址：gateway.push.apple.com / 2195

     需要javaPNS_2.2.jar包
     ***************************************************/
    /**

     *这是一个比较简单的推送方法，
     * apple的推送方法

     * @param tokens   iphone手机获取的token
     * @param path 这里是一个.p12格式的文件路径，需要去apple官网申请一个
     * @param password  p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
     * @param message 推送消息的内容
     * @param count 应用图标上小红圈上的数值
     * @param sendCount 单发还是群发  true：单发 false：群发
     */

    public void sendpush(List<String> tokens, String path, String password, String message, Integer count, boolean sendCount) {

        try {
            //message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}

            PushNotificationPayload payLoad =  new PushNotificationPayload();
            payLoad.addAlert(message); // 消息内容
            payLoad.addBadge(count); // iphone应用图标上小红圈上的数值
            payLoad.addSound("default"); // 铃音 默认



            PushNotificationManager pushManager = new PushNotificationManager();

            //true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, password, false));
            List<PushedNotification> notifications = new ArrayList<PushedNotification>();

            // 发送push消息
            if (sendCount) {
                System.out.println("--------------------------apple 推送 单-------");
                Device device = new BasicDevice();
                device.setToken(tokens.get(0));
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                notifications.add(notification);
            } else {
                System.out.println("--------------------------apple 推送 群-------");
                List<Device> device = new ArrayList<Device>();
                for (String token : tokens) {
                    device.add(new BasicDevice(token));
                }
                notifications = pushManager.sendNotifications(payLoad, device);

            }

            /**
             *推送结果反回
             */
            List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
            List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
            int failed = failedNotifications.size();
            int successful = successfulNotifications.size();


            if (successful > 0 && failed == 0) {
                System.out.println("-----All notifications pushed 成功 (" + successfulNotifications.size() + "):");
            } else if (successful == 0 && failed > 0) {
                System.out.println("-----All notifications 失败 (" + failedNotifications.size() + "):");
            } else if (successful == 0 && failed == 0) {
                System.out.println("No notifications could be sent, probably because of a critical error");
            } else {
                System.out.println("------Others 成功 (" + successfulNotifications.size() + "):");

            }

            // pushManager.stopConnection();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }





}
