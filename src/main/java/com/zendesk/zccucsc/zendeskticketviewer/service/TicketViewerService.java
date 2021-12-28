package com.zendesk.zccucsc.zendeskticketviewer.service;

import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;

public interface TicketViewerService {
    TicketViewerEntity getAllTickets();
    TicketDetailEntity getTicketById(String ticketId);

}
