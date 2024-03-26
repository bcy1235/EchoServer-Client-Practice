package server.multiNioVersion;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class SocketStation {
    private static Selector selector;
    static {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            System.out.println("SocketStation's Static Block Error! : " + e);
        }
    }
    public static void register(SocketChannel socketChannel) {
        try {
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            System.out.println("Can't register channel which is already closed : " + e);
        }
    }
    public static Optional<SelectionKey> get() {
        try {
            while (selector.selectNow() == 0) {}
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            SelectionKey nowKey = iterator.next();
            iterator.remove();
            return Optional.of(nowKey);
        } catch (IOException e) {
            System.out.println("SocketStation's get Method Error! : " + e);
            return Optional.empty();
        }
    }
}
