package com.pxene.pap.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

public class ScpUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScpUtils.class);
    
    private static ScpUtils instance;
    private static Connection connection;
    
    private String ip;
    private int port;
    private String username;
    private String password;
    
    private String mode = "0755";
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    
    public ScpUtils(String IP, int port, String username, String passward) 
    {
        this.ip = IP;
        this.port = port;
        this.username = username;
        this.password = passward;
    }
    
    
    public static synchronized ScpUtils getInstance(String IP, int port, String username, String passward)
    {
        if (instance == null)
        {
            doSync(IP, port, username, passward);
        }
        return instance;
    }
    
    private synchronized static void doSync(String IP, int port, String username, String passward)
    {
        if (instance == null)
        {
            instance = new ScpUtils(IP, port, username, passward);
        }
    }
    
    
    public void getFile(String remoteFile, String localTargetDirectory) throws IOException
    {
        SCPClient client = new SCPClient(connection);
        client.get(remoteFile, localTargetDirectory);
    }
    
    public void putFile(String localFile, String remoteTargetDirectory) throws IOException
    {
        SCPClient client = new SCPClient(connection);
        client.put(localFile, remoteTargetDirectory);
    }
    
    public void putFile(byte[] data, String remoteFileName, String remoteTargetDirectory) throws IOException
    {
        SCPClient client = new SCPClient(connection);
        client.put(data, remoteFileName, remoteTargetDirectory, mode);
    }
    
    public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory, String mode) throws IOException
    {
        SCPClient client = new SCPClient(connection);
        if ((mode == null) || (mode.length() == 0))
        {
            mode = "0600";
        }
        client.put(localFile, remoteFileName, remoteTargetDirectory, mode);
        
        // 重命名
        ch.ethz.ssh2.Session sess = connection.openSession();
        String tmpPathName = remoteTargetDirectory + File.separator + remoteFileName;
        String newPathName = tmpPathName.substring(0, tmpPathName.lastIndexOf("."));
        sess.execCommand("mv " + remoteFileName + " " + newPathName);// 重命名回来
    }
    
    public void copy(String from, String to) throws IOException
    {
        ch.ethz.ssh2.Session sess = connection.openSession();
        sess.execCommand("cp " + from + " " + to);
        sess.close();
    }
    
    public void delete(String path) throws IOException
    {
        ch.ethz.ssh2.Session sess = connection.openSession();
        sess.execCommand("rm -rf " + path);
        sess.close();
    }

    public ScpUtils connect() throws Exception
    {
        connection = new Connection(ip, port);
        connection.connect();
        
        boolean isAuthenticated = connection.authenticateWithPassword(username, password);
        if (isAuthenticated == false)
        {
            String msg = "authentication failed";
            LOGGER.error(msg);
            throw new ConnectException(msg);
        }
        return instance;
    }
    
    public void close()
    {
        connection.close();
    }
    
    public static byte[] getBytes(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream(1024 * 1024);
            byte[] b = new byte[1024 * 1024];
            int i;
            while ((i = fis.read(b)) != -1)
            {
                byteArray.write(b, 0, i);
            }
            fis.close();
            byteArray.close();
            buffer = byteArray.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }
}
