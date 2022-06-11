package com.example.SOMusic.domain;

import java.util.Date;
import lombok.*;

@Getter @Setter
public class Reply {

	private int replyId;
	private Date replyDate;
	private String replyContent;
	private Integer productId;
	private String userId;
}
