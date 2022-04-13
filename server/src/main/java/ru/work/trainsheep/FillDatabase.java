package ru.work.trainsheep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.work.trainsheep.service.NotesService;

import java.util.List;

@Component
public class FillDatabase implements InitializingBean {

    @Autowired
    NotesService notes;
    DataGenerator generator = new DataGenerator();

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void afterPropertiesSet() {
        int k = 100;
        for (int i = 0; i < k; i++) {
            notes.save(generator.generateVacancyNote(List.of()));
        }

        logger.info("create " + k +" notes. OK!");

//        notes.findAll().forEach((ent) -> {
//            System.out.println(ent);
//            System.out.print("    ");
//            for(Tag tag : ent.getTags()){
//                System.out.print(tag + " ");
//            }
//            System.out.println();
//        });
    }
}
