package com.zendesk.zccucsc.zendeskticketviewer.service;

import com.zendesk.zccucsc.zendeskticketviewer.entity.Ticket;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.gateway.TicketViewerGateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zendesk.zccucsc.zendeskticketviewer.config.Constants;

@Component
public class TicketViewerServiceImpl implements TicketViewerService {
    @Autowired
    private TicketViewerGateway ticketViewerGateway;
    private static final Logger LOG = LogManager.getLogger(TicketViewerServiceImpl.class);
    private final String BASE_CLASS = "TicketViewerServiceImpl";

    @Override
    public TicketViewerEntity getAllTickets() {
        LOG.info("Entered " + " " + BASE_CLASS + "-> getAllTickets");
        TicketViewerEntity ticketViewerEntity = ticketViewerGateway.getTickets(Constants.PAGE_SIZE);
        return ticketViewerEntity;
    }
    @Override
    public TicketDetailEntity getTicketById(String ticketId) {
        LOG.info("Entered " + " " + BASE_CLASS + "-> getTicketById");
        TicketDetailEntity ticketViewerEntity = ticketViewerGateway.getTicketById(ticketId);
        return ticketViewerEntity;
    }
}
