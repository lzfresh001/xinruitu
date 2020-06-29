package com.yx.xinruitu.entity;

/**
 * ClassName: AccountLog(日志表实体类)
 * Package: com.yx.xinruitu.entity
 * Decription:
 * <p>
 * Date: 2019/12/11 15:53
 * Author: lzfresh
 */
public class AccountLog {

    private long id;

    private long user_id;

    private long prj_id;

    private long ac_id;

    private String prj_name;

    private String prj_name_2nd;

    private String drawing_no;

    private String ac_name;

    private String part_no;

    private String process_type;

    private int count;

    private double thickness;

    private double height;

    private double width;

    private double single_weight;

    private int single_hole;

    private double single_length;

    private long total;

    private Integer coefficient;

    private int status;

    private String reply;

    private String add_user;

    private String add_time;

    private String accept_user;

    private String accept_time;

    private String update_user;

    private String update_time;

    /** 实动孔数; */
    private Integer actual_hole ;

    /** 计量孔数; */
    private Integer measure_hole ;

    /** 工序4中所需属性:重量*/
    private double weight ;

    /** 工序5中所需属性:实动长度*/
    private double actual_length ;

    /** 工序4中所需属性:计量长度*/
    private double measure_length ;

    private Integer sum;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getPrj_id() {
        return prj_id;
    }

    public void setPrj_id(long prj_id) {
        this.prj_id = prj_id;
    }

    public long getAc_id() {
        return ac_id;
    }

    public void setAc_id(long ac_id) {
        this.ac_id = ac_id;
    }

    public String getPrj_name() {
        return prj_name;
    }

    public void setPrj_name(String prj_name) {
        this.prj_name = prj_name;
    }

    public String getPrj_name_2nd() {
        return prj_name_2nd;
    }

    public void setPrj_name_2nd(String prj_name_2nd) {
        this.prj_name_2nd = prj_name_2nd;
    }

    public String getDrawing_no() {
        return drawing_no;
    }

    public void setDrawing_no(String drawing_no) {
        this.drawing_no = drawing_no;
    }

    public String getAc_name() {
        return ac_name;
    }

    public void setAc_name(String ac_name) {
        this.ac_name = ac_name;
    }

    public String getPart_no() {
        return part_no;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }

    public String getProcess_type() {
        return process_type;
    }

    public void setProcess_type(String process_type) {
        this.process_type = process_type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getSingle_weight() {
        return single_weight;
    }

    public void setSingle_weight(double single_weight) {
        this.single_weight = single_weight;
    }

    public int getSingle_hole() {
        return single_hole;
    }

    public void setSingle_hole(int single_hole) {
        this.single_hole = single_hole;
    }

    public double getSingle_length() {
        return single_length;
    }

    public void setSingle_length(double single_length) {
        this.single_length = single_length;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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

    public String getAccept_user() {
        return accept_user;
    }

    public void setAccept_user(String accept_user) {
        this.accept_user = accept_user;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Integer getActual_hole() {
        return actual_hole;
    }

    public void setActual_hole(Integer actual_hole) {
        this.actual_hole = actual_hole;
    }

    public Integer getMeasure_hole() {
        return measure_hole;
    }

    public void setMeasure_hole(Integer measure_hole) {
        this.measure_hole = measure_hole;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getActual_length() {
        return actual_length;
    }

    public void setActual_length(double actual_length) {
        this.actual_length = actual_length;
    }

    public double getMeasure_length() {
        return measure_length;
    }

    public void setMeasure_length(double measure_length) {
        this.measure_length = measure_length;
    }
}
