package com.mworld.common;

import com.mworld.common.exception.MistakeDocTypeException;
import org.springframework.util.FileCopyUtils;

import java.io.*;

public class Test {
    //    Logger logger = LoggerFactory.getLogger(this.getClass());
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void doTest() {
        PageUtil pageUtil = new PageUtil(2, -5);
        pageUtil.setPage(-3);
        System.out.println(pageUtil.getStart());
    }

    public int say(int i) {
        return Integer.valueOf(i);
    }

    public static void main(String[] args) throws Exception {
//        Test test = new Test();
//        test.doTest();;
//        int i=0;
//        Integer a = null;
//        Test test = new Test();
//        test.setAge(Integer.valueOf(""));
//        System.out.println("Begin-----");
//        File file = new File("D:/test.swf");
//        System.out.println(ByteOrder.nativeOrder());
//
//       /* try {
//            FileInputStream fis = new FileInputStream(file);
//            FileDescriptor.in.sync();
//            FileDescriptor.out.sync();
//            System.out.println("FILE DESCRIPTION :" + fis.getFD());
//            fis.close();
//        } catch (Exception e) {
//
//        }*/
//
//        ByteBuffer bf = ByteBuffer.allocate(1024);
//        Selector selector = Selector.open();
//        ServerSocketChannel sc = ServerSocketChannel.open();
//        sc.configureBlocking(false);
//        sc.bind(new InetSocketAddress(8080));
//        sc.register(selector, SelectionKey.OP_ACCEPT);
//
//        while (true){
//            Set selectorKeys = selector.keys();
//            Iterator it = selectorKeys.iterator();
//            while (it.hasNext()){
//                SelectionKey key = (SelectionKey) it.next();
//                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
//                    ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
//                    SocketChannel scl = socketChannel.accept();
//                    scl.configureBlocking(false);
//                    scl.register(selector, SelectionKey.OP_READ);
//                    it.remove();
//                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
//                    SocketChannel skc = (SocketChannel) key.channel();
//                    while (true) {
//                        bf.clear();
//                        int n = skc.read(bf);
//                        if (n <= 0){
//                            break;
//                        }
//                        bf.flip();
//                    }
//                    it.remove();
//                }
//            }
//
//        }

//        Process process = Runtime.getRuntime().exec("cmd /c dir d:");
        String cmd = "\"D:/OpenOffice 4/program/soffice.exe\" soffice \"-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager\" -nologo -headless -nofirststartwizard";
        Process process = Runtime.getRuntime().exec("cmd /c start " + cmd);
//        System.out.println(process.exitValue());
        /*Process process = Runtime.getRuntime().exec("sc query ggg");

        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
        String temp = "", result = "";
        while ((temp = bufferedReader.readLine())!=null){
            result += temp;
        }
        bufferedReader.close();
        inputStream.close();
        System.out.println(result.equals(result.replaceAll("EnumQueryServicesStatus:OpenService 失败", "")));*/

//        InputStream is = process.getInputStream();
//        Scanner scanner = new Scanner(is);
//        while (scanner.hasNextLine())
//            System.out.println(scanner.nextLine());
//        scanner.close();
//
//        SwfMaker swfMaker = new SwfMaker();
//        swfMaker.doWordToSwf();

//        File file = new File("D:/test.swf");
//        FileCopyUtils.copy(file,new File("D:/test1.swf"));
//        FileCopyUtils.copy(file, new File(file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-1)));
//        System.out.println(file.getAbsolutePath() + "____" + file.getName());
//        System.out.println(file.getName());
//        System.out.println(file.exists());
//        System.out.println(file.length()/1024);
//        System.out.println(1024 << 2);
//        System.out.println(1024 << 64);
//        System.out.println(1024 << 8);
//        System.out.println(1024 << 10);
//        System.out.println(1024 << 12);
//        System.out.println(6 & 3);
//        System.out.println(1024 | 2);
//        System.out.println(~1024);
//        throw new MistakeDocTypeException("Error Type", new Throwable("Only DOC can be use"));
    }
}
