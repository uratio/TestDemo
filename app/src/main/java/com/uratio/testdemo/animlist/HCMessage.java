package com.uratio.testdemo.animlist;

/**
 * @author lang
 * @data 2021/3/19
 */
public class HCMessage {
    private String content;
    private int type;
    private boolean isCanEdit;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCanEdit() {
        return isCanEdit;
    }

    public void setCanEdit(boolean canEdit) {
        isCanEdit = canEdit;
    }
}
