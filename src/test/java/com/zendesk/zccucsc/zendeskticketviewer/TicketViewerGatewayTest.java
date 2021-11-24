package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.config.Util;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.gateway.TicketViewerGatewayImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static com.zendesk.zccucsc.zendeskticketviewer.TestEntity.getMockTicketDetailEntity;
import static com.zendesk.zccucsc.zendeskticketviewer.TestEntity.getMockTicketViewerEntity;
import static com.zendesk.zccucsc.zendeskticketviewer.config.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TicketViewerGatewayTest {
    @Mock
    RestTemplate restTemplate;
    @Mock
    Util util;
    @InjectMocks
    TicketViewerGatewayImpl ticketViewerGateway;

    @Test
    public void testGetTickets_ok() {
        ResponseEntity<TicketViewerEntity> responseEntity = new ResponseEntity(getMockTicketViewerEntity(), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                            ArgumentMatchers.any(HttpMethod.class),
                                            ArgumentMatchers.any(),
                                            ArgumentMatchers.<Class<TicketViewerEntity>>any())).thenReturn(responseEntity);

         TicketViewerEntity actualResponse = ticketViewerGateway.getTickets("sample");
         assertEquals("Sample Subject", actualResponse.getTickets().get(0).subject);
         //test if restTemplate.exchange was called - and that it was called exactly once.
         Mockito.verify(restTemplate, Mockito.times(1))
                 .exchange(ArgumentMatchers.anyString(),
                         ArgumentMatchers.any(HttpMethod.class),
                         ArgumentMatchers.any(),
                         ArgumentMatchers.<Class<TicketViewerEntity>>any());
    }

    @Test
    public void testGetTickets_null() {
        ResponseEntity<TicketViewerEntity> responseEntity = new ResponseEntity(getMockTicketViewerEntity(), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<TicketViewerEntity>>any())).thenReturn(null);

        TicketViewerEntity actualResponse = ticketViewerGateway.getTickets("sample");
        assertNull(actualResponse);
        //test if restTemplate.exchange was called - and that it was called exactly once.
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<Class<TicketViewerEntity>>any());
    }

    @Test
    public void testGetTicketById_ok() {
        ResponseEntity<TicketDetailEntity> responseEntity = new ResponseEntity(getMockTicketDetailEntity(), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<TicketDetailEntity>>any())).thenReturn(responseEntity);

        TicketDetailEntity actualResponse = ticketViewerGateway.getTicketById("sample");
        assertEquals("Sample Subject", actualResponse.getTicket().getSubject());
        //test if restTemplate.exchange was called - and that it was called exactly once.
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<Class<TicketDetailEntity>>any());
    }

    @Test
    public void testGetTicketById_null() {
        ResponseEntity<TicketDetailEntity> responseEntity = new ResponseEntity(getMockTicketDetailEntity(), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<TicketDetailEntity>>any())).thenReturn(null);

        TicketDetailEntity actualResponse = ticketViewerGateway.getTicketById("sample");
        assertNull(actualResponse);
        //test if restTemplate.exchange was called - and that it was called exactly once.
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<Class<TicketDetailEntity>>any());
    }

}
