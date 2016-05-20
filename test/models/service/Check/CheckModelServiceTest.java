package models.service.Check;

import com.avaje.ebean.Ebean;
import apps.FakeApp;
import models.entity.Check;
import org.junit.Test;
import java.util.List;

import static play.libs.F.*;
import static org.fest.assertions.Assertions.assertThat;

import utils.OptionUtil;


public class CheckModelServiceTest extends FakeApp {

    // 正常系（Some）：1件のレコードから1つ取り出す
    @Test
    public void testFindByIdTo1ReturnSome() throws Exception {
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
        Option<Check> model = new CheckModelService().findById(1L);
        assertThat(model.getClass()).isEqualTo(Some.class);
        assertThat(model.get().getId()).isEqualTo(1L);
        assertThat(model.get().getName()).isEqualTo("test_t");
        assertThat(model.get().getResult()).isEqualTo("test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。");
    }

    // 正常系（None）：1件のレコードから該当しないIdのものを取り出す
    @Test
    public void test2FindByIdTo1ReturnSome() throws Exception {
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
        Option<Check> model = new CheckModelService().findById(2L);
        assertThat(model.getClass()).isEqualTo(None.class);
    }    

    // 異常系（None）：1件のレコードから検索のキーとしてnullを渡す
    @Test
    public void test3FindByIdTo1ReturnSome() throws Exception {
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
        Option<Check> model = new CheckModelService().findById(null);
        assertThat(model.getClass()).isEqualTo(None.class);
    }

    // OKケース
    // Checkモデルのインスタンスを作成し、データベースに保存する（Option型, ID確認）
    @Test
    public void testSaveByIdTo1ReturnSome() throws Exception {
        Check check = new Check(1L);
        Option<Check> model = new CheckModelService().save(check);
        System.out.println("id確認："+model.get().id);
        assertThat(model.getClass()).isEqualTo(Some.class);
    }    

    // NGケース
    // Checkモデルのインスタンスがnullだが、データベースに保存する
    @Test
    public void test2SaveByIdTo1ReturnSome() throws Exception {
        Check check = null;
        Option<Check> model = new CheckModelService().save(null);
        assertThat(model.getClass()).isEqualTo(None.class);
    }


   // ページ1に5件存在し、Idが1と5が存在する（Some型、件数、ID=1L, 5Lを確認）
    @Test
    public void testFindWithPage1Contains1And5() throws Exception {

        //DBにデータ保存
        prepareSaveData();

        //ページ1から取り出し
        Option<List<Check>> listOpt =  new CheckModelService().findWithPage(1);
        List<Check> list = listOpt.get();

        String judge = null;
        System.out.println("list->list.size:"+list.size());
        for(Check check: list){
            System.out.println("list->check.id:"+check.id);
            for(Check check2: list){
                if(check.id == 1L && check2.id == 5L)
                    judge = "1も5もあります!";
            }
        }
        assertThat(OptionUtil.apply(judge).getClass()).isEqualTo(Some.class);        

    }

    // 要実装（Some型、件数、ID=6Lを確認）
    // ページ2に1件存在し、Idが6が存在している
    @Test
    public void test2FindWithPage2Contains6() throws Exception {

        //DBにデータ保存
        prepareSaveData();

        //ページ2から取り出し
        Option<List<Check>> listOpt =  new CheckModelService().findWithPage(2);
        List<Check> list = listOpt.get();

        String judge = null;
        System.out.println("list->list.size:"+list.size());
        for(Check check: list){
            System.out.println("list->check.id:"+check.id);
            if(check.id == 6L)
                judge = "6があります!";
        }

        assertThat(OptionUtil.apply(judge).getClass()).isEqualTo(Some.class);     

    }

    // ページ3は存在しない
    @Test
    public void testFindWithPage3ContainsNone() throws Exception {

        //DBにデータ保存
        prepareSaveData();
        
        //ページ3から取り出し
        Option<List<Check>> listOpt =  new CheckModelService().findWithPage(3);
        List<Check> list = listOpt.get();

        String judge = null;        
        System.out.println("list->list.size:"+list.size());
        if(list.size() != 0){
            judge = "ページが存在します";
        }
        assertThat(OptionUtil.apply(judge).getClass()).isEqualTo(None.class);             

    }

    // 1件しかデータがない場合、MaxPageは1（Option型、ページ数確認）
    @Test
    public void testFindData1Maxpage1() throws Exception {  

        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_a',  'test_aさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:11', '2013-08-20 12:34:56');"));

        Integer maxPage =  new CheckModelService().getMaxPage().getOrElse(0);          
        assertThat(maxPage).isEqualTo(1);             

    }   

    // 0件しかデータがない場合、MaxPageは0（Option型、ページ数確認）
    @Test
    public void testFindData0Maxpage0() throws Exception {        

        Integer maxPage =  new CheckModelService().getMaxPage().getOrElse(0);          
        assertThat(maxPage).isEqualTo(0);             
    }       

    // 6件データがある場合はMaxPageは2（Option型、ページ数確認） 
    @Test
    public void testFindData6Maxpage2() throws Exception {        

        prepareSaveData();
        Integer maxPage =  new CheckModelService().getMaxPage().getOrElse(0);          
        assertThat(maxPage).isEqualTo(2);            
    }          

    // 6件のデータを作るメソッド
    private void prepareSaveData() {
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_a',  'test_aさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:11', '2013-08-20 12:34:56');"));
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('2',  'test_b',  'test_bさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:12', '2013-08-20 12:34:56');"));
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('3',  'test_c',  'test_cさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:13', '2013-08-20 12:34:56');"));
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('4',  'test_d',  'test_dさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:14', '2013-08-20 12:34:56');"));
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('5',  'test_e',  'test_eさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:15', '2013-08-20 12:34:56');"));
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('6',  'test_f',  'test_fさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:16', '2013-08-20 12:34:56');"));
    }    
}