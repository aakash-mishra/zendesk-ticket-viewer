package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.service.TicketViewerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CLIRunnerTest {
    @Mock
    TicketViewerService ticketViewerService;
    @InjectMocks
    CLIRunner cliRunner;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testCli_input_1_3() throws Exception{
        String[] args = {""};
        String input = "1\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(TestEntity.getMockTicketViewerEntity());
        assertThrows(Exception.class, () -> {cliRunner.run(args);},
                "CLI Runner should throw an exception when user exits the app");
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
    }

    @Test
    public void testCli_input_1_3_lastPage() throws Exception{
        String[] args = {""};
        String input = "1\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        TicketViewerEntity mockTicketViewerEntity = TestEntity.getMockTicketViewerEntity();
        //when this is the last page of tickets
        mockTicketViewerEntity.getMeta().setHas_more(false);

        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(mockTicketViewerEntity);
        assertThrows(Exception.class, () -> {cliRunner.run(args);},
                "CLI Runner should throw an exception when user exits the app");
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        assertTrue(outputStreamCaptor.toString().contains("No more tickets to display. Select '1' to go back to the first page"));
    }

    @Test
    public void testCli_input_1_2_3() throws Exception {
        String[] args = {""};
        String input = "1\n2\n1\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(TestEntity.getMockTicketViewerEntity());
        Mockito.when(ticketViewerService.getTicketById(anyString()))
                .thenReturn(TestEntity.getMockTicketDetailEntity());
        assertThrows(Exception.class, () -> {cliRunner.run(args);},
                "CLI Runner should throw an exception when user exits the app");
        Mockito.verify(ticketViewerService, Mockito.times(1)).getAllTickets();
        Mockito.verify(ticketViewerService, Mockito.times(1)).getTicketById("1");
    }

    @Test
    public void testCli_1_menu_3() throws Exception {
        String[] args = {""};
        String input = "1\nmenu\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(TestEntity.getMockTicketViewerEntity());
        assertThrows(Exception.class, () -> {cliRunner.run(args);},
                "CLI Runner should throw an exception when user exits the app");
        verify(ticketViewerService, times(1)).getAllTickets();
    }

    @Test
    public void testCli_1_menu_invalidEntry() throws Exception {
        String[] args = {""};
        String input = "1\nmenu\n5\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(TestEntity.getMockTicketViewerEntity());
        assertThrows(Exception.class, () -> {cliRunner.run(args);},
                "CLI Runner should throw an exception when user exits the app");
        verify(ticketViewerService, times(1)).getAllTickets();
        verify(ticketViewerService, times(0)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Please input a valid choice"));
    }

    @Test
    public void testCli_1_apiUnavailable() throws Exception {
        String[] args = {""};
        String input = "1\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(null);
        assertThrows(Exception.class, () -> {cliRunner.run(args);},
                "CLI Runner should throw an exception when user exits the app");
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        verify(ticketViewerService, times(0)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Please try again"));
    }

    @Test
    public void testCli_1_2_apiUnavailable() throws Exception {
        String[] args = {""};
        String input = "1\n2\n1\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(TestEntity.getMockTicketViewerEntity());
        Mockito.when(ticketViewerService.getTicketById(anyString()))
                .thenReturn(null);
        assertThrows(Exception.class, () -> {cliRunner.run(args);},
                "CLI Runner should throw an exception when user exits the app");
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        Mockito.verify(ticketViewerService, times(1)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Please try again"));
    }
}
