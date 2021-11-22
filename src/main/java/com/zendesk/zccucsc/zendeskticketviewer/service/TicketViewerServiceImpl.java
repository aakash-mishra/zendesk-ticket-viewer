package com.zendesk.zccucsc.zendeskticketviewer.service;

import com.zendesk.zccucsc.zendeskticketviewer.config.Constants;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.gateway.TicketViewerGateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.zendesk.zccucsc.zendeskticketviewer.config.Constants.TICKETS_BASE_URL;

@Component
public class TicketViewerServiceImpl implements TicketViewerService {
    @Autowired
    private TicketViewerGateway ticketViewerGateway;
    private static final Logger LOG = LogManager.getLogger(TicketViewerServiceImpl.class);
    private final String BASE_CLASS = "TicketViewerServiceImpl";
    String nextUrl = TICKETS_BASE_URL + "?page[size]=" + Constants.PAGE_SIZE;

    @Override
    public TicketViewerEntity getAllTickets() {
        LOG.info("Entered " + " " + BASE_CLASS + "-> getAllTickets");
        TicketViewerEntity ticketViewerEntity = ticketViewerGateway.getTickets(nextUrl);
        if(ticketViewerEntity != null && ticketViewerEntity.getMeta().getHas_more())
            nextUrl = TICKETS_BASE_URL + "?page[size]=" + Constants.PAGE_SIZE + "&page[after]=" + ticketViewerEntity.getMeta().getAfter_cursor();
        else if (ticketViewerEntity != null && !ticketViewerEntity.getMeta().getHas_more()) {
            LOG.info("No more results to display. Setting cursor back to first page");
            nextUrl = TICKETS_BASE_URL + "?page[size]=" + Constants.PAGE_SIZE;
        }
        return ticketViewerEntity;
    }
    @Override
    public TicketDetailEntity getTicketById(String ticketId) {
        LOG.info("Entered " + " " + BASE_CLASS + "-> getTicketById");
        TicketDetailEntity ticketViewerEntity = ticketViewerGateway.getTicketById(ticketId);
        return ticketViewerEntity;
    }

    //to test if nextUrl is being set correctly
    public String getNextUrl() {
        return this.nextUrl;
    }
}
