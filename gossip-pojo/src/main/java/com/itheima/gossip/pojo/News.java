package com.itheima.gossip.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zjl
 * @create 2018-09-29 15:59
 **/
public class News implements Serializable {
    @Field
    private String title; //新闻的标题
    @Field
    private String docurl; // 新闻的url
    @Field
    private Date time ;  //时间
    @Field
    private String  source;
    @Field
    private String content ; //新闻的正文
    @Field
    private String editor; //新闻的编辑
    @Field
    private  String id;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocurl() {
        return docurl;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", docurl='" + docurl + '\'' +
                ", time='" + time + '\'' +
                ", source='" + source + '\'' +
                ", content='" + content + '\'' +
                ", editor='" + editor + '\'' +
                ", id=" + id +
                '}';
    }
}
