package com.example.timetable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据类，加载课程数据
 * @author zf
 *
 */
public class SubjectRepertory {

    public static List<Subject> loadDefaultSubjects(){
        //json转义
        String json="[[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"1周上\", 1, 1, 2, \"\", \"计算机综合楼106\", \"\"]," +
                "[\"2017-2018学年秋\", \"\", \"\", \"hahaha\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"2周上\", 1, 1, 4, \"\", \"计算机综合楼106\", \"\"],"+
                " [\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1周\", 1, 3, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 1, 5, 2, \"\", \"3号教学楼3208\", \"\"]," +
                " [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 1, 9, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 2, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 2, 3, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 2, 9, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-17周上\", 3, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-12周\", 3, 3, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 3, 5, 2, \"\", \"3号教学楼3208\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-4周上\", 3, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 3, 9, 2, \"\", \"计算机综合楼202\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 4, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\", 4, 3, 2, \"\", \"计算机综合楼102\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 4, 5, 2, \"\", \"计算机综合楼202\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 4, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 4, 9, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-18周上\", 5, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 5, 3, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 5, 5, 2, \"\", \"3号教学楼3208\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 5, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\", 5, 9, 2, \"\", \"计算机综合楼102\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"形势与政策-4\", \"\", \"\", \"\", \"\", \"孔祥增\", \"\", \"\", \"9周上\", 7, 5, 4, \"\", \"3号教学楼3311\", \"\"]]";
        return parse(json);
    }

    public static List<Subject> loadDefaultSubjects1(){
        //json转义
        String json= " [\n" +
                "    {\n" +
                "      \"courseId\": 4,\n" +
                "      \"userId\": 1,\n" +
                "      \"termId\": 1,\n" +
                "      \"courseName\": \"软件需求分析\",\n" +
                "      \"startWeek\": 1,\n" +
                "      \"endWeek\": 18,\n" +
                "      \"courseTimeId\": 1,\n" +
                "      \"weekday\": 2,\n" +
                "      \"classroom\": \"6D-403\",\n" +
                "      \"startLesson\": \"1\",\n" +
                "      \"endLesson\": \"2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"courseId\": 3,\n" +
                "      \"userId\": 1,\n" +
                "      \"termId\": 1,\n" +
                "      \"courseName\": \"javaWeb\",\n" +
                "      \"startWeek\": 1,\n" +
                "      \"endWeek\": 16,\n" +
                "      \"courseTimeId\": 2,\n" +
                "      \"weekday\": 1,\n" +
                "      \"classroom\": \"6D-104\",\n" +
                "      \"startLesson\": \"5\",\n" +
                "      \"endLesson\": \"7\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"courseId\": 5,\n" +
                "      \"userId\": 1,\n" +
                "      \"termId\": 1,\n" +
                "      \"courseName\": \"测试框\",\n" +
                "      \"startWeek\": 1,\n" +
                "      \"endWeek\": 16,\n" +
                "      \"courseTimeId\": 5,\n" +
                "      \"weekday\": 2,\n" +
                "      \"classroom\": \"6D-104\",\n" +
                "      \"startLesson\": \"3\",\n" +
                "      \"endLesson\": \"4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"courseId\": 4,\n" +
                "      \"userId\": 1,\n" +
                "      \"termId\": 1,\n" +
                "      \"courseName\": \"Android开发\",\n" +
                "      \"startWeek\": 1,\n" +
                "      \"endWeek\": 16,\n" +
                "      \"courseTimeId\": 4,\n" +
                "      \"weekday\": 2,\n" +
                "      \"classroom\": \"6D-104\",\n" +
                "      \"startLesson\": \"5\",\n" +
                "      \"endLesson\": \"7\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"courseId\": 4,\n" +
                "      \"userId\": 1,\n" +
                "      \"termId\": 1,\n" +
                "      \"courseName\": \"软件需求分析\",\n" +
                "      \"startWeek\": 1,\n" +
                "      \"endWeek\": 18,\n" +
                "      \"courseTimeId\": 2,\n" +
                "      \"weekday\": 4,\n" +
                "      \"classroom\": \"6D-403\",\n" +
                "      \"startLesson\": \"1\",\n" +
                "      \"endLesson\": \"2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"courseId\": 4,\n" +
                "      \"userId\": 1,\n" +
                "      \"termId\": 1,\n" +
                "      \"courseName\": \"测试框\",\n" +
                "      \"startWeek\": 1,\n" +
                "      \"endWeek\": 18,\n" +
                "      \"courseTimeId\": 6,\n" +
                "      \"weekday\": 5,\n" +
                "      \"classroom\": \"6D-403\",\n" +
                "      \"startLesson\": \"9\",\n" +
                "      \"endLesson\": \"11\"\n" +
                "    }\n" +
                "  ]";
        return parse(json);
    }

    public static List<Subject> loadDefaultSubjects2() {
        //json转义
        String json = "[[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"1,2,3,4,5\", 1, 1, 4, \"\", \"计算机综合楼106\", \"\"]," +
                "[\"2017-2018学年秋\", \"\", \"\", \"高等数学\", \"\", \"\", \"\", \"\", \"壮飞\", \"\", \"\", \"1,2,3,7,8\", 1, 2, 2, \"\", \"计算机综合楼106\", \"\"]," +
                "[\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1,3,5,9,10\", 1, 5, 2, \"\", \"计算机综合楼205\", \"\"]]";
        return parse(json);
    }

    /**
     * 对json字符串进行解析
     * @param parseString
     * @return
     */
    public static List<Subject> parse(String parseString) {
        List<Subject> courses = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(parseString);
            JSONObject jsonObject;
            for(int i=0;i<array.length();i++){
                jsonObject = array.getJSONObject(i);
                Integer courseId = jsonObject.getInt("courseId");
                Integer userId = jsonObject.getInt("userId");
                Integer termId = jsonObject.getInt("termId");
                String courseName = jsonObject.getString("courseName");
                Integer startWeek = jsonObject.getInt("startWeek");
                Integer endWeek = jsonObject.getInt("endWeek");
                Integer courseTiemId = jsonObject.getInt("courseTimeId");
                Integer weekday = jsonObject.getInt("weekday");
                String classroom = jsonObject.getString("classroom");
                Integer startLesson = jsonObject.getInt("startLesson");
                Integer endLesson = jsonObject.getInt("endLesson");

                courses.add(new Subject(courseId,userId, termId, courseName, startWeek, endWeek, courseTiemId, weekday, classroom,startLesson,endLesson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static List<Integer> getWeekList(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        if(weeksString==null||weeksString.length()==0) return weekList;

        weeksString=weeksString.replaceAll("[^\\d\\-\\,]", "");
        if(weeksString.indexOf(",")!=-1){
            String[] arr=weeksString.split(",");
            for(int i=0;i<arr.length;i++){
                weekList.addAll(getWeekList2(arr[i]));
            }
        }else{
            weekList.addAll(getWeekList2(weeksString));
        }
        return weekList;
    }

    public static List<Integer> getWeekList2(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        int first=-1,end=-1,index=-1;
        if((index=weeksString.indexOf("-"))!=-1){
            first=Integer.parseInt(weeksString.substring(0,index));
            end=Integer.parseInt(weeksString.substring(index+1));
        }else{
            first=Integer.parseInt(weeksString);
            end=first;
        }
        for(int i=first;i<=end;i++)
            weekList.add(i);
        return weekList;
    }
}
