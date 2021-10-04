package com.example.galtest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

    TextView textview1, textview2, textview3, textview4;
    ImageButton imageView1, imageView2, imageView3, imageView4;
    ImageButton imagebtn;
    Context  context;

    Document doc = null;
    LinearLayout layout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview1 = (TextView) findViewById(R.id.textView1);
        textview2 = (TextView) findViewById(R.id.textView2);
        textview3 = (TextView) findViewById(R.id.textView3);
        textview4 = (TextView) findViewById(R.id.textView4);

        imageView1 = (ImageButton) findViewById(R.id.image01);
        imageView2 = (ImageButton) findViewById(R.id.image02);
        imageView3 = (ImageButton) findViewById(R.id.image03);
        imageView4 = (ImageButton) findViewById(R.id.image04);

        imagebtn = (ImageButton)findViewById(R.id.addbtn);
        layout = (LinearLayout)findViewById(R.id.layout);
        context = this;

        Integer[] cropID = {R.drawable.crop0, R.drawable.crop1, R.drawable.crop2, R.drawable.crop3,
                R.drawable.crop4, R.drawable.crop5, R.drawable.crop6};
        Integer[] ID={-1};

        int i;
        for(i=0;i<7;i++){
            ImageButton img = new ImageButton(context);

            img.setLayoutParams(new ViewGroup.LayoutParams(350,220));
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setPadding(20,20,20,20);
            img.setBackgroundColor(Color.argb(0,0,0,0));
            img.setImageResource(cropID[i]);
            switch (i){
                case 0:
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "배추", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case 1 :
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "파", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case 2:
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "고추", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case 3:
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "호박", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case 4:
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "무", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case 5:
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "귤", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case 6:
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "토마토", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }

            layout.addView(img);
        }

        findViewById(R.id.image01).setOnClickListener(onClickListener);
        findViewById(R.id.image02).setOnClickListener(onClickListener);
        findViewById(R.id.image03).setOnClickListener(onClickListener);
        findViewById(R.id.image04).setOnClickListener(onClickListener);
    }


    public void onClick01(View view){
        GetXMLTask1 task = new GetXMLTask1();
        task.execute("https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4311135000");
    }

    private class GetXMLTask1 extends AsyncTask<String, Void, Document> {

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

            //날씨 데이터를 추출
            s += ""+ " ";

            Node node = nodeList.item(0); //data엘리먼트 노드
            Element fstElmnt = (Element) node;

            NodeList tempList = fstElmnt.getElementsByTagName("temp");
            s += "충청북도 청주시 \n상당구 문의면\n 기온 : " + tempList.item(0).getChildNodes().item(0).getNodeValue() +"도 \n";

            NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
            s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";
            switch (websiteList.item(0).getChildNodes().item(0).getNodeValue()){
                case "맑음" :
                    imageView1.setImageResource(R.drawable.sunny);
                    break;
                case "구름 조금" :
                    imageView1.setImageResource(R.drawable.cloudy);
                    break;
                case "구름 많음" :
                    imageView1.setImageResource(R.drawable.littlecloud);
                    break;
                case "흐림" :
                    imageView1.setImageResource(R.drawable.cloud);
                    break;
                case "비":
                    imageView1.setImageResource(R.drawable.rain);
                    break;
                case "눈" :
                    imageView1.setImageResource(R.drawable.snow);
                    break;
                case "눈/비" :
                    imageView1.setImageResource(R.drawable.snowrain);
                    break;
            }
            NodeList moistList = fstElmnt.getElementsByTagName("reh");
            s += "습도 = "+  moistList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            NodeList percentList = fstElmnt.getElementsByTagName("pop");
            s += "강수확률 = "+  percentList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            textview1.setText(s);

            super.onPostExecute(doc);
        }
    }

    public void onClick02(View view){
        GetXMLTask2 task = new GetXMLTask2();
        task.execute("https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4311425300");
    }

    private class GetXMLTask2 extends AsyncTask<String, Void, Document> {

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

            //날씨 데이터를 추출
            s += ""+ " ";

            Node node = nodeList.item(0); //data엘리먼트 노드
            Element fstElmnt = (Element) node;

            NodeList tempList = fstElmnt.getElementsByTagName("temp");
            s += "충청북도 청원군 \n오창읍\n 기온 : " + tempList.item(0).getChildNodes().item(0).getNodeValue() +"도 \n";

            NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
            s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";
            switch (websiteList.item(0).getChildNodes().item(0).getNodeValue()){
                case "맑음" :
                    imageView2.setImageResource(R.drawable.sunny);
                    break;
                case "구름 조금" :
                    imageView2.setImageResource(R.drawable.cloudy);
                    break;
                case "구름 많음" :
                    imageView2.setImageResource(R.drawable.littlecloud);
                    break;
                case "흐림" :
                    imageView2.setImageResource(R.drawable.cloud);
                    break;
                case "비":
                    imageView2.setImageResource(R.drawable.rain);
                    break;
                case "눈" :
                    imageView2.setImageResource(R.drawable.snow);
                    break;
                case "눈/비" :
                    imageView2.setImageResource(R.drawable.snowrain);
                    break;
            }
            NodeList moistList = fstElmnt.getElementsByTagName("reh");
            s += "습도 = "+  moistList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            NodeList percentList = fstElmnt.getElementsByTagName("pop");
            s += "강수확률 = "+  percentList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            textview2.setText(s);

            super.onPostExecute(doc);
        }
    }

    public void onClick03(View view){
        GetXMLTask3 task = new GetXMLTask3();
        task.execute("https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4311134000");
    }

    private class GetXMLTask3 extends AsyncTask<String, Void, Document> {

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

            //날씨 데이터를 추출
            s += ""+ " ";

            Node node = nodeList.item(0); //data엘리먼트 노드
            Element fstElmnt = (Element) node;

            NodeList tempList = fstElmnt.getElementsByTagName("temp");
            s += "충청북도 청주시 \n상당구 남일면\n 기온 : " + tempList.item(0).getChildNodes().item(0).getNodeValue() +"도 \n";

            NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
            s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";
            switch (websiteList.item(0).getChildNodes().item(0).getNodeValue()){
                case "맑음" :
                    imageView3.setImageResource(R.drawable.sunny);
                    break;
                case "구름 조금" :
                    imageView3.setImageResource(R.drawable.cloudy);
                    break;
                case "구름 많음" :
                    imageView3.setImageResource(R.drawable.littlecloud);
                    break;
                case "흐림" :
                    imageView3.setImageResource(R.drawable.cloud);
                    break;
                case "비":
                    imageView3.setImageResource(R.drawable.rain);
                    break;
                case "눈" :
                    imageView3.setImageResource(R.drawable.snow);
                    break;
                case "눈/비" :
                    imageView3.setImageResource(R.drawable.snowrain);
                    break;
            }
            NodeList moistList = fstElmnt.getElementsByTagName("reh");
            s += "습도 = "+  moistList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            NodeList percentList = fstElmnt.getElementsByTagName("pop");
            s += "강수확률 = "+  percentList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            textview3.setText(s);

            super.onPostExecute(doc);
        }
    }

    public void onClick04(View view){
        GetXMLTask4 task = new GetXMLTask4();
        task.execute("https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4311425300");
    }

    private class GetXMLTask4 extends AsyncTask<String, Void, Document> {

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

            //날씨 데이터를 추출
            s += ""+ " ";

            Node node = nodeList.item(0); //data엘리먼트 노드
            Element fstElmnt = (Element) node;

            NodeList tempList = fstElmnt.getElementsByTagName("temp");
            s += "충청북도 청원군 \n오창읍\n 기온 : " + tempList.item(0).getChildNodes().item(0).getNodeValue() +"도 \n";

            NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
            s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";
            switch (websiteList.item(0).getChildNodes().item(0).getNodeValue()){
                case "맑음" :
                    imageView4.setImageResource(R.drawable.sunny);
                    break;
                case "구름 조금" :
                    imageView4.setImageResource(R.drawable.cloudy);
                    break;
                case "구름 많음" :
                    imageView4.setImageResource(R.drawable.littlecloud);
                    break;
                case "흐림" :
                    imageView4.setImageResource(R.drawable.cloud);
                    break;
                case "비":
                    imageView4.setImageResource(R.drawable.rain);
                    break;
                case "눈" :
                    imageView4.setImageResource(R.drawable.snow);
                    break;
                case "눈/비" :
                    imageView4.setImageResource(R.drawable.snowrain);
                    break;
            }
            NodeList moistList = fstElmnt.getElementsByTagName("reh");
            s += "습도 = "+  moistList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            NodeList percentList = fstElmnt.getElementsByTagName("pop");
            s += "강수확률 = "+  percentList.item(0).getChildNodes().item(0).getNodeValue() +"% \n";

            textview4.setText(s);

            super.onPostExecute(doc);
        }
    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image01:
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "부부농장", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.image02:
                    Toast.makeText(MainActivity.this, "하나로 대신 주말 농장", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.image03:
                    Toast.makeText(MainActivity.this, "청주 농협 주말 농장", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.image04:
                    Toast.makeText(MainActivity.this, "오창 다래 농장", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


}