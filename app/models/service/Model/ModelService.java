package models.service.Model;

import java.util.*;

import javax.persistence.*;
import play.db.ebean.*;

import play.data.format.*;
import com.avaje.ebean.annotation.*;
import play.data.validation.Constraints.*;

import utils.OptionUtil;
import play.libs.F.*;

// モデル向けサービスのインターフェース
public interface ModelService<T extends Model> {

	//メソッド
    public Option<T> findById(Long id);
    public Option<T> save(T entry);
    public Option<List<T>> findWithPage(Integer pageSource);


}