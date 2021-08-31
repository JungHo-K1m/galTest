package com.example.galtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {


    TextView textview;
    Document doc = null;
    LinearLayout layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.textView1);
/*
        Gallery gallery = (Gallery) findViewById(R.id.gallery01);
        MyGalleryAdapter galAdapter = new MyGalleryAdapter(this);
        gallery.setAdapter(galAdapter);*/
    }

    public void onClick(View view){
        GetXMLTask task = new GetXMLTask();
        task.execute("https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4182025000");

    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("data");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

            for(int i = 0; i< nodeList.getLength(); i++){

                //날씨 데이터를 추출
                s += ""+ " ";

                Node node = nodeList.item(i); //data엘리먼트 노드
                Element fstElmnt = (Element) node;


                NodeList dayList = fstElmnt.getElementsByTagName("day");
                s += dayList.item(0).getChildNodes().item(0).getNodeValue()+"일"+"  ";

                NodeList timeList = fstElmnt.getElementsByTagName("hour");
                s += timeList.item(0).getChildNodes().item(0).getNodeValue()+"시"+"  ";

                NodeList tempList = fstElmnt.getElementsByTagName("temp");
                s += tempList.item(0).getChildNodes().item(0).getNodeValue() +" , ";

                NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
                s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            }

            textview.setText(s);

            super.onPostExecute(doc);
        }

    }
/*
    public class MyGalleryAdapter extends BaseAdapter {
        Context context;
        Integer[] cropID = {R.drawable.crop01, R.drawable.crop02, R.drawable.crop03,
                R.drawable.crop04, R.drawable.crop05, R.drawable.crop06, R.drawable.crop07};

        public MyGalleryAdapter(Context c){
            context = c;
        }

        @Override
        public int getCount() {
            return cropID.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new ViewGroup.LayoutParams(300,200));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5,5,5,5);

            imageview.setImageResource(cropID[position]);

            return imageview;
        }
    }*/
}