package com.lyf.utils;

import com.lyf.dao.domain.User;
import org.junit.Test;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;

import java.sql.Date;
import java.util.Random;

public class MockUserUtil {



    String firstName = "李、王、张、刘、陈、" +
            "杨、赵、黄、周、吴、徐、孙、胡、朱、高" +
            "、林、何、郭、姜、吕、丘、丁、郑、许、封、沙、郝、" +
            "谢、纪、崔、高、章、贺、柯、卢、薄、赖、连、饶、厉、聂、" +
            "王、解、陆、岳、谷、谭、覃、焦、芮、柴、浦、申、富、向、宇、卞" +
            "、佘、艾、安、彦、晏、年、齐、文、骆、国、盖、蒲、尚、楂、易、伍、栾、庆、甫、闾、" +
            "储、左、右、北、癸、壬、旦、门、井、牙、龚、洛、角、蛇、掌、亘、麻、竹" +
            "、棠、析、桓、檀、茶、莱、苑、画、弦、旗、禚、指、琅、郦、省、汲、溪、景、剧" +
            "、阚、青、钭、铎、姁、组、郸、邮、隰、裔、我、氏、是、其、及、并、即、盍、望、朋" +
            "、士、营、坴、戎、充、捷、强、礼、孝、懿、威、奕、富、明、绍、灵、畅、平、襄";

    String str = "俊、威、英、健、壮、焕、挺、秀、伟" +
            "达、耀、兴、荣、华、旺、盈、丰、余、昌、盛" +
            "、武、雄、巍、松、柏、山、石、婵、娟、姣、妯、" +
            "婷、姿、媚、婉、妩、倩、兰乎、安、静、顺、通、坦、泰、然、宁、定、和、" +
            "颖、灵、睿、锐、哲、慧、敦、迪、明、晓、" +
            "蔼、仁、容、德、轩、贤、良、伦、正、清、义、诚、直、显、悉、晰、维、学、思、悟、析、文、书、勤、" +
            "康" + "\n" ;


   // @Test
    Integer mockUserId(){
        Random random = new Random();
        //random.setSeed(seed); // 不设置seed？？
        return  Math.abs(random.nextInt());
    }

    String mockUserName(){
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        for(int i =0;i<Math.max(firstName.length(),str.length());i++)
        {
            if(i<firstName.length()&&firstName.charAt(i)!='、'){
                stringBuffer1.append(firstName.charAt(i));
            }
            if(i<str.length()&&str.charAt(i)!='、'){
                stringBuffer2.append(str.charAt(i));
            }
        }

        for(int i =0;i<10;i++) {
            Random random = new Random();
            int count = random.nextInt(20);
            int count2 = random.nextInt(20);
            if (i % 2 == 0) {
                System.out.println(stringBuffer1.substring(2 * count, 2 * count + 1) + stringBuffer2.substring(2 * count2, 2 * count2 + 1));
                String name = stringBuffer1.substring(2 * count, 2 * count + 1) + stringBuffer2.substring(2 * count2, 2 * count2 + 1);
                return name;
            } else {
                String name =stringBuffer1.substring(2 * count, 2 * count + 1) + stringBuffer2.substring(2 * count2, 2 * count2 + 1) + stringBuffer2.substring(2 * count, 2 * count + 1);

                System.out.println(stringBuffer1.substring(2 * count, 2 * count + 1) + stringBuffer2.substring(2 * count2, 2 * count2 + 1) + stringBuffer2.substring(2 * count, 2 * count + 1));
                return name;
            }
        }
        return null;
    }

    String mockPassword(){

        String[] str ={"A","B","c","d","d","e","w","r","d"};
        Random random = new Random();

        return random.nextInt(1000)+str[random.nextInt(8)]+random.nextInt(20);
    }

    Integer mockAge(String job){

        Random random = new Random();

        if(job.equals("学生")){   // 可考虑正则匹配
            return 5+random.nextInt(20);
        }else if(job.equals("教师")){
            return 20+random.nextInt(30);
        }else if(job.equals("工程师")){
            return 22+random.nextInt(30);
        }else if(job.equals("销售员")){
            return 18+random.nextInt(30);
        }else if(job.equals("医生")){
            return 25+random.nextInt(30);
        }else{ // 自由
            return random.nextInt(50);
        }
    }

    Date mockBirthDay(Integer age){

        Integer year = 2020-age;

        Date date = Date.valueOf(year+"-10-1");

        return date;
        //return ;
    }

