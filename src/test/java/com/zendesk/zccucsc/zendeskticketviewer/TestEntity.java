package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.entity.*;

import java.util.ArrayList;
import java.util.List;

public class TestEntity {
    public static TicketViewerEntity getMockTicketViewerEntity() {
        TicketViewerEntity ticketViewerEntity = new TicketViewerEntity();
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(getMockTicket());
        ticketViewerEntity.setTickets(ticketList);
        Meta meta = new Meta();
        meta.setHas_more(true);
        meta.setAfter_cursor("SampleAfterCursor");
        ticketViewerEntity.setMeta(meta);
        Links links = new Links();
        links.setNext("sample next url");
        ticketViewerEntity.setLinks(links);
        return ticketViewerEntity;
    }

    public static TicketDetailEntity getMockTicketDetailEntity() {
        TicketDetailEntity ticketDetailEntity = new TicketDetailEntity();
        ticketDetailEntity.setTicket(getMockTicket());
        return ticketDetailEntity;
    }

    private static Ticket getMockTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setSubject("Sample Subject");
        ticket.setDescription("Sample description");
        return ticket;
    }
}
