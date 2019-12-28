package cn.yj.idiomsline;

import cn.yj.idiomsline.util.IdiomsPlay;
import cn.yj.idiomsline.util.TxtToTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.LinkedList;

@SpringBootTest
class IdiomslineApplicationTests {
    @Autowired
    private TxtToTable txtToTable;
    @Autowired
    private IdiomsPlay idiomsPlay;
    @Test
    void contextLoads() {
    }
    @Test
    void testReplaceAll() {
        String data = "觉\n" +"悟";
        //\S表示非\n字符
        // ()括起来的部分替换时可用“$数字”表示，可以实现部分替换。
        //本来以为正则不能部分替换一度很绝望，但其实是可以的，果然不负它的强大。
        String result = data.replaceAll("\\n(\\S)","$1");
        System.out.println(result);
    }
    @Test
    void testSplit() {
        String data = " 重厚寡言  拼音：zhòng　hòu　guǎ　yán释义：持重敦厚，不爱多说话。出处：《金史·襄传》襄重厚寡言，务以镇静守法。”示例：无\n";
        String[] result = data.split("拼音：|释义：|出处：|示例：");
        for (String r: result) {
            System.out.println("a"+r);

        }
    }
    @Test
    void testSplit1() {
        String data = " 重厚寡言  拼音：zhòng　hòu　guǎ　yán释义：持重敦厚，不爱多说话。出处：《金史·襄传》襄重厚寡言，务以镇静守法。”示例：无\n";
        String[] temp = data.split("拼音：|释义：|出处：|示例：");
        String pinyin = temp[1].trim();
        String[] sp = pinyin.split("\\s");
        String pinyinS = pinyin.substring(0,pinyin.indexOf(" "));//" "和"　"是不一样的...天啊。全角空格匹配\p{Zs}
    }
    @Test
    void testUtiltxtToTable(){
//        直接new TxtToTable会使注解失效
//        new TxtToTable().process("G:\\小练习\\成语接龙\\idioms.txt",idiomRepo);
//        txtToTable.process("G:\\小练习\\成语接龙\\idioms.txt");
        txtToTable.process("G:\\小练习\\成语接龙\\补充.txt");
    }
    @Test
    void testUtilPlayStart(){
//        idiomsPlay.startLine("开");
//        idiomsPlay.DFSAllLines("开",new LinkedList<String>(),new HashSet<String>());
//        idiomsPlay.DFSAllLinesStack("开");
        idiomsPlay.DFSAllLinesStackNotRestart("烟");
    }


}
