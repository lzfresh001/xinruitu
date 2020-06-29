package com.yx.xinruitu.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileEveryDaySerialNumber extends EveryDaySerialNumber {

    /**
     * 持久化存储的文件
     */    
    private File file = null;
    
    private String treatKey= null;
    
    /**
     * 存储的分隔符
     */
    private final static String FIELD_SEPARATOR = ",";   

    public FileEveryDaySerialNumber(int width, String filename,String key) {
        super(width);
        file = new File(filename);
        treatKey=key;
    }

//    @Override
//    protected int getOrUpdateNumber(Date current, int start) {
//        String date = format(current);
//        int num = start;
//        if(file.exists()) {
//            List<String> list = FileUtil.readList(file);
//            String[] data = list.get(0).split(FIELD_SEPARATOR);
//            if(date.equals(data[0])) {
//                num = Integer.parseInt(data[1]);
//            }
//        }
//        FileUtil.rewrite(file, date + FIELD_SEPARATOR + (num + 1));
//        return num;
//    }

    @Override
    protected int getOrUpdateNumber(Date current, int start) {
        String date = format(current);
        int num = start;
        if(file.exists()) {
            List<String> list = FileUtil.readList(file);
            String[] data = list.get(0).split(FIELD_SEPARATOR);
            num = Integer.parseInt(data[1]);
        }
        FileUtil.rewrite(file, date + FIELD_SEPARATOR + (num + 1));
        return num;
    }

    @Override
    protected int getOrUpdateYearNumber(Date current, int start)  {
    	String date = formatYear(current);
    	int num = start;
    	if(file.exists()) {
    		String val = FileUtil.GetValueByKey(file.getPath(),treatKey);  
    		String[] data = val.split(FIELD_SEPARATOR);
    		if(date.equals(data[0])) {
    			num = Integer.parseInt(data[1]);
    		}
    	}
    	FileUtil.WriteProperties(file.getPath(),treatKey,date + FIELD_SEPARATOR + (num + 1));
    	return num;
    }

	@Override
	protected int getOrUpdateCodeNumber(int start) {
    	int num = start;
        if(file.exists()) {
            List<String> list = FileUtil.readList(file);        
            String data = list.get(0);
            num = Integer.parseInt(data);
        }
        FileUtil.rewrite(file, String.valueOf(num + 1));
        return num;
	}

    /**
     * 获得序列号
     */
    public static String getValue()
    {
        FileEveryDaySerialNumber serialNumber= new FileEveryDaySerialNumber(4,"EveryDaySerialNumber.properties","");
        String code="No"+serialNumber.getSerialNumber();
        return code;
    }

    /**
     * 获得序列号(PNo+年+3位流水)
     */
    public static String getValue3()
    {
        FileEveryDaySerialNumber serialNumber= new FileEveryDaySerialNumber(3,"EveryDaySerialNumber.properties","");
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String code="PNo"+ year +serialNumber.getSerialNumber();
        return code;
    }

    /**
     * 获得序列号(XRT+4位流水号)
     */
    public static String getValue4()
    {
        FileEveryDaySerialNumber serialNumber= new FileEveryDaySerialNumber(4,"Number.properties","");
        String code="XRT"+serialNumber.getSerialNumber();
        return code;
    }
}



