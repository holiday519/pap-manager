package com.pxene.pap;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pxene.pap.domain.protobuf.MoMoEntity;
import com.pxene.pap.domain.protobuf.MoMoEntity.BidRequest;
import com.pxene.pap.domain.protobuf.MoMoEntity.BidRequest.Imp;
import com.pxene.pap.domain.protobuf.MoMoEntity.BidRequest.Native;
import com.pxene.pap.domain.protobuf.MoMoEntity.BidRequest.NativeFormat;

public class ProtobufTest
{
    public static void main(String[] args) throws ClientProtocolException, IOException
    {
        Native nativeObj = MoMoEntity.BidRequest.Native.newBuilder().addNativeFormat(NativeFormat.FEED_LANDING_PAGE_LARGE_IMG).build();
        
        Imp impObj = MoMoEntity.BidRequest.Imp.newBuilder().setId(UUID.randomUUID().toString()).setSlotid("xxxx").setNative(nativeObj).build();

        MoMoEntity.BidRequest.Builder builder = MoMoEntity.BidRequest.newBuilder();
        builder.setId(UUID.randomUUID().toString());
        builder.setVersion("1.0");
        builder.setIsTest(false);
        builder.setIsPing(false);
        builder.addImp(impObj);
        
        
        MoMoEntity.BidRequest bidRequest = builder.build();
        //doTest(bidRequest);
        sendRequest(bidRequest);
        
    }

    private static void sendRequest(MoMoEntity.BidRequest bidRequest) throws IOException, ClientProtocolException
    {
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        
        HttpPost httpPost = new HttpPost("http://192.168.3.91:81/z"); 
        httpPost.addHeader("Content-Type", "application/x-protobuf");  
        
        HttpEntity byteArrayEntity = new ByteArrayEntity(bidRequest.toByteArray());
        httpPost.setEntity(byteArrayEntity); 
        
        CloseableHttpResponse response = httpClient.execute(httpPost);
        
        try
        {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null)
            {
                System.out.println("Response content: " + EntityUtils.toString(responseEntity));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            response.close();
        }
    }

    public static void doTest(MoMoEntity.BidRequest bidRequest) throws InvalidProtocolBufferException
    {
        System.out.println("Before: " + bidRequest.toString());
        
        System.out.println("========== BidRequest Bytes ==========");
        for (byte brByte : bidRequest.toByteArray())
        {
            System.out.print(brByte);
        }
        System.out.println();
        System.out.println(bidRequest.toByteString());
        System.out.println("==============================");
        
        
        // 模拟接收到Protobuf字节数组，反序列化为BidRequest对象
        byte[] byteArray = bidRequest.toByteArray();
        BidRequest brObj = MoMoEntity.BidRequest.parseFrom(byteArray);
        System.out.println("After :" + brObj.toString());
    }
}
