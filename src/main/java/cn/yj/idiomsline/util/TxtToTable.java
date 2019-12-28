package cn.yj.idiomsline.util;

import cn.yj.idiomsline.entity.IdiomEntity;
import cn.yj.idiomsline.repository.IdiomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 把成语txt文件内容输入到数据库表中
 * （先用编辑器把多余信息去掉了）
 */
@Component//以后再把idiomRepo提出来。好像提出来也没有还是得注入
public class TxtToTable {
    @Autowired
    private IdiomRepository idiomRepo;
//   注入失败。。？是因为调用process时是自己去new的TxtToTable对象，所以spring无法帮你注入idiomRepo。
//    在test中不要new TxtToTable,直接注入就搞定了

    @Transactional(rollbackFor = Exception.class)
    public void process(String  pathName){
//-------------------------------------------------------
        //处理文本
        //若下一行不是空行则内容接到上一行中
        //可以都读出来后，正则匹配修改完后写到新文件中
        //或者直接在文件上修改
//        File file = new File(fileName);
//        InputStream in = null;
//        StringBuffer sb = new StringBuffer();
// ----------已使用能用正则的编辑器大概处理好文本---------

        //以行为单位读取文件内容
        StringBuffer sb = new StringBuffer();
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {//确保结束后关闭它们
            while((line = reader.readLine()) != null) {
                if(!line.isEmpty()){
                    String[] temp = line.split("拼音：|释义：|出处：|示例：");
                    if(temp.length==5){
                        String value = temp[0].trim();
                        if(idiomRepo.findByValue(value) == null){
                            String wordS = value.substring(0,1);//substring取beginIndex到endIndex-1
                            String wordE = value.substring(value.length()-1,value.length());
                            String pinyin = temp[1].trim();
                            String pinyinS = pinyin.substring(0,pinyin.indexOf(" "));
                            String pinyinE = pinyin.substring(pinyin.lastIndexOf(" ")+1);
                            String para = temp[2].trim();
                            String provenance = temp[3].trim();
                            String example = temp[4].trim();

                            IdiomEntity idiomEntity = new IdiomEntity();
                            idiomEntity.setPinyin(pinyin);
                            idiomEntity.setParaphrase(para);
                            idiomEntity.setPinyinE(pinyinE);
                            idiomEntity.setPinyinS(pinyinS);
                            idiomEntity.setProvenance(provenance);
                            idiomEntity.setValue(value);
                            idiomEntity.setWordE(wordE);
                            idiomEntity.setWordS(wordS);
                            idiomEntity.setExample(example);

                            idiomRepo.save(idiomEntity);

                        }

                    }else {
//                       (拼音：)[^\r\n]+(拼音：)
                        System.out.println(line);
                    }
                }

            }
            System.out.println("文件读取完成");
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在！");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
            System.out.println(line);
            e.printStackTrace();
        }
//--------录入用了15分钟，共30080条记录--------

//-----------直接用编辑器完成，或用字符串的replaceAll代替，完全没必要用下面的步骤----------
//        //正则表达式
//        String regex = "\\n\\s";
//        //编译正则字符串
//        Pattern p = Pattern.compile(regex);
//        //匹配
//        Matcher matcher = p.matcher(sb.toString());
//------------------------------------------------------------------------------------------

    }
//    public static void transfer(){
//        //读取文件
//        //读每行进行分割
//        //正则分割
//    }


}
