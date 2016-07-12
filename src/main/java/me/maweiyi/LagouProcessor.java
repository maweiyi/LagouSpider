package me.maweiyi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

/**
 * Created by maweiyi on 7/10/16.
 */
public class LagouProcessor implements PageProcessor {

    private OneLevel gOneLevel;

    private List<FourLevel> fourLevel;

    public OneLevel getgOneLevel() {
        return gOneLevel;
    }

    public void setgOneLevel(OneLevel gOneLevel) {
        this.gOneLevel = gOneLevel;
    }

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    public void process(Page page) {



        //将JSON数据转为Java对象并保存到mongodb
       // System.out.println(page.getJson());

        OneLevel oneLevel = JSON.parseObject(String.valueOf(page.getJson()), OneLevel.class);
        this.gOneLevel = oneLevel;

        //System.out.println(String.valueOf(page.getJson()));

        System.out.println("XXXXXXX" + oneLevel.getContent().getPageSize());
        if (oneLevel.getContent().getPositionResult().getTotalCount() / oneLevel.getContent().getPageSize() == 0) {

            this.saveForMongoDB();

        } else {

            this.fourLevel = gOneLevel.getContent().getPositionResult().getResult();
            //Integer getCount = gOneLevel.getContent().getPositionResult().getTotalCount() / gOneLevel.getContent().getPositionResult().getPageSize() + 1;
            for (int i = 1; i <= 499; i++) {
                Spider.create(new JavaProcessor()).addUrl("http://www.lagou.com/jobs/positionAjax.json?city=%E5%85%A8%E5%9B%BD&kd=%E5%89%8D%E7%AB%AF"+ "&pn=" + i).thread(1).run();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /*this.fourLevel = gOneLevel.getContent().getPositionResult().getResult();

        System.out.println("Size: " + this.fourLevel.size());

        for (int i = 0; i < this.fourLevel.size(); i++) {
            System.out.println(this.fourLevel.get(i).getCity());
        }*/









    }


    public void saveForMongoDB() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("lagou");
        MongoCollection<Document> collection = mongoDatabase.getCollection("frontend");

        for (int i = 0; i < this.fourLevel.size(); i++) {
            Document document = new Document("positionName", this.fourLevel.get(i).getCity())
                    .append("workYear", this.fourLevel.get(i).getWorkYear())
                    .append("education", this.fourLevel.get(i).getEducation())
                    .append("salary", this.fourLevel.get(i).getSalary())
                    .append("jobNature", this.fourLevel.get(i).getJobNature());

            List<Document> documents = new ArrayList<Document>();
            documents.add(document);
            collection.insertMany(documents);

        }



    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new LagouProcessor()).addUrl("http://www.lagou.com/jobs/positionAjax.json?city=%E5%85%A8%E5%9B%BD&kd=%E5%89%8D%E7%AB%AF").thread(1).run();
    }
}
