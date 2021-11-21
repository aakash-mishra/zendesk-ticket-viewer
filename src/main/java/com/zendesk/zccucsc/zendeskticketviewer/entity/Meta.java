package com.zendesk.zccucsc.zendeskticketviewer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    private Boolean has_more;
    private String after_cursor;
    private String before_cursor;
}
