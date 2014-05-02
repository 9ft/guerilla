package com.mindy.gettjuseatinfo.util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class GetWebPage {
	/**
	 * ��ȡurl��xml��Դ ת��String
	 * 
	 * @param url
	 * @return ���� ��ȡurl��xml�ַ���
	 */
	public static String getStringByUrl(String url) {
		String outputString = "";
		// DefaultHttpClient
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// HttpGet
		HttpGet httpget = new HttpGet(url);
		// ResponseHandler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			outputString = httpclient.execute(httpget, responseHandler);
			// outputString = new String(outputString.getBytes("ISO-8859-1"),
			// "utf-8"); // �����������
			outputString = new String(outputString.getBytes("utf-8"), "utf-8");

			Log.i("HttpClientConnector", "���ӳɹ�");
		} catch (Exception e) {
			Log.i("HttpClientConnector", "����ʧ��");
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return outputString;
	}
}
