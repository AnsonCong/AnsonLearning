package service;

import com.demo.AnnotationMain;
import com.demo.service.GetAnimalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Anson
 * @date 2019/5/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnnotationMain.class)
@ActiveProfiles("Dog")
public class GetAnimalServiceTest {

    @Autowired
    private GetAnimalService getAnimalService;

    @Test
    public void testAnimal(){
        getAnimalService.eat();
    }
}
