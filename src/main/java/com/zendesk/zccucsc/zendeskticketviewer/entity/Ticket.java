package com.zendesk.zccucsc.zendeskticketviewer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket{
    public String url;
    public int id;
    public Date created_at;
    public Date updated_at;
    public String type;
    public String subject;
    public String description;
    public String priority;
    public String status;
    public Object recipient;
    public long requester_id;
    public long submitter_id;
    public long assignee_id;
}
