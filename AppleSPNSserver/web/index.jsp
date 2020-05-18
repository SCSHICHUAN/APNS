<%--
  Created by IntelliJ IDEA.
  User: SHICHUAN
  Date: 2019/3/1
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>APNS</title>
    <style>
      body{
        font-family: "微软雅黑";
      }

      .title{
        margin-top: 50px;
        font-size: 100px;
        width: 100%;
        line-height: 100px;
        text-align: center;
      }
      .a,.b{
        width: 90%;
        display: block;
        height: 90px;
        font-size: 50px;
        margin: 0 auto;
        border: none;
        border-bottom: 1px solid black;
        font-family: "微软雅黑";
        font-weight: 200;
        text-indent:20px;

      }
      .a{
        margin-top: 100px;
      }
      .b{
        margin-top: 40px;
      }
      button{
        margin: 0;
        display: block;
        width: 300px;
        height: 300px;
        font-size: 80px;
        border-radius: 150px;
        margin: 0 auto;
        margin-top: 50px;
        border: none;
        background-color: white;
        box-shadow: 0 0 50px rgba(0,0,0,0.2);
        line-height: 80px;
        text-align: center;
        padding: 0;
      }
      .stan{
        font-size: 40px;
        width: 100%;
        height: 40px;
        line-height: 40px;
        margin: 0 auto;
        margin-top: 10px;
        text-align: center;
      }

    </style>
  </head>
  <body>
  <div class="title">APNS</div>
  <div class="stan">stanserver.cn</div>
  <form action="/APNS/apnsServer">
    <input class="a" type="text" name="message" placeholder="message">
    <input class="b" type="number" pattern="\d*" name="count" placeholder="red dots">
    <button>PUSH</button>
  </form>

  </body>
</html>
