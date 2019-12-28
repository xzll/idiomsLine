package cn.yj.idiomsline.util;

import cn.yj.idiomsline.entity.IdiomEntity;
import cn.yj.idiomsline.repository.IdiomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *    接下来怎么开始，系统自己随机取一个成语开始，还是用户输入后开始，先不排除非四字成语
 *    （sql语句的char_length()得到字符串的长度，一个中文一个字符长度；length()是一个utf8中文3个字符长度，gbk中文2个字符长度；）
 *    把成语放进set，取尾字在表中查找，结果集中取一个出来比较是否前面已经出现过（或都列出来），继续循环。
 */
@Component
public class IdiomsPlay {
    @Autowired
    private IdiomRepository idiomRepo;
    /**
     * 只接相同的字，不接同音
     * @param startWord 提供开始的字
     */
    public void startLine(String startWord){
        Set<String> resultSet = new HashSet<String>();
        if(startWord.length()!=1) {
            System.out.println("提供一个汉字即可");
        }else {

            while (true){
                List<IdiomEntity> result = idiomRepo.findByWordS(startWord);//查找
                if(result.isEmpty()) {
                    System.out.println("不存在以"+startWord+"开头的成语");
                    break;
                }else {
                    String idiom = null;
                    int i = 0;
                    while ( i < result.size() ) {
                        idiom = result.get(i).getValue();
                        if( !resultSet.contains(idiom) ) {//从结果集中找出未使用过的成语
                            resultSet.add(idiom);       //添加到set集合中
                            startWord = idiom.substring(idiom.length() - 1);//重设startWord
                            System.out.print(idiom+"--");
                            break;
                        } else {
                            i++;
                        }
                    }
                    if(i>=result.size()) {//结果集中没有未使用过的成语，循环结束
                        break;
                    }

                }
            }
            System.out.println("结束");

        }
    }

//    树的深度遍历，得出所有可能的结果。得结果集遍历，递归，set要作为参数传下去
//    不用添加根节点。要添加的话，值是最开始的startWord

    /**
     *  一条路径的结束条件应该是（1）到叶结点（不存在以*开头的成语）
     *                      或者（2）子结点全被使用过（以*开头的成语都被用过了）
     *
     * 递归结束条件（1）result为空（2）for循环结束
     * 递归实现
     * @param startWord
     * @param list
     * @param set
     */
    public void DFSAllLines(String startWord,LinkedList<String> list,Set<String> set){

        List<IdiomEntity> result = idiomRepo.findByWordS(startWord);//取出子结点，即可接龙的成语
        if(!result.isEmpty()){

            boolean flag = true;//是否子结点都已被当前路径使用过

            for (IdiomEntity entity: result) {  //遍历成语（遍历子结点）
                String idiom = entity.getValue();
                if(!set.contains(idiom)){   //取未使用过的成语（过滤子结点）

                    flag = false;//有未被当前路径使用过的子结点

                    set.add(idiom);     //添加进set
                    list.add(idiom);    //添加进路径（接龙）

                    DFSAllLines(idiom.substring(idiom.length()-1),list,set);    //递归，深度
//---------------- 什么时候需要输出？（1）result为空时       --------------------------------------------
//                                   （2）for结束flag为true
//                 注意递归结束条件
//                    System.out.println(list.toString()); //递归完，到叶结点（Null）输出路径
//                    //把路径中当前子结点移除出去，为下一个子结点做准备。
//                    set.remove(idiom);
//                    list.removeLast();
//---------上面的操作不应该放在这里，并不能实现（1），因为不管result是否为空，返回上一层时都会执行--------
// ---------不对，其实并不完全是错的，递归结束后确实需要移除当前子结点，为下一个结点做准备。需不需要输出就看情况了。
//          输出和移除是分开的。在既不是empty又不是子结点都被用过的情况下，也是需要移除当前结点的，只是不用输出。
                    //把路径中当前子结点移除出去，为下一个子结点做准备。
                    set.remove(idiom);
                    list.removeLast();
//  ------------stack over flow...深度到4000...------------------
                }

            }

            if(flag){//若可接龙成语都当前路径被用过
                System.out.println(list.toString());//输出路径
//                //移除父结点（当前路径最后一个成语）
//                String last = list.removeLast();
//                set.remove(last);
            }


        } else {
            System.out.println(list.toString()); //到叶结点（Null）输出路径
//            //移除父结点（当前路径最后一个成语）
//            String last = list.removeLast();
//            set.remove(last);
        }

    }
//
//    /**
//     * FAULT 逻辑出错
//     * 非递归实现
//     * @param startWord
//     */
//    public void DFSAllLinesStack(String startWord){
//        LinkedList<String> idioms = getIdiomEntitiesValues(idiomRepo.findByWordS(startWord));
//        if(!idioms.isEmpty()){
//            LinkedList<String> line = new LinkedList<>();//路径
//            LinkedList<String> list = idioms;   //指针指向子结点集合
//            Set<String> set = new HashSet<String>();    //set
//            LinkedList<LinkedList<String>> stack = new LinkedList<>();  //stack记录子结点集合，以便遍历子结点和返回父结点
//            stack.push(idioms);     //先初始化
//            while(!stack.isEmpty()) {   //stack为空时深度遍历结束
//
//                list = stack.peek();    //现在的list不会为空
//                String temp = list.removeFirst();   //取出子结点
//                //若取出子结点后集合空了，说明子结点都访问过了，移出栈
//                if(list.isEmpty()){
//                    stack.pop();
//                }
//                if(!set.contains(temp)){    //若该成语当前路径未使用过
//                    set.add(temp);  //添加进set
//                    line.add(temp); //添加进路径
//
//                    LinkedList tlist = getIdiomEntitiesValues(idiomRepo.findByWordS(temp.substring(temp.length()-1)));  //获取该成语的子结点集合（可接龙的成语集合）
//                    if(tlist.isEmpty()){    //若为空，说明路径到头了，可以输出路径了
//                        System.out.println(line.toString());
//                        //移除
//                        set.remove(temp);
//                        line.removeLast();
//
//                    } else {    //不为空说明还得往下走
//                        stack.push(tlist);  //添加它的子结点集合
//                    }
//
//
//
//                }
//
//            }
//        }
////---------内存使用直线上升，强制关闭-------------
//
//    }
    public LinkedList<String> getIdiomEntitiesValues(List<IdiomEntity> entities) {
        LinkedList<String> list = new LinkedList<String>();
        for (IdiomEntity entity : entities) {
            list.add(entity.getValue());
        }
        return list;
    }

