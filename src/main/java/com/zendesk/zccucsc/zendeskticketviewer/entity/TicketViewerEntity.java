package com.zendesk.zccucsc.zendeskticketviewer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketViewerEntity {
    Meta meta;
    Links links;
    List<Ticket> tickets;
}
