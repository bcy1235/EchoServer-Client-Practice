package server.multiNioVersion;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class EchoService implements Runnable{
    private final int BUF_SIZE = 1026;
    private ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
    @Override
    public void run() {
        while (true) {
            Optional<SelectionKey> keyOptional = SocketStation.get();
            if (keyOptional.isEmpty())
                continue;

            SelectionKey selectionKey = keyOptional.get();
            SocketChannel connectedChannel = (SocketChannel) selectionKey.channel();
            try {
                int readByte = connectedChannel.read(buf);
                if (readByte == -1) {
                    connectedChannel.close();
                    selectionKey.cancel();
                    continue;
                }
                buf.flip();

                int writeByte = 0;
                while (writeByte < readByte) {
                    writeByte += connectedChannel.write(buf);
                }
                buf.clear();
            } catch (IOException e) {
                System.out.println("EchoService's run Method Error! : " + e);
                selectionKey.cancel();
                try {
                    connectedChannel.close();
                } catch (IOException ex) {
                    System.out.println("EchoService's run Method_channel close Error! : " + e);
                }
            }
        }
    }
}


