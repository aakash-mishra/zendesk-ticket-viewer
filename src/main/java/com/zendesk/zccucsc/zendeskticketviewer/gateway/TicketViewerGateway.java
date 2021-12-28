package com.zendesk.zccucsc.zendeskticketviewer.gateway;

import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;

public interface TicketViewerGateway {
    TicketViewerEntity getTickets(String url);
    TicketDetailEntity getTicketById(String ticketId);
}
