package com.nsd.recruitment.listener;
import com.nsd.recruitment.annotation.AutoIncKey;
import com.nsd.recruitment.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SaveEventListener extends AbstractMongoEventListener<ChatMessage> {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void onBeforeConvert(BeforeConvertEvent<ChatMessage> event) {

        final Object source = event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
                @Override
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    ReflectionUtils.makeAccessible(field);
                    if (field.isAnnotationPresent(AutoIncKey.class)) {//判断字段是否被自定义注解标识
                        field.set(source, CreateProjectCode.getInstance().createProjectCode(mongoTemplate));//设置id
                    }
                }
            });

        }
    }
}
    @Component
    class CreateProjectCode {

        private static CreateProjectCode createProjectCode = null;
        private CreateProjectCode(){}

        static synchronized CreateProjectCode getInstance(){
            if(createProjectCode == null){
                synchronized (CreateProjectCode.class) {
                    if(createProjectCode == null){
                        createProjectCode = new CreateProjectCode();
                    }
                }
            }
            return createProjectCode;
        }

        synchronized String createProjectCode(MongoTemplate template){
            LocalDate date = LocalDate.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
            String dateStr = date.format(fmt);
            Query query = new Query(Criteria.where("code").regex(dateStr+"\\d*")).with(new Sort(Sort.Direction.DESC,"code"));
            List<ChatMessage> list = template.find(query, ChatMessage.class);
            if (list != null && !list.isEmpty()) {
                String numStr = list.get(0).getCode().substring(8);
                Integer numInteger = new Integer(numStr);
                int num = numInteger.intValue();
                return dateStr+( ++num<10 ? "0"+num : num );
            }else{
                return dateStr+"01";
            }
        }
    }


