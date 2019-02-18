package com.angelhack.ri.schooriken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;



public class internetSocket {
	
	private String webAddr;
	private static int BUFFER_SIZE = 2000;
	
	public internetSocket(String address){
		this.webAddr = address;
	}
	
	public Bitmap getBitmapImg(String url){
		
		Bitmap bitImg = null;
		InputStream in = null;
		
		try{
			in = HTTPconnection(url,"",false);
			bitImg = BitmapFactory.decodeStream(in);
			in.close();
		}
		catch (IOException err){
			err.printStackTrace();
		}
		
		return bitImg;
	}
	
	public String getData(Context contxt,String data){
		
		//Try connecting --> Get Output from http
		InputStream in = null;
		
		try{
			
			in = HTTPconnection(this.webAddr,data,true);
			
		}
		catch (IOException err) {
			Toast.makeText(contxt, err.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
			err.printStackTrace();
			return "";
			
		}
		
		//Initialize Input Reading
		InputStreamReader inRead = new InputStreamReader(in);
		int readChar;
		String output = "";
		char[] inputBuffer = new char[BUFFER_SIZE];
		
		//Read Input to JSON
		try{
			
			while((readChar = inRead.read(inputBuffer))>0){
				String readString = String.copyValueOf(inputBuffer, 0, readChar);
				output = output+readString;
				inputBuffer = new char[BUFFER_SIZE];
			}
			in.close();
			
		}
		catch (IOException err){
			
			err.printStackTrace();
			return "";
			
		}
		
		return output;
	}
	
	public void sendData(Context contxt,String data){
		
	}
	
	private InputStream HTTPconnection(String webAddr,String inData,Boolean request) throws IOException {
		
		int response = -1;
		InputStream in = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append(inData);
		
		URL url = new URL(webAddr);
		URLConnection connect = url.openConnection();
		if(!(connect instanceof HttpURLConnection)) throw new IOException("Not a HTTP Connection!");
		
		try{
			
			HttpURLConnection httpCon = (HttpURLConnection) connect;
			httpCon.setAllowUserInteraction(false);
			httpCon.setInstanceFollowRedirects(true);
			httpCon.setDoInput(true);
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			
			if(request){
				OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());
				wr.write(sb.toString());
				wr.close();
			}
			
			httpCon.connect();
			response = httpCon.getResponseCode();
			
			if(response == HttpURLConnection.HTTP_OK){
				in = httpCon.getInputStream();
			}
			
		}
		catch (Exception ex){
			
			throw new IOException("Cannot Connect to HTTP site");
			
		}
		
		return in;
	}
	
}