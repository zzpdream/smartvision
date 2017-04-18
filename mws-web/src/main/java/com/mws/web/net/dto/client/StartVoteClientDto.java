package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;

import java.util.List;

/**
 * 主控机开始表决的请求实体
 * <p/>
 * Created by ranfi on 2/28/16.
 */
public class StartVoteClientDto extends BaseDto {

    private static final long serialVersionUID = 6039907540673307069L;

    private String subject;

    private Integer type;

    private String title;

    private String horizontal;

    private String vertical;

    private List<Items> items;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public List<Items> getItems() {
        return items;
    }


    public void setItems(List<Items> items) {
        this.items = items;
    }


    /**
     * 表决议题实体
     *
     * @author ranfi
     */
    public static class Items {
        private Integer id;
        private String title;

        public Items() {

        }

        public Items(Integer id, String title) {
            this.id = id;
            this.title = title;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
