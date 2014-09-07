package pl.jug.torun.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.repository.DrawRepository;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DrawControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DrawController drawController;

    @Autowired
    private DrawRepository drawRepository;

    private String json = "{\"event_id\":\"201836452\",\"prizes\":[{\"id\":\"1\",\"name\":\"test1\",\"amount\":2},{\"id\":\"2\",\"name\":\"test2\",\"amount\":1}],\"participants\":[{\"member_id\":\"143164022\",\"name\":\"Adam Ch.\",\"id\":1},{\"member_id\":\"53449422\",\"name\":\"Artur JabÅ\u0082oÅ\u0084ski\",\"id\":2},{\"member_id\":\"162605742\",\"name\":\"Damian Kurpiewski\",\"id\":3},{\"member_id\":\"139095322\",\"name\":\"Dariusz Delman\",\"id\":4},{\"member_id\":\"154125082\",\"name\":\"Jakub KorociÅ\u0084ski\",\"id\":5},{\"member_id\":\"162523772\",\"name\":\"justyna\",\"id\":6},{\"member_id\":\"147676592\",\"name\":\"Krzysztof PoboÅ¼an\",\"id\":7},{\"member_id\":\"139207922\",\"name\":\"Å\u0081ukasz Bojarski\",\"id\":8},{\"member_id\":\"162959452\",\"name\":\"Maciej Kuropatwa\",\"id\":9},{\"member_id\":\"138802742\",\"name\":\"Marek Nowicki\",\"id\":10},{\"member_id\":\"137051132\",\"name\":\"MichaÅ\u0082 Gruca\",\"id\":11},{\"member_id\":\"150577122\",\"name\":\"MichaÅ\u0082 Karykowski\",\"id\":12},{\"member_id\":\"139092952\",\"name\":\"PaweÅ\u0082 Rekowski\",\"id\":13},{\"member_id\":\"138741592\",\"name\":\"Piotr PrÄ\u0085dzyÅ\u0084ski\",\"id\":14},{\"member_id\":\"140030012\",\"name\":\"PrzemysÅ\u0082aw ZajÄ\u0085c\",\"id\":15},{\"member_id\":\"24693032\",\"name\":\"Rafal Sadkowski\",\"id\":16},{\"member_id\":\"130036542\",\"name\":\"Szymon StÄ\u0099pniak\",\"id\":17},{\"member_id\":\"139095562\",\"name\":\"Tomasz WiÅ\u009Bniewski\",\"id\":18},{\"member_id\":\"139091762\",\"name\":\"Zbyszko Papierski\",\"id\":19}]}";

    @Test
    public void shouldCreateTwoDraws() {
        drawController.startDraw(json);
        drawController.startDraw(json);

        assertEquals(2, drawRepository.count());
    }
}