package com.yx.xinruitu.entity;

/**
 * ClassName: Account(台账表实体类)
 * Package: com.yx.xinruitu.entity
 * Decription:
 * <p>
 * Date: 2019/12/10 14:10
 * Author: lzfresh
 */
public class Account {

    private long ac_id;

    private long prj_id;

    private String drawing_no;

    //private String ac_name;

    private String part_no;

    private int thickness;

    private int height;

    private int width;

    private int count;

    private String add_user;

    private String add_time;

    public long getAc_id() {
        return ac_id;
    }

    public void setAc_id(long ac_id) {
        this.ac_id = ac_id;
    }

    public long getPrj_id() {
        return prj_id;
    }

    public void setPrj_id(long prj_id) {
        this.prj_id = prj_id;
    }

    public String getDrawing_no() {
        return drawing_no;
    }

    public void setDrawing_no(String drawing_no) {
        this.drawing_no = drawing_no;
    }

//    public String getAc_name() {
//        return ac_name;
//    }
//
//    public void setAc_name(String ac_name) {
//        this.ac_name = ac_name;
//    }

    public String getPart_no() {
        return part_no;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAdd_user() {
        return add_user;
    }

    public void setAdd_user(String add_user) {
        this.add_user = add_user;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
