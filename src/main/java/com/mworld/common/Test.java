package com.mworld.common;

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

    public static void main(String[] args) throws IOException {
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
        String cmd = "D:/OpenOffice4/program/soffice.exe soffice \"-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager\" nologo -headless -nofirststartwizard";
        Process process = Runtime.getRuntime().exec("cmd /c start " + cmd);
//        InputStream is = process.getInputStream();
//        Scanner scanner = new Scanner(is);
//        while (scanner.hasNextLine())
//            System.out.println(scanner.nextLine());
//        scanner.close();
//
//        SwfMaker swfMaker = new SwfMaker();
//        swfMaker.doWordToSwf();
    }
}
