package Douban;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Dao.bookDap;
import Model.doubanBook;
import Proxy.HttpProxy;
import Proxy.ProxyPool;
import Util.HttpStatus;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
/**
 * 
 * WebCollector 2.40新特性 page.matchType
 * 在添加CrawlDatum时（添加种子、或在抓取时向next中添加任务），
 * 可以为CrawlDatum设置type信息
 * 
 * type的本质也是meta信息，为CrawlDatum的附加信息
 * 在添加种子或向next中添加任务时，设置type信息可以简化爬虫的开发
 * 
 * 例如在处理列表页时，爬虫解析出内容页的链接，在将内容 页链接作为后续任务
 * 将next中添加时，可设置其type信息为content（可自定义），在后续抓取中，
 * 通过page.matchType("content")就可判断正在解析的页面是否为内容页
 * 
 * 设置type的方法主要有3种：
 * 1）添加种子时，addSeed(url,type)
 * 2）向next中添加后续任务时：next.add(url,type)或next.add(links,type)
 * 3）在定义CrawlDatum时：crawlDatum.type(type)
 *
 * @author hu
 */
public class DemoTypeCrawler extends BreadthCrawler {

	 /*
    该教程是DemoMetaCrawler的简化版

    该Demo爬虫需要应对豆瓣图书的三种页面：
    1）标签页（taglist，包含图书列表页的入口链接）
    2）列表页（booklist，包含图书详情页的入口链接）
    3）图书详情页（content）

    另一种常用的遍历方法可参考TutorialCrawler
 */
    public DemoTypeCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		// TODO Auto-generated constructor stub
        this.addSeed("https://book.douban.com/tag/","taglist");
//		this.addSeed("https://book.douban.com/subject/21869094/","list");
	}
    public doubanBook getInfo(doubanBook book,String url){
    	String author ="暂无";
    	String publish ="暂无";
    	String publishTime ="暂无";
    	String price ="暂无";
    	String ISBN ="暂无";
    	
    	try {
			Document dc = Jsoup.connect(url).get();
			Element info = dc.getElementById("info");
			author = info.select("a").first().text();
//			System.out.println("author"+author);
			String html = info.html();
			
			String regex1 = "出版社:</span>(.*)\n";
			String regex2 = "出版年:</span>(.*)\n";
			String regex3 = "定价:</span>(.*)\n";
			String regex4 = "ISBN:</span>(.*)\n";
			
			Pattern pattern1 = Pattern.compile(regex1);
			Pattern pattern2 = Pattern.compile(regex2);
			Pattern pattern3 = Pattern.compile(regex3);
			Pattern pattern4 = Pattern.compile(regex4);
			
			Matcher matcher1 = pattern1.matcher(html);
			Matcher matcher2 = pattern2.matcher(html);
			Matcher matcher3 = pattern3.matcher(html);
			Matcher matcher4 = pattern4.matcher(html);
			while(matcher1.find()){
				publish = matcher1.group(1);
				publish = publish.replace(" ","");
			}
			while(matcher2.find()){
				publishTime = matcher2.group(1);
				publishTime = publishTime.replace(" ","");
			} 
			while(matcher3.find()){
				price = matcher3.group(1);
				price = price.replace(" ","");
			}
			while(matcher4.find()){
				ISBN = matcher4.group(1);
				ISBN = ISBN.replace(" ","");
			}
//			System.out.println(info.html());
//			System.out.println(info.text());
//			Document temp = Jsoup.parse(info.html());
//			Elements div = temp.select("span");
//			for(Element temp2:div){
//				String tempInfor = temp2.text();
//				System.out.println(tempInfor);
//			}
//			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	book.setAuthor(author);
		book.setPublish(publish);
		book.setPublishTime(publishTime);
		book.setPrice(price);
		book.setISBN(ISBN);
		
    	return book;
    }
	
    public doubanBook getBookIntro(doubanBook book,String url){
    	String bookDes = "暂无"; 
    	try {
				Document dc = Jsoup.connect(url).get();
				Elements h2 = dc.getElementsByTag("h2");
				
				//判断有没有内容简介
				String h2Text = h2.text();
				if(h2Text.indexOf("内容简介")==-1){//没有内容简介
					book.setDescrip(bookDes);
					return book;
				}
				
				Element el = dc.getElementById("link-report");
				String temp =  el.select("p").text();
				if(!temp.equals(""))
					bookDes = temp;
//				System.out.println(el.select("p").text());
			} catch (IOException e) {
				bookDes = "暂无"; 
				e.printStackTrace();
			}
    	book.setDescrip(bookDes);
    	return book;
    }
    
    public doubanBook getAuthorDes(doubanBook book,String url){
    	String authorDes="暂无";
		try {
			Document dc = Jsoup.connect(url).get();
			Elements h2 = dc.getElementsByTag("h2");
			
			//判断有没有作者简介
			String h2Text = h2.text();
			if(h2Text.indexOf("作者简介")==-1){//没有作者简介
				book.setAuthorDes(authorDes);
				return book;
			}
			
			Element el = dc.getElementsByClass("intro").last();
			String temp = el.select("p").text();
			if(!temp.equals(""))
				authorDes = temp;
		} catch (IOException e) {
			authorDes="暂无";
			e.printStackTrace();
		}
		book.setAuthorDes(authorDes);
    	return book;
    }
    
    @Override
    public void visit(Page page, CrawlDatums next) {
		doubanBook book = new doubanBook();
		bookDap a = new bookDap();
		
    	 if(page.matchType("taglist")){
    		 next.add(page.links("table.tagCol td>a"),"booklist");
         }else if(page.matchType("booklist")){
        	 next.add(page.links("div.info>h2>a"),"content");
         }else if(page.matchType("content")){
			 String score = "暂无";
			 String title=page.select("h1>span").first().text();
             String temp =page.select("strong.ll.rating_num").first().text();
             if(!temp.equals("")){
            	 score = temp;
             }
             book.setBookName(title);
             book.setScore(score);
            //单独处理书籍信息
             
             book = getInfo(book,page.url());
             book = getBookIntro(book,page.url());
             book = getAuthorDes(book,page.url());
             
             System.out.println(book.getBookName());
             System.out.println(book.getAuthor());
             System.out.println(book.getScore());
             System.out.println(book.getPublish());
             System.out.println(book.getPublishTime());
             System.out.println(book.getPrice());
             System.out.println(book.getISBN());
             System.out.println(book.getDescrip());
             System.out.println(book.getAuthorDes());
             
             a.update(book);
         }
         
	}	
    public static void main(String[] args) throws Exception {
        DemoTypeCrawler crawler = new DemoTypeCrawler("crawler",true);
//        Proxys a = new Proxys();
        /*获取爬虫的http请求器*/

        ProxyPool proxyPool = new ProxyPool();

        proxyPool.add("61.178.238.122",63000);
        proxyPool.add("123.130.11.82",8118);
        proxyPool.add("175.0.192.70",8118);
        proxyPool.add("110.73.34.17",8123);
        proxyPool.add("116.224.190.236",8118);
        proxyPool.add("219.152.17.156",8118);
        proxyPool.add("113.134.95.72",80);
        proxyPool.add("114.102.38.172",8118);
        proxyPool.add("110.73.1.30",8123);
        proxyPool.add("122.72.32.88",80);
        proxyPool.add("14.20.181.230",8118);
        proxyPool.add("49.73.240.42",808);
        proxyPool.add("219.139.179.160",808);
        proxyPool.add("219.145.69.174",8118);
        proxyPool.add("112.114.97.157",8118);
        proxyPool.add("121.31.101.48",8118);
        proxyPool.add("183.66.64.120",3128);
        
        HttpProxy httpProxy = proxyPool.borrow(); // 从 ProxyPool 中获取一个Proxy
        httpProxy.getProxy();
        HttpRequest a =  new HttpRequest("db",httpProxy.getProxy());
		//或者直接new HttpRequest(crawlDatum, proxys.nextRandom());
		a.setProxy(httpProxy.getProxy()); //随机获取一个ip

        crawler.getConf().setExecuteInterval(5000);
        

        crawler.setThreads(100);
        crawler.start(5);
        
        proxyPool.reback(httpProxy, HttpStatus.SC_OK); // 使用完成之后，归还 Proxy,并将请求结果的 http 状态码一起传入

	    proxyPool.allProxyStatus();  // 可以获取 ProxyPool 中所有 Proxy 的当前状态
    }

}