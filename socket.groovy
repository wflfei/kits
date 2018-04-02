import java.net.ServerSocket  
  
class TestSocketServer {  
    static server  
  
    static {  
        server = new ServerSocket(6666)  
    }  
    static main(args) {  
        println "SOCKET测试服务已启动，运行于端口6666，请勿关闭此窗口。。。。"  
          
        while(true) {  
            server.accept { socket ->  
                println "\n========================================\n${new Date().format('yyyy-MM-dd HH:mm:ss')} 正在处理请求..."  
                socket.withStreams { input, output ->  
  
                    byte[] msgBody = new byte[200000];  
                    input.read(msgBody, 0, 200000);  
                    def msg = new String(msgBody, 'GBK').trim()  
                    println "${new Date().format('yyyy-MM-dd HH:mm:ss')} 接收到的消息: \n========================================\n$msg"  
  
//                    output << "服务器处理时间：" + new Date().format('yyyy-MM-dd HH:mm:ss') + "\n" + msg
                    output.write(new File('test.dex').readBytes())
                    socket.shutdownOutput()  
                }  
                println "${new Date().format('yyyy-MM-dd HH:mm:ss')} 处理完毕！\n========================================"  
            }  
        }  
        }  
}  