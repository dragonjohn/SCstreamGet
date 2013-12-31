import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class SCStreamMain implements ClipboardOwner{
	
	public static void main(String [] args) throws Exception{
		
	    /*System.setProperty("http.proxyHost", "proxy.iet");
		System.setProperty("http.proxyPort", "3128");*/
		
		SCStreamMain thg = new SCStreamMain();
		thg.executeHttpGet();
		//thg.executeHttpGetLTV();
		//thg.executeHttpGetSCV();
		//thg.executeHttpGetADV();
		
		
		
	}
	
    public void executeHttpGet() throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            //earlier URLs
            //request.setURI(new URI("http://www.studioclassroom.com/default.php"));
            //request.setURI(new URI("http://www.studioclassroom.com.tw"));
            
            
            // RADIO::  1:let's talk, 2:studioclassroom, 3:advance
            /*int target = 2;
            switch(target){
            case 1:
            	request.setURI(new URI("http://lt.studioclassroom.com/LT-RaDio.php"));
            	break;
            case 2:
            	request.setURI(new URI("http://sc.studioclassroom.com/Sc-rD.php"));
            	break;
            case 3:
            	request.setURI(new URI("http://ad.studioclassroom.com/Ad-RAd.php"));
            	break;
            }*/
            
            
            //2013-08-11 the source change to send correct URL (contains url and header parameters)
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            
            //url params: mag = baa 空中英語教室, day = 2013-08-10 日期
            request.setURI(new URI("http://www.studioclassroom.com/index_play.php?mag=baa&day="+timeStamp));

            //設定header
            request.setHeader("Host","www.studioclassroom.com");
			request.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
			request.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			request.setHeader("Accept-Language",
					"zh-tw,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			request.setHeader("Accept-Encoding", "gzip, deflate");
			request.setHeader("Connection", "keep-alive");
			//加入此參數讓對方伺服器確認是從以下網指連入的
            request.setHeader("Referer", "http://www.studioclassroom.com/default.php");
            request.getAllHeaders();
            HttpResponse response = client.execute(request);
            response.getAllHeaders();
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            System.out.println(page);
            //2013-08-11
            String [] wmaCut = page.split(".wma");
            String [] httpCut;
            for (int i = 0;i<wmaCut.length-1;i++){
            	httpCut = wmaCut[i].split("http");
            	//System.out.println("http"+httpCut[httpCut.length-1]+".wma");
            	// 0:url with domain name, 1:url with ip
        		if(i==1){
        			setClipboardContents("http"+httpCut[httpCut.length-1]+".wma");
        		}
            }
            
            
            
            
            
            
            
            
            //2013/06/08
            /*String [] mmsCut = page.split("mms:");
            String [] wmaCut;
            for (int i = 0;i<mmsCut.length;i++){
            	if(i!=0){
            		wmaCut = mmsCut[i].split(".wma");
            		System.out.println("mms:"+wmaCut[0]+".wma");
            		
            		// 0: no use, 1:today, 2:2d ago (56K), 3:1d ago (56k), 4: today (56k)
            		// 6: 2d ago, 7: 1d ago, 8: today
            		if(i==1){
            			setClipboardContents("http:"+wmaCut[0]+".wma");
            		}
            	}
            }
            */

            // on 2013/06/05
            /*String [] mmsCut = page.split("mms:");
            String [] wmaCut;
            for (int i = 0;i<mmsCut.length;i++){
            	if(i!=0){
            		wmaCut = mmsCut[i].split(".wma");
            		System.out.println("mms:"+wmaCut[0]+".wma");
            		
            		// 0: no use, 1:let's talk, 2:studioclassroom, 3:advance
            		if(i==2){
            			setClipboardContents("http:"+wmaCut[0]+".wma");
            		}
            	}
            }*/
            
            //earlier split methid, end on 2012/12/03
            /*String [] wmaCut = page.split(".wma");
            String [] httpCut;
            for (int i = 0;i<wmaCut.length-1;i++){
            	httpCut = wmaCut[i].split("http");
            	System.out.println("http"+httpCut[httpCut.length-1]+".wma");
            	// 0:let's talk, 1:studioclassroom, 2:advance
        		if(i==1){
        			setClipboardContents("http"+httpCut[httpCut.length-1]+".wma");
        		}
            }*/
            
            
            // earlier split methid, end on 2012/10/17
            /*String [] mmsCut = page.split("mms:");
            String [] wmaCut;
            for (int i = 0;i<mmsCut.length;i++){
            	if(i!=0){
            		wmaCut = mmsCut[i].split(".wma");
            		System.out.println("mms:"+wmaCut[0]+".wma");
            		
            		// 0: no use, 1:let's talk, 2:studioclassroom, 3:advance
            		if(i==2){
            			setClipboardContents("mms:"+wmaCut[0]+".wma");
            		}
            	}
            }*/
            //System.out.println(page);
            } finally {
            if (in != null) {
                try {
                    in.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void executeHttpGetLTV() throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://digichannel.go2school.com.tw/lt/main01.asp?no=1&sec=1"));
            HttpResponse response = client.execute(request);
            
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            
            System.out.println(page);
            
            
            client = new DefaultHttpClient();
            request.setURI(new URI("http://digichannel.go2school.com.tw/lt/play.asp?media=2&no=1"));
            response = client.execute(request);
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            sb = new StringBuffer("");
            line = "";
            NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            page = sb.toString();
            
            System.out.println(page);
            
            /*request.setURI(new URI("http://digichannel.go2school.com.tw/lt/play.asp?media=2&no=1"));
            response = client.execute(request);
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            sb = new StringBuffer("");
            line = "";
            NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            page = sb.toString();
            
            System.out.println(page);*/
            } finally {
            if (in != null) {
                try {
                    in.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void executeHttpGetSCV() throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.goodtv.tv/SC/index.phtml"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            String [] mmsCut = page.split("mms:");
            String [] wmaCut;
            for (int i = 0;i<mmsCut.length;i++){
            	if(i!=0){
            		wmaCut = mmsCut[i].split(".wmv");
            		System.out.println("mms:"+wmaCut[0]+".wmv");
            	}
            }
            //System.out.println(page);
            } finally {
            if (in != null) {
                try {
                    in.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    	
    	public void executeHttpGetADV() throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.studioclassroom.com/ad/ad_topic.php?article_id=02a03"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            String [] mmsCut = page.split("value='http:");
            String [] wmaCut;
            for (int i = 0;i<mmsCut.length;i++){
            	if(i!=0){
            		wmaCut = mmsCut[i].split(".wmv");
            		System.out.println("mms:"+wmaCut[0]+".wmv");
            	}
            }
            //System.out.println(page);
            } finally {
            if (in != null) {
                try {
                    in.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    	
    	

	/**
	 * Empty implementation of the ClipboardOwner interface.
	 */
	public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
		// do nothing
	}

	/**
	 * Place a String on the clipboard, and make this class the owner of the
	 * Clipboard's contents.
	 */
	public void setClipboardContents(String aString) {
		StringSelection stringSelection = new StringSelection(aString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}

	/**
	 * Get the String residing on the clipboard.
	 * 
	 * @return any text found on the Clipboard; if none found, return an empty
	 *         String.
	 */
	public String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null)
				&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException ex) {
				// highly unlikely since we are using a standard DataFlavor
				System.out.println(ex);
				ex.printStackTrace();
			} catch (IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		return result;
	}

}