    /**
     * 非递归实现\n
     * 开头文字不会重复
     * @param startWord
     */
    public void DFSAllLinesStackNotRestart(String startWord){
        LinkedList<String> idioms = getIdiomEntitiesValues(idiomRepo.findByWordS(startWord));
        if(!idioms.isEmpty()){
            LinkedList<String> line = new LinkedList<>();//路径
            LinkedList<String> list = idioms;   //指针指向子结点集合
            Set<String> set = new HashSet<String>();    //set
            LinkedList<LinkedList<String>> stack = new LinkedList<>();  //stack记录子结点集合，以便遍历子结点和返回父结点
            stack.push(idioms);     //先初始化
            while(!stack.isEmpty()) {   //stack为空时深度遍历结束
                 list = stack.peek();
                 if(list.isEmpty()){    //若list为空说明当前结点的子结点都遍历完了（当前成语的接龙成语都遍历完了
                     if(!line.isEmpty()){   //从路径中移除当前结点。根节点未放进line和set中所以需要判空
                         set.remove(line.removeLast().substring(0,1));
                     }
                     stack.pop();   //出栈
                     continue;      //继续下一个循环（回父结点）
                 } else {
                     String idiom = list.removeFirst();//取子结点
                     String idiomS = idiom.substring(0,1);
                     String idiomE = idiom.substring(idiom.length()-1);
                     line.add(idiom);
                     set.add(idiomS);
//                     if(set.contains(idiomS)){//开头字之前用过，有下面的判断不会有这种情况了
//                         continue;
//                     }
                     if(set.contains(idiomE)){//尾字之前用过，不会再用它开头，所以路径完成了，输出，继续下一个循环（回父结点
                         System.out.println(line);
                         line.removeLast();
                         set.remove(idiomS);
                     } else {
                         LinkedList<String> tlist = getIdiomEntitiesValues(idiomRepo.findByWordS(idiomE));//子结点集合
                         if(tlist.isEmpty()){ //为空路径到头，输出
                             System.out.println(line);
                             line.removeLast();
                             set.remove(idiomS);
                         } else {   //不为空压栈
                             stack.push(tlist);
                         }
                     }

                 }

            }
//----- 跑半个钟，没跑完，估计要好几天？并发试下？ ----
//----- 深度到800，stack内容也才250kB。不重复wordS的话深度最多到3040，每个list平均到10，stack占用内存不大 -----
//--- 为什么mysql cpu占用那么大 ---




        }

    }
}
