package me.maweiyi;

import com.alibaba.fastjson.JSON;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maweiyi on 7/10/16.
 */
public class JavaProcessor implements PageProcessor {

    private OneLevel gOneLevel;

    private List<FourLevel> fourLevel;

    public List<FourLevel> getFourLevel() {
        return fourLevel;
    }

    public void setFourLevel(List<FourLevel> fourLevel) {
        this.fourLevel = fourLevel;
    }

    public OneLevel getgOneLevel() {
        return gOneLevel;
    }

    public void setgOneLevel(OneLevel gOneLevel) {
        this.gOneLevel = gOneLevel;
    }

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {

        OneLevel oneLevel = JSON.parseObject(String.valueOf(page.getJson()), OneLevel.class);
        this.gOneLevel = oneLevel;
        this.fourLevel = gOneLevel.getContent().getPositionResult().getResult();

        System.out.println("VVVBVBVBVBVBVBVBVBBVBVVBV");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.saveForMongoDB();

    }

    public Site getSite() {
        return site;
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
}
