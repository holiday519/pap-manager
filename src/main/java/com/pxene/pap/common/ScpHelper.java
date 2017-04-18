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

public class ScpHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScpHelper.class);
    
    private String host;
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
    
    
    public ScpHelper(String host, int port, String username, String password) throws Exception 
    {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    
    
    public void getFile(String remoteFile, String localTargetDirectory) throws Exception
    {
        Connection connection = connect(host, port, username, password);
        SCPClient client = new SCPClient(connection);
        client.get(remoteFile, localTargetDirectory);
        connection.close();
    }
    
    public void putFile(String localFile, String remoteTargetDirectory) throws Exception
    {
        Connection connection = connect(host, port, username, password);
        SCPClient client = new SCPClient(connection);
        client.put(localFile, remoteTargetDirectory);
        connection.close();
    }
    
    public void putFile(byte[] data, String remoteFileName, String remoteTargetDirectory) throws Exception
    {
        Connection connection = connect(host, port, username, password);
        SCPClient client = new SCPClient(connection);
        client.put(data, remoteFileName, remoteTargetDirectory, mode);
        connection.close();
    }
    
    public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory, String mode) throws Exception
    {
        Connection connection = connect(host, port, username, password);
        SCPClient client = new SCPClient(connection);
        
        if ((mode == null) || (mode.length() == 0))
        {
            mode = "0600";
        }
        client.put(localFile, remoteFileName, remoteTargetDirectory, mode);
        
        // 重命名
        ch.ethz.ssh2.Session session = connection.openSession();
        String tmpPathName = remoteTargetDirectory + File.separator + remoteFileName;
        String newPathName = tmpPathName.substring(0, tmpPathName.lastIndexOf("."));
        session.execCommand("mv " + remoteFileName + " " + newPathName);// 重命名回来
        
        session.close();
        connection.close();
    }
    
    public void copy(String from, String to) throws Exception
    {
        Connection connection = connect(host, port, username, password);
        ch.ethz.ssh2.Session session = connection.openSession();
        session.execCommand("cp " + from + " " + to);
        connection.close();
    }
    
    public void delete(String path) throws Exception
    {
        Connection connection = connect(host, port, username, password);
        ch.ethz.ssh2.Session session = connection.openSession();
        session.execCommand("rm -rf " + path);
        session.close();
        connection.close();
    }

    public static Connection connect(String host, int port, String username, String password) throws Exception
    {
        Connection connection = new Connection(host, port);
        
        connection.connect();
        
        boolean isAuthenticated = connection.authenticateWithPassword(username, password);
        if (isAuthenticated == false)
        {
            String msg = "authentication failed";
            LOGGER.error(msg);
            throw new ConnectException(msg);
        }
        
        return connection;
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
