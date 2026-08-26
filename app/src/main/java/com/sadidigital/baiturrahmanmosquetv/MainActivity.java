package com.sadidigital.baiturrahmanmosquetv;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    Handler h = new Handler(); TextView clock,date,next,countdown,notice; LinearLayout list; SharedPreferences p;
    String[] names={"ফজর","যুহর","আসর","মাগরিব","ইশা"};
    String[] sk={"fajrStart","dhuhrStart","asrStart","maghribStart","ishaStart"};
    String[] jk={"fajrJamaat","dhuhrJamaat","asrJamaat","maghribJamaat","ishaJamaat"};
    String[] ds={"04:19","12:02","16:04","18:26","19:42"}, dj={"05:10","13:00","17:00","18:33","20:15"};
    int green=Color.rgb(10,106,45);
    @Override public void onCreate(Bundle b){super.onCreate(b);p=getSharedPreferences("mosque",MODE_PRIVATE);getWindow().getDecorView().setSystemUiVisibility(5894);ui();h.post(tick);}
    TextView t(String s,int z,int c,boolean bold){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(12,8,12,8);v.setGravity(Gravity.CENTER_VERTICAL);if(bold)v.setTypeface(null,1);return v;}
    LinearLayout r(){LinearLayout x=new LinearLayout(this);x.setOrientation(LinearLayout.HORIZONTAL);x.setGravity(Gravity.CENTER_VERTICAL);return x;}
    void ui(){LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(18,18,18,18);root.setBackgroundColor(Color.rgb(247,250,245));
      LinearLayout hd=r(), tb=new LinearLayout(this);tb.setOrientation(LinearLayout.VERTICAL);tb.addView(t(p.getString("name","বাইতুর রহমান জামে মসজিদ"),34,green,true));tb.addView(t(p.getString("address","উত্তরহাওলা, মনোহরগঞ্জ, কুমিল্লা"),20,Color.BLACK,false));hd.addView(tb,new LinearLayout.LayoutParams(0,110,1.4f));
      LinearLayout tm=new LinearLayout(this);tm.setOrientation(LinearLayout.VERTICAL);clock=t("",31,Color.WHITE,true);clock.setGravity(17);clock.setBackgroundColor(green);date=t("",15,Color.BLACK,false);date.setGravity(17);tm.addView(clock,new LinearLayout.LayoutParams(-1,70));tm.addView(date,new LinearLayout.LayoutParams(-1,40));hd.addView(tm,new LinearLayout.LayoutParams(0,110,.8f));root.addView(hd);
      LinearLayout mid=r(),left=new LinearLayout(this);left.setOrientation(LinearLayout.VERTICAL);TextView head=t("ওয়াক্ত              ওয়াক্ত শুরু              জামাত",19,green,true);head.setGravity(17);left.addView(head,new LinearLayout.LayoutParams(-1,55));list=new LinearLayout(this);list.setOrientation(LinearLayout.VERTICAL);left.addView(list,new LinearLayout.LayoutParams(-1,0,1));mid.addView(left,new LinearLayout.LayoutParams(0,-1,1.2f));
      LinearLayout right=new LinearLayout(this);right.setOrientation(LinearLayout.VERTICAL);right.setPadding(20,10,10,10);right.addView(t("পরবর্তী ওয়াক্ত",20,green,true));next=t("",34,green,true);countdown=t("",24,Color.BLACK,true);right.addView(next);right.addView(countdown);right.addView(t("আজকের গুরুত্বপূর্ণ সময়",20,green,true));right.addView(t("সূর্যোদয়: 05:38    সূর্যাস্ত: 18:23\nইশরাক: 05:53    ইফতার: 18:26",18,Color.BLACK,false));right.addView(t("মসজিদ উন্নয়নে সহযোগিতা করুন",20,green,true));right.addView(t(p.getString("donation","বিকাশ / নগদ / রকেট: 01XXXXXXXXX"),18,Color.BLACK,true));right.addView(t("রিমোটের OK / Menu চাপলে Settings",14,Color.DKGRAY,false));mid.addView(right,new LinearLayout.LayoutParams(0,-1,.9f));root.addView(mid,new LinearLayout.LayoutParams(-1,0,1));
      notice=t(p.getString("notice","নোটিশ: সবাইকে সময়মতো জামাতে উপস্থিত থাকার অনুরোধ।"),18,Color.WHITE,true);notice.setBackgroundColor(green);notice.setSingleLine();notice.setEllipsize(android.text.TextUtils.TruncateAt.MARQUEE);notice.setMarqueeRepeatLimit(-1);notice.setSelected(true);root.addView(notice,new LinearLayout.LayoutParams(-1,58));setContentView(root);}
    Runnable tick=new Runnable(){public void run(){refresh();h.postDelayed(this,1000);}};
    void refresh(){Date n=new Date();clock.setText(new SimpleDateFormat("hh:mm:ss a",Locale.ENGLISH).format(n));date.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy",Locale.ENGLISH).format(n));draw();int x=nextIdx();next.setText(names[x]);countdown.setText("শুরু হতে বাকি: "+cd(p.getString(sk[x],ds[x])));}
    int mins(String s){try{String[] a=s.split(":");return Integer.parseInt(a[0])*60+Integer.parseInt(a[1]);}catch(Exception e){return 0;}}
    int cur(){Calendar c=Calendar.getInstance();int m=c.get(11)*60+c.get(12),z=4;for(int i=0;i<5;i++)if(m>=mins(p.getString(sk[i],ds[i])))z=i;return z;}
    int nextIdx(){Calendar c=Calendar.getInstance();int m=c.get(11)*60+c.get(12);for(int i=0;i<5;i++)if(m<mins(p.getString(sk[i],ds[i])))return i;return 0;}
    String fmt(String s){try{return new SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(new SimpleDateFormat("HH:mm",Locale.ENGLISH).parse(s));}catch(Exception e){return s;}}
    String cd(String s){Calendar c=Calendar.getInstance();int n=c.get(11)*3600+c.get(12)*60+c.get(13),q=mins(s)*60;if(q<=n)q+=86400;int d=q-n;return String.format(Locale.ENGLISH,"%02d:%02d:%02d",d/3600,(d%3600)/60,d%60);}
    void draw(){list.removeAllViews();int a=cur();for(int i=0;i<5;i++){LinearLayout x=r();if(i==a)x.setBackgroundColor(green);int c=i==a?Color.WHITE:Color.BLACK;TextView n=t("• "+names[i],22,c,true),s=t(fmt(p.getString(sk[i],ds[i])),20,c,false),j=t(fmt(p.getString(jk[i],dj[i])),20,c,true);s.setGravity(17);j.setGravity(17);x.addView(n,new LinearLayout.LayoutParams(0,68,1));x.addView(s,new LinearLayout.LayoutParams(0,68,1));x.addView(j,new LinearLayout.LayoutParams(0,68,1));list.addView(x);}}
    @Override public boolean onKeyDown(int k,KeyEvent e){if(k==23||k==66||k==82||k==176){settings();return true;}return super.onKeyDown(k,e);}
    EditText f(String hint,String val){EditText e=new EditText(this);e.setHint(hint);e.setText(val);e.setSingleLine();return e;}
    void settings(){LinearLayout f=new LinearLayout(this);f.setOrientation(LinearLayout.VERTICAL);f.setPadding(25,10,25,10);EditText name=f("মসজিদের নাম",p.getString("name","বাইতুর রহমান জামে মসজিদ")),addr=f("ঠিকানা",p.getString("address","উত্তরহাওলা, মনোহরগঞ্জ, কুমিল্লা"));f.addView(name);f.addView(addr);EditText[] s=new EditText[5],j=new EditText[5];for(int i=0;i<5;i++){s[i]=f(names[i]+" শুরু HH:mm",p.getString(sk[i],ds[i]));j[i]=f(names[i]+" জামাত HH:mm",p.getString(jk[i],dj[i]));f.addView(s[i]);f.addView(j[i]);}EditText don=f("দান/বিকাশ তথ্য",p.getString("donation","বিকাশ / নগদ / রকেট: 01XXXXXXXXX")),no=f("নোটিশ",p.getString("notice","নোটিশ: সবাইকে সময়মতো জামাতে উপস্থিত থাকার অনুরোধ।"));f.addView(don);f.addView(no);ScrollView sv=new ScrollView(this);sv.addView(f);new AlertDialog.Builder(this).setTitle("মসজিদ TV Settings").setView(sv).setNegativeButton("বাতিল",null).setPositiveButton("Save",(d,w)->{SharedPreferences.Editor ed=p.edit().putString("name",name.getText().toString()).putString("address",addr.getText().toString()).putString("donation",don.getText().toString()).putString("notice",no.getText().toString());for(int i=0;i<5;i++){ed.putString(sk[i],s[i].getText().toString());ed.putString(jk[i],j[i].getText().toString());}ed.apply();recreate();}).show();}
}