    String mockJob(){ // 和年龄以及出生日期放在一块

        String jobs[]= {
         "学生","教师","工程师","销售员","医生","自由"
        };

        Random random = new Random();

        return jobs[random.nextInt(5)];
    }

    Float mockHeight(){
        Random random = new Random();
        return 160+random.nextInt(30)+random.nextInt(1)+0F;

    }
    Float mockWeight(){
        Random random = new Random();
        return 100+random.nextInt(70)+random.nextInt(1)+0F;
    }


    public User mockUser(){

        MockUserUtil mockUserUtil = new MockUserUtil();

        Integer userId = mockUserUtil.mockUserId();
        String userName = mockUserUtil.mockUserName();
        String  pwd = mockUserUtil.mockPassword();
        String job = mockUserUtil.mockJob();
        Integer age = mockUserUtil.mockAge(job);
        Float height = mockUserUtil.mockHeight();
        Float weight =  mockUserUtil.mockWeight();
        Date birthday = mockUserUtil.mockBirthDay(age);

        Random random = new Random();
        return new User(null,userName,pwd,userName,
                age,job,height,weight,birthday,"51302"+random.nextInt(9)+birthday.getYear()+birthday.getMonth()+birthday.getDay()+"321",null);
    }





    public static void main(String[]args){




        String firstName = "李、王、张、刘、陈、" +
                "杨、赵、黄、周、吴、徐、孙、胡、朱、高" +
                "、林、何、郭、姜、吕、丘、丁、郑、许、封、沙、郝、" +
                "谢、纪、崔、高、章、贺、柯、卢、薄、赖、连、饶、厉、聂、" +
                "王、解、陆、岳、谷、谭、覃、焦、芮、柴、浦、申、富、向、宇、卞" +
                "、佘、艾、安、彦、晏、年、齐、文、骆、国、盖、蒲、尚、楂、易、伍、栾、庆、甫、闾、" +
                "储、左、右、北、癸、壬、旦、门、井、牙、龚、洛、角、蛇、掌、亘、麻、竹" +
                "、棠、析、桓、檀、茶、莱、苑、画、弦、旗、禚、指、琅、郦、省、汲、溪、景、剧" +
                "、阚、青、钭、铎、姁、组、郸、邮、隰、裔、我、氏、是、其、及、并、即、盍、望、朋" +
                "、士、营、坴、戎、充、捷、强、礼、孝、懿、威、奕、富、明、绍、灵、畅、平、襄";

        String str = "俊、威、英、健、壮、焕、挺、秀、伟" +
                "达、耀、兴、荣、华、旺、盈、丰、余、昌、盛" +
                "、武、雄、巍、松、柏、山、石、婵、娟、姣、妯、" +
                "婷、姿、媚、婉、妩、倩、兰乎、安、静、顺、通、坦、泰、然、宁、定、和、" +
                "颖、灵、睿、锐、哲、慧、敦、迪、明、晓、" +
                "蔼、仁、容、德、轩、贤、良、伦、正、清、义、诚、直、显、悉、晰、维、学、思、悟、析、文、书、勤、" +
                "康" + "\n" ;


        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();

        for(int i =0;i<Math.max(firstName.length(),str.length());i++)
        {
            if(i<firstName.length()&&firstName.charAt(i)!='、'){
                stringBuffer1.append(firstName.charAt(i));
            }
            if(i<str.length()&&str.charAt(i)!='、'){
                stringBuffer2.append(str.charAt(i));
            }
        }

        System.out.println("string1:"+stringBuffer1+"string2"+stringBuffer2);


        for(int i =0;i<10;i++){
            Random random =  new Random();
            int count = random.nextInt(20);
            int count2 = random.nextInt(20);
            if(i%2==0){
                System.out.println(stringBuffer1.substring(2*count,2*count+1)+stringBuffer2.substring(2*count2,2*count2+1));

               // System.out.println(stringBuffer1.charAt(count)+stringBuffer2.charAt(count2));

            }else {
              System.out.println(stringBuffer1.substring(2*count,2*count+1)+stringBuffer2.substring(2*count2,2*count2+1)+stringBuffer2.substring(2*count,2*count+1));
               // System.out.println(stringBuffer1.charAt(count)+stringBuffer2.charAt(count2)+stringBuffer2.charAt(count));

            }




        }




    }










}
