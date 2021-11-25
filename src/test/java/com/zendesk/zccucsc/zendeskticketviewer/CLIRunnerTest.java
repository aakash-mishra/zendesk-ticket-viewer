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
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static com.zendesk.zccucsc.zendeskticketviewer.TestEntity.getMockTicketViewerEntity;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        matcherForGetAllTickets();
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        assertTrue(outputStreamCaptor.toString().contains("Thank you for using Zendesk Ticket Viewer."));
    }

    @Test
    public void testCli_input_1_3_emptyTicketList() throws Exception{
        String[] args = {""};
        String input = "1\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        TicketViewerEntity mockTicketViewerEntity = getMockTicketViewerEntity();
        mockTicketViewerEntity.setTickets(new ArrayList<>());
        when(ticketViewerService.getAllTickets()).thenReturn(mockTicketViewerEntity);
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        assertTrue(outputStreamCaptor.toString().contains("No tickets were returned by the server."));
    }



    @Test
    public void testCli_input_1_3_lastPage() throws Exception{
        String[] args = {""};
        String input = "1\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        TicketViewerEntity mockTicketViewerEntity = TestEntity.getMockTicketViewerEntity();
        //when this is the last page of tickets
        mockTicketViewerEntity.getMeta().setHas_more(false);
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(mockTicketViewerEntity);
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        assertTrue(outputStreamCaptor.toString().contains("No more tickets to display. Select '1' to go back to the first page"));
    }

    @Test
    public void testCli_input_1_2_3() throws Exception {
        String[] args = {""};
        String input = "1\n2\n1\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        matcherForGetAllTickets();
        matcherForGetTicketById();
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, Mockito.times(1)).getAllTickets();
        Mockito.verify(ticketViewerService, Mockito.times(1)).getTicketById("1");
    }

    @Test
    public void testCli_input_1_2_3_illFormattedTicketId() throws Exception {
        String[] args = {""};
        String input = "1\n2\nABCD\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        matcherForGetAllTickets();
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, Mockito.times(1)).getAllTickets();
        //making sure that random strings are not sent to server
        Mockito.verify(ticketViewerService, Mockito.times(0)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Please enter a valid ticket id."));
    }

    @Test
    public void testCli_1_menu_3() throws Exception {
        String[] args = {""};
        String input = "1\nmenu\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        matcherForGetAllTickets();
        cliRunner.run(args);
        verify(ticketViewerService, times(1)).getAllTickets();
    }

    @Test
    public void testCli_1_menu_invalidEntry() throws Exception {
        String[] args = {""};
        String input = "1\nmenu\n5\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        matcherForGetAllTickets();
        cliRunner.run(args);
        verify(ticketViewerService, times(1)).getAllTickets();
        verify(ticketViewerService, times(0)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Please input a valid choice"));
    }

    @Test
    public void testCli_1_clientError() throws Exception {
        String[] args = {""};
        String input = "1\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Mockito.when(ticketViewerService.getAllTickets()).
                thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        verify(ticketViewerService, times(0)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Thank you for using Zendesk Ticket Viewer."));
    }

    @Test
    public void testCli_1_serverError() throws Exception {
        String[] args = {""};
        String input = "1\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Mockito.when(ticketViewerService.getAllTickets()).
                thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        verify(ticketViewerService, times(0)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Thank you for using Zendesk Ticket Viewer."));
    }

    @Test
    public void testCli_1_2_invalidTicketId() throws Exception {
        String[] args = {""};
        String input = "1\n2\n255\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        matcherForGetAllTickets();
        Mockito.when(ticketViewerService.getTicketById(anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        Mockito.verify(ticketViewerService, times(1)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("The ticket ID you've entered is invalid or no longer exists"));
    }

    @Test
    public void testCli_1_2_serverError() throws Exception {
        String[] args = {""};
        String input = "1\n2\n255\n3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        matcherForGetAllTickets();
        Mockito.when(ticketViewerService.getTicketById(anyString()))
                .thenThrow(new HttpServerErrorException(HttpStatus.NOT_FOUND));
        cliRunner.run(args);
        Mockito.verify(ticketViewerService, times(1)).getAllTickets();
        Mockito.verify(ticketViewerService, times(1)).getTicketById(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Thank you for using Zendesk Ticket Viewer."));
    }

    public void matcherForGetAllTickets() {
        Mockito.when(ticketViewerService.getAllTickets()).
                thenReturn(TestEntity.getMockTicketViewerEntity());
    }

    public void matcherForGetTicketById() {
        Mockito.when(ticketViewerService.getTicketById(anyString()))
                .thenReturn(TestEntity.getMockTicketDetailEntity());
    }
}
