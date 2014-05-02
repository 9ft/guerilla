package com.mindy.gettjuseatinfo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mindy.gettjuseatinfo.MainActivity;
import com.mindy.gettjuseatinfo.domain.TjuSeatInfo;
import com.mindy.gettjuseatinfo.util.GetWebPage;

public class TjuSeatInfoPullParse {

	public static ArrayList<TjuSeatInfo> getAllInfos(int startWeek, int endWeek, String[] buildings) {
		int building_count = buildings.length;
		String baseUrl = "http://pipes.yahoo.com/pipes/pipe.run?_id=4b360559dab641efd7489e79f32cacdd&_render=rss";
		String weekNum = "&����=";
		String buildingNum = "&¥���=";
		ArrayList<TjuSeatInfo> allInfos = new ArrayList<TjuSeatInfo>();
		ArrayList<TjuSeatInfo> tmpInfos = new ArrayList<TjuSeatInfo>();

		for (int i = startWeek; i <= endWeek; i++) {
			for (int j = 0; j < building_count; j++) {
				tmpInfos = getInfosByUrl(baseUrl + weekNum + i + buildingNum + buildings[j]);
				allInfos.addAll(tmpInfos);
				tmpInfos.clear();
				Log.i("parser", "========================> ��" + i + "/26��");
			}
		}
		return allInfos;
	}

	public static ArrayList<TjuSeatInfo> getAllInfos(int startWeek, int endWeek, String building) {
		// int building_count = buildings.length;
		String baseUrl = "http://pipes.yahoo.com/pipes/pipe.run?_id=4b360559dab641efd7489e79f32cacdd&_render=rss";
		String weekNum = "&����=";
		String buildingNum = "&¥���=";
		ArrayList<TjuSeatInfo> allInfos = new ArrayList<TjuSeatInfo>();
		ArrayList<TjuSeatInfo> tmpInfos = new ArrayList<TjuSeatInfo>();

		for (int i = startWeek; i <= endWeek; i++) {

			tmpInfos = getInfosByUrl(baseUrl + weekNum + i + buildingNum + building);
			allInfos.addAll(tmpInfos);
			tmpInfos.clear();
			Log.i("parser", "========================> ��" + i + "/26��");

		}
		return allInfos;
	}

	public static ArrayList<TjuSeatInfo> getInfosByUrl(String url) {

		String xmlStr = GetWebPage.getStringByUrl(url);
		ArrayList<TjuSeatInfo> seatInfos = Parse(xmlStr);

		return seatInfos;
	}

	public static ArrayList<TjuSeatInfo> Parse(String seatInfoString) {
		ArrayList<TjuSeatInfo> tjuSeatInfos = new ArrayList<TjuSeatInfo>();

		try {
			// ���幤�� XmlPullParserFactory
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

			// ��������� XmlPullParser
			XmlPullParser parser = factory.newPullParser();

			// ��ȡxml��������
			parser.setInput(new StringReader(seatInfoString));

			tjuSeatInfos = ParseXml(parser);

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tjuSeatInfos;
	}

	public static ArrayList<TjuSeatInfo> Parse(InputStream inputStream) {
		ArrayList<TjuSeatInfo> tjuSeatInfos = new ArrayList<TjuSeatInfo>();
		try {
			// ���幤�� XmlPullParserFactory
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

			// ��������� XmlPullParser
			XmlPullParser parser = factory.newPullParser();

			// ��ȡxml��������
			parser.setInput(inputStream, "utf-8");

			tjuSeatInfos = ParseXml(parser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return tjuSeatInfos;
	}

	public static ArrayList<TjuSeatInfo> ParseXml(XmlPullParser parser) {
		ArrayList<TjuSeatInfo> seatInfos = new ArrayList<TjuSeatInfo>();
		TjuSeatInfo seatInfo = null;

		try {
			// ��ʼ�����¼�
			int eventType = parser.getEventType();
			boolean tmp = false;
			// �����¼����������ĵ�������һֱ����
			while (eventType != XmlPullParser.END_DOCUMENT) {

				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("channel".equals(parser.getName())) {
						// ������ȫ�ֿ�ʼ�ı�ǩ
						seatInfos = new ArrayList<TjuSeatInfo>();
					} else if ("item".equals(parser.getName())) {
						seatInfo = new TjuSeatInfo();
						tmp = true;

					} else if ("title".equals(parser.getName())) {
						if (tmp) {
							String className = parser.nextText();
							seatInfo.setClassName(className);
						}
					} else if ("link".equals(parser.getName())) {
						if (tmp) {
							String weekNum = parser.nextText();
							seatInfo.setWeekNum(weekNum);
						}

					} else if ("description".equals(parser.getName())) {
						if (tmp) {
							String infos = parser.nextText();
							seatInfo.setInfos(infos);
						}

					} else if ("author".equals(parser.getName())) {
						if (tmp) {
							String buildingNum = parser.nextText();
							seatInfo.setBuildingNum(buildingNum);
						}
					}
					break;

				case XmlPullParser.END_TAG:
					if ("item".equals(parser.getName())) {
						// һ����Ϣ�����
						// ����ID
						seatInfo.genTjuSeatInfoId();
						seatInfos.add(seatInfo);

						Log.i("parser", "========================> �Ѽ���һ��: " + "BuildingNum:" + seatInfo.getBuildingNum() + " WeekNum:" + seatInfo.getWeekNum());

						seatInfo = null;
						tmp = false;
					}
					break;
				}

				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return seatInfos;
	}
}
