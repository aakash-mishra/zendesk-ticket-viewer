package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.gateway.TicketViewerGateway;
import com.zendesk.zccucsc.zendeskticketviewer.service.TicketViewerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.zendesk.zccucsc.zendeskticketviewer.TestEntity.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
class TicketViewerServiceTest {
    @Mock
    TicketViewerGateway ticketViewerGateway;
    @InjectMocks
    TicketViewerServiceImpl ticketViewerService;

    @Test
    public void getAllTickets_nullResponse() {
        Mockito.when(ticketViewerGateway.getTickets(anyString())).thenReturn(null);
        TicketViewerEntity actualResponse = ticketViewerService.getAllTickets();
        assertNull(actualResponse);
    }

    @Test
    public void getAllTickets_hasMore_true() {
        Mockito.when(ticketViewerGateway.getTickets(anyString())).thenReturn(getMockTicketViewerEntity());
        TicketViewerEntity actualResponse = ticketViewerService.getAllTickets();
        assertEquals("https://zccucsc.zendesk.com/api/v2/tickets?page[size]=25&page[after]=SampleAfterCursor", ticketViewerService.getNextUrl());
        assertEquals(1, actualResponse.getTickets().get(0).getId());
    }

    @Test
    public void getAllTickets_hasMore_false() {
        TicketViewerEntity mockTicketViewerEntity = getMockTicketViewerEntity();
        mockTicketViewerEntity.getMeta().setHas_more(false);
        Mockito.when(ticketViewerGateway.getTickets(anyString())).thenReturn(mockTicketViewerEntity);
        TicketViewerEntity actualResponse = ticketViewerService.getAllTickets();
        assertEquals("https://zccucsc.zendesk.com/api/v2/tickets?page[size]=25", ticketViewerService.getNextUrl());
        assertEquals(1, actualResponse.getTickets().get(0).getId());
    }

    @Test
    public void getTicketById_ok() {
        Mockito.when(ticketViewerGateway.getTicketById(anyString())).thenReturn(getMockTicketDetailEntity());
        TicketDetailEntity actualResponse = ticketViewerService.getTicketById("1");
        assertEquals("Sample Subject", actualResponse.getTicket().getSubject());
    }

    @Test
    public void getTicketById_null() {
        Mockito.when(ticketViewerGateway.getTicketById(anyString())).thenReturn(null);
        TicketDetailEntity actualResponse = ticketViewerService.getTicketById("1");
        assertNull(actualResponse);
    }
}
