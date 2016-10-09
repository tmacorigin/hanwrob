package com.tmac.onsite.updateversion;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by Tmac on 2016/6/2.
 */
public class UpdataInfoParser {

    public static UpdateInfo getUpdataInfo(InputStream is) throws Exception{

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");
        int type = parser.getEventType();
        UpdateInfo info = new UpdateInfo();
        while (type != XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("version".equals(parser.getName())){
                        info.setVersion(parser.nextText());
                    }else if("url".equals(parser.getName())){
                        info.setUrl(parser.nextText());
                    }else if("description".equals(parser.getName())){
                        info.setDescription(parser.nextText());
                    }
                    break;
            }
            type = parser.next();
        }
        return info;

    }


}
