package models.request.Check;

import java.util.*;

import com.avaje.ebean.annotation.*;
import play.data.validation.Constraints.*;

// 診断リクエストのフォームモデル
// フォームから送信されるnameを取り出す
public class ResultPostRequest {

    @Required
    @Pattern("[\\w]+")  // a~z or A~Z or  _ or 1~9と同意
    @MaxLength(15)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}