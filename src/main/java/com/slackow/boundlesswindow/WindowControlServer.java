package com.slackow.boundlesswindow;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindowControlServer {


    private ServerSocketChannel serverChannel;
    private Selector selector;

    public WindowControlServer() {
    }

    public void init() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(0));
        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        int port = serverChannel.socket().getLocalPort();
        Files.write(FabricLoader.getInstance().getGameDir().resolve("boundless_port.txt"), (port + "").getBytes(StandardCharsets.UTF_8));
        System.out.println("[Boundless Window] Boundless Window Socket open on port: " + port);
    }

    public void tick(Window window) {
        try {
            // Non-blocking
            int readyChannels = selector.selectNow();
            if (readyChannels == 0) {
                return;
            }
            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isAcceptable()) {
                    // Accept the new connection
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    // Register for read events
                    clientChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    int read = clientChannel.read(buffer);
                    if (read <= 0) {
                        key.cancel();
                        clientChannel.close();
                    } else {
                        buffer.flip();
                        String command = new String(buffer.array(), 0, buffer.limit());
                        String response = processCommand(command, window) + "\n";
                        clientChannel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
                    }
                }
            }
            selector.selectedKeys().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static final Pattern setPattern = Pattern.compile("^set (-|-?\\d+) (-|-?\\d+) (-|\\d+) (-|\\d+)$");

    private String processCommand(String command, Window window) {
        command = command.trim();
        System.out.println("[Boundless Window] Processing: " + command);
        int oldX = window.getX();
        int oldY = window.getY();
        int oldWidth = window.getWidth();
        int oldHeight = window.getHeight();
        Matcher setMatcher = setPattern.matcher(command);
        if (setMatcher.matches()) {
            String xIn = setMatcher.group(1);
            String yIn = setMatcher.group(2);
            String widthIn = setMatcher.group(3);
            String heightIn = setMatcher.group(4);
            int x = xIn.equals("-") ? oldX : Integer.parseInt(xIn);
            int y = yIn.equals("-") ? oldY : Integer.parseInt(yIn);
            int width = widthIn.equals("-") ? oldWidth : Integer.parseUnsignedInt(widthIn);
            int height = heightIn.equals("-") ? oldHeight : Integer.parseUnsignedInt(heightIn);
            if (width != oldWidth || height != oldHeight) {
                GLFW.glfwSetWindowSize(window.getHandle(), width, height);
            }
            if (xIn.equals("-") && width != oldWidth) {
                x += (oldWidth - width)/2;
            }
            if (yIn.equals("-") && height != oldHeight) {
                y += (oldHeight - height)/2;
            }
            if (x != oldX || y != oldY) {
                GLFW.glfwSetWindowPos(window.getHandle(), x, y);
            }
        } else if (!command.equals("get")) {
            return "Unrecognized command: '" + command + "', ex: (get; set - - 1000 16384)";
        }
        return window.getX() + " " + window.getY() + " " + window.getWidth() + " " + window.getHeight();
    }
}